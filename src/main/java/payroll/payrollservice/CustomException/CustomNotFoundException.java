package payroll.payrollservice.CustomException;

import org.springframework.web.servlet.function.EntityResponse;

public class CustomNotFoundException extends Exception {

   public CustomNotFoundException(String message){
        super(message);
   }


}
