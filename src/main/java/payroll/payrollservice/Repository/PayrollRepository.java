package payroll.payrollservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import payroll.payrollservice.Model.Payroll;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll , Long> {


}
