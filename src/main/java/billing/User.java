package billing;

public class User {
    private String username;
    private String password; // Consider hashing in a real application

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters
}
