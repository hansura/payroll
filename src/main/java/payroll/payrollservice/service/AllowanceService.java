package payroll.payrollservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import payroll.payrollservice.model.Allowance;
import payroll.payrollservice.model.Payroll;
import payroll.payrollservice.repository.AllowanceRepository;
import payroll.payrollservice.util.Common;

import java.util.List;

@Service
public class AllowanceService implements Common<Allowance,Allowance> {

    private AllowanceRepository allowanceRepository;

public  AllowanceService( AllowanceRepository allowanceRepository){
     this.allowanceRepository = allowanceRepository;
}

    @Override
    public Allowance store(Allowance allowance) {
        return  allowanceRepository.save(allowance);
    }

    @Override
    public Iterable<Allowance> store(List<Allowance> t) {
        return null;
    }

    @Override
    public Allowance show(long id) {
        return  allowanceRepository.findById(id).get();
    }

    @Override
    public Allowance update(long id, Allowance allowance) {

         Allowance oldAllowance = allowanceRepository.findById(id).get();

         oldAllowance.setAllowanceType(allowance.getAllowanceType());
         oldAllowance.setAmount(allowance.getAmount());
         oldAllowance.setPartialTaxable(allowance.isPartialTaxable());
         oldAllowance.setPercent(allowance.getPercent());
         oldAllowance.setTaxable(allowance.isTaxable());

        return allowanceRepository.save(oldAllowance);
    }

    @Override
    public boolean delete(long id) {

        Allowance allowance = allowanceRepository.findById(id).get();
        if(allowance != null){

            allowanceRepository.delete(allowance);
            return true;
        }
        return false;
    }

    @Override
    public Iterable<Allowance> getAll(Pageable pageable, Sort sort) {
        return  allowanceRepository.findAll();
    }
}
