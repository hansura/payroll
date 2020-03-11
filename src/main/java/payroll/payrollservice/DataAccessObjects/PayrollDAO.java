package payroll.payrollservice.DataAccessObjects;


import payroll.payrollservice.Model.Payroll;

import java.util.List;

public class PayrollDAO {

    private Payroll payroll ;

    private  List<Long> deductionIds;

    private List<Long> allowanceIds ;

    private Long employeeId;

    public Payroll getPayroll() {
        return payroll;
    }

    public void setPayroll(Payroll payroll) {
        this.payroll = payroll;
    }

    public List<Long> getDeductionIds() {
        return deductionIds;
    }

    public void setDeductionIds(List<Long> deductionIds) {
        this.deductionIds = deductionIds;
    }

    public List<Long> getAllowanceIds() {
        return allowanceIds;
    }

    public void setAllowanceIds(List<Long> allowanceIds) {
        this.allowanceIds = allowanceIds;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
