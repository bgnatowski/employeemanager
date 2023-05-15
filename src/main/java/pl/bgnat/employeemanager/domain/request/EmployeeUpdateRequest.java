package pl.bgnat.employeemanager.domain.request;

import org.springframework.lang.NonNull;

public record EmployeeUpdateRequest(
		@NonNull Long id,
		String name,
		String email,
		String jobTitle,
		String phoneNumber,
		String imageUrl) {
}
