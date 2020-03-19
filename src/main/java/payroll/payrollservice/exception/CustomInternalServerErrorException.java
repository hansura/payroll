package payroll.payrollservice.exception;

public class CustomInternalServerErrorException extends  Exception {
       public CustomInternalServerErrorException(String message){
               super(message);
       }

}
