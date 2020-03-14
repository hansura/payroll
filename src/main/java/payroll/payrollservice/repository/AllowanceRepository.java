package payroll.payrollservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import payroll.payrollservice.model.Allowance;

@Repository
public interface AllowanceRepository extends JpaRepository<Allowance , Long> {
}
