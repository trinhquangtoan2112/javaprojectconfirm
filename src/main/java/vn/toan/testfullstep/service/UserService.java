package vn.toan.testfullstep.Service;

import vn.toan.testfullstep.controller.request.UserCreationRequest;
import vn.toan.testfullstep.controller.request.UserPasswordRequest;
import vn.toan.testfullstep.controller.request.UserUpdateRequest;
import vn.toan.testfullstep.controller.response.UserPageResponse;
import vn.toan.testfullstep.controller.response.UserResponse;

public interface UserService {
     UserPageResponse findAll(String keyword, String sort, int pageNumber, int pageSize);

     UserResponse findById(Long id);

     UserResponse findByUsername(String username);

     long saveUser(UserCreationRequest req);

     void update(UserUpdateRequest req);

     void changePassword(UserPasswordRequest req);

     void deleteUser(Long id);
}
