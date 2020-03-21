package payroll.payrollservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import payroll.payrollservice.dao.PayrollDAO;
import payroll.payrollservice.model.*;
import payroll.payrollservice.repository.*;
import payroll.payrollservice.util.Common;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PayrollService implements Common<Payroll, Payroll> {

    private PayrollRepository payrollRepository;
    private DeductionRepository deductionRepository;
    private TaxRateRepository taxRateRepository;
    private EmployeeRepository employeeRepository;
    private AllowanceRepository allowanceRepository;
    Double totalAllowance = 0.0;
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
    public Payroll store(Payroll payroll) {


        return null;
    }

    @Override
    public Iterable<Payroll> store(List<Payroll> t) {
        return null;
    }


    @Override
    public Payroll show(long id) {

        return payrollRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Payroll with id "+id+" not found"));
    }

    @Override
    public Payroll update(long id, Payroll payroll) {

        return null;
    }

    @Override
    public boolean delete(long id) {

        Payroll payroll = show(id);

        if (payroll != null) {
            payrollRepository.delete(payroll);
            return true;
        }

        return false;
    }

    @Override
    public Page<Payroll> getAll(Pageable pageable) {
        return payrollRepository.findAll(pageable);
    }

    //Done
    public Iterable<Payroll> getAll() {
        return payrollRepository.findAll();
    }


    public Payroll updatePayroll(Long id, PayrollDAO payrollDAO) {

        Payroll oldPayroll = payrollRepository.findById(id).get();


        Employee employee = employeeRepository.findById(payrollDAO.getEmployeeId()).get();

        List<Long> allowanceIds = payrollDAO.getAllowanceIds();

        List<Allowance> allowances = new ArrayList<>();

        allowanceIds.forEach((ids) -> allowances.add(allowanceRepository.findById(ids).get()));
        List<Deduction> deductions = new ArrayList<>();
        payrollDAO.getAllowanceIds().forEach((ids) -> deductions.add(deductionRepository.findById(ids).get()));


        calculateAllowance(allowances);

        taxRatePercent = taxRate.getPercent();

        incomeTax = calculateIncomeTax(grossSalary, taxRatePercent, taxRate.getDeduction());

        taxRate = getTaxRateDetail(grossSalary, taxRateRepository.findAll());

        List<Double> deductedAmounts = calculateDeduction(deductions, grossSalary);

        totalAllowance = allowanceAddedToNetSalary + allowanceNonTaxable;

        totalSalary = (netIncome + totalAllowance);


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

    public Double calculateIncomeTax(Double grossSalary, Double taxRatePercent, Double deduction) {

        return ((grossSalary * taxRatePercent) - deduction);
    }

    public Double convertPercentToNumber(String percent) {
        allowanceTaxPercent = (Double.parseDouble(
                percent.substring(0, percent.length() - 1)) / 100);

        return allowanceTaxPercent;
    }

    public List<Double> calculateDeduction(List<Deduction> deductions, Double grossSalary) {


        List<Double> deductAmounts = new ArrayList<>();


        for (Deduction deduct : deductions) {

            deductAmounts.add(grossSalary * deduct.getPercent());
        }

        return deductAmounts;
    }


    public List<Deduction> getDeduction(List<Long> deductionIds) {

        List<Deduction> deductions = new ArrayList<>();
        deductionIds.forEach(
                (id) -> deductions.add(deductionRepository.findById(id).get())
        );

        return deductions;
    }

    public Double calculateNetIncome(Double grossSalary, Double incomeTax, List<Double> deductAmounts) {

        for (Double deduction : deductAmounts) {

            grossSalary = grossSalary - deduction;

        }

        return (grossSalary - incomeTax);
    }


    public void calculateAllowance(List<Allowance> allowances) {

        for (int i = 0; i < allowances.size(); i++) {

            //for partial taxable
            if (allowances.get(i).isPartialTaxable()) {
                allowanceTaxPercent = allowances.get(i).getPercent();
                allowanceIncludeInGrossSalary = (allowances.get(i).getAmount() * allowanceTaxPercent);
                grossSalary = grossSalary + allowanceIncludeInGrossSalary;
                allowanceAddedToNetSalary = allowances.get(i).getAmount() - allowanceIncludeInGrossSalary;
                allowanceTaxPercent = 0.0;
            }
            //for non taxable
            if (!allowances.get(i).isTaxable() && !allowances.get(i).isPartialTaxable()) {

                allowanceNonTaxable = allowanceNonTaxable + allowances.get(i).getAmount();

                allowanceTaxPercent = 0.0;
            }
            if (allowances.get(i).isTaxable()) {
                taxableAllowance = allowances.get(i).getAmount();
                grossSalary = grossSalary + allowances.get(i).getAmount();

            }


        }


    }


    public TaxRate getTaxRateDetail(Double grossSalary, List<TaxRate> taxRates) {
        taxRates.sort((a, b) -> a.getFromSalary().compareTo(b.getFromSalary()));

        for (TaxRate taxRate1 : taxRates)
            if (grossSalary >= taxRate1.getFromSalary() && grossSalary <= taxRate1.getUptoSalary()) {


                return taxRate1;
            }


        return taxRates.get(taxRates.size() - 1);
    }

    public void setClassVariableToDefault() {
        totalAllowance = 0.0;
        allowanceIncludeInGrossSalary = 0.0;
        allowanceNonTaxable = 0.0;
        allowanceAddedToNetSalary = 0.0;
        allowanceTaxPercent = 0.0;
        totalSalary = 0.0;
        taxRatePercent = 0.0;
    }


    public Payroll storePayroll(PayrollDAO payrollDAO) {

        grossSalary = payrollDAO.getPayroll().getGrossSalary();

        Employee employee = employeeRepository.findById(payrollDAO.getEmployeeId()).get();

        List<Long> allowanceIds = payrollDAO.getAllowanceIds();
        List<Allowance> allowances = new ArrayList<>();
        allowanceIds.forEach((id) -> allowances.add(allowanceRepository.findById(id).get()));

        List<Deduction> deductions = new ArrayList<>();
        payrollDAO.getDeductionIds().forEach(
                (id) -> deductions.add(deductionRepository.findById(id).get())
        );


        Payroll payroll = payrollDAO.getPayroll();
        payroll.setEmployee(employee);
        payroll.setDeduction(deductions);
        payroll.setAllowance(allowances);

        calculateAllowance(payroll.getAllowance());

        taxRate = getTaxRateDetail(grossSalary, taxRateRepository.findAll());

        taxRatePercent = taxRate.getPercent();

        incomeTax = calculateIncomeTax(grossSalary, taxRatePercent, taxRate.getDeduction());


        List<Double> deductedAmounts = calculateDeduction(payroll.getDeduction(), grossSalary);

        totalAllowance = allowanceAddedToNetSalary + allowanceNonTaxable;

        totalSalary = (netIncome + totalAllowance);


        netIncome = calculateNetIncome(grossSalary, incomeTax, deductedAmounts);


        payroll.setGrossSalary(grossSalary);
        payroll.setTaxRate(taxRate);
        payroll.setIncomeTax(incomeTax);
        payroll.setNetSalary(netIncome);
        payroll.setTotalAllowance(totalAllowance);
        payroll.setTotalAmount(totalSalary);


        return payrollRepository.save(payroll);

    }
}
