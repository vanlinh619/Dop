package org.dop.module.tenant.filter;

import org.mapstruct.ap.internal.util.Collections;

import java.util.List;

public class ByPassFilterUrl {
    public static final List<String> whiteList = List.of(
            "css", "js", "error", "favicon.ico"
    );

    public static final List<String> blackListTenant = Collections.join(
            whiteList, List.of(
                    "default", "system"
            )
    );

}
