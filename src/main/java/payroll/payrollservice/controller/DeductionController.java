package payroll.payrollservice.controller;


import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import payroll.payrollservice.model.Deduction;
import payroll.payrollservice.service.DeductionService;
import payroll.payrollservice.util.Common;

import java.util.List;

@RestController
@RequestMapping(value = "api/deduction")
public class DeductionController implements Common<Deduction, Deduction> {


    private DeductionService deductionService;


    public DeductionController(DeductionService deductionService) {
        this.deductionService = deductionService;
    }


    @Override
    public Deduction store(@RequestBody Deduction deduction) {

        return deductionService.store(deduction);
    }

    @Override
    public Iterable<Deduction> store(List<Deduction> t) {
        return null;
    }

    @Override
    public Deduction show(long id) {
        return deductionService.show(id);
    }

    @Override
    public Deduction update(long id, @RequestBody Deduction deduction) {
        return deductionService.update(id, deduction);
    }

    @Override
    public boolean delete(long id) {
        return deductionService.delete(id);
    }

    @Override
    public Iterable<Deduction> getAll(Pageable pageable) {
        return deductionService.getAll(pageable);
    }
}
