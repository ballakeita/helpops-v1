package helpops.model;

import java.io.Serializable;

/**
 * Implémente Serializable pour être transmis via RMI.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nom;
    private String email;
    private String role; // "UTILISATEUR" ou "AGENT"
    
    public User(String nom, String email, String role) {
        this.nom = nom;
        this.email = email;
        this.role = role;
    }
    
    public String getNom() {
        return nom;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getRole() {
        return role;
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s) - %s", nom, email, role);
    }
}
