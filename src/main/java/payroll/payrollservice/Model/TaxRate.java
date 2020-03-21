package payroll.payrollservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tax_rates")
@Data
public class TaxRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "From salary is required")
    private BigDecimal fromSalary;

    private BigDecimal uptoSalary;


    @Min(value = 10, message = "Percent value should be greater than or equals to 10")
    @Max(value = 100, message = "Percent value should be less than or equals to 100")
    private Double percent;


    private BigDecimal deduction;

    @OneToMany(mappedBy = "taxRate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Payroll> payroll = new ArrayList<>();


}
