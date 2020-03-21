package payroll.payrollservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "allowances")
@Data
public class Allowance {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Allowance title shouldn't be blank")
    @NotNull(message = "Allowance title is required")
    private String allowanceTitle;

    @NotNull(message = "Can't determine if the allowance is taxable")
    @JsonProperty
    private boolean isTaxable;

    @Min(value = 10, message = "Percent value should be greater than or equals to 10")
    @Max(value = 100, message = "Percent value should be less than or equals to 100")
    private Double percent;

    private BigDecimal amount;

    @JsonProperty
    private boolean isPartialTaxable;

    @ManyToMany(mappedBy = "allowance", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Payroll> payroll = new ArrayList<Payroll>();


}
