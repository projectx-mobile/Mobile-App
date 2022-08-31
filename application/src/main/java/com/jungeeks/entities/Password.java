package com.jungeeks.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "sec_pass")
public class Password implements Serializable {
    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    private User id;
    private String hash;
    private String salt;
}