package payroll.payrollservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import payroll.payrollservice.model.TaxRate;

@Repository
public interface TaxRateRepository extends JpaRepository<TaxRate, Long> {

}
