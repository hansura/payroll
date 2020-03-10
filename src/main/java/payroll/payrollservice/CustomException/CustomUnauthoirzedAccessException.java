package payroll.payrollservice.CustomException;

import org.springframework.web.client.HttpClientErrorException;

public class CustomUnauthoirzedAccessException extends Exception {
              public CustomUnauthoirzedAccessException( String message){
                   super(message);
              }
}
