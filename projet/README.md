# HELP'OPS - Version 1
**Projet MCPR - Master 1 STRI**

Plateforme de gestion d'incidents distribuée avec Java RMI.

---

## Fonctionnalités

**Authentification** : Connexion sécurisée avec token (3 tentatives max)  
**Déclaration d'incident** : Catégorie, titre, description  
**Consultation** : Liste et détail des incidents par utilisateur  
**Persistance** : Sauvegarde automatique dans un fichier

---

## Utilisateurs de test

| Identifiant | Mot de passe |
|-------------|--------------|
| alice       | pass123      |
| bob         | pass456      |
| charlie     | pass789      |

---

## Commandes

### 1. Compilation

```bash
"C:\Program Files\Eclipse Adoptium\jdk-21.0.10.7-hotspot\bin\javac.exe" -encoding UTF-8 -d bin src\helpops\model\*.java src\helpops\rmi\*.java src\helpops\server\*.java src\helpops\client\*.java
```

### 2. Lancer le serveur (Terminal 1)

```bash
cd bin
java helpops.server.HelpOpsServer
```

### 3. Lancer le client (Terminal 2)

```bash
cd bin
java helpops.client.HelpOpsClient
```

---

## Architecture

```
src/helpops/
├── model/      Incident, User (classes de données)
├── rmi/        IHelpOps (interface RMI)
├── server/     HelpOpsServer (implémentation + persistance)
└── client/     HelpOpsClient (interface console)
```

**Persistance** : Les incidents sont sauvegardés dans `helpops_donnees.dat` (dossier `bin/`)

---

