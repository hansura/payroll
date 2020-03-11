package payroll.payrollservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import payroll.payrollservice.Model.Deduction;

@Repository
public interface DeductionRepository extends JpaRepository<Deduction, Long> {
}
