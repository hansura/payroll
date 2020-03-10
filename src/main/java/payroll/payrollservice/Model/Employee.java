package payroll.payrollservice.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee")
public  class Employee   {


    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private  Long id;

    private String firstName;

    private String lastName;

    @OneToMany( mappedBy = "employee" , cascade = CascadeType.ALL , fetch =  FetchType.LAZY)
    @JsonIgnore
    private List<Payroll> payroll = new ArrayList<>();


    public Employee() {
    }

    public Employee(String firstName, String lastName, List<Payroll> payroll) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.payroll = payroll;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Payroll> getPayroll() {
        return payroll;
    }

    public void setPayroll(List<Payroll> payroll) {
        this.payroll = payroll;
    }
}
