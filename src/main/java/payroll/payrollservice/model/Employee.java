package payroll.payrollservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
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


}
