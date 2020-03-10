package payroll.payrollservice.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payroll.payrollservice.CustomException.CustomNotFoundException;
import payroll.payrollservice.CustomException.CustomNullException;
import payroll.payrollservice.Model.Pension;
import payroll.payrollservice.Model.TaxRate;
import payroll.payrollservice.Repository.PensionRepository;
import payroll.payrollservice.Repository.TaxRateRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( value="api/pension/")
public class PensionController {


    @Autowired
    PensionRepository pensionRepository;

    private Map<String, String> response;

    public PensionController( ){
        this.response = new HashMap<String, String>();
    }

    @GetMapping( value = "getAllPensions")
    public List<Pension> getAllPensions( )
    {

        return  pensionRepository.findAll();
    }

    @PostMapping( value = "addPension")
    public ResponseEntity<?> addPension(@RequestBody Pension pension)
            throws CustomNotFoundException, CustomNullException {

        if (pension == null)
            throw new CustomNullException("the object  contain null value ");

        pensionRepository.save(pension);
        response.put("message", "Pension successfully inserted");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @PutMapping( value = "updatePension/{pensionId}")
    public ResponseEntity<?> updatePension (@PathVariable(value = "pensionId") Long pensionId ,@Valid @RequestBody Pension newPension)
            throws CustomNotFoundException , CustomNullException {


        Pension oldPension = pensionRepository.findById(pensionId).orElseThrow(
                () -> new CustomNotFoundException("Pension Not Found")
        );
        if (newPension == null)
            throw new CustomNullException("Update Faild something wrong");
            oldPension.setByCompany(newPension.getByCompany());
            oldPension.setByEmployee(newPension.getByEmployee());
            oldPension.setTotal(newPension.getTotal());
            oldPension.setYear(newPension.getYear());


          pensionRepository.save(oldPension);

        response.put("message", "Pension Successfully Updated");


        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping( value="deletePension/{pensionId}")
    public ResponseEntity<?> deletePension(@PathVariable(value ="pensionId") Long pensionId)
            throws CustomNotFoundException{

        Pension deletedPension = pensionRepository.findById(pensionId).orElseThrow(
                () -> new CustomNotFoundException("Pension Not Found")
        );

        pensionRepository.delete(deletedPension);
        response.put("message" ,"Pension Successfully Deleted");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @GetMapping("getPension/{pensionId}")
    public ResponseEntity <Pension> getPension(@PathVariable( value ="pensionId") Long pensionId)
            throws CustomNotFoundException{

         Pension pension = pensionRepository.findById(pensionId).orElseThrow(
                () -> new CustomNotFoundException("Pension Not Found")
        );


        return ResponseEntity.ok().body(pension);
    }





}
