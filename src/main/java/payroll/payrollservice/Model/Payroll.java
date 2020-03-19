package payroll.payrollservice.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "payrolls")
@Data
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double incomeTax;

    private Double grossSalary;

    private Double netSalary;

    private Double totalAllowance;

    private Double totalAmount;


    @ManyToMany
    @JoinTable(name = "payroll_allowance",
            joinColumns = {@JoinColumn(name = "payroll_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "allowance_id", nullable = false, updatable = false)}
    )
    private List<Allowance> allowance = new ArrayList<Allowance>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private TaxRate taxRate;

    @ManyToMany
    @JoinTable(name = "payroll_deduction",
            joinColumns = {@JoinColumn(name = "payroll_id")},
            inverseJoinColumns = {@JoinColumn(name = "deduction_id")})
    private List<Deduction> deduction = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;


}
