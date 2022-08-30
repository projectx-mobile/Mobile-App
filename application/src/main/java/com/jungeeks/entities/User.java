package com.jungeeks.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sec_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;

    @OneToOne(fetch = FetchType.EAGER)
    private Password pass;

//    private Photo photo;

    private String name;
    private Long points;

    @Enumerated(value = EnumType.STRING)
    private USER_ROLE user_role;
    @Enumerated(value = EnumType.STRING)
    private USER_STATUS user_status;
    private String familyId;

}
