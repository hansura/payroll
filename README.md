# payroll
Payroll Service

Allowance
{   
	 "allowanceType" : "Transport",
	 "isTaxable" : "true" ,
	 "percent" : "25%" ,
	  "amount" : 900 ,
	  "isIncludeInSalary" : "true"
	 
}


TaxRate
{
	 "salaryRange" : "601 - 1650",
	 "taxRate" : "10%" ,
	 "deduction" : "60" 
}



Payroll
{
"payroll":{
         	"grossSalary" : 2000
 },
 "employeeId":1,
 "taxRateId" : 2,
 "deductionIds" : [1], // list of deductionIds 
 "allowanceIds" : [ 1 , 2 ,3 ,5 ] // list of allowance Ids
}

Deduction
{
	 "deductionType" : "Cost Sharing",
	 "deductionType" : "Student Cost Sharing " ,
	 "deductionPercent" : "2%" 
}

TaxRate
{
	 "salaryRange" : "601 - 1650",
	 "taxRate" : "10%" ,
	 "deduction" : "60" 
}
