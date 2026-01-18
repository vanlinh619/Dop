package org.dop.module.startup;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dop.entity.Startup;
import org.dop.repository.StartupRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Log4j2
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class StartupManagerImpl implements StartupManager {

    private final List<Starter> starters;
    private final StartupRepository startupRepository;

    @Override
    public void startAll() {
        starters.stream()
                .sorted(Comparator.comparing(Starter::priority))
                .forEach((starter) -> {
                    if (starter.alwaysStart()) {
                        starter.start();
                    } else if (startupRepository.findByName(starter.getName()).isEmpty()) {
                        log.info("Startup with name {}", starter.getName());
                        starter.start();
                        Startup startup = Startup.builder()
                                .name(starter.getName())
                                .priority(starter.priority())
                                .build();
                        startupRepository.save(startup);
                    }
                });
    }

}
