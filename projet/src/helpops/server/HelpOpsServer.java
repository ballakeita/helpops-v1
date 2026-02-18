package helpops.server;

import helpops.model.Incident;
import helpops.rmi.IHelpOps;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class HelpOpsServer extends UnicastRemoteObject implements IHelpOps {

    private static final String FICHIER_DONNEES = "helpops_donnees.dat";
    
    // Stockage en mémoire des incidents
    private List<Incident> incidents;

    private AtomicInteger compteurId;
    
    // Base de données des utilisateurs (identifiant -> mot de passe)
    private Map<String, String> utilisateurs;

    private Map<String, String> tokens;

    public HelpOpsServer() throws RemoteException {
        super();
        this.utilisateurs = new HashMap<>();
        this.tokens = new HashMap<>();
        
        utilisateurs.put("alice", "pass123");
        utilisateurs.put("bob", "pass456");
        utilisateurs.put("charlie", "pass789");
        
        // Charger les données existantes depuis le fichier
        chargerDonnees();
        
        System.out.println("[SERVEUR] HelpOpsServer initialise.");
        System.out.println("[SERVEUR] Utilisateurs de test : alice/pass123, bob/pass456, charlie/pass789");
        System.out.println("[SERVEUR] " + incidents.size() + " incident(s) charge(s) depuis le fichier.");
    }
    
    @Override
    public String authentifier(String identifiant, String motDePasse) throws RemoteException {
        if (utilisateurs.containsKey(identifiant) && utilisateurs.get(identifiant).equals(motDePasse)) {
            String token = UUID.randomUUID().toString();
            tokens.put(token, identifiant);
            System.out.println("[SERVEUR] Authentification reussie pour : " + identifiant);
            return token;
        }
        System.out.println("[SERVEUR] Echec d'authentification pour : " + identifiant);
        return null;
    }
    
    @Override
    public boolean verifierToken(String token) throws RemoteException {
        return tokens.containsKey(token);
    }
    
    @Override
    public Incident signalerIncident(String token, String categorie, String titre, String description) throws RemoteException {
        if (!verifierToken(token)) {
            System.out.println("[SERVEUR] Token invalide pour signalerIncident");
            return null;
        }
        
        String utilisateur = tokens.get(token);
        int id = compteurId.getAndIncrement();
        Incident incident = new Incident(id, categorie, titre, description, utilisateur);
        incidents.add(incident);
        sauvegarderDonnees();
        System.out.println("[SERVEUR] Incident cree par " + utilisateur + " : " + incident);
        return incident;
    }
    
    @Override
    public List<Incident> listerMesIncidents(String token) throws RemoteException {
        if (!verifierToken(token)) {
            System.out.println("[SERVEUR] Token invalide pour listerMesIncidents");
            return null;
        }
        String utilisateur = tokens.get(token);
        List<Incident> mesIncidents = new ArrayList<>();
        for (Incident incident : incidents) {
            if (incident.getUtilisateur().equals(utilisateur)) {
                mesIncidents.add(incident);
            }
        }
        
        System.out.println("[SERVEUR] Liste des incidents de " + utilisateur + " : " + mesIncidents.size() + " incident(s)");
        return mesIncidents;
    }
    
    @Override
    public Incident consulterIncident(String token, int id) throws RemoteException {
        if (!verifierToken(token)) {
            System.out.println("[SERVEUR] Token invalide pour consulterIncident");
            return null;
        }
        String utilisateur = tokens.get(token);
        for (Incident incident : incidents) {
            if (incident.getId() == id && incident.getUtilisateur().equals(utilisateur)) {
                System.out.println("[SERVEUR] Consultation incident #" + id + " par " + utilisateur);
                return incident;
            }
        }
        System.out.println("[SERVEUR] Incident #" + id + " introuvable ou non autorise pour " + utilisateur);
        return null;
    }
    
    @Override
    public boolean changerStatut(String token, int id, String nouveauStatut) throws RemoteException {
        if (!verifierToken(token)) {
            System.out.println("[SERVEUR] Token invalide pour changerStatut");
            return false;
        }
        String utilisateur = tokens.get(token);
        for (Incident incident : incidents) {
            if (incident.getId() == id && incident.getUtilisateur().equals(utilisateur)) {
                String ancienStatut = incident.getStatut();
                incident.setStatut(nouveauStatut);
                sauvegarderDonnees();
                System.out.println("[SERVEUR] Incident #" + id + " : " + ancienStatut + " -> " + nouveauStatut);
                return true;
            }
        }
        System.out.println("[SERVEUR] Incident #" + id + " introuvable ou non autorise.");
        return false;
    }
    
    @Override
    public String ping() throws RemoteException {
        return "HELP'OPS Server v1.0 - Connecté";
    }
    
    /**
     * Charge les données depuis le fichier au démarrage.
     */
    @SuppressWarnings("unchecked")
    private void chargerDonnees() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FICHIER_DONNEES))) {
            incidents = (List<Incident>) ois.readObject();
            int dernierID = ois.readInt();
            compteurId = new AtomicInteger(dernierID + 1);
            System.out.println("[SERVEUR] Donnees chargees depuis " + FICHIER_DONNEES);
            
        } catch (Exception e) {
            System.out.println("[SERVEUR] Aucune donnee trouvee. Demarrage avec une base vide.");
            incidents = new ArrayList<>();
            compteurId = new AtomicInteger(1);
        }
    }

    private void sauvegarderDonnees() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHIER_DONNEES))) {
            oos.writeObject(incidents);
            oos.writeInt(compteurId.get() - 1);
            System.out.println("[SERVEUR] Donnees sauvegardees dans " + FICHIER_DONNEES);
        } catch (Exception e) {
            System.err.println("[ERREUR SERVEUR] Impossible de sauvegarder les donnees : " + e.getMessage());
        }
    }
    
    /**
     * Point d'entrée du serveur.
     * Crée le Registry RMI et enregistre l'objet distant.
     */
    public static void main(String[] args) {
        try {
            System.setProperty("file.encoding", "UTF-8");
            System.setProperty("console.encoding", "UTF-8");
            Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println("[SERVEUR] Registry RMI créé sur le port 1099");
            HelpOpsServer server = new HelpOpsServer();
            registry.rebind("HelpOps", server);
            System.out.println("[SERVEUR] Service 'HelpOps' enregistré dans le Registry");
            System.out.println("[SERVEUR] Serveur prêt. En attente de connexions...");
        } catch (Exception e) {
            System.err.println("[ERREUR SERVEUR] " + e.getMessage());
            e.printStackTrace();
        }
    }
}
