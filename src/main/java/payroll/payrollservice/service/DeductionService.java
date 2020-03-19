package payroll.payrollservice.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import payroll.payrollservice.model.Deduction;
import payroll.payrollservice.repository.DeductionRepository;
import payroll.payrollservice.util.Common;

import java.util.List;

@Service
public class DeductionService implements Common<Deduction,Deduction> {

    private DeductionRepository deductionRepository;

    public DeductionService(DeductionRepository deductionRepository) {
        this.deductionRepository = deductionRepository;
    }

    @Override
    public Deduction store(Deduction deduction) {
        return  deductionRepository.save(deduction);
    }

    @Override
    public Iterable<Deduction> store(List<Deduction> t) {
        return null;
    }

    @Override
    public Deduction show(long id) {
        return  deductionRepository.findById(id).get();
    }

    @Override
    public Deduction update(long id, Deduction deduction) {

        Deduction oldDeduction = deductionRepository.findById(id).get();

        oldDeduction.setDeductionType(deduction.getDeductionType());
        oldDeduction.setDeductionDescription(deduction.getDeductionDescription());
        oldDeduction.setDeductionPercent(deduction.getDeductionPercent());


        return  deductionRepository.save(oldDeduction);
    }

    @Override
    public boolean delete(long id) {

        Deduction deduction = deductionRepository.findById(id).get();

        if(deduction != null){
            deductionRepository.delete(deduction);
            return true;
        }
        return false;
    }

    @Override
    public Iterable<Deduction> getAll(Pageable pageable, Sort sort) {
        return  deductionRepository.findAll();
    }
}
