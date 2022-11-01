package com.jungeeks.entity;

import com.jungeeks.entity.enums.NOTIFICATION_PERIOD;
import lombok.*;

import javax.persistence.*;
import java.util.Map;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode
@ToString
@Entity(name = "parent_notification")
public class ParentNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean allOff;
    private boolean newTaskStatus;
    private boolean newRequest;
    private boolean newReward;
}
