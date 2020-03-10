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
@Table(name = "pension")
public   class Pension   {


    @Id
    @GeneratedValue( strategy =  GenerationType.IDENTITY)
    private  Long id;

    private String byCompany;
    private String byEmployee;
    private Long total;
    private String year;

    @OneToMany( mappedBy = "pension" ,cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Payroll> payroll = new ArrayList<>();


    public Pension() {
    }

    public Pension(String byCompany, String byEmployee, Long total, String year, List<Payroll> payroll) {
        this.byCompany = byCompany;
        this.byEmployee = byEmployee;
        this.total = total;
        this.year = year;
        this.payroll = payroll;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getByCompany() {
        return byCompany;
    }

    public void setByCompany(String byCompany) {
        this.byCompany = byCompany;
    }

    public String getByEmployee() {
        return byEmployee;
    }

    public void setByEmployee(String byEmployee) {
        this.byEmployee = byEmployee;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<Payroll> getPayroll() {
        return payroll;
    }

    public void setPayroll(List<Payroll> payroll) {
        this.payroll = payroll;
    }
}
