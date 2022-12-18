package com.tass.project_tasc.services;

import com.tass.project_tasc.database.entities.Product;
import com.tass.project_tasc.database.entities.Role;
import com.tass.project_tasc.database.entities.User;
import com.tass.project_tasc.database.entities.myenums.UserStatus;
import com.tass.project_tasc.database.repository.RoleRepository;
import com.tass.project_tasc.database.repository.UserRepository;
import com.tass.project_tasc.model.ApiException;
import com.tass.project_tasc.model.BaseResponse;
import com.tass.project_tasc.model.ERROR;
import com.tass.project_tasc.model.dto.CredentialDTO;
import com.tass.project_tasc.model.request.LoginRequest;
import com.tass.project_tasc.model.request.RegisterRequest;
import com.tass.project_tasc.model.request.UserRequest;
import com.tass.project_tasc.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    final PasswordEncoder passwordEncoder;

    public boolean matchPassword(String rawPassword, String hashPassword) {
        return passwordEncoder.matches(rawPassword, hashPassword);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsernameAndStatus(username, UserStatus.ACTIVE);
        User user = userOptional.orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());
        authorities.add(authority);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }


    public CredentialDTO generateCredential(UserDetails userDetail, HttpServletRequest request) {
        String accessToken = JWTUtil.generateToken(userDetail.getUsername(),
                userDetail.getAuthorities().iterator().next().getAuthority(),
                request.getRequestURI(),
                JWTUtil.ONE_DAY * 7);

        String refreshToken = JWTUtil.generateToken(userDetail.getUsername(),
                userDetail.getAuthorities().iterator().next().getAuthority(),
                request.getRequestURI(),
                JWTUtil.ONE_DAY * 14);
        return new CredentialDTO(accessToken, refreshToken);
    }

    public BaseResponse login(LoginRequest loginRequest, HttpServletRequest request) throws ApiException {
        UserDetails userDetails = loadUserByUsername(loginRequest.getUsername());
        if (this.matchPassword(loginRequest.getPassword(), userDetails.getPassword())) {
            CredentialDTO credentialDTO = this.generateCredential(userDetails, request);
            return new BaseResponse(200, "login success!", credentialDTO);
        }
        return new BaseResponse(ERROR.SYSTEM_ERROR);
    }

    public BaseResponse register(RegisterRequest registerRequest) throws ApiException {
        if (!registerRequest.getPassword().equals(registerRequest.getRePass())) {
            throw new ApiException(ERROR.SYSTEM_ERROR, "rePass not correct");
        }
        Role role = roleRepository.findByName("USER");
        User user = User.builder()
                .fullName(registerRequest.getFullName())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .phone(registerRequest.getPhone())
                .role(role)
                .gender("male")
                .address("HTM")
                .status(UserStatus.ACTIVE)
                .avatar("https://as1.ftcdn.net/v2/jpg/03/53/11/00/1000_F_353110097_nbpmfn9iHlxef4EDIhXB1tdTD0lcWhG9.jpg?fbclid=IwAR0IeeX4fdIKXrmKyVLdn3mGEhAkNFdQv3MH7f4P5okIBtG_Rx_fqonZjss")
                .build();
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setCreatedBy(user.getFullName());
        user.setUpdatedBy(user.getFullName());
        userRepository.save(user);
        return new BaseResponse(200, "register success!", new String[]{"username:" + registerRequest.getUsername(), "password:" + registerRequest.getPassword()});
    }

    // dùng chung
    public Optional<User> findByNameActive(String username) {
        return userRepository.findByUsernameAndStatus(username, UserStatus.ACTIVE);
    }
    // danh cho admin
    public BaseResponse findById(Long id) throws ApiException {
        return new BaseResponse(200, "SUCCESS", userRepository.findById(id));
    }

    // dùng cho admin
    public BaseResponse findAllUser(Specification<User> specification, Integer page, Integer pageSize) throws ApiException {
        return new BaseResponse(200, "SUCCESS", userRepository.findAll(specification, PageRequest.of(page, pageSize, Sort.by("updatedAt").descending())));
    }

    // dung chung
    public BaseResponse updateProfile(UserRequest userRequest, Principal principal, Long id) throws ApiException {
        Optional<User> optionalUser = userRepository.findById(id);
        Optional<User> optionalPrincipal = userRepository.findByUsernameAndStatus(principal.getName(), UserStatus.ACTIVE);
        if (optionalPrincipal.isEmpty()){
            throw  new ApiException(ERROR.SYSTEM_ERROR, "User not found!");
        }
        User existUser= optionalUser.get();
        existUser.setFullName(userRequest.getFullName());
        existUser.setGender(userRequest.getGender());
        existUser.setAvatar(userRequest.getAvatar());
        existUser.setEmail(userRequest.getEmail());
        existUser.setPhone(userRequest.getPhone());
        if (!userRequest.getPassword().equals(userRequest.getRePass())){
            throw new ApiException(ERROR.INVALID_PARAM, "Confirm New Pass not found!");
        }
        existUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        existUser.setCreatedAt(LocalDateTime.now());
        existUser.setUpdatedAt(LocalDateTime.now());
        existUser.setCreatedBy(userRequest.getFullName());
        existUser.setUpdatedBy(userRequest.getFullName());
        userRepository.save(existUser);
        return new BaseResponse(200, "Update profile success!", existUser);
    }
    // for admin
    public BaseResponse deleteUser(Long id) throws ApiException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ApiException(ERROR.SYSTEM_ERROR, "user not found!");
        }
        User existUser = optionalUser.get();
        existUser.setStatus(UserStatus.DELETED);
        userRepository.save(existUser);
        return new BaseResponse(200, "Deleted!");
    }
    // for admin
    public BaseResponse blockUser(Long id) throws ApiException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ApiException(ERROR.SYSTEM_ERROR, "user not found!");
        }
        User existUser = optionalUser.get();
        existUser.setStatus(UserStatus.BLOCKED);
        userRepository.save(existUser);
        return new BaseResponse(200, "Blocked!");
    }
    // for admin
    public BaseResponse activeUser(Long id) throws ApiException{
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ApiException(ERROR.SYSTEM_ERROR, "user not found!");
        }
        User existUser = optionalUser.get();
        existUser.setStatus(UserStatus.ACTIVE);
        userRepository.save(existUser);
        return new BaseResponse(200, "Done!");
    }

}
