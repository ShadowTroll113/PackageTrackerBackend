import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CreateUsers {

    public static void main(String[] args) {
        // Crear un encoder de contraseñas BCrypt
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Crear los usuarios
        User admin = createUser(1, "admin", "admin@example.com", "admin", "ADMIN", passwordEncoder);
        User user1 = createUser(2, "user1", "user1@example.com", "tienda", "USER", passwordEncoder);
        User user2 = createUser(3, "user2", "user2@example.com", "almacen", "USER", passwordEncoder);

        // Imprimir los usuarios creados
        System.out.println("Usuario creado: " + admin);
        System.out.println("Usuario creado: " + user1);
        System.out.println("Usuario creado: " + user2);
    }

    // Método para crear un usuario
    private static User createUser(int id, String username, String email, String password, String role, BCryptPasswordEncoder passwordEncoder) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // Codificar la contraseña
        user.setRole(role);
        user.setName(username); // El nombre es igual al nombre de usuario
        return user;
    }
}

// Clase User (simplificada para este ejemplo)
class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String role;
    private String name;

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}