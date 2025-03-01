package org.dop.module.role.service;

import jakarta.annotation.Nullable;

import java.util.List;
import java.util.Set;

public interface RoleService {

    Set<String> verifyRole(@Nullable List<String> roles);
}
