package com.jungeeks.entitiy;


import lombok.Data;
import javax.persistence.*;
@Entity
@Data
public class SocialCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;
    
    private Long sub;
    private Long socialId;
}
