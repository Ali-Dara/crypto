package org.dara.cryptosecurity.config;

import lombok.RequiredArgsConstructor;
import org.dara.cryptosecurity.properties.JwtProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@AutoConfiguration
@EnableConfigurationProperties(JwtProperties.class)
@RequiredArgsConstructor
public class JwtDecoderConfiguration {

    private final JwtProperties jwtProperties;

    @Bean
    @ConditionalOnProperty(
            prefix = "crypto.security.jwt",
            name = "secret-key"
    )
    public JwtDecoder jwtDecoder() {
        SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(jwtProperties.secretKey()), "HmacSHA256");
        return NimbusJwtDecoder
                .withSecretKey(key)
                .build();
    }
}
