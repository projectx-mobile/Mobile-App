package com.jungeeks.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.jungeeks.email.entity.User;
import com.jungeeks.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;


@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public User save(User user) throws Exception {
        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> apiFuture = db.collection("user").document(user.getUid()).set(user, SetOptions.merge());
        WriteResult writeResult = apiFuture.get();
        LOGGER.info("Succesfully saved, updated time: {}", writeResult.getUpdateTime());
        userRepository.save(user);
        return user;
    }

    public User get(String uid) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<DocumentSnapshot> apiFuture = db.collection("user").document(uid).get();

        DocumentSnapshot document = apiFuture.get();
        if (document.exists()) {
            LOGGER.info("User found: {}", uid);
            return document.toObject(User.class);
        }

        LOGGER.info("User not found: {}", uid);
        return null;
    }
}
