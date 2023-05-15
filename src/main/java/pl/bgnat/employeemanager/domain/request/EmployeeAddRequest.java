package pl.bgnat.employeemanager.domain.request;

import org.springframework.lang.NonNull;

public record EmployeeAddRequest(
		@NonNull String name,
		@NonNull String email,
		@NonNull String jobTitle,
		@NonNull String phoneNumber,
		@NonNull String imageUrl
) {
}
