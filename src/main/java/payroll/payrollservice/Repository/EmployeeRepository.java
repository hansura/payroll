package payroll.payrollservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import payroll.payrollservice.Model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee , Long> {
}
