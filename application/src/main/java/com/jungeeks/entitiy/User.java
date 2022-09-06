package com.jungeeks.entitiy;

import com.jungeeks.entitiy.enums.USER_ROLE;
import com.jungeeks.entitiy.enums.USER_STATUS;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sec_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;

    @Enumerated(value = EnumType.STRING)
    private USER_ROLE user_role;

    @Enumerated(value = EnumType.STRING)
    private USER_STATUS user_status;
    private Long points;
    private String name;

    @ManyToOne
    private Family familyId;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_photo", joinColumns = @JoinColumn(name = "user_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "path", column = @Column(name = "path"))})
    private List<Photo> photo;

}