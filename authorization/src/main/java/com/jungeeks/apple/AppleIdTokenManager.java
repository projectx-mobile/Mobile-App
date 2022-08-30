package com.jungeeks.apple;

import com.jungeeks.apple.key.AppleClientPrivateKeyFactory;
import com.jungeeks.apple.user.AppleAuthorizationToken;

import java.io.IOException;
import java.io.InputStream;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class AppleIdTokenManager {

    private final static String CLIENT_ID = "Your client id";
    private static final String KEY_ID = "Your Key id";
    private static final String TEAM_ID = "Your team id";
    private static final String REDIRECT_URL = "Your redirect url";

    public static void main(String[] args) throws IOException, InvalidKeySpecException {
        //Generating your private key.
        //This could be just a string containing the key.
        InputStream pkStream = AppleIdTokenManager.class
                .getClassLoader().getResourceAsStream("your_pk_file.p8");
        AppleClientPrivateKeyFactory appleClientPrivateKeyFactory = new AppleClientPrivateKeyFactory();
        ECPrivateKey privateKey = appleClientPrivateKeyFactory.getEcPrivateKey(pkStream);

        //Creating provider instance.
        SecretGenerator secretGenerator = new SecretGenerator();
        AppleAuthProvider appleAuthProvider = new AppleAuthProvider(
                CLIENT_ID,
                KEY_ID,
                TEAM_ID,
                secretGenerator,
                privateKey,
                Arrays.asList(AppleUserScope.EMAIL, AppleUserScope.NAME),
                REDIRECT_URL
        );

        //We are ready to start using the provider.

        //Generate a url and navigate the user to it.
        String loginLink = appleAuthProvider.getLoginLink("Some form of state");

        //Once the user is redirected back to our domain get the "code" in the request.
        String authCode = "the code in the callback request";
        //Now we can authenticate the user.
        AppleAuthorizationToken initialToken = appleAuthProvider.makeNewAuthorisationTokenRequest(authCode);
        //After the authentication we should check (not more than once every once 24 hours) if the user still
        // logged in using "Sign in with Apple" by retrieving a refresh token.
        AppleAuthorizationToken refreshToken = appleAuthProvider.makeNewRefreshTokenRequest(initialToken
                .getRefreshToken());

    }
}
