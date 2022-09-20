package com.jungeeks.firebase;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirebaseConfig.class);

    @PostConstruct
    public void onStart() {
        LOGGER.info("Initializing Firebase App...");
        try {
            this.initializeFirebaseApp();
        } catch (IOException e) {
            LOGGER.error("Initializing Firebase App {0}", e);
        }
    }

    private void initializeFirebaseApp() throws IOException {

        if (FirebaseApp.getApps() == null || FirebaseApp.getApps().isEmpty()) {
            InputStream serviceAccount = FirebaseConfig.class.getResourceAsStream("/firebase-service-credentials.json");
            assert serviceAccount != null;
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();

            FirebaseApp.initializeApp(options);
        }

    }
}
