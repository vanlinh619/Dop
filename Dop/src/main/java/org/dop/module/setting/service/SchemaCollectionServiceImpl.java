package org.dop.module.setting.service;

import lombok.RequiredArgsConstructor;
import org.dop.module.setting.entity.SchemaCollection;
import org.dop.module.setting.repository.SchemaCollectionRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SchemaCollectionServiceImpl implements SchemaCollectionService {

    private final SchemaCollectionRepository schemaCollectionRepository;

    @Override
    public void save(String schema) {
        SchemaCollection schemaCollection = SchemaCollection.builder()
                .id(schema)
                .build();
        schemaCollectionRepository.save(schemaCollection);
    }

    @Override
    public Set<String> getSchemas() {
        return schemaCollectionRepository.getSchemas();
    }


}
