package payroll.payrollservice.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import payroll.payrollservice.model.Deduction;
import payroll.payrollservice.util.Common;

import java.util.List;

@Service
public class DeductionService implements Common<Deduction,Deduction> {
    private DeductionService deductionService;

    public DeductionService(DeductionService deductionService) {
        this.deductionService = deductionService;
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
