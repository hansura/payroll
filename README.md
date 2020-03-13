# payroll
Payroll Service

Allowance
{   
	  "allowanceType" : "Transport",
	  "isTaxable" : true,
	  "percent" : "25%" ,
	  "amount" : 900 ,
	  "isPartialTaxable" : true
	 
}

TaxRate
{
	 "fromSalary" : 601,
	 "uptoSalary" : 1550,
	 "taxRatePercent" : "10%" ,
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
	 "deductionDescription" : "Student Cost Sharing " ,
	 "deductionPercent" : "2%" 
}


