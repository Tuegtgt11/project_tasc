package com.tass.project_tasc.controllers;

import com.tass.project_tasc.database.entities.User;
import com.tass.project_tasc.model.ApiException;
import com.tass.project_tasc.model.BaseResponse;
import com.tass.project_tasc.model.ERROR;
import com.tass.project_tasc.model.request.LoginRequest;
import com.tass.project_tasc.model.request.RegisterRequest;
import com.tass.project_tasc.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class AccountController extends BaseController{
    @Autowired
    UserService userService;
    @PostMapping("/register")
    public ResponseEntity<BaseResponse> register(@Valid @RequestBody RegisterRequest registerRequest) throws ApiException {
        return createdResponse(userService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) throws ApiException{
        return createdResponse(userService.login(loginRequest, request));
    }


    @GetMapping("/users/profile")
    public ResponseEntity userPanel(Principal principal, Model model) {
        Optional<User> user = userService.findByNameActive(principal.getName());

        if (user != null) {
            System.out.println("Profile" + user);
            model.addAttribute("user", user);
        } else {
            return ResponseEntity.badRequest().body("error/404");
        }

        return ResponseEntity.ok(user);
    }
}
