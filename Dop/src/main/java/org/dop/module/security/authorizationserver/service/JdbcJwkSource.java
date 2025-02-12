package org.dop.module.security.authorizationserver.service;

import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.dop.module.constant.RedisKey;
import org.dop.repository.KeySourceRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcJwkSource implements JWKSource<SecurityContext> {

    private final KeySourceRepository keySourceRepository;

    @Cacheable(value = RedisKey.CACHE_KEY_SOURCE, key = "1")
    @Override
    public List<JWK> get(JWKSelector jwkSelector, SecurityContext context) throws KeySourceException {
        try {
            return keySourceRepository.findAll().stream()
                    .map(keySource -> {
                        try {
                            return JWK.parse(keySource.getSource());
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toList();
        } catch (Exception ex) {
            throw new KeySourceException(ex);
        }
    }
}
