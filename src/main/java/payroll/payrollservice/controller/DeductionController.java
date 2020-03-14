package payroll.payrollservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payroll.payrollservice.exception.CustomNotFoundException;
import payroll.payrollservice.exception.CustomNullException;
import payroll.payrollservice.model.Deduction;
import payroll.payrollservice.repository.DeductionRepository;
import payroll.payrollservice.util.Common;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( value="api/deduction/")
public class DeductionController implements Common<Deduction,Deduction> {


    @Autowired
    DeductionRepository deductionRepository;

    private Map<String, String> response;

    public DeductionController( ){
        this.response = new HashMap<String, String>();
    }

    @GetMapping( value = "getAllDeductions")
    public List<Deduction> getAllDeduction( )
    {

        return  deductionRepository.findAll();
    }

    @PostMapping( value = "addDeduction")
    public ResponseEntity<?> addDeduction(@RequestBody Deduction deduction)
            throws CustomNotFoundException, CustomNullException {

        if (deduction == null)
            throw new CustomNullException("the object  contain null value ");

        deductionRepository.save(deduction);
        response.put("message", "Deduction successfully inserted");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PutMapping( value = "updateDeduction/{deductionId}")
    public ResponseEntity<?> updateDeduction (@PathVariable(value = "deductionId") Long deductionId ,@Valid @RequestBody Deduction newDeduction)
            throws CustomNotFoundException , CustomNullException {


        Deduction oldDeduction = deductionRepository.findById(deductionId).orElseThrow(
                () -> new CustomNotFoundException("Deduction Not Found")
        );
        if (newDeduction == null)
            throw new CustomNullException("Update Faild something wrong");
            oldDeduction.setDeductionType( newDeduction.getDeductionType());
            oldDeduction.setDeductionDescription(newDeduction.getDeductionDescription());
            oldDeduction.setDeductionPercent(newDeduction.getDeductionPercent());


          deductionRepository.save(oldDeduction);

        response.put("message", "Deduction Successfully Updated");


        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping( value="deleteDeduction/{deductionId}")
    public ResponseEntity<?> deleteDeduction(@PathVariable(value ="deductionId") Long pensionId)
            throws CustomNotFoundException{

        Deduction deletedDeduction = deductionRepository.findById(pensionId).orElseThrow(
                () -> new CustomNotFoundException("Deduction Not Found")
        );

        deductionRepository.delete(deletedDeduction);
        response.put("message" ,"Deduction Successfully Deleted");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("getDeduction/{deductionId}")
    public ResponseEntity <Deduction> getPension(@PathVariable( value ="deductionId") Long pensionId)
            throws CustomNotFoundException{

         Deduction deduction = deductionRepository.findById(pensionId).orElseThrow(
                () -> new CustomNotFoundException("Deduction Not Found")
        );


        return ResponseEntity.ok().body(deduction);
    }


    @Override
    public Deduction store(Deduction deduction) {
        return null;
    }

    @Override
    public Iterable<Deduction> store(List<Deduction> t) {
        return null;
    }

    @Override
    public Deduction show(long id) {
        return null;
    }

    @Override
    public Deduction update(long id, Deduction deduction) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Iterable<Deduction> getAll(Pageable pageable, Sort sort) {
        return null;
    }
}
