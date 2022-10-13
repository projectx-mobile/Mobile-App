package com.jungeeks.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties("security.firebase-props")
@Component
public class FirebaseProperties {

    @Value("${security.firebase-props.session-expiry-in-days}")
    int sessionExpiryInDays;

    @Value("${security.firebase-props.database-url}")
    String databaseUrl;

    @Value("${security.firebase-props.enable-strict-server-session}")
    boolean enableStrictServerSession;

    @Value("${security.firebase-props.enable-check-session-revoked}")
    boolean enableCheckSessionRevoked;

    @Value("${security.firebase-props.enable-logout-everywhere}")
    boolean enableLogoutEverywhere;
}