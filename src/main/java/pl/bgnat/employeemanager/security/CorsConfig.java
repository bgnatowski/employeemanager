package pl.bgnat.employeemanager.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {
	@Value("#{'${cors.allowed-origins}'.split(',')}")
	private List<String> allowedOrigins;

	@Value("#{'${cors.allowed-methods}'.split(',')}")
	private List<String> allowedMethods;

	@Value("#{'${cors.allowed-headers}'.split(',')}")
	private List<String> allowedHeaders;

	@Value("#{'${cors.exposed-headers}'.split(',')}")
	private List<String> expectedHeaders;

	@Bean
	public CorsFilter corsFilter(){
		CorsConfiguration configuration  = new CorsConfiguration();
		configuration.setAllowedOrigins(allowedOrigins);
		configuration.setAllowedMethods(allowedMethods);
		configuration.setAllowedHeaders(allowedHeaders);
		configuration.setExposedHeaders(expectedHeaders);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return new CorsFilter(source);
	}
}
