package payroll.payrollservice.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payroll.payrollservice.CustomException.CustomNotFoundException;
import payroll.payrollservice.CustomException.CustomNullException;
import payroll.payrollservice.Model.TaxRate;
import payroll.payrollservice.Repository.TaxRateRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( value ="api/taxrate/")
public class TaxRateController {


    @Autowired
    TaxRateRepository taxRateRepository;

    private Map<String, String> response;

    public TaxRateController( ){
        this.response = new HashMap<String, String>();
    }

    @GetMapping( value = "getAllTaxRates")
    public List<TaxRate> getAllTaxRates( )
    {

        return  taxRateRepository.findAll();
    }

    @PostMapping( value = "addTaxRate")
    public ResponseEntity<?> addTaxRate(@RequestBody TaxRate taxRate)
            throws CustomNotFoundException, CustomNullException {


        if (taxRate == null)
            throw new CustomNullException("the object  contain null value ");
        taxRateRepository.save(taxRate);
        response.put("message", "TaxRate successfully inserted");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @PutMapping( value = "updateTaxRate/{taxRateId}")
    public ResponseEntity<?> updateTaxRate (@PathVariable(value = "taxRateId") Long taxRateId ,@Valid @RequestBody TaxRate newTaxRate)
            throws CustomNotFoundException , CustomNullException {


        TaxRate oldTaxRate = taxRateRepository.findById(taxRateId).orElseThrow(
                () -> new CustomNotFoundException("TaxRate Not Found")
        );
        if (newTaxRate == null)
            throw new CustomNullException("Update Faild something wrong");

         oldTaxRate.setFromSalary(newTaxRate.getFromSalary());
         oldTaxRate.setUptoSalary(newTaxRate.getUptoSalary());
         oldTaxRate.setTaxRatePercent(newTaxRate.getTaxRatePercent());
         oldTaxRate.setDeduction(newTaxRate.getDeduction());


          taxRateRepository.save(oldTaxRate);

        response.put("message", "TaxRate Successfully Updated");


        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping( value="deleteTaxRate/{taxRateId}")
    public ResponseEntity<?> deleteTaxRate(@PathVariable(value ="taxRateId") Long taxRateId)
            throws CustomNotFoundException{

        TaxRate deletedTaxRate = taxRateRepository.findById(taxRateId).orElseThrow(
                () -> new CustomNotFoundException("TaxRate Not Found")
        );

        taxRateRepository.delete(deletedTaxRate);
        response.put("message" ,"TaxRate Successfully Deleted");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @GetMapping("getTaxRate/{taxRateId}")
    public ResponseEntity <TaxRate> getTaxRate(@PathVariable( value ="taxRateId") Long taxRateId)
            throws CustomNotFoundException{

        TaxRate taxRate = taxRateRepository.findById(taxRateId).orElseThrow(
                () -> new CustomNotFoundException("TaxRate Not Found")
        );


        return ResponseEntity.ok().body(taxRate);
    }










}
