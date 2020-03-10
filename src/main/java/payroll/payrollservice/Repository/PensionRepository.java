package payroll.payrollservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import payroll.payrollservice.Model.Pension;

@Repository
public interface PensionRepository  extends JpaRepository<Pension, Long> {
}
