package payroll.payrollservice.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import payroll.payrollservice.CustomException.CustomBadRequestException;
import payroll.payrollservice.CustomException.CustomInternalServerErrorException;
import payroll.payrollservice.CustomException.CustomNotFoundException;
import payroll.payrollservice.CustomException.CustomNullException;
import payroll.payrollservice.Model.Allowance;
import payroll.payrollservice.Repository.AllowanceRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( value ="api/allowance/")
public class AllowanceController {

     @Autowired
     AllowanceRepository allowanceRepository;

     private Map<String, String> response;

     public AllowanceController( ){
          this.response = new HashMap<String, String>();
      }

        @GetMapping( value = "getAllAllowance")
       public List<Allowance>  getAllowances( )
       {

          return  allowanceRepository.findAll();
       }

       @PostMapping( value = "addAllowance")
       public ResponseEntity<?> addAllowance( @RequestBody  Allowance allowance)
               throws CustomNotFoundException , CustomNullException {

           if (allowance == null)
               throw new CustomNullException("the object  contain null value ");
           System.out.println("istaxable " + allowance.isPartialTaxable() + " \tand \t Partial taxable "+allowance.isTaxable() );
           allowanceRepository.save(allowance);
           response.put("message", "Allowance successfully inserted");

           return new ResponseEntity<>(response, HttpStatus.OK);

       }
        @PutMapping( value = "updateAllowance/{allowanceId}")
       public ResponseEntity<?> updateAllowance (@PathVariable(value = "allowanceId") Long allowanceId ,@Valid @RequestBody Allowance newAllowance)
                throws CustomNotFoundException , CustomNullException {


             Allowance oldAllowance = allowanceRepository.findById(allowanceId).orElseThrow(
                     () -> new CustomNotFoundException("Allowance Not Found")
             );
             if (newAllowance == null)
                 throw new CustomNullException("update faild something wrong");

             oldAllowance.setAllowanceType(newAllowance.getAllowanceType());
             oldAllowance.setAmount(newAllowance.getAmount());
             oldAllowance.setPercent(newAllowance.getPercent());
             oldAllowance.setTaxable(newAllowance.isTaxable());
             oldAllowance.setPartialTaxable(newAllowance.isPartialTaxable());

             allowanceRepository.save(oldAllowance);
             response.put("message", "Allowance Successfully Updated");


            return new ResponseEntity<>(response, HttpStatus.OK);

       }

        @DeleteMapping( value="deleteAllowance/{allowanceId}")
        public ResponseEntity<?> deleteAllowance(@PathVariable(value ="allowanceId") Long allowanceId)
        throws CustomNotFoundException{

              Allowance deletedAllowance = allowanceRepository.findById(allowanceId).orElseThrow(
                      () -> new CustomNotFoundException("Allowance Not Found")
              );

              allowanceRepository.delete(deletedAllowance);
              response.put("message" ,"Allowance Successfully Deleted");

             return new ResponseEntity<>(response, HttpStatus.OK);

        }
        @GetMapping("getAllowance/{allowanceId}")
     public ResponseEntity <Allowance> getAllowance(@PathVariable( value ="allowanceId") Long allowanceId)
        throws CustomNotFoundException{

            Allowance allowance = allowanceRepository.findById(allowanceId).orElseThrow(
                    () -> new CustomNotFoundException("Allowance Not Found")
            );


         return ResponseEntity.ok().body(allowance);
     }


}
