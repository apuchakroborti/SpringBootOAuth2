package com.apu.springmvc.springsecuritymvc.business;

import com.apu.springmvc.springsecuritymvc.models.AuthenticationRequest;
import com.apu.springmvc.springsecuritymvc.models.AuthenticationResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GetPostRequestHandler {

    public String createGet(String jwtToken) {
        // request url
        //String url = "http://localhost:9090/hello";
        //for wildfly
        String url="http://127.0.0.1:8080/spring-security-jwt-0.0.1-SNAPSHOT/hello";
        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> map=new HashMap<>();
        map.put("Authorization","Bearer "+jwtToken);
        headers.setAll(map);

        ResponseEntity<String> response = null;

        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
        };

        HttpEntity<AuthenticationRequest> requestEntity = new HttpEntity<AuthenticationRequest>(new AuthenticationRequest("apucsedu","@#$%apu123"), headers);
        response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, typeRef);



        if(response!=null){
            System.out.println("response from hello:"+response.getBody());
            return response.getBody();
        }else{
            System.out.println("response is null");
            return null;
        }


    }




    public String createPost() {

        try {
            // request url
            //String url = "http://localhost:9090/authenticate";
            //for wildfly
            String url="http://127.0.0.1:8080/spring-security-jwt-0.0.1-SNAPSHOT/authenticate";
            // create an instance of RestTemplate
            RestTemplate restTemplate = new RestTemplate();

            // create headers
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);

            ResponseEntity<AuthenticationResponse> response = null;

            ParameterizedTypeReference<AuthenticationResponse> typeRef = new ParameterizedTypeReference<AuthenticationResponse>() {
            };

            HttpEntity<AuthenticationRequest> requestEntity = new HttpEntity<AuthenticationRequest>(new AuthenticationRequest("apucsedu", "@#$%apu123"), headers);
            response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, typeRef);

            if(response!=null){
                System.out.println("response:"+response.getBody().getJwt());
                GetPostRequestHandler gph=new GetPostRequestHandler();
                String res=gph.createGet(response.getBody().getJwt());
                if(res!=null){
                    return res;
                }
            }else{
                System.out.println("response is null");
                return null;

            }


        }catch (Exception e){
            System.out.println("exception occur during GET request!!!");
            return null;
        }
        return null;
    }

}
