package me.ilias.backoffice.config;

import feign.RequestInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
@AllArgsConstructor
public class OAuthFeignClientConf {
    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    OAuth2AuthorizedClientManager authorizedClientManager() {
        OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials()
                .build();
        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(clientRegistrationRepository, oAuth2AuthorizedClientService);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
        return authorizedClientManager;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("keycloak");
        OAuthClientCredentialsFeignManager clientCredentialsFeignManager =
                new OAuthClientCredentialsFeignManager(authorizedClientManager(), clientRegistration);
        return requestTemplate -> requestTemplate.header("Authorization", "Bearer " + clientCredentialsFeignManager.getAccessToken());
    }

}
