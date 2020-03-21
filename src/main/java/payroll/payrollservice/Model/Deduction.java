package payroll.payrollservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "deductions")
@Data
public class Deduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
<<<<<<< HEAD
    private String deductionType;
    private String deductionDescription;
    private Double deductionPercent;
=======
    @NotBlank(message = "Deduction title shouldn't be blank")
    @NotNull(message = "Deduction title is required")
    private String title;
    private String description;
    @Min(value = 10, message = "Percent value should be greater than or equals to 10")
    @Max(value = 100, message = "Percent value should be less than or equals to 100")
    private Double percent;
>>>>>>> remove system.out

    @ManyToMany(mappedBy = "deduction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Payroll> payroll = new ArrayList<>();


}
