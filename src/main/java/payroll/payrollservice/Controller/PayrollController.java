package payroll.payrollservice.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payroll.payrollservice.CustomException.CustomNotFoundException;
import payroll.payrollservice.DataAccessObjects.PayrollDAO;
import payroll.payrollservice.Model.*;
import payroll.payrollservice.Repository.*;

import javax.transaction.Transactional;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( value = "api/payroll/")

public class PayrollController {

    Double totalAllowance= 0.0;
    Double allowanceIncludeInGrossSalary = 0.0;
    Double allowanceNonTaxable = 0.0;
    Double allowanceAddedToNetSalary = 0.0;
    Double allowanceTaxPercent = 0.0;
    Double totalAmount = 0.0;
    Double taxRatePercent = 0.0;
    Double incomeTax = 0.0;
    Double grossSalary = 0.0;
    TaxRate taxRate = null;
    Double pensionPercent = 0.0;
    Double employeePension = 0.0;
    Double netIncome = 0.0;

    @Autowired
    PensionRepository pensionRepository;

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
    public ResponseEntity<?> addPayroll(@RequestBody   PayrollDAO payrollDAO) throws  CustomNotFoundException{

                    grossSalary = payrollDAO.getPayroll().getGrossSalary();

                    System.out.println("grosssalary \t" + grossSalary);

             Employee employee = employeeRepository.findById(payrollDAO.getEmployeeId()).orElseThrow(
                ()-> new CustomNotFoundException("Employee Not Found")
              );

        List<TaxRate> taxRates = taxRateRepository.findAll();

        Pension pension = pensionRepository.findById(payrollDAO.getPensionId()).orElseThrow(
                () -> new CustomNotFoundException("Pension Not Found")
        );

        List<Long>  allowanceIds = payrollDAO.getAllowanceIds();
        List<Allowance> allowances = new ArrayList<>();
        allowanceIds.forEach( (id) -> allowances.add(allowanceRepository.findById(id).get()));

          calculateAllowance(allowances);

           taxRate = new TaxRate();
           taxRate = getTaxRateDetail(grossSalary , taxRates);

            taxRatePercent =  converPercentToNumber(taxRate.getTaxRatePercent());
               System.out.println("taxrate percent \t" + taxRatePercent);

         incomeTax = calcuateIncomeTax( grossSalary , taxRatePercent , taxRate.getDeduction());

         System.out.println("income tax \t" + incomeTax);

         pensionPercent = converPercentToNumber(pension.getByEmployee()) ;
         System.out.println("Pension Percent \t" + pensionPercent);

        employeePension = calculatePension(grossSalary , pensionPercent);

        System.out.println("by employee \t" + employeePension);

        netIncome =   calculateNetIncome( grossSalary , incomeTax ,employeePension ) ;
        System.out.println("allowance non taxable \t" + allowanceNonTaxable);
        System.out.println("allowance taxable \t" + allowanceAddedToNetSalary);

        totalAllowance = allowanceAddedToNetSalary + allowanceNonTaxable;

        totalAmount = (netIncome + totalAllowance);
        System.out.println("net salary \t" + netIncome);
        System.out.println("net Salary + Partial allowance + non taxable allowance\t" + totalAmount);
        System.out.println("total net salary \t" + netIncome);
        System.out.println("total amount \t" + totalAmount );


        Payroll payroll = payrollDAO.getPayroll();
        payroll.setGrossSalary(grossSalary);
        payroll.setTaxRate(taxRate);
        payroll.setEmployee(employee);
        payroll.setIncomeTax(incomeTax);
        payroll.setPension(pension);
        payroll.setNetSalary(netIncome);
        payroll.setAllowance(allowances);
        payroll.setTotalAllowance(totalAllowance);
        payroll.setTotalAmount(totalAmount);

        payrollRepository.save(payroll);

        setClassVariableToDefault();

        response.put("message" , "paryroll Successfully inserted");

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
        Pension pension = pensionRepository.findById(payrollDAO.getPensionId()).orElseThrow(
                () -> new CustomNotFoundException("Pension Not Found")
        );

        List<Long>  allowanceIds = payrollDAO.getAllowanceIds();
        List<Allowance> allowances = new ArrayList<>();
        allowanceIds.forEach( (id) -> allowances.add(allowanceRepository.findById(id).get()));

          calculateAllowance(allowances);

         taxRate = new TaxRate();
         taxRate = getTaxRateDetail(grossSalary , taxRates);

        taxRatePercent =  converPercentToNumber(taxRate.getTaxRatePercent());
        System.out.println("taxrate percent \t" + taxRatePercent);

         incomeTax =  calcuateIncomeTax(grossSalary , taxRatePercent ,taxRate.getDeduction() );
        System.out.println("income tax \t" + incomeTax);

         pensionPercent = converPercentToNumber(pension.getByEmployee());
        System.out.println("Pension Percent \t" + pensionPercent);

         employeePension =  calculatePension(grossSalary , pensionPercent) ;
        System.out.println("by employee \t" + employeePension);

         netIncome = calculateNetIncome ( grossSalary , incomeTax , employeePension ) ;
        System.out.println("allowance non taxable \t" + allowanceNonTaxable);
        System.out.println("allowance taxable \t" + allowanceAddedToNetSalary);

        totalAllowance = allowanceAddedToNetSalary + allowanceNonTaxable;

        totalAmount = (netIncome + totalAllowance);

        System.out.println("net salary \t" + netIncome);
        System.out.println("net Salary + Partial allowance + non taxable allowance\t" + totalAmount);
        System.out.println("total net salary \t" + netIncome);
        System.out.println("total amount \t" + totalAmount );

        Payroll newPayroll = payrollDAO.getPayroll();
        oldPayroll.setGrossSalary(newPayroll.getGrossSalary());
        oldPayroll.setEmployee(employee);
        oldPayroll.setIncomeTax(incomeTax);
        oldPayroll.setTaxRate(taxRate);
        oldPayroll.setPension(pension);
        oldPayroll.setNetSalary(netIncome);
        oldPayroll.setAllowance(allowances);
        oldPayroll.setTotalAllowance(totalAllowance);
        oldPayroll.setTotalAmount(totalAmount);


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




    public  Double calcuateIncomeTax(Double grossSalary , Double taxRatePercent , Double deduction){

        return ((grossSalary * taxRatePercent) - deduction);
    }

    public  Double converPercentToNumber( String percent){
        allowanceTaxPercent =(Double.parseDouble(
               percent.substring(0, percent.length() -1))/100);

        return allowanceTaxPercent;
    }

    public  Double calculatePension(Double grossSalary , Double pensionPercent ){

        return (grossSalary * pensionPercent);
    }

    public  Double calculateNetIncome(Double grossSalary , Double incomeTax , Double pension){

        return  (grossSalary - incomeTax - pension);
    }


    public void calculateAllowance( List<Allowance> allowances){

        for (int i = 0 ; i < allowances.size(); i++){

            //for partial taxable
            if( allowances.get(i).isPartialTaxable() == true)
            {
                allowanceTaxPercent =  converPercentToNumber(allowances.get(i).getPercent());
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
                grossSalary = grossSalary + allowances.get(i).getAmount();
                System.out.println("taxable allowance => gross salary + allwoance \t" + grossSalary);
            }


        }


    }


    public TaxRate getTaxRateDetail(Double grossSalary , List<TaxRate> taxRates) {
        for (TaxRate taxRate1 : taxRates)
            if (grossSalary >= taxRate1.getFromSalary() && grossSalary <= taxRate1.getUptoSalary())
                return taxRate1;

            return  null;
    }

    public  void setClassVariableToDefault(){
        totalAllowance= 0.0;
        allowanceIncludeInGrossSalary = 0.0;
        allowanceNonTaxable = 0.0;
        allowanceAddedToNetSalary = 0.0;
        allowanceTaxPercent = 0.0;
        totalAmount = 0.0;
        taxRatePercent = 0.0;
    }



}
