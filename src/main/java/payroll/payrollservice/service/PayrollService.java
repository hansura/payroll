package payroll.payrollservice.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import payroll.payrollservice.model.Payroll;
import payroll.payrollservice.repository.PayrollRepository;
import payroll.payrollservice.util.Common;

import java.util.List;

@Service
public class PayrollService implements Common<Payroll,Payroll> {
    private PayrollRepository payrollRepository;

    public PayrollService(PayrollRepository payrollRepository) {
        this.payrollRepository = payrollRepository;
    }

    @Override
    public Payroll store(Payroll payroll) {
        return null;
    }

    @Override
    public Iterable<Payroll> store(List<Payroll> t) {
        return null;
    }

    @Override
    public Payroll show(long id) {
        return null;
    }

    @Override
    public Payroll update(long id, Payroll payroll) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Iterable<Payroll> getAll(Pageable pageable, Sort sort) {
        return null;
    }
}
