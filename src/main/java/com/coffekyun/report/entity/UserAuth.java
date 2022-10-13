package com.coffekyun.report.entity;

import com.coffekyun.report.model.enums.ApplicationUserRole;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_auth")
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull  @Enumerated(EnumType.STRING)
    private ApplicationUserRole userRole;

    private boolean isEnabled = true;

}
