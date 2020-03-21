package payroll.payrollservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import payroll.payrollservice.model.Allowance;
import payroll.payrollservice.service.AllowanceService;
import payroll.payrollservice.util.Common;

import java.util.List;

@RestController
@RequestMapping(value = "api/allowance")
public class AllowanceController implements Common<Allowance, Allowance> {


    private AllowanceService allowanceService;


    public AllowanceController(AllowanceService allowanceService) {

        this.allowanceService = allowanceService;
    }


    @Override
    public Allowance store(@RequestBody Allowance allowance) {

        return allowanceService.store(allowance);

    }

    @Override
    public Iterable<Allowance> store(List<Allowance> t) {
        return null;
    }

    @Override
    public Allowance show(long id) {
        return allowanceService.show(id);
    }

    @Override
    public Allowance update(long id, @RequestBody Allowance allowance) {
        return allowanceService.update(id, allowance);
    }

    @Override
    public boolean delete(long id) {
        return allowanceService.delete(id);
    }

    @Override
    public Iterable<Allowance> getAll(Pageable pageable) {
        return allowanceService.getAll(pageable);
    }
}

