package com.jungeeks.entity;

import com.jungeeks.entity.enums.NOTIFICATION_PERIOD;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode
@ToString
@Entity(name = "child_notification")
public class ChildNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean allOff;
    private boolean newTask;
    private boolean confirmTask;
    private boolean confirmReward;
    private boolean penaltyAndBonus;

    @Enumerated(EnumType.STRING)
    private NOTIFICATION_PERIOD notificationPeriod;
}
