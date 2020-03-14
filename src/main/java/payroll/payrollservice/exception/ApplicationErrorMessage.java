package payroll.payrollservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationErrorMessage {

    private int statusCode;
    private String errorMessage;


}
