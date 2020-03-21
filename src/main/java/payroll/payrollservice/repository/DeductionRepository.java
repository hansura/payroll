package payroll.payrollservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import payroll.payrollservice.model.Deduction;

@Repository
public interface DeductionRepository extends PagingAndSortingRepository<Deduction, Long> {
}
