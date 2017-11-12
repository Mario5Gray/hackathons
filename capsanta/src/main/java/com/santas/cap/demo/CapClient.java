package com.santas.cap.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class CapClient {

    @Autowired
    RestTemplate restTemplate;

    @Value("${capone.app.url}")
    String url;

    @Value("${capone.token.url}")
    String tokenUrl;

    @Value("${capone.auth.url}")
    String authUrl;

    @Value("${capone.client.id}")
    String clientId;

    @Value("${capone.client.secret}")
    String clientSecret;

    @Value("${redirect.url}")
    String appRedirectUrl;

    public CapAuthToken accessToken() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(
                        new LinkedMultiValueMap<String, String>() {{
                            add("client_id", clientId);
                            add("client_secret", clientSecret);
                            add("grant_type", "client_credentials");
                        }},
                        headers
                );

        return restTemplate.postForEntity(tokenUrl,
                request,
                CapAuthToken.class)
                .getBody();
    }

    public String getRedirectUrl() {
    /*
    https://apiit.capitalone.com/oauth2/authorize?
    client_id=your_client_id&redirect_uri=your_redirect_uri&
    scope=signin openid&response_type=code&state=your_state_value

     */
    String simpleState="1234";
    String url = "%s?client_id=%s&redirect_uri=%s&scope=signin openid&response_type=code&state=%s";
    return String.format(url, this.authUrl, this.clientId, this.appRedirectUrl, simpleState);
    }
}


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@NoArgsConstructor
@AllArgsConstructor
class CapAuthToken {
    String issuedAt;
    String tokenType;
    Long expiresIn;
    String accessToken;
}