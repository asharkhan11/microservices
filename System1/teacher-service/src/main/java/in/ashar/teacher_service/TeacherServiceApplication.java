package in.ashar.teacher_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TeacherServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeacherServiceApplication.class, args);
	}

}
