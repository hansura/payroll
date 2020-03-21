package payroll.payrollservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "deductions")
@Data
public class Deduction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deductionType;
    private String deductionDescription;
    private Double deductionPercent;

    @ManyToMany(mappedBy = "deduction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Payroll> payroll = new ArrayList<>();


}
