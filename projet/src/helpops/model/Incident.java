package helpops.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Implémente Serializable pour être transmis via RMI.
 */
public class Incident implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String categorie;
    private String titre;
    private String description;
    private String statut; // "OPEN", "EN_COURS", "RESOLU"
    private String utilisateur;
    private Date dateCreation;
    
    public Incident(int id, String categorie, String titre, String description, String utilisateur) {
        this.id = id;
        this.categorie = categorie;
        this.titre = titre;
        this.description = description;
        this.utilisateur = utilisateur;
        this.statut = "OPEN";
        this.dateCreation = new Date();
    }
    
    public int getId() {
        return id;
    }
    
    public String getCategorie() {
        return categorie;
    }
    
    public String getTitre() {
        return titre;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getStatut() {
        return statut;
    }
    
    public void setStatut(String statut) {
        this.statut = statut;
    }
    
    public String getUtilisateur() {
        return utilisateur;
    }
    
    public Date getDateCreation() {
        return dateCreation;
    }
    
    @Override
    public String toString() {
        return String.format("[ID: %d] %s - %s | Categorie: %s | Statut: %s | Utilisateur: %s | Date: %s", 
            id, titre, description, categorie, statut, utilisateur, dateCreation);
    }
}
