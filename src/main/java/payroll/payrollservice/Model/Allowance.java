package payroll.payrollservice.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.org.apache.xpath.internal.operations.Bool;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "allowance")
public   class Allowance    {



         @Id
         @GeneratedValue( strategy =  GenerationType.IDENTITY)
         private Long id;
         @NotNull
         private String allowanceType;
         @NotNull
         @JsonProperty
         private boolean isTaxable;
         @NotNull
         private String percent;
         @NotNull
         private Double amount;
         @NotNull
         @JsonProperty
         private boolean isPartialTaxable;

         @ManyToMany(mappedBy =  "allowance" , fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
         @JsonIgnore
         private List<Payroll> payroll = new ArrayList<Payroll>();


    public Allowance() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAllowanceType() {
        return allowanceType;
    }

    public void setAllowanceType(String allowanceType) {
        this.allowanceType = allowanceType;
    }

    public boolean isTaxable() {
        return isTaxable;
    }

    public void setTaxable(boolean taxable) {
        isTaxable = taxable;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public boolean isPartialTaxable() {
        return isPartialTaxable;
    }

    public void setPartialTaxable(boolean partialTaxable) {
        isPartialTaxable = partialTaxable;
    }

    public List<Payroll> getPayroll() {
        return payroll;
    }

    public void setPayroll(List<Payroll> payroll) {
        this.payroll = payroll;
    }
}
