package pl.bgnat.employeemanager.domain;

import org.springframework.stereotype.Service;
import pl.bgnat.employeemanager.domain.request.EmployeeAddRequest;
import pl.bgnat.employeemanager.domain.request.EmployeeUpdateRequest;
import pl.bgnat.employeemanager.domain.response.EmployeeDeleteResponse;
import pl.bgnat.employeemanager.exception.RequestValidationException;
import pl.bgnat.employeemanager.exception.ResourceNotFoundException;
import pl.bgnat.employeemanager.exception.employee.EmployeeNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
class EmployeeService {
	private static final String EMPLOYEE_WITH_ID_S_DOES_NOT_EXIST = "Employee with id=%s does not exist!";
	public static final String EMPLOYEE_WITH_ID_S_DELETED = "Employee with id=%s deleted!";
	public static final String INVALID_REQUEST_S_FIELDS_CANNOT_BE_NULL = "Invalid request: %s. Fields cannot be null!";
	private final EmployeeRepository employeeRepository;

	EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	List<Employee> addAllEmployees(List<EmployeeAddRequest> employeeAddRequestList) {
		employeeAddRequestList.forEach(request -> validAddRequest(request));
		List<Employee> createdEmployees = employeeAddRequestList
				.stream()
				.map(request -> createEmployee(request))
				.collect(Collectors.toList());
		return employeeRepository.saveAll(createdEmployees);
	}

	Employee addEmployee(EmployeeAddRequest addRequest) {
		validAddRequest(addRequest);
		Employee employee = createEmployee(addRequest);
		return employeeRepository.save(employee);
	}

	List<Employee> findAllEmployees() {
		return employeeRepository.findAll();
	}

	Employee updateEmployee(EmployeeUpdateRequest updateRequest) {
		validUpdateRequest(updateRequest);
		Long id = updateRequest.id();
		checkIfUserWithIdExists(id);
		Employee employeeToUpdate = findEmployeeById(id);
		updateEmployee(updateRequest, employeeToUpdate);
		return employeeRepository.save(employeeToUpdate);
	}

	EmployeeDeleteResponse deleteEmployeeById(Long id) {
		checkIfUserWithIdExists(id);
		employeeRepository.deleteById(id);
		return new EmployeeDeleteResponse(
				String.format(EMPLOYEE_WITH_ID_S_DELETED, id));
	}

	Employee findEmployeeById(Long id) {
		return employeeRepository.findById(id)
				.orElseThrow(() -> employeeNotFoundExceptionForUserWithId(id));
	}

	private void checkIfUserWithIdExists(Long id) {
		if (!isExistById(id)) {
			throw employeeNotFoundExceptionForUserWithId(id);
		}
	}

	private boolean isExistById(Long id) {
		return employeeRepository.existsById(id);
	}

	private ResourceNotFoundException employeeNotFoundExceptionForUserWithId(Long id) {
		return new EmployeeNotFoundException(
				String.format(EMPLOYEE_WITH_ID_S_DOES_NOT_EXIST, id));
	}

	private static Employee createEmployee(EmployeeAddRequest employeeAddRequest) {
		return Employee.builder()
				.name(employeeAddRequest.name())
				.email(employeeAddRequest.email())
				.jobTitle(employeeAddRequest.jobTitle())
				.phoneNumber(employeeAddRequest.phoneNumber())
				.imageUrl(employeeAddRequest.imageUrl())
				.employeeCode(UUID.randomUUID().toString())
				.build();
	}


	private static void updateEmployee(EmployeeUpdateRequest updateRequest, Employee employeeToUpdate) {
		employeeToUpdate.setName(updateRequest.name());
		employeeToUpdate.setEmail(updateRequest.email());
		employeeToUpdate.setJobTitle(updateRequest.jobTitle());
		employeeToUpdate.setImageUrl(updateRequest.imageUrl());
	}

	private void validAddRequest(EmployeeAddRequest addRequest) {
		if (Stream.of(addRequest.name(), addRequest.email(), addRequest.jobTitle(),
						addRequest.phoneNumber(), addRequest.imageUrl())
				.anyMatch(Objects::isNull)) {
			throw new RequestValidationException(
					String.format(INVALID_REQUEST_S_FIELDS_CANNOT_BE_NULL, addRequest));
		}
	}

	private void validUpdateRequest(EmployeeUpdateRequest updateRequest) {
		if (Stream.of(updateRequest.name(), updateRequest.email(), updateRequest.jobTitle(),
						updateRequest.phoneNumber(), updateRequest.imageUrl())
				.anyMatch(Objects::isNull)) {
			throw new RequestValidationException(String.format(INVALID_REQUEST_S_FIELDS_CANNOT_BE_NULL, updateRequest));
		}
	}
}
