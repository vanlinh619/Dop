package org.dop.module.startup.key;

import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import org.dop.entity.KeySource;
import org.dop.entity.state.AlgorithmKey;
import org.dop.module.common.constant.RedisKey;
import org.dop.module.startup.Starter;
import org.dop.module.startup.StartupName;
import org.dop.repository.KeySourceRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * Generate key for authorization server
 */
@Service(StartupName.KEY_SOURCE)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class KeySourceStarter implements Starter {

    private final KeySourceRepository keySourceRepository;

    @CacheEvict(value = RedisKey.CACHE_KEY_SOURCE, key = "T(org.dop.module.tenant.context.TenantContext).getTenant()")
    @Override
    public void start() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();

        String json = rsaKey.toJSONString();

        /// Save the key to the database
        KeySource keySource = KeySource.builder()
                .algorithm(AlgorithmKey.RSA)
                .source(json)
                .build();
        keySourceRepository.save(keySource);
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(AlgorithmKey.RSA.name());
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }
}
