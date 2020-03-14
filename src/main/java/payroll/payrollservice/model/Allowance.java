package payroll.payrollservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "allowances")
@Data
public class Allowance {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String allowanceType;
    @NotNull
    @JsonProperty
    private boolean isTaxable;
    @NotNull
    private String percent;
    @NotNull
    private Double amount;
    @NotNull
    @JsonProperty
    private boolean isPartialTaxable;

    @ManyToMany(mappedBy = "allowance", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Payroll> payroll = new ArrayList<Payroll>();



}
