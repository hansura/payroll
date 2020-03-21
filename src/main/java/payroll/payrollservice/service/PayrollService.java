package payroll.payrollservice.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import payroll.payrollservice.dao.PayrollDAO;
import payroll.payrollservice.exception.ApplicationErrorMessage;
import payroll.payrollservice.exception.CustomNotFoundException;
import payroll.payrollservice.model.*;
import payroll.payrollservice.repository.*;
import payroll.payrollservice.util.Common;

import java.util.ArrayList;
import java.util.List;

@Service
public class PayrollService implements Common<Payroll,Payroll> {

    private PayrollRepository payrollRepository;
    private DeductionRepository deductionRepository;
    private TaxRateRepository taxRateRepository;
    private EmployeeRepository employeeRepository;
    private AllowanceRepository allowanceRepository;

    Double totalAllowance= 0.0;
    Double allowanceIncludeInGrossSalary = 0.0;
    Double allowanceNonTaxable = 0.0;
    Double allowanceAddedToNetSalary = 0.0;
    Double  percentToNumber = 0.0;
    Double allowanceTaxPercent = 0.0;
    Double totalSalary = 0.0;
    Double taxRatePercent = 0.0;
    Double incomeTax = 0.0;
    Double grossSalary = 0.0;
    TaxRate taxRate = null;
    Double taxableAllowance = 0.0;
    Double netIncome = 0.0;


    public PayrollService(PayrollRepository payrollRepository, DeductionRepository deductionRepository,
                          TaxRateRepository taxRateRepository, EmployeeRepository employeeRepository,
                          AllowanceRepository allowanceRepository) {
        this.payrollRepository = payrollRepository;
        this.deductionRepository = deductionRepository;
        this.taxRateRepository = taxRateRepository;
        this.employeeRepository = employeeRepository;
        this.allowanceRepository = allowanceRepository;
    }

    //Done
    @Override
    public Payroll store(Payroll payroll)  {


       return null;
    }

    @Override
    public Iterable<Payroll> store(List<Payroll> t) {
        return null;
    }


    //Done
    @Override
    public Payroll show(long id) {


        return  payrollRepository.findById(id).get();
    }

    @Override
    public Payroll update(long id, Payroll payroll) {





        return null;
    }

    @Override
    public boolean delete(long id) {

        Payroll payroll  = payrollRepository.findById(id).get();

         if(payroll != null ){
             payrollRepository.delete(payroll);
             return  true;
         }

        return false;
    }

    @Override
    public Iterable<Payroll> getAll(Pageable pageable, Sort sort) {
        return null;
    }

    //Done
    public Iterable<Payroll> getAll() {
        return payrollRepository.findAll();
    }




    public Payroll updatePayroll( Long id, PayrollDAO payrollDAO){

        Payroll oldPayroll = payrollRepository.findById(id).get();


        Employee employee = employeeRepository.findById(payrollDAO.getEmployeeId()).get();

        List<Long> allowanceIds = payrollDAO.getAllowanceIds();

        List<Allowance> allowances = new ArrayList<>();

        allowanceIds.forEach((ids) -> allowances.add(allowanceRepository.findById(ids).get()));
        List<Deduction> deductions = new ArrayList<>();
        payrollDAO.getAllowanceIds().forEach( (ids) -> deductions.add(deductionRepository.findById(ids).get()));


        calculateAllowance(allowances);

        taxRatePercent = (taxRate.getTaxRatePercent());

        incomeTax = calculateIncomeTax(grossSalary, taxRatePercent, taxRate.getDeduction());

        taxRate = getTaxRateDetail(grossSalary, taxRateRepository.findAll() );

        List<Double> deductedAmounts = calculateDeduction(deductions, grossSalary);
        System.out.println("allowance non taxable \t" + allowanceNonTaxable);
        System.out.println("allowance taxable \t" + allowanceAddedToNetSalary);
        System.out.println("taxable allowance that added to gross \t" + taxableAllowance);
        totalAllowance = allowanceAddedToNetSalary + allowanceNonTaxable;

        totalSalary = (netIncome + totalAllowance);

        System.out.println("net salary \t" + netIncome);
        System.out.println("net Salary + Partial allowance + non taxable allowance\t" + totalSalary);
        System.out.println("total amount \t" + totalSalary);


        netIncome = calculateNetIncome(grossSalary, incomeTax, deductedAmounts);

        oldPayroll.setGrossSalary(grossSalary);
        oldPayroll.setTaxRate(taxRate);
        oldPayroll.setIncomeTax(incomeTax);
        oldPayroll.setNetSalary(netIncome);
        oldPayroll.setTotalAllowance(totalAllowance);
        oldPayroll.setTotalAmount(totalSalary);
        oldPayroll.setAllowance(allowances);
        oldPayroll.setDeduction(deductions);
        oldPayroll.setEmployee(employee);


        return payrollRepository.save(oldPayroll);
    }

    public  Double calculateIncomeTax(Double grossSalary , Double taxRatePercent , Double deduction){

        return ((grossSalary * taxRatePercent) - deduction);
    }

//    public  Double convertPercentToNumber( Double percent)
//    {
//        percentToNumber =( percent/100);
//
//        return percentToNumber;
//    }

    public  List<Double> calculateDeduction(List<Deduction> deductions , Double grossSalary ){


        List<Double> deductAmounts = new ArrayList<>();


        for( Deduction deduct : deductions){

            deductAmounts.add(grossSalary * (deduct.getDeductionPercent()));
            System.out.println("deduct percents \t" +  (deduct.getDeductionPercent()));
            System.out.println("deduct amounts \t" + (grossSalary * (deduct.getDeductionPercent()) ));
        }

        return deductAmounts;
    }




    public  Double calculateNetIncome(Double grossSalary , Double incomeTax , List<Double> deductAmounts){

        for  ( Double deduction :  deductAmounts){

            grossSalary = grossSalary - deduction;
            System.out.println("Dedction \t "+ deduction + "\t gross salary become \t" + grossSalary);
        }

        return  (grossSalary - incomeTax);
    }


    public void calculateAllowance( List<Allowance> allowances){

        for (int i = 0 ; i < allowances.size(); i++){

            //for partial taxable
            if( allowances.get(i).isPartialTaxable() == true)
            {
                allowanceTaxPercent =  (allowances.get(i).getPercent());
                allowanceIncludeInGrossSalary = (allowances.get(i).getAmount() * allowanceTaxPercent );
                grossSalary = grossSalary + allowanceIncludeInGrossSalary;
                allowanceAddedToNetSalary = allowances.get(i).getAmount() - allowanceIncludeInGrossSalary;
                System.out.println("partial taxable allowance that will added to gross salary \t" + allowanceIncludeInGrossSalary);
                System.out.println("gross salary after allowance added  \t" + grossSalary );
                allowanceTaxPercent = 0.0;
            }
            //for non taxable
            if( allowances.get(i).isTaxable() != true && allowances.get(i).isPartialTaxable() != true){

                allowanceNonTaxable = allowanceNonTaxable + allowances.get(i).getAmount();
                System.out.println("non taxable allowance  < 600 \t" + allowanceNonTaxable);
                allowanceTaxPercent = 0.0;
            }
            if(allowances.get(i).isTaxable() == true )
            {
                taxableAllowance = allowances.get(i).getAmount();
                grossSalary = grossSalary + allowances.get(i).getAmount();
                System.out.println("taxable allowance => gross salary + allwoance \t" + grossSalary);
            }


        }


    }


    public TaxRate getTaxRateDetail(Double grossSalary , List<TaxRate> taxRates) {
        taxRates.sort( (a, b) -> a.getFromSalary().compareTo(b.getFromSalary()));

        for (TaxRate taxRate1 : taxRates)
            if (grossSalary >= taxRate1.getFromSalary() && grossSalary <= taxRate1.getUptoSalary()) {

                System.out.println("gross salary \t" + grossSalary + "\t fall in to \t" + taxRate1.getTaxRatePercent());
                return taxRate1;
            }

        System.out.println("the last tax rate is \t" + taxRates.get(taxRates.size() -1).getTaxRatePercent());

        return  taxRates.get(taxRates.size() -1);
    }

    public  void setClassVariableToDefault(){
        totalAllowance= 0.0;
        allowanceIncludeInGrossSalary = 0.0;
        allowanceNonTaxable = 0.0;
        allowanceAddedToNetSalary = 0.0;
        allowanceTaxPercent = 0.0;
        totalSalary = 0.0;
        taxRatePercent = 0.0;
    }


    public Payroll storePayroll(PayrollDAO payrollDAO) {
        //System.out.println("Deduction \t" + payrollDAO.getDeductionIds());
         grossSalary = payrollDAO.getPayroll().getGrossSalary();

        Employee employee = employeeRepository.findById(payrollDAO.getEmployeeId()).get();

        List<Long> allowanceIds = payrollDAO.getAllowanceIds();
        List<Allowance> allowances = new ArrayList<>();
        allowanceIds.forEach((id) -> allowances.add(allowanceRepository.findById(id).get()));

        List<Deduction>  deductions = new ArrayList<>();
        payrollDAO.getDeductionIds().forEach(
                (id) -> deductions.add(deductionRepository.findById(id).get())
        );


        Payroll payroll =  payrollDAO.getPayroll();
        payroll.setEmployee(employee);
        payroll.setDeduction(deductions);
        payroll.setAllowance(allowances);

        calculateAllowance(payroll.getAllowance());

        taxRate = getTaxRateDetail(grossSalary, taxRateRepository.findAll() );

        taxRatePercent = (taxRate.getTaxRatePercent());

        incomeTax = calculateIncomeTax(grossSalary, taxRatePercent, taxRate.getDeduction());


        List<Double> deductedAmounts = calculateDeduction(payroll.getDeduction(), grossSalary);
        System.out.println("allowance non taxable \t" + allowanceNonTaxable);
        System.out.println("allowance taxable \t" + allowanceAddedToNetSalary);
        System.out.println("taxable allowance that added to gross \t" + taxableAllowance);

        totalAllowance = allowanceAddedToNetSalary + allowanceNonTaxable;

        totalSalary = (netIncome + totalAllowance);

        System.out.println("net salary \t" + netIncome);
        System.out.println("net Salary + Partial allowance + non taxable allowance\t" + totalSalary);
        System.out.println("total amount \t" + totalSalary);


        netIncome = calculateNetIncome(grossSalary, incomeTax, deductedAmounts);


        payroll.setGrossSalary(grossSalary);
        payroll.setTaxRate(taxRate);
        payroll.setIncomeTax(incomeTax);
        payroll.setNetSalary(netIncome);
        payroll.setTotalAllowance(totalAllowance);
        payroll.setTotalAmount(totalSalary);

        //setClassVariableToDefault();

        return payrollRepository.save(payroll);

    }
}
