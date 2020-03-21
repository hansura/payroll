package payroll.payrollservice.service;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import payroll.payrollservice.model.Deduction;
import payroll.payrollservice.repository.DeductionRepository;
import payroll.payrollservice.util.Common;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static payroll.payrollservice.util.Util.getNullPropertyNames;

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
        return  deductionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Deduction with id " + id + " not found"));
    }

    @Override
    public Deduction update(long id, Deduction deduction) {

        Deduction oldDeduction = show(id);
        BeanUtils.copyProperties(deduction, oldDeduction, getNullPropertyNames(deduction));
//        oldDeduction.setDeductionType(deduction.getDeductionType());
//        oldDeduction.setDeductionDescription(deduction.getDeductionDescription());
//        oldDeduction.setDeductionPercent(deduction.getDeductionPercent());


        return  deductionRepository.save(oldDeduction);
    }

    @Override
    public boolean delete(long id) {

        Deduction deduction =show(id);

        if(deduction != null){
            deductionRepository.delete(deduction);
            return true;
        }
        return false;
    }

    @Override
    public Page<Deduction> getAll(Pageable pageable) {
        return  deductionRepository.findAll(pageable);
    }
}
