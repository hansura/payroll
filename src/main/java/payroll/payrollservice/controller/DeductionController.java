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
import payroll.payrollservice.service.DeductionService;
import payroll.payrollservice.util.Common;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( value="api/deduction")
public class DeductionController implements Common<Deduction,Deduction> {


    @Autowired
    DeductionRepository deductionRepository;

    @Autowired
    private DeductionService  deductionService;



    public DeductionController( ){
    }




    @Override
    public Deduction store(@RequestBody  Deduction deduction) {

        return  deductionService.store(deduction) ;
    }

    @Override
    public Iterable<Deduction> store(List<Deduction> t) {
        return null;
    }

    @Override
    public Deduction show(long id) {
        return  deductionService.show(id);
    }

    @Override
    public Deduction update(long id, @RequestBody  Deduction deduction) {
        return  deductionService.update(id, deduction);
    }

    @Override
    public boolean delete(long id) {
        return  deductionService.delete(id);
    }

    @Override
    public Iterable<Deduction> getAll(Pageable pageable, Sort sort) {
        return  deductionService.getAll(pageable , sort);
    }
}
