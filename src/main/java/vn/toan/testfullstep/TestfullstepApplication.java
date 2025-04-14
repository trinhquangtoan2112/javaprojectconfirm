package vn.toan.testfullstep;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class TestfullstepApplication {
	@Value("${jwt.sercetkey}")
	private String KEY;

	public static void main(String[] args) {
		SpringApplication.run(TestfullstepApplication.class, args);
	}

}
