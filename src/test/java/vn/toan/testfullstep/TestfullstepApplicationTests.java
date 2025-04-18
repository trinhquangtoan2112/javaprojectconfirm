package vn.toan.testfullstep;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import vn.toan.testfullstep.controller.UserController;

@SpringBootTest
class TestfullstepApplicationTests {

	@InjectMocks
	private UserController user;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(user);
	}

}
