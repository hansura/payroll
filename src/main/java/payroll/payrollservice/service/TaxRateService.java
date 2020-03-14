package payroll.payrollservice.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import payroll.payrollservice.model.TaxRate;
import payroll.payrollservice.repository.TaxRateRepository;
import payroll.payrollservice.util.Common;

import java.util.List;

@Service
public class TaxRateService implements Common<TaxRate,TaxRate> {
    private TaxRateRepository taxRateRepository;

    public TaxRateService(TaxRateRepository taxRateRepository) {
        this.taxRateRepository = taxRateRepository;
    }

    @Override
    public TaxRate store(TaxRate taxRate) {
        return null;
    }

    @Override
    public Iterable<TaxRate> store(List<TaxRate> t) {
        return null;
    }

    @Override
    public TaxRate show(long id) {
        return null;
    }

    @Override
    public TaxRate update(long id, TaxRate taxRate) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Iterable<TaxRate> getAll(Pageable pageable, Sort sort) {
        return null;
    }
}
