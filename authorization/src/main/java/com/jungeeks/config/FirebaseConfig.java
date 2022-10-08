package com.jungeeks.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.jungeeks.entity.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Autowired
    private SecurityProperties secProps;

    @Autowired
    private FirebaseConfigProperties firebaseConfigProperties;


    @Primary
    @Bean
    public void firebaseInit() {
        InputStream inputStream = null;
        try {
            inputStream = new ClassPathResource("firebase_config.json").getInputStream();//TODO:set credentials: https://medium.com/google-cloud/kubernetes-configmaps-and-secrets-with-firebase-426e5f4c8a36
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        try {

//            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//            String s = ow.writeValueAsString(firebaseConfigProperties);
//            InputStream targetStream = new ByteArrayInputStream(s.getBytes());

//            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setCredentials(GoogleCredentials.fromStream(inputStream))
//                    .build();

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
            System.out.println("Firebase Initialize");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}