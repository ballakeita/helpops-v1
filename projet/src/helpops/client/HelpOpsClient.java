package helpops.client;

import helpops.model.Incident;
import helpops.rmi.IHelpOps;
import java.io.Console;
import java.nio.charset.StandardCharsets;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class HelpOpsClient {
    private IHelpOps service;
    private Scanner scanner;
    private String token; 
    private String utilisateur;
    
    /**
     * Se connecte au serveur via le Registry RMI.
     */
    public HelpOpsClient(String host, int port) {
        try {
            // encodage UTF-8 pour les accents
            System.setProperty("file.encoding", "UTF-8");
            System.setProperty("console.encoding", "UTF-8");
            // Localise le Registry RMI sur le serveur distant
            Registry registry = LocateRegistry.getRegistry(host, port);
            System.out.println("[CLIENT] Connexion au Registry sur " + host + ":" + port);
            // recup ref objet distant "HelpOps"
            service = (IHelpOps) registry.lookup("HelpOps");
            System.out.println("[CLIENT] Service 'HelpOps' trouve.");
            // Test
            String reponse = service.ping();
            System.out.println("[CLIENT] Ping : " + reponse);
            System.out.println();
            scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.err.println("[ERREUR CLIENT] Impossible de se connecter au serveur.");
            System.err.println("Assurez-vous que le serveur est demarre.");
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private boolean authentifier() {
        System.out.println("=== AUTHENTIFICATION ===");
        System.out.println("Utilisateurs de test : alice/pass123, bob/pass456, charlie/pass789");
        System.out.println();
        int tentatives = 0;
        int maxTentatives = 3;
        while (tentatives < maxTentatives) {
            try {
                System.out.print("Identifiant : ");
                utilisateur = scanner.nextLine().trim();
                String motDePasse = lireMotDePasse();
                // Appel RMI pour authentification
                token = service.authentifier(utilisateur, motDePasse);
                if (token != null) {
                    System.out.println("Authentification réussie ! Bienvenue " + utilisateur);
                    System.out.println();
                    return true;
                } else {
                    tentatives++;
                    int restant = maxTentatives - tentatives;
                    if (restant > 0) {
                        System.out.println("Echec d'authentification. Il vous reste " + restant + " tentative(s).\n");
                    } else {
                        System.out.println("Nombre maximum de tentatives atteint. Fermeture de l'application.");
                    }
                }
                
            } catch (Exception e) {
                System.err.println("[ERREUR] " + e.getMessage());
                tentatives++;
            }
        }
        return false;
    }
    
    private String lireMotDePasse() {
        Console console = System.console();
        if (console != null) {
            char[] passwordChars = console.readPassword("Mot de passe : ");
            return new String(passwordChars);
        } else {
            System.out.print("Mot de passe : ");
            return scanner.nextLine().trim();
        }
    }
    
    private void afficherMenu() {
        System.out.println("=== HELP'OPS - Menu ===");
        System.out.println("1. Declarer un incident");
        System.out.println("2. Consulter mes incidents");
        System.out.println("3. Consulter le detail d'un incident");
        System.out.println("4. Quitter");
        System.out.print("Choix : ");
    }
    
    public void demarrer() {
        if (!authentifier()) {
            System.out.println("Impossible de continuer sans authentification.");
            scanner.close();
            return;
        }
        boolean continuer = true;
        while (continuer) {
            afficherMenu();
            String choix = scanner.nextLine().trim();
            System.out.println();
            try {
                switch (choix) {
                    case "1":
                        declarerIncident();
                        break;
                    case "2":
                        consulterMesIncidents();
                        break;
                    case "3":
                        consulterDetailIncident();
                        break;
                    case "4":
                        continuer = false;
                        System.out.println("Deconnexion...");
                        break;
                    default:
                        System.out.println("Choix invalide. Veuillez entrer 1, 2, 3 ou 4.");
                }
            } catch (Exception e) {
                System.err.println("[ERREUR] " + e.getMessage());
            }
            System.out.println();
        }
        scanner.close();
    }
    

    private void declarerIncident() throws Exception {
        System.out.println("\n--- Declarer un incident ---");
        System.out.print("Categorie : ");
        String categorie = scanner.nextLine().trim();
        System.out.print("Titre : ");
        String titre = scanner.nextLine().trim();
        System.out.print("Description : ");
        String description = scanner.nextLine().trim();
        if (categorie.isEmpty() || titre.isEmpty() || description.isEmpty()) {
            System.out.println("Tous les champs sont obligatoires.");
            return;
        }
        // Appel RMI distant avec le token
        Incident incident = service.signalerIncident(token, categorie, titre, description);
        if (incident != null) {
            System.out.println("Incident cree avec succes !");
            System.out.println(incident);
        } else {
            System.out.println("Erreur lors de la creation de l'incident.");
        }
    }
    

    private void consulterMesIncidents() throws Exception {
        System.out.println("\n--- Mes incidents ---");
        // Appel RMI distant avec le token
        List<Incident> incidents = service.listerMesIncidents(token);
        if (incidents == null) {
            System.out.println("Erreur d'authentification.");
        } else if (incidents.isEmpty()) {
            System.out.println("Aucun incident declare.");
        } else {
            for (Incident incident : incidents) {
                System.out.println(incident);
            }
            System.out.println("Total : " + incidents.size() + " incident(s)");
        }
    }
    

    private void consulterDetailIncident() throws Exception {
        System.out.println("\n--- Detail d'un incident ---");
        
        System.out.print("ID de l'incident : ");
        String idStr = scanner.nextLine().trim();
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.out.println("ID invalide.");
            return;
        }
        // Appel RMI distant avec le token
        Incident incident = service.consulterIncident(token, id);
        if (incident != null) {
            System.out.println("\n=== Detail de l'incident #" + id + " ===");
            System.out.println("Titre        : " + incident.getTitre());
            System.out.println("Categorie    : " + incident.getCategorie());
            System.out.println("Description  : " + incident.getDescription());
            System.out.println("Statut       : " + incident.getStatut());
            System.out.println("Utilisateur  : " + incident.getUtilisateur());
            System.out.println("Date creation: " + incident.getDateCreation());
        } else {
            System.out.println("Incident introuvable ou non autorise.");
        }
    }
    

    public static void main(String[] args) {
        String host = "localhost";
        int port = 1099;
        if (args.length >= 1) {
            host = args[0];
        }
        if (args.length >= 2) {
            port = Integer.parseInt(args[1]);
        }
        HelpOpsClient client = new HelpOpsClient(host, port);
        client.demarrer();
    }
}
