package org.dop.module.setting.service;

import lombok.RequiredArgsConstructor;
import org.dop.module.constant.RedisKey;
import org.dop.module.setting.entity.SchemaCollection;
import org.dop.module.setting.repository.SchemaCollectionRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SchemaCollectionServiceImpl implements SchemaCollectionService {

    private final SchemaCollectionRepository schemaCollectionRepository;

    @CacheEvict(value = RedisKey.CACHE_TENANT, key = "'schemas'")
    @Override
    public void save(String schema) {
        SchemaCollection schemaCollection = SchemaCollection.builder()
                .id(schema)
                .build();
        schemaCollectionRepository.save(schemaCollection);
    }

    @Cacheable(value = RedisKey.CACHE_TENANT, key = "'schemas'")
    @Override
    public Set<String> getSchemas() {
        return schemaCollectionRepository.getSchemas();
    }


}
