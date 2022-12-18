package com.tass.project_tasc.controllers.admins;

import com.tass.project_tasc.controllers.BaseController;
import com.tass.project_tasc.database.entities.*;
import com.tass.project_tasc.database.entities.myenums.UserStatus;
import com.tass.project_tasc.model.ApiException;
import com.tass.project_tasc.model.BaseResponse;
import com.tass.project_tasc.services.UserService;
import com.tass.project_tasc.spec.Specifications;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(path = "/api/admins/users")
public class UsersController extends BaseController {
    @Autowired
    UserService userService;
    @GetMapping("")
    public ResponseEntity<BaseResponse> findAllUser(@RequestParam(value = "username", required = false) String username,
                                                    @RequestParam(value = "fullName", required = false) String fullName,
                                                    @RequestParam(value = "phone", required = false) String phone,
                                                    @RequestParam(value = "email", required = false) String email,
                                                    @RequestParam(value = "gender", required = false) String gender,
                                                    @RequestParam(value = "address", required = false) String address,
                                                    @RequestParam(value = "status", required = false) UserStatus status,
                                                    @RequestParam(value = "role", required = false) Role role,
                                                    @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) throws ApiException {
        Specification<User> specification = Specifications.userSpec(username, fullName, phone, email, gender, address, status, role, page, pageSize);
        return createdResponse(userService.findAllUser(specification, page, pageSize));

    }
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> findUserById(@PathVariable Long id) throws ApiException {
        return createdResponse(userService.findById(id));
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deleteUser(@PathVariable Long id) throws ApiException {
        return createdResponse(userService.deleteUser(id));
    }

    @PutMapping("/active/{id}")
    public ResponseEntity<BaseResponse> activeUser(@PathVariable Long id) throws ApiException {
        return createdResponse(userService.activeUser(id));
    }
    @PutMapping("/block/{id}")
    public ResponseEntity<BaseResponse> blockUser(@PathVariable Long id) throws ApiException {
        return createdResponse(userService.blockUser(id));
    }
}
