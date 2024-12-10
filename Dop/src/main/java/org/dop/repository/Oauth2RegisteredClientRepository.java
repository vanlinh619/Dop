package org.dop.repository;

import org.dop.entity.Oauth2RegisteredClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Oauth2RegisteredClientRepository extends JpaRepository<Oauth2RegisteredClient, Long> {
}
