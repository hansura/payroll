package payroll.payrollservice.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name ="taxrate")
public   class TaxRate  {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    private Double fromSalary;

    private Double uptoSalary;


    private String taxRatePercent;

    private Double deduction;

    @OneToMany( mappedBy =  "taxRate" ,cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Payroll> payroll = new ArrayList<>();


    public TaxRate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFromSalary() {
        return fromSalary;
    }

    public void setFromSalary(Double fromSalary) {
        this.fromSalary = fromSalary;
    }

    public Double getUptoSalary() {
        return uptoSalary;
    }

    public void setUptoSalary(Double uptoSalary) {
        this.uptoSalary = uptoSalary;
    }

    public String getTaxRatePercent() {
        return taxRatePercent;
    }

    public void setTaxRatePercent(String taxRatePercent) {
        this.taxRatePercent = taxRatePercent;
    }

    public Double getDeduction() {
        return deduction;
    }

    public void setDeduction(Double deduction) {
        this.deduction = deduction;
    }

    public List<Payroll> getPayroll() {
        return payroll;
    }

    public void setPayroll(List<Payroll> payroll) {
        this.payroll = payroll;
    }
}
