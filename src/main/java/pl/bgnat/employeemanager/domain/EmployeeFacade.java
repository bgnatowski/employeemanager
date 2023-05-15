package pl.bgnat.employeemanager.domain;

public class EmployeeFacade {
	public static Employee.EmployeeBuilder employeeBuilder(){
		return Employee.builder();
	}
}
