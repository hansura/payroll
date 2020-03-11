package payroll.payrollservice.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "deduction")
public   class Deduction {


    @Id
    @GeneratedValue( strategy =  GenerationType.IDENTITY)
    private Long id;
    private String  deductionType;
    private String  deductionDescription;
    private String  deductionPercent;

    @ManyToMany( mappedBy = "deduction" ,cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Payroll> payroll = new ArrayList<>();


    public Deduction() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeductionType() {
        return deductionType;
    }

    public void setDeductionType(String deductionType) {
        this.deductionType = deductionType;
    }

    public String getDeductionDescription() {
        return deductionDescription;
    }

    public void setDeductionDescription(String deductionDescription) {
        this.deductionDescription = deductionDescription;
    }

    public String getDeductionPercent() {
        return deductionPercent;
    }

    public void setDeductionPercent(String deductionPercent) {
        this.deductionPercent = deductionPercent;
    }

    public List<Payroll> getPayroll() {
        return payroll;
    }

    public void setPayroll(List<Payroll> payroll) {
        this.payroll = payroll;
    }
}
