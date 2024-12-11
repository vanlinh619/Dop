package org.dop.module.startup;

import lombok.RequiredArgsConstructor;
import org.dop.entity.Startup;
import org.dop.entity.state.StartupName;
import org.dop.repository.StartupRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class StartupManagerImpl implements StartupManager {

    private final Map<String, Starter> startupMap;
    private final StartupRepository startupRepository;

    @Override
    public void startAll() {
        startupMap.forEach((name, starter) -> {
            if (startupRepository.findByName(StartupName.valueOf(name)).isEmpty()) {
                starter.start();
                Startup startup = Startup.builder()
                        .name(StartupName.valueOf(name))
                        .build();
                startupRepository.save(startup);
            }
        });
    }
}
