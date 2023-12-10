package com.artbids.repository;

import com.artbids.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthRepository extends JpaRepository<Auth, Long> {

    Optional<Auth> findByEmailAndPassword(String email, String password);

    Optional<Auth> findByUsernameAndPassword(String username, String password);

    Optional<Auth> findByEmail(String email);

    Auth findByUsername(String username);

}
