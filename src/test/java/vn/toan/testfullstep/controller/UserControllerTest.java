package vn.toan.testfullstep.controller;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import vn.toan.testfullstep.common.Gender;
import vn.toan.testfullstep.controller.response.UserPageResponse;
import vn.toan.testfullstep.controller.response.UserResponse;
import vn.toan.testfullstep.service.JwtService;
import vn.toan.testfullstep.service.UserService;
import vn.toan.testfullstep.service.UserServiceDetail;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserServiceDetail userServiceDetail;

    @MockitoBean
    private JwtService jwtService;

    private static UserResponse user1;
    private static UserResponse user2;

    @BeforeAll
    static void beforeAll() {
        // Chuẩn bị dữ liệu
        user1 = new UserResponse();
        user1.setId(1L);
        user1.setFirstName("Tay");
        user1.setLastName("Java");
        user1.setGender("MALE");
        user1.setBirthday(new Date());
        user1.setEmail("quoctay87@gmail.com");
        user1.setPhone("0975118228");
        user1.setUsername("user1");

        user2 = new UserResponse();
        user2.setId(2L);
        user2.setFirstName("John");
        user2.setLastName("Doe");
        user2.setGender("FEMALE");
        user2.setBirthday(new Date());
        user2.setEmail("user2@gmail.com");
        user2.setPhone("0123456789");
        user2.setUsername("user2");
    }

    @Test
    @WithMockUser(authorities = {"SYSADMIN"})
    void shouldGetUserList() throws Exception {
        List<UserResponse> userListResponses = List.of(user1, user2);

        UserPageResponse userPageResponse = new UserPageResponse();
        userPageResponse.setPageNumber(0);
        userPageResponse.setPageSize(20);
        userPageResponse.setTotalPages(1);
        userPageResponse.setTotalElements(2);
        userPageResponse.setUsers(userListResponses);

        when(userService.findAll(null, null, 0, 10))
                .thenReturn(userPageResponse);

        // Perform the test
        mockMvc.perform(get("/user/list")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(200)))
                .andExpect(jsonPath("$.message", is("Lấy dữ liệu thành công")))
                .andExpect(jsonPath("$.data.pageNumber", is(0)))
                .andExpect(jsonPath("$.data.pageSize", is(20)))
                .andExpect(jsonPath("$.data.totalPages", is(1)))
                .andExpect(jsonPath("$.data.totalElements", is(2)))
                .andExpect(jsonPath("$.data.users[0].id", is(1)))
                .andExpect(jsonPath("$.data.users[1].id", is(2)));

    }

    @Test
    @WithMockUser(authorities = {"admin", "manager"})
    void shouldGetUserListWithoutSysAdmin() throws Exception {
        List<UserResponse> userListResponses = List.of(user1, user2);

        UserPageResponse userPageResponse = new UserPageResponse();
        userPageResponse.setPageNumber(0);
        userPageResponse.setPageSize(20);
        userPageResponse.setTotalPages(1);
        userPageResponse.setTotalElements(2);
        userPageResponse.setUsers(userListResponses);

        when(userService.findAll(null, null, 0, 20)).thenReturn(userPageResponse);

        // Perform the test
        mockMvc.perform(get("/user/list")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(403)))
                .andExpect(jsonPath("$.message", is("Access Denied")))
                .andExpect(jsonPath("$.error", is("Forbidden")));
        ;

    }
}
