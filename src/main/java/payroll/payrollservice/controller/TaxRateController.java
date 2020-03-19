package payroll.payrollservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payroll.payrollservice.exception.CustomNotFoundException;
import payroll.payrollservice.exception.CustomNullException;
import payroll.payrollservice.model.TaxRate;
import payroll.payrollservice.repository.TaxRateRepository;
import payroll.payrollservice.service.TaxRateService;
import payroll.payrollservice.util.Common;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( value ="api/taxrate")
public class TaxRateController implements Common<TaxRate,TaxRate> {
    @Autowired
    private TaxRateService taxRateService;

    @Autowired
    TaxRateRepository taxRateRepository;



    public TaxRateController( ){

    }


    @Override
    public TaxRate store(@RequestBody  TaxRate taxRate) {
        return  taxRateService.store(taxRate);
    }

    @Override
    public Iterable<TaxRate> store(List<TaxRate> t) {
        return null;
    }

    @Override
    public TaxRate show(long id) {
        return  taxRateService.show(id);
    }

    @Override
    public TaxRate update(long id, @RequestBody TaxRate taxRate) {

        return  taxRateService.update(id, taxRate);
    }

    @Override
    public boolean delete(long id) {

        return taxRateService.delete(id);
    }

    @Override
    public Iterable<TaxRate> getAll(Pageable pageable, Sort sort) {
        return  taxRateService.getAll(pageable , sort);
    }
}
