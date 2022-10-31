package com.jungeeks.entity;

import com.jungeeks.entity.enums.NOTIFICATION_PERIOD;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
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

    private boolean taskReminder;
    @Enumerated(EnumType.STRING)
    private NOTIFICATION_PERIOD notificationPeriod;
}
