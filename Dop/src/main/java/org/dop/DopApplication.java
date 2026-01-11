package org.dop;

import lombok.RequiredArgsConstructor;
import org.dop.config.property.DopSettingProperties;
import org.dop.module.startup.StartupManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@RequiredArgsConstructor
public class DopApplication {

    private final DopSettingProperties dopSettingProperties;
    private final StartupManager startupManager;

    static void main(String[] args) {
        SpringApplication.run(DopApplication.class, args);
    }

    @EventListener
    public void runAfterStartup(ApplicationReadyEvent event) {
        startupManager.startTenantDefault();
//        startupManager.startNewDatasource("test");
    }

}
