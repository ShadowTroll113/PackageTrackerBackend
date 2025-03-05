package com.Project.PackageTracker.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Buscar un usuario por nombre de usuario
    User findByUsername(String username);

    // Buscar un usuario por nombre de usuario y contraseña
    Optional<User> findByUsernameAndPassword(String username, String password);
}