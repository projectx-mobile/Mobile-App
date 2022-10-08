package com.jungeeks.test;

import com.jungeeks.test.dto.SignInResponseDTO;
import com.jungeeks.test.dto.SignUpResponseDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TestService {
    public SignUpResponseDTO signUp(String email,String password){

        String url="https://identitytoolkit.googleapis.com/v1/accounts:signUp?key={key}";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Map<String ,Object> body = new HashMap<>();
        body.put("email",email);
        body.put("password",password);
        body.put("returnSecureToken",true);

        Map<String ,Object> params = new HashMap<>();
        params.put("key","AIzaSyCIMiR045t2Z_F-FhVPhpcfodQ3fSTZiXU");

        HttpEntity<Map<String, Object>> mapHttpEntity = new HttpEntity<>(body, httpHeaders);

//        ResponseEntity<SignUpResponseDTO> response = restTemplate.postForEntity("https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyPassword&key={key}", mapHttpEntity, SignUpResponseDTO.class, params);
        ResponseEntity<SignUpResponseDTO> response = restTemplate.postForEntity(url, mapHttpEntity, SignUpResponseDTO.class, params);
        return response.getBody();
    }

    public SignInResponseDTO signIn(String email,String password){

        String url="https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key={key}";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Map<String ,Object> body = new HashMap<>();
        body.put("email",email);
        body.put("password",password);
        body.put("returnSecureToken",true);

        Map<String ,Object> params = new HashMap<>();
        params.put("key","AIzaSyCIMiR045t2Z_F-FhVPhpcfodQ3fSTZiXU");

        HttpEntity<Map<String, Object>> mapHttpEntity = new HttpEntity<>(body, httpHeaders);

//        ResponseEntity<SignUpResponseDTO> response = restTemplate.postForEntity("https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyPassword&key={key}", mapHttpEntity, SignUpResponseDTO.class, params);
        ResponseEntity<SignInResponseDTO> response = restTemplate.postForEntity(url, mapHttpEntity, SignInResponseDTO.class, params);
        return response.getBody();
    }

}
