package com.tass.project_tasc.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotEmpty(message = "password missing!")
    private String password;
    @NotEmpty(message = "confirm New Pass missing!")
    private String rePass;
    @NotEmpty(message = "fullName missing!")
    private String fullName;
    @NotEmpty(message = "phone missing!")
    private String phone;
    @NotEmpty(message = "email missing!")
    private String email;
    @Column(columnDefinition = "text")
    @NotEmpty(message = "avatar missing!")
    private String avatar;
    @NotEmpty(message = "gender missing!")
    private String gender;
}
