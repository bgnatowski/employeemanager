package pl.bgnat.employeemanager.domain;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.bgnat.employeemanager.domain.request.EmployeeAddRequest;
import pl.bgnat.employeemanager.domain.request.EmployeeUpdateRequest;
import pl.bgnat.employeemanager.domain.response.EmployeeDeleteResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employeemanager/employee")
class EmployeeController {
	private final EmployeeService employeeService;

	EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/all")
	ResponseEntity<List<Employee>> getAllEmployees(){
		return ResponseEntity.ok(employeeService.findAllEmployees());
	}

	@GetMapping("/find/{id}")
	ResponseEntity<Employee> getEmployee(@PathVariable("id") Long id){
		return ResponseEntity.ok(employeeService.findEmployeeById(id));
	}

	@PostMapping("/add")
	ResponseEntity<Employee> addEmployee(@RequestBody @Validated EmployeeAddRequest employeeAddRequest){
		Employee employee = employeeService.addEmployee(employeeAddRequest);
		return new ResponseEntity<>(employee, HttpStatus.CREATED);
	}

	@PostMapping("/addAll")
	ResponseEntity<List<Employee>> addAllEmployees(@RequestBody List<EmployeeAddRequest> employeeAddRequest){
		List<Employee> employees = employeeService.addAllEmployees(employeeAddRequest);
		return new ResponseEntity<>(employees, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	ResponseEntity<Employee> updateEmployee(@RequestBody @Validated EmployeeUpdateRequest employeeUpdateRequest){
		Employee updatedEmployee = employeeService.updateEmployee(employeeUpdateRequest);
		return ResponseEntity.ok(updatedEmployee);
	}

	@DeleteMapping("/delete/{id}")
	ResponseEntity<EmployeeDeleteResponse> deleteEmployeeById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(employeeService.deleteEmployeeById(id));
	}
}
