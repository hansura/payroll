package payroll.payrollservice.controller;


import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import payroll.payrollservice.model.Payroll;
import payroll.payrollservice.repository.*;
import payroll.payrollservice.dao.PayrollDAO;
import payroll.payrollservice.service.PayrollService;
import payroll.payrollservice.util.Common;

import java.util.*;

@RestController
@RequestMapping(value = "api/payroll")

public class PayrollController implements Common<PayrollDAO, Payroll> {


    private PayrollService payrollService;


    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }


    @Override
    public Payroll store(@RequestBody PayrollDAO payrollDAO) {

        return payrollService.storePayroll(payrollDAO);
    }

    @Override
    public Iterable<Payroll> store(List<PayrollDAO> t) {
        return null;
    }

    @Override
    public Payroll show(long id) {
        return payrollService.show(id);
    }

    @Override
    public Payroll update(long id, @RequestBody PayrollDAO payrollDAO) {


        return payrollService.updatePayroll(id, payrollDAO);
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Iterable<Payroll> getAll(Pageable pageable) {
        return payrollService.getAll(pageable);
    }
}
