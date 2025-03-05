package com.Project.PackageTracker.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserQueryController {

    @Autowired
    private UserService userService;

    // Obtener todos los usuarios
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Login de usuario
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();

        // Buscar al usuario en la base de datos
        Optional<User> userOptional = userService.findUserByUsernameAndPassword(username, password);

        // Si el usuario existe, devolvemos el usuario
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("user", user); // Incluimos la información del usuario en la respuesta
            return ResponseEntity.ok(response);
        } else {
            // Si las credenciales son incorrectas, devolvemos un error 401
            Map<String, String> response = new HashMap<>();
            response.put("message", "Invalid credentials");
            return ResponseEntity.status(401).body(response); // Unauthorized
        }
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar un usuario por nombre de usuario
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}