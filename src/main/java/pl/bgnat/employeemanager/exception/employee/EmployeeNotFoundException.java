package pl.bgnat.employeemanager.exception.employee;

import pl.bgnat.employeemanager.exception.ResourceNotFoundException;

public class EmployeeNotFoundException extends ResourceNotFoundException {
	public EmployeeNotFoundException(String message) {
		super(message);
	}
}
