package payroll.payrollservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import payroll.payrollservice.Model.Allowance;

@Repository
public interface AllowanceRepository extends JpaRepository<Allowance , Long> {
}
