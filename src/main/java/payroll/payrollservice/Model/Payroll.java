package payroll.payrollservice.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "payroll")
public   class Payroll   {


        @Id
        @GeneratedValue( strategy =  GenerationType.IDENTITY)
        private  Long id;

        private  Double incomeTax;

        private  Double  grossSalary;

        private  Double netSalary;

        private Double   totalAllowance;

        private Double totalAmount;



        @ManyToMany
        @JoinTable( name = "payroll_allowance" ,
                joinColumns =  {@JoinColumn( name="payroll_id" , nullable= false, updatable = false)} ,
             inverseJoinColumns = { @JoinColumn ( name = "allowance_id" , nullable =  false, updatable =  false)}
        )
        private  List<Allowance> allowance = new ArrayList<Allowance>();

        @ManyToOne( fetch = FetchType.LAZY , cascade = CascadeType.ALL)
        private  TaxRate taxRate;

        @ManyToOne(fetch = FetchType.LAZY)
        private  Pension pension;

        @ManyToOne( fetch = FetchType.LAZY)
        private  Employee employee ;


        public Payroll() {
        }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getIncomeTax() {
        return incomeTax;
    }

    public void setIncomeTax(Double incomeTax) {
        this.incomeTax = incomeTax;
    }

    public Double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(Double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public Double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(Double netSalary) {
        this.netSalary = netSalary;
    }

    public Double getTotalAllowance() {
        return totalAllowance;
    }

    public void setTotalAllowance(Double totalAllowance) {
        this.totalAllowance = totalAllowance;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Allowance> getAllowance() {
        return allowance;
    }

    public void setAllowance(List<Allowance> allowance) {
        this.allowance = allowance;
    }

    public TaxRate getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(TaxRate taxRate) {
        this.taxRate = taxRate;
    }

    public Pension getPension() {
        return pension;
    }

    public void setPension(Pension pension) {
        this.pension = pension;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
