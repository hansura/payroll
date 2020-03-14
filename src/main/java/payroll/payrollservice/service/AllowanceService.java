package payroll.payrollservice.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import payroll.payrollservice.model.Allowance;
import payroll.payrollservice.repository.AllowanceRepository;
import payroll.payrollservice.util.Common;

import java.util.List;

@Service
public class AllowanceService implements Common<Allowance,Allowance> {
    private AllowanceRepository allowanceRepository;

    public AllowanceService(AllowanceRepository allowanceRepository) {
        this.allowanceRepository = allowanceRepository;
    }

    @Override
    public Allowance store(Allowance allowance) {
        return null;
    }

    @Override
    public Iterable<Allowance> store(List<Allowance> t) {
        return null;
    }

    @Override
    public Allowance show(long id) {
        return null;
    }

    @Override
    public Allowance update(long id, Allowance allowance) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Iterable<Allowance> getAll(Pageable pageable, Sort sort) {
        return null;
    }
}
