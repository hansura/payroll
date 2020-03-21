package payroll.payrollservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "employees")
@Data
public  class Employee   {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private  Long id;
    private String employeeId;

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


}
