package com.jungeeks.entitiy;

import com.jungeeks.entitiy.enums.TASK_STATUS;
import com.jungeeks.entitiy.enums.TASK_TYPE;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @Enumerated(EnumType.STRING)
    @Column(name = "task_type")
    private TASK_TYPE taskType;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "task_photo", joinColumns = @JoinColumn(name = "task_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "path", column = @Column(name = "path"))
    })
    private List<Photo> photos;

}