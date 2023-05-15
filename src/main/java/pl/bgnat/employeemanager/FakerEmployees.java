package pl.bgnat.employeemanager;

import com.github.javafaker.Faker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FakerEmployees {
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		Faker faker = Faker.instance();

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("E:\\glowneRepo\\employeemanager\\src\\main\\resources\\templates\\data.txt"))){
			for (int i = 0; i < 9; i++) {
				sb = new StringBuilder();
				String name = faker.name().name();
				String emailAddress = faker.internet().emailAddress();
				String jobTitle = faker.job().title();
				String phoneNumber = faker.phoneNumber().phoneNumber();
				String image = "https://bootdey.com/img/Content/avatar/avatar"+(i+1)+".png";
				sb.append("{\n")
						.append("\t\"name\":").append("\"").append(name).append("\",\n")
						.append("\t\"email\":").append("\"").append(emailAddress).append("\",\n")
						.append("\t\"jobTitle\":").append("\"").append(jobTitle).append("\",\n")
						.append("\t\"phoneNumber\":").append("\"").append(phoneNumber).append("\",\n")
						.append("\t\"imageUrl\":").append("\"").append(image).append("\"\n")
						.append("},\n");
				writer.write(sb.toString());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}



	}
}
