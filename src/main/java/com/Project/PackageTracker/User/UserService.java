package com.Project.PackageTracker.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Validar usuario y contraseña
    public boolean validateUserCredentials(String username, String password) {
        // Buscar al usuario en la base de datos por su nombre de usuario
        User user = userRepository.findByUsername(username);

        // Si el usuario no existe o la contraseña no es correcta, devolver false
        if (user == null || !user.getPassword().equals(password)) {
            return false;
        }

        // Si las credenciales son válidas, devolver true
        return true;
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setRole(userDetails.getRole());
            return userRepository.save(user);
        });
    }

    public boolean deleteUser(Long id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }
}
