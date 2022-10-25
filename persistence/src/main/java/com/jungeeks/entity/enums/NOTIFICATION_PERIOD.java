package com.jungeeks.entity.enums;

import java.time.LocalDateTime;

public enum NOTIFICATION_PERIOD {

    IN_MOMENT(0L),
    FIVE_MINUTES(5L),
    TEN_MINUTES(10L),
    FIFTEEN_MINUTES(15L),
    THIRTY_MINUTES(30L),
    ONE_HOUR(60L),
    TWO_HOUR(120L),
    ONE_DAY(1440L),
    TWO_DAY(2880L),
    ONE_WEEK(10080L);

    private Long delay;

    NOTIFICATION_PERIOD(Long delay) {
        this.delay = delay;
    }
}
