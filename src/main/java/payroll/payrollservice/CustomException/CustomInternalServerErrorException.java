package payroll.payrollservice.CustomException;

public class CustomInternalServerErrorException extends  Exception {
       public CustomInternalServerErrorException(String message){
               super(message);
       }

}
