package payroll.payrollservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@AllArgsConstructor
public class Deduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Deduction title shouldn't be blank")
    @NotNull(message = "Deduction title is required")
    private String title;
    private String description;
    @Min(value = 10, message = "Percent value should be greater than or equals to 10")
    @Max(value = 100, message = "Percent value should be less than or equals to 100")
    private Double percent;


    @ManyToMany(mappedBy = "deduction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Payroll> payroll = new ArrayList<>();


}
