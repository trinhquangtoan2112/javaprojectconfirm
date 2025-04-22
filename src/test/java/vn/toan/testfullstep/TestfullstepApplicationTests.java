package vn.toan.testfullstep;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import vn.toan.testfullstep.controller.AuthenticationController;
import vn.toan.testfullstep.controller.UserController;

@SpringBootTest
class TestfullstepApplicationTests {

    @InjectMocks
    private UserController userController;
    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(userController);
        Assertions.assertNotNull(authenticationController);
    }

}
