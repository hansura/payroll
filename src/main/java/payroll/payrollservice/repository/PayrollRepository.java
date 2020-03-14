package payroll.payrollservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import payroll.payrollservice.model.Payroll;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll , Long> {


}
