package com.jungeeks.entity;

import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.entity.enums.USER_STATUS;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "sec_user")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firebaseId;
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

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private List<ClientApp> clientApps;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private ParentNotification parentNotifications;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private ChildNotification childNotifications;
}