package com.gingermadfire.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    //@Column(name = "first_name")
    private String firstName;

    @NotBlank
   // @Column(name = "first_name")
    private String lastName;

    @Email
    private String email;

    @NotBlank
    private String password;

    //@Column(name = "vip_status")
    private boolean vipStatus;


}
