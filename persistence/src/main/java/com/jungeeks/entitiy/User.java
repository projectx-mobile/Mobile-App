package com.jungeeks.entitiy;

import com.jungeeks.entitiy.enums.USER_ROLE;
import com.jungeeks.entitiy.enums.USER_STATUS;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "sec_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private Long points;
    private String name;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "family_id")
    private Family family;

    @Enumerated(value = EnumType.STRING)
    private USER_ROLE user_role;

    @Enumerated(value = EnumType.STRING)
    private USER_STATUS user_status;

    @ManyToMany(mappedBy = "users")
    private List<FamilyTask> tasks;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_photo", joinColumns = @JoinColumn(name = "user_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "path", column = @Column(name = "path"))})
    private List<Photo> photo;

    @OneToOne(mappedBy = "user")
    private ConfirmationToken confirmationToken;

    @OneToOne(mappedBy = "user", optional = false, cascade = CascadeType.PERSIST)
    private SocialCredentials socialCredentials;

    public User(Long id, String name, USER_ROLE user_role) {
        this.id = id;
        this.name = name;
        this.user_role = user_role;
    }
}