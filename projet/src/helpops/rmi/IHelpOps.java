package helpops.rmi;

import helpops.model.Incident;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface RMI définissant les méthodes distantes de HELP'OPS.
 * Toutes les méthodes doivent throw RemoteException.
 */
public interface IHelpOps extends Remote {
    
    String authentifier(String identifiant, String motDePasse) throws RemoteException;

    boolean verifierToken(String token) throws RemoteException;
    
    Incident signalerIncident(String token, String categorie, String titre, String description) throws RemoteException;
    
    List<Incident> listerMesIncidents(String token) throws RemoteException;
    
    Incident consulterIncident(String token, int id) throws RemoteException;
    
    boolean changerStatut(String token, int id, String nouveauStatut) throws RemoteException;
    
    String ping() throws RemoteException;
}
