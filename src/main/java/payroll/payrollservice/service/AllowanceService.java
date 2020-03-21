package payroll.payrollservice.service;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import payroll.payrollservice.model.Allowance;
import payroll.payrollservice.repository.AllowanceRepository;
import payroll.payrollservice.util.Common;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static payroll.payrollservice.util.Util.getNullPropertyNames;

@Service
public class AllowanceService implements Common<Allowance, Allowance> {

    private AllowanceRepository allowanceRepository;

    public AllowanceService(AllowanceRepository allowanceRepository) {
        this.allowanceRepository = allowanceRepository;
    }

    @Override
    public Allowance store(Allowance allowance) {
        return allowanceRepository.save(allowance);
    }

    @Override
    public Iterable<Allowance> store(List<Allowance> t) {
        return null;
    }

    @Override
    public Allowance show(long id) {
        return allowanceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Allowance with id " + id + " not found"));
    }

    @Override
    public Allowance update(long id, Allowance allowance) {

        Allowance oldAllowance = show(id);
        BeanUtils.copyProperties(allowance, oldAllowance, getNullPropertyNames(allowance));
//         oldAllowance.setAllowanceTitle(allowance.getAllowanceTitle());
//         oldAllowance.setAmount(allowance.getAmount());
//         oldAllowance.setPartialTaxable(allowance.isPartialTaxable());
//         oldAllowance.setPercent(allowance.getPercent());
//         oldAllowance.setTaxable(allowance.isTaxable());

        return allowanceRepository.save(oldAllowance);
    }

    @Override
    public boolean delete(long id) {

        Allowance allowance = show(id);
        if (allowance != null) {

            allowanceRepository.delete(allowance);
            return true;
        }
        return false;
    }

    @Override
    public Page<Allowance> getAll(Pageable pageable) {
        return allowanceRepository.findAll(pageable);
    }
}
