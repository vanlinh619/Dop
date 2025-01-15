package org.dop.module.role.service;

import jakarta.annotation.Nullable;

import java.util.List;

public interface RoleService {

    List<String> verifyRole(@Nullable List<String> roles);
}
