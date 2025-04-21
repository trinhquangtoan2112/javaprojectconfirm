package vn.toan.testfullstep.service;

import vn.toan.testfullstep.controller.request.UserCreationRequest;
import vn.toan.testfullstep.controller.request.UserPasswordRequest;
import vn.toan.testfullstep.controller.request.UserUpdateRequest;
import vn.toan.testfullstep.controller.response.UserPageResponse;
import vn.toan.testfullstep.controller.response.UserResponse;
import vn.toan.testfullstep.model.UserEntity;

public interface UserService {
     UserPageResponse findAll(String keyword, String sort, int pageNumber, int pageSize);

     UserEntity findById(Long id);

     UserResponse findByUsername(String username);

     long saveUser(UserCreationRequest req);

     void update(UserUpdateRequest req);

     void changePassword(UserPasswordRequest req);

     void deleteUser(Long id);
}
