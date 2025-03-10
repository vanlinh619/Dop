package org.dop.module.startup;

import org.springframework.stereotype.Service;


public interface Starter {
    void start();

    default int priority() {
        return 0;
    };

    default String getName() {
        Service service = this.getClass().getAnnotation(Service.class);
        return service.value();
    }

    default boolean alwaysStart() {
        return false;
    }
}
