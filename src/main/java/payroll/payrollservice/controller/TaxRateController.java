package payroll.payrollservice.controller;


import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import payroll.payrollservice.model.TaxRate;
import payroll.payrollservice.repository.TaxRateRepository;
import payroll.payrollservice.service.TaxRateService;
import payroll.payrollservice.util.Common;

import java.util.List;

@RestController
@RequestMapping(value = "api/taxrate")
public class TaxRateController implements Common<TaxRate, TaxRate> {

    private TaxRateService taxRateService;


    public TaxRateController(TaxRateService taxRateService) {

        this.taxRateService = taxRateService;
    }


    @Override
    public TaxRate store(@RequestBody TaxRate taxRate) {
        return taxRateService.store(taxRate);
    }

    @Override
    public Iterable<TaxRate> store(List<TaxRate> t) {
        return null;
    }

    @Override
    public TaxRate show(long id) {
        return taxRateService.show(id);
    }

    @Override
    public TaxRate update(long id, @RequestBody TaxRate taxRate) {

        return taxRateService.update(id, taxRate);
    }

    @Override
    public boolean delete(long id) {

        return taxRateService.delete(id);
    }

    @Override
    public Iterable<TaxRate> getAll(Pageable pageable) {
        return taxRateService.getAll(pageable);
    }
}
