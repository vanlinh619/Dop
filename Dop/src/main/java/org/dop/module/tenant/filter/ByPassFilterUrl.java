package org.dop.module.tenant.filter;

import java.util.List;

public class ByPassFilterUrl {
    public static final List<String> whiteList = List.of(
            "css", "js", "error", "favicon.ico"
    );


}
