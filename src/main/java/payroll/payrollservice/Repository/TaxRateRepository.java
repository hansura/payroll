package payroll.payrollservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import payroll.payrollservice.Model.TaxRate;

@Repository
public interface TaxRateRepository extends JpaRepository<TaxRate, Long> {

}
