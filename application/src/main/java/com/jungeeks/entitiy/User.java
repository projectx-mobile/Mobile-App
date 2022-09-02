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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
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
    private String familyId;

//    @OneToOne(mappedBy = "user")
//    private Password pass;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "user")
    private List<Request> requests;

    @ManyToMany
    @JoinTable(name = "family_task",
            joinColumns = {@JoinColumn(name = "family_id", referencedColumnName = "familyId")},
            inverseJoinColumns = {@JoinColumn(name = "task_id", referencedColumnName = "id")})
    private List<Task> tasks;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_photo", joinColumns = @JoinColumn(name = "user_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "path", column = @Column(name = "path"))})
    private List<Photo> photo;

}