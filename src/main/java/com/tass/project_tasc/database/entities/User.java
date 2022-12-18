package com.tass.project_tasc.database.entities;

import com.tass.project_tasc.database.entities.base.BaseEntity;
import com.tass.project_tasc.database.entities.myenums.UserStatus;
import javax.persistence.*;
import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private String phone;
    private String email;
    @Column(columnDefinition = "text")
    private String avatar;
    @Enumerated(EnumType.ORDINAL)
    private UserStatus status;
    private String gender;
    private String address;
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;
}
