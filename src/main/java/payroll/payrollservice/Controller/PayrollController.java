package payroll.payrollservice.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import payroll.payrollservice.CustomException.CustomBadRequestException;
import payroll.payrollservice.CustomException.CustomInternalServerErrorException;
import payroll.payrollservice.CustomException.CustomNotFoundException;
import payroll.payrollservice.DataAccessObjects.PayrollDAO;
import payroll.payrollservice.Model.*;
import payroll.payrollservice.Repository.*;

import javax.transaction.Transactional;
import java.util.*;

@RestController
@RequestMapping( value = "api/payroll/")

public class PayrollController {

    Double totalAllowance= 0.0;
    Double allowanceIncludeInGrossSalary = 0.0;
    Double allowanceNonTaxable = 0.0;
    Double allowanceAddedToNetSalary = 0.0;
    Double allowanceTaxPercent = 0.0;
    Double totalSalary = 0.0;
    Double taxRatePercent = 0.0;
    Double incomeTax = 0.0;
    Double grossSalary = 0.0;
    TaxRate taxRate = null;
    Double taxableAllowance = 0.0;
    Double netIncome = 0.0;

    @Autowired
    DeductionRepository deductionRepository;

    @Autowired
    TaxRateRepository taxRateRepository;

    @Autowired
    AllowanceRepository allowanceRepository;

    @Autowired
    PayrollRepository payrollRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    Map<String, String> response;

    public PayrollController(){
           this.response = new HashMap<>();
    }

    @GetMapping( value = "getAllPayrolls" , produces = "application/json")
    public List<Payroll>  getAllPayrolls(){

        return payrollRepository.findAll();
    }

    @Transactional
    @PostMapping( value = "addPayroll")
    public ResponseEntity<?> addPayroll(@RequestBody   PayrollDAO payrollDAO) throws
            CustomNotFoundException {


            grossSalary = payrollDAO.getPayroll().getGrossSalary();

            System.out.println("grosssalary \t" + grossSalary);

            Employee employee = employeeRepository.findById(payrollDAO.getEmployeeId()).orElseThrow(
                    () -> new CustomNotFoundException("Employee Not Found")
            );

            List<TaxRate> taxRates = taxRateRepository.findAll();


            List<Long> allowanceIds = payrollDAO.getAllowanceIds();
            List<Allowance> allowances = new ArrayList<>();
            allowanceIds.forEach((id) -> allowances.add(allowanceRepository.findById(id).get()));

            calculateAllowance(allowances);

            taxRate = new TaxRate();

            taxRate = getTaxRateDetail(grossSalary, taxRates);

            taxRatePercent = convertPercentToNumber(taxRate.getTaxRatePercent());
            System.out.println("taxrate percent  \t" + taxRatePercent);

            incomeTax = calculateIncomeTax(grossSalary, taxRatePercent, taxRate.getDeduction());
            System.out.println("income tax \t" + incomeTax);

            List<Double> deductedAmounts = calculateDeduction(payrollDAO.getDeductionIds(), grossSalary);

            List<Deduction> deductions = getDeduction(payrollDAO.getDeductionIds());


            netIncome = calculateNetIncome(grossSalary, incomeTax, deductedAmounts);

            System.out.println("allowance non taxable \t" + allowanceNonTaxable);
            System.out.println("allowance taxable \t" + allowanceAddedToNetSalary);
            System.out.println("taxable allowance that added to gross \t" + taxableAllowance);
            totalAllowance = allowanceAddedToNetSalary + allowanceNonTaxable;

            totalSalary = (netIncome + totalAllowance);

            System.out.println("net salary \t" + netIncome);
            System.out.println("net Salary + Partial allowance + non taxable allowance\t" + totalSalary);
            System.out.println("total amount \t" + totalSalary);


            Payroll payroll = payrollDAO.getPayroll();
            payroll.setGrossSalary(grossSalary);
            payroll.setTaxRate(taxRate);
            payroll.setEmployee(employee);
            payroll.setIncomeTax(incomeTax);
            payroll.setDeduction(deductions);
            payroll.setNetSalary(netIncome);
            payroll.setAllowance(allowances);
            payroll.setTotalAllowance(totalAllowance);
            payroll.setTotalAmount(totalSalary);

            payrollRepository.save(payroll);

            setClassVariableToDefault();

            response.put("message", "paryroll Successfully inserted");



      return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @PutMapping( value = "updatePayroll/{payrollId}")
    public ResponseEntity<?> updatePayroll ( @PathVariable( value = "payrollId") Long payrollId , @RequestBody PayrollDAO payrollDAO)
    throws CustomNotFoundException{

        Payroll oldPayroll = payrollRepository.findById(payrollId).orElseThrow(
                () -> new CustomNotFoundException("Payroll Not Found ")
        );

         grossSalary = payrollDAO.getPayroll().getGrossSalary();

        System.out.println("grosssalary \t" + grossSalary);

        Employee employee = employeeRepository.findById(payrollDAO.getEmployeeId()).orElseThrow(
                ()-> new CustomNotFoundException("Employee Not Found")
        );

        List<TaxRate> taxRates = taxRateRepository.findAll();


        List<Long>  allowanceIds = payrollDAO.getAllowanceIds();
        List<Allowance> allowances = new ArrayList<>();

        allowanceIds.forEach( (id) -> allowances.add(allowanceRepository.findById(id).get()));

        calculateAllowance(allowances);

         taxRate = new TaxRate();
         taxRate = getTaxRateDetail(grossSalary , taxRates);

        taxRatePercent =  convertPercentToNumber(taxRate.getTaxRatePercent());
        System.out.println("taxrate percent \t" + taxRatePercent);

        incomeTax =  calculateIncomeTax(grossSalary , taxRatePercent ,taxRate.getDeduction() );

        System.out.println("income tax \t" + incomeTax);

        List<Double> deductedAmounts =  calculateDeduction(payrollDAO.getDeductionIds() , grossSalary );

        List<Deduction> deductions = getDeduction(payrollDAO.getDeductionIds());



         netIncome = calculateNetIncome ( grossSalary , incomeTax , deductedAmounts ) ;

        System.out.println("allowance non taxable \t" + allowanceNonTaxable);
        System.out.println("allowance taxable \t" + allowanceAddedToNetSalary);
        System.out.println("taxable allowance  that added to gross\t" + taxableAllowance );

        totalAllowance = allowanceAddedToNetSalary + allowanceNonTaxable;

        totalSalary = (netIncome + totalAllowance);

        System.out.println("net salary \t" + netIncome);
        System.out.println("net Salary + Partial allowance + non taxable allowance = net salar \t" + totalSalary);
        System.out.println("total salary \t" + totalSalary );

        Payroll newPayroll = payrollDAO.getPayroll();
        oldPayroll.setGrossSalary(newPayroll.getGrossSalary());
        oldPayroll.setEmployee(employee);
        oldPayroll.setIncomeTax(incomeTax);
        oldPayroll.setTaxRate(taxRate);
        oldPayroll.setDeduction(deductions);
        oldPayroll.setNetSalary(netIncome);
        oldPayroll.setAllowance(allowances);
        oldPayroll.setTotalAllowance(totalAllowance);
        oldPayroll.setTotalAmount(totalSalary);


        payrollRepository.save(oldPayroll);
          setClassVariableToDefault();

        response.put("message" , "Payroll Successfully Updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

@DeleteMapping( value ="deletePayroll/{payrollId}")
    public ResponseEntity<?> deletePayroll( @PathVariable( value =  "payrollId") Long payrollId) throws CustomNotFoundException{

        Payroll payroll = payrollRepository.findById(payrollId).orElseThrow(
                () -> new CustomNotFoundException("Payroll Not Found")
        );

        payrollRepository.delete(payroll);

        response.put("message", "Payroll Successfully Deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping( value = "getPayroll/{payrollId}")
    public Payroll getPayroll( @PathVariable( value = "payrollId") Long payrollId) throws  CustomNotFoundException{
        Payroll payroll = payrollRepository.findById(payrollId).orElseThrow(
                ()-> new CustomNotFoundException("Payroll Not Found")
        );

        return payroll;
    }




    public  Double calculateIncomeTax(Double grossSalary , Double taxRatePercent , Double deduction){

        return ((grossSalary * taxRatePercent) - deduction);
    }

    public  Double convertPercentToNumber( String percent){
        allowanceTaxPercent =(Double.parseDouble(
               percent.substring(0, percent.length() -1))/100);

        return allowanceTaxPercent;
    }

    public  List<Double> calculateDeduction(List<Long> deductionIds , Double grossSalary ){

        List<Deduction>  deductions =  getDeduction(deductionIds);

        List<Double> deductAmounts = new ArrayList<>();


        for( Deduction deduct : deductions){

             deductAmounts.add(grossSalary * convertPercentToNumber(deduct.getDeductionPercent()));
             System.out.println("deduct percents \t" +  convertPercentToNumber(deduct.getDeductionPercent()));
             System.out.println("deduct amounts \t" + (grossSalary * convertPercentToNumber(deduct.getDeductionPercent()) ));
        }

        return deductAmounts;
    }


    public List<Deduction> getDeduction( List<Long> deductionIds){

        List<Deduction>  deductions = new ArrayList<>();
        deductionIds.forEach(
                (id) -> deductions.add(deductionRepository.findById(id).get())
        );

        return deductions;
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
                allowanceTaxPercent =  convertPercentToNumber(allowances.get(i).getPercent());
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



}
