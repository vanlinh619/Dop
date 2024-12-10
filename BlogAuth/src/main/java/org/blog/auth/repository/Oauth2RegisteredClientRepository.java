package org.blog.auth.repository;

import org.blog.auth.entity.Oauth2RegisteredClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Oauth2RegisteredClientRepository extends JpaRepository<Oauth2RegisteredClient, Long> {
}
