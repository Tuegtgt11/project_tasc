package com.tass.project_tasc.controllers.users;

import com.tass.project_tasc.controllers.BaseController;
import com.tass.project_tasc.model.ApiException;
import com.tass.project_tasc.model.BaseResponse;
import com.tass.project_tasc.model.request.UserRequest;
import com.tass.project_tasc.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(path = "/api/users")
public class ProfileController extends BaseController {

    @Autowired
    UserService userService;

    @PutMapping("/update/{id}")
    public ResponseEntity<BaseResponse> updateUser(@RequestBody UserRequest userRequest, Principal principal, @PathVariable Long id) throws ApiException {
        return createdResponse(userService.updateProfile(userRequest,principal, id));
    }
}
