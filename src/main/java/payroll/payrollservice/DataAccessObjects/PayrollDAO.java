package payroll.payrollservice.DataAccessObjects;


import payroll.payrollservice.Model.Payroll;

import java.util.List;

public class PayrollDAO {

    private Payroll payroll ;

    private Long pensionId;

    private List<Long> allowanceIds ;

    private Long employeeId;

    public Payroll getPayroll() {
        return payroll;
    }

    public void setPayroll(Payroll payroll) {
        this.payroll = payroll;
    }



    public Long getPensionId() {
        return pensionId;
    }

    public void setPensionId(Long pensionId) {
        this.pensionId = pensionId;
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
