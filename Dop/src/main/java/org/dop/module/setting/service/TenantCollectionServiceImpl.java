package org.dop.module.setting.service;

import lombok.RequiredArgsConstructor;
import org.dop.module.common.constant.RedisKey;
import org.dop.module.setting.entity.TenantCollection;
import org.dop.module.setting.repository.TenantCollectionRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TenantCollectionServiceImpl implements TenantCollectionService {

    private final TenantCollectionRepository tenantCollectionRepository;

    @CacheEvict(value = RedisKey.CACHE_TENANT, key = "'schemas'")
    @Override
    public void save(String tenant) {
        TenantCollection tenantCollection = TenantCollection.builder()
                .id(tenant)
                .build();
        tenantCollectionRepository.save(tenantCollection);
    }

    @Cacheable(value = RedisKey.CACHE_TENANT, key = "'schemas'")
    @Override
    public Set<String> getTenants() {
        return tenantCollectionRepository.getSchemas();
    }


}
