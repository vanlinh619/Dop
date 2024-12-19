package org.dop.module.startup;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.dop.entity.Startup;
import org.dop.entity.state.StartupName;
import org.dop.repository.StartupRepository;
import org.springframework.security.access.hierarchicalroles.CycleInRoleHierarchyException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StartupManagerImpl implements StartupManager {

    private final List<Starter> starters;
    private final StartupRepository startupRepository;

    @Override
    public void startAll() {
        starters.stream()
                .sorted(Comparator.comparing(Starter::priority))
                .forEach((starter) -> {
                    if (startupRepository.findByName(StartupName.valueOf(starter.getName())).isEmpty()) {
                        starter.start();
                        Startup startup = Startup.builder()
                                .name(StartupName.valueOf(starter.getName()))
                                .build();
                        startupRepository.save(startup);
                    }
                });
    }

}
