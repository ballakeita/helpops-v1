% DIAGRAMME DE CAS D'UTILISATION - HELP'OPS V1
% Projet JAVA - Sockets/RMI
% Version 1 - Février 2026

# DIAGRAMME DE CAS D'UTILISATION - HELP'OPS V1

## 1. CONTEXTE DU PROJET

### 1.1 Objectif général
Concevoir et réaliser une **plateforme logicielle distribuée** permettant la gestion des incidents et des interventions au sein d'une organisation.

### 1.2 Version 1 - Les fondations
La Version 1 vise à mettre en place les **fondations fonctionnelles et techniques**:
- ✓ Authentification des utilisateurs
- ✓ Déclaration d'incidents
- ✓ Consultation des incidents

### 1.3 Technologie
- **Java RMI** (Remote Method Invocation)
- **Persistance**: Fichier sérialisé
- **Utilisateurs de test**: alice, bob, charlie

---

## 2. ACTEURS DU SYSTÈME

### 2.1 UTILISATEUR (Acteur Principal)

| Attribut | Valeur |
|----------|--------|
| **Nom** | Utilisateur |
| **Définition** | Personne qui signale des problèmes ou demandes d'assistance |
| **Rôle** | Déclarant d'incidents |
| **Pré-conditions** | Posséder un compte valide |
| **Comptes de test** | alice/pass123, bob/pass456, charlie/pass789 |

#### Responsabilités:
1. S'authentifier auprès du système
2. Déclarer de nouveaux incidents
3. Consulter la liste de ses incidents
4. Consulter les détails d'un incident

#### Objectifs:
- Accéder au système de support
- Signaler les problèmes rencontrés
- Suivre ses demandes

---

## 3. CAS D'UTILISATION

### 3.1 CAS UC-001 : S'AUTHENTIFIER

| Propriété | Description |
|-----------|-------------|
| **Identifiant** | UC-001 |
| **Nom** | S'Authentifier |
| **Acteur(s)** | Utilisateur |
| **Type** | Cas critique (prérequis pour tous les autres) |
| **Pré-conditions** | L'utilisateur a un compte valide |
| **Post-conditions** | Un token JWT valide est généré et stocké côté serveur |

#### Flux Principal (Happy Path):

```
Étape | Acteur      | Action
------|-------------|-------------------------------------
1     | Utilisateur | Lance HelpOpsClient
2     | Système     | Affiche : "Utilisateurs de test: alice/pass123..."
3     | Utilisateur | Saisit identifiant
4     | Utilisateur | Saisit mot de passe (masqué)
5     | Système     | Vérifie identifiant dans la base
6     | Système     | Vérifie mot de passe
7     | Système     | Génère token = UUID.randomUUID()
8     | Système     | Stocke mapping: token → identifiant
9     | Système     | Retourne le token
10    | Utilisateur | Reçoit confirmation + token
11    | Utilisateur | Accède au menu principal
```

#### Flux Alternatif (Erreur):

```
Condition d'Erreur:
  • Identifiant non trouvé → Retour null
  • Mot de passe incorrect → Retour null

Actions:
  1. Système affiche: "Échec d'authentification"
  2. Affiche tentatives restantes (max 3)
  3. Si tentative 3 échouée → Ferme l'application
```

#### Données:
- **Entrées**: identifiant (string), motDePasse (string)
- **Sorties**: token (UUID)
- **Stockage**: tokens (HashMap<String, String>)

#### Remarques:
- Token basé sur UUID (sécurisé)
- Pas d'expiration en V1 (à ajouter en V2)
- Limite: 3 tentatives maximum
- Base d'utilisateurs en dur (à remplacer par DB réelle en production)

---

### 3.2 CAS UC-002 : DÉCLARER UN INCIDENT

| Propriété | Description |
|-----------|-------------|
| **Identifiant** | UC-002 |
| **Nom** | Déclarer un Incident |
| **Acteur(s)** | Utilisateur (authentifié) |
| **Pré-conditions** | UC-001 réussie (token valide) |
| **Post-conditions** | Nouvel incident créé, ID généré, état = OPEN, données persistées |

#### Flux Principal:

```
Étape | Acteur      | Action
------|-------------|-------------------------------------
1     | Utilisateur | Sélectionne menu "1. Déclarer un incident"
2     | Système     | Affiche formulaire de création
3     | Utilisateur | Saisit catégorie (ex: "Réseau")
4     | Utilisateur | Saisit titre (ex: "Pas de WiFi")
5     | Utilisateur | Saisit description (ex: "Impossible...")
6     | Utilisateur | Valide le formulaire
7     | Système     | Vérifie le token (appel verifierToken())
8     | Système     | Token invalide → Retour null + affiche erreur
9     | Système     | Token valide → Récupère utilisateur = tokens.get(token)
10    | Système     | Génère ID = compteurId.getAndIncrement()
11    | Système     | Crée objet Incident(id, categorie, titre, description, utilisateur)
12    | Système     | Incident.statut = "OPEN" (automatique)
13    | Système     | Incident.dateCreation = new Date()
14    | Système     | Ajoute incident à la liste
15    | Système     | Appelle sauvegarderDonnees() (fichier)
16    | Système     | Affiche confirmations serveur (logs)
17    | Système     | Retourne l'incident créé
18    | Utilisateur | Reçoit confirmation: "Incident créé avec succès!"
19    | Utilisateur | Voit affichés: [ID: X] Titre | Catégorie | Statut: OPEN | Date
```

#### Champs du Formulaire:

| Champ | Type | Obligatoire | Exemple |
|-------|------|-------------|---------|
| Catégorie | String | OUI | "Réseau", "Logiciel", "Matériel", "Autre" |
| Titre | String | OUI | "Pas d'accès WiFi" |
| Description | String | OUI | "Je n'arrive pas à me connecter au WiFi" |

#### Validations:
- Tous les champs obligatoires
- Si champ vide → Message "Tous les champs sont obligatoires"
- Longueur de description ≥ 1 caractère

#### Données Générées Automatiquement:
- **ID**: Auto-incrémenté (1, 2, 3...)
- **Statut**: "OPEN" (fixé pour V1)
- **Utilisateur**: Récupéré du token
- **Date de création**: Date actuelle

#### Exemple:
```
Entrée:
  Catégorie: "Réseau"
  Titre: "Pas d'accès WiFi"
  Description: "Je n'arrive pas à me connecter au WiFi de l'entreprise"

Incident créé:
  [ID: 1] Pas d'accès WiFi - Je n'arrive pas à me connecter au WiFi de l'entreprise
  | Catégorie: Réseau | Statut: OPEN | Utilisateur: alice | Date: 2026-02-18 14:30:45
```

#### Stockage:
- **Mémoire**: Liste `incidents` (ArrayList<Incident>)
- **Persistance**: Fichier `helpops_donnees.dat` (sérialisation)

#### Erreurs Possibles:
- Token invalide → Message "Erreur lors de la création"
- Champ vide → Message "Tous les champs sont obligatoires"
- Erreur I/O → Exception affichée

---

### 3.3 CAS UC-003 : CONSULTER MES INCIDENTS

| Propriété | Description |
|-----------|-------------|
| **Identifiant** | UC-003 |
| **Nom** | Consulter Mes Incidents |
| **Acteur(s)** | Utilisateur (authentifié) |
| **Pré-conditions** | UC-001 réussie (token valide) |
| **Post-conditions** | Liste de ses incidents affichée (ou message vide) |

#### Flux Principal:

```
Étape | Acteur      | Action
------|-------------|-------------------------------------
1     | Utilisateur | Sélectionne menu "2. Consulter mes incidents"
2     | Système     | Appel RMI: listerMesIncidents(token)
3     | Système     | Vérifie token
4     | Système     | Token invalide → Retour null
5     | Système     | Token valide → Récupère utilisateur = tokens.get(token)
6     | Système     | Crée liste vide mesIncidents = []
7     | Système     | Pour chaque incident dans incidents:
8     | Système     | Si incident.utilisateur == utilisateur → Ajoute à mesIncidents
9     | Système     | Retourne mesIncidents (peut être vide)
10    | Utilisateur | Affiche la liste complète ou "Aucun incident déclaré"
```

#### Format d'Affichage:

```
--- Mes incidents ---
[ID: 1] Pas d'accès WiFi - Je n'arrive pas à me connecter au WiFi 
        | Catégorie: Réseau | Statut: OPEN | Utilisateur: alice 
        | Date: 2026-02-18 14:30:45
[ID: 2] Bug dans l'app - L'interface se fige
        | Catégorie: Logiciel | Statut: OPEN | Utilisateur: alice 
        | Date: 2026-02-18 15:45:30
Total : 2 incident(s)
```

#### Cas Particuliers:

| Condition | Affichage |
|-----------|-----------|
| Aucun incident | "Aucun incident déclaré" |
| Token invalide | "Erreur d'authentification" |
| N incidents | Affiche tous + compteur |

#### Règles de Sécurité:
- **Isolation des données**: Chaque user voit UNIQUEMENT ses incidents
- **Filtrage côté serveur**: La vérification se fait au niveau du serveur

#### Données Retournées:
- Liste de Incident objects (sérialisés via RMI)
- Chaque incident contient: ID, titre, catégorie, description, statut, utilisateur, date

---

### 3.4 CAS UC-004 : CONSULTER DÉTAIL D'UN INCIDENT

| Propriété | Description |
|-----------|-------------|
| **Identifiant** | UC-004 |
| **Nom** | Consulter Détail d'un Incident |
| **Acteur(s)** | Utilisateur (authentifié) |
| **Pré-conditions** | UC-001 réussie + Incident doit exister + Doit appartenir à l'utilisateur |
| **Post-conditions** | Détails complets affichés ou message erreur |

#### Flux Principal:

```
Étape | Acteur      | Action
------|-------------|-------------------------------------
1     | Utilisateur | Sélectionne menu "3. Consulter le détail d'un incident"
2     | Système     | Affiche: "ID de l'incident : "
3     | Utilisateur | Saisit ID (ex: 1)
4     | Système     | Parse ID (parseInt, catch NumberFormatException)
5     | Système     | ID invalide → Affiche "ID invalide" + retour menu
6     | Système     | Appel RMI: consulterIncident(token, id)
7     | Système     | Vérifie token
8     | Système     | Token invalide → Retour null
9     | Système     | Récupère utilisateur = tokens.get(token)
10    | Système     | Cherche incident avec incident.getId() == id
11    | Système     | Incident non trouvé → Retour null
12    | Système     | Incident trouvé, check: incident.utilisateur == utilisateur
13    | Système     | Pas propriétaire → Retour null
14    | Système     | Propriétaire → Retourne incident
15    | Utilisateur | Affiche les 7 détails (voir format ci-dessous)
```

#### Format d'Affichage:

```
=== Détail de l'incident #1 ===
Titre        : Pas d'accès WiFi
Catégorie    : Réseau
Description  : Je n'arrive pas à me connecter au WiFi de l'entreprise
Statut       : OPEN
Utilisateur  : alice
Date creation: Wed Feb 18 14:30:45 CET 2026
```

#### Détails Affichés:

| Champ | Valeur | Exemple |
|-------|--------|---------|
| ID | incident.getId() | 1 |
| Titre | incident.getTitre() | "Pas d'accès WiFi" |
| Catégorie | incident.getCategorie() | "Réseau" |
| Description | incident.getDescription() | "Je n'arrive pas..." |
| Statut | incident.getStatut() | "OPEN" |
| Utilisateur | incident.getUtilisateur() | "alice" |
| Date création | incident.getDateCreation() | Date complète |

#### Cas d'Erreur:

| Condition | Message |
|-----------|---------|
| ID non numérique | "ID invalide." |
| Incident n'existe pas | "Incident introuvable ou non autorisé." |
| Incident d'un autre user | "Incident introuvable ou non autorisé." |
| Token invalide | "Incident introuvable ou non autorisé." |

#### Sécurité:
- **Double check**: Token + Propriété de l'incident
- **Isolation stricte**: Impossible de voir les incidents des autres users
- **Messages génériques**: Pas de distinction "existe mais pas le tien" vs "n'existe pas"

---

## 4. RELATIONS ENTRE CAS D'UTILISATION

```
S'Authentifier (UC-001)
    │
    ├──► Déclarer Incident (UC-002)   [<<include>>]
    │
    ├──► Consulter Mes Incidents (UC-003)  [<<include>>]
    │
    └──► Consulter Détail (UC-004)    [<<include>>]
```

**Type**: Tous les cas sauf UC-001 **dépendent** de UC-001 (relation <<include>>)

---

## 5. RÈGLES DE SÉCURITÉ

### 5.1 Authentification
```
✓ Token obligatoire pour tout appel (sauf authentification)
✓ Token = UUID unique généré côté serveur
✓ Token stocké dans HashMap côté serveur
✓ Max 3 tentatives de connexion
✓ Pas d'expiration de token en V1
```

### 5.2 Autorisation
```
✓ Vérification du token à CHAQUE appel RMI
✓ Filtrage par utilisateur (propriété des incidents)
✓ Utilisateur A ne peut accéder qu'aux incidents de A
✓ Messages d'erreur génériques (ne révèlent pas qui a créé)
```

### 5.3 Intégrité des Données
```
✓ ID unique auto-généré (compteurId.getAndIncrement())
✓ Statut initial fixé à "OPEN"
✓ Utilisateur et date créés automatiquement (non saisis)
✓ Persistance dans fichier sérialisé
```

---

## 6. TABLEAU RÉCAPITULATIF

| UC | Nom | Acteur | Pré-cond | Type | Priorité |
|----|-----|--------|----------|------|----------|
| UC-001 | S'Authentifier | Utilisateur | Compte valide | Base | **CRITIQUE** |
| UC-002 | Déclarer Incident | Utilisateur | Token valid | Principal | **HAUTE** |
| UC-003 | Consulter Incidents | Utilisateur | Token valid | Principal | **HAUTE** |
| UC-004 | Consulter Détail | Utilisateur | Token valid | Principal | **HAUTE** |

---

## 7. SCÉNARIOS D'EXÉCUTION

### 7.1 Scénario Nominal: Alice crée et consulte un incident

```
ÉTAPES:
  1. Alice lance HelpOpsClient
     → Connexion établie, "Bienvenue sur HELP'OPS"

  2. Alice s'authentifie
     Identifiant: alice
     Mot de passe: pass123
     → "Authentification réussie! Bienvenue alice"
     → token = "f47ac10b-58cc-4372-a567-0e02b2c3d479"

  3. Alice accède au menu
     Choix: 1 (Déclarer un incident)
     Catégorie: Réseau
     Titre: Pas d'accès WiFi
     Description: Je n'arrive pas à me connecter au WiFi
     → "Incident créé avec succès!"
     → [ID: 1] Pas d'accès WiFi | Réseau | OPEN | alice | 2026-02-18 14:30:45

  4. Alice consulte ses incidents
     Choix: 2
     → Affichage de l'incident #1
     Total : 1 incident(s)

  5. Alice voit le détail
     Choix: 3
     ID: 1
     → === Détail de l'incident #1 ===
        Titre        : Pas d'accès WiFi
        Catégorie    : Réseau
        Description  : Je n'arrive pas à me connecter au WiFi
        Statut       : OPEN
        Utilisateur  : alice
        Date creation: Wed Feb 18 14:30:45 CET 2026

  6. Alice quitte
     Choix: 4
     → "Déconnexion..."
     → Fin du programme

RÉSULTAT: ✓ Tous les cas d'utilisation exécutés avec succès
```

### 7.2 Scénario d'Erreur: Bob essaie de voir l'incident d'Alice

```
ÉTAPES:
  1. Bob s'authentifie
     Identifiant: bob
     Mot de passe: pass456
     → Token valide

  2. Bob consulte ses incidents
     Choix: 2
     → "Aucun incident déclaré"

  3. Bob essaie de voir l'incident #1 (créé par Alice)
     Choix: 3
     ID: 1
     → SERVEUR: Cherche incident #1
     → SERVEUR: Trouve incident #1 avec utilisateur = "alice"
     → SERVEUR: Check bob == alice? NON
     → SERVEUR: Retourne null
     → CLIENT: "Incident introuvable ou non autorisé."

RÉSULTAT: ✓ Sécurité respectée, Bob ne peut pas voir l'incident d'Alice
```

---

## 8. EXIGENCES NON FONCTIONNELLES

| Exigence | Description | État V1 |
|----------|-------------|---------|
| Performance | Réponse < 1s en local | ✓ OK |
| Disponibilité | Serveur H24 | ✓ À tester |
| Sécurité | Token + isolation | ✓ Implémentée |
| Persistance | Données survivent redémarrage | ✓ Fichier OK |
| Scalabilité | Support multi-clients | ✓ RMI OK |
| Maintenabilité | Code lisible | ✓ Bonne |
| Testabilité | Tests unitaires | ✗ À ajouter |

---

## 9. ÉVOLUTION VERS V2

Pour la **Version 2**, les cas d'utilisation suivants seront ajoutés:

- **UC-005**: Assigner un incident (Agent)
- **UC-006**: Consulter incidents assignés (Agent)
- **UC-007**: Changer statut OPEN → ASSIGNED
- **UC-008**: Gérer les rôles UTILISATEUR/AGENT

---

## 10. CONCLUSION

Le diagramme de cas d'utilisation V1 est **simple mais complet** pour les fondations:
- ✓ Authentification robuste
- ✓ Création d'incidents sécurisée
- ✓ Consultation isolée par utilisateur
- ✓ Persistance des données

Le système est **extensible** pour les versions futures (agents, statistiques, événements).

---

**Document généré le**: Février 18, 2026  
**Version**: 1.0  
**Auteurs**: Équipe HELP'OPS  
**Statut**: Approuvé pour développement V1
