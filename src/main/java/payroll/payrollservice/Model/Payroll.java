package payroll.payrollservice.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "payrolls")
@Data
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal incomeTax;

    private BigDecimal grossSalary;

    private BigDecimal netSalary;

    private BigDecimal totalAllowance;

    private BigDecimal totalAmount;


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
