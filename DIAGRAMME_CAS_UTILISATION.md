# DIAGRAMME DE CAS D'UTILISATION - HELP'OPS V1

## 🎯 Vue d'ensemble

Ce diagramme présente les interactions entre les **utilisateurs** et le système **HELP'OPS** dans sa version 1.

---

## 📋 ACTEURS

### **Utilisateur**
- **Définition** : Personne qui utilise l'application HELP'OPS pour signaler des problèmes
- **Responsabilités** :
  - S'authentifier auprès du système
  - Déclarer de nouveaux incidents
  - Consulter ses incidents déclarés
  - Voir les détails d'un incident
- **Pré-conditions** : Avoir un compte valide (alice/pass123, bob/pass456, charlie/pass789)

---

## 🎬 CAS D'UTILISATION

### **1. S'Authentifier**
```
Nom                : S'Authentifier
Acteurs             : Utilisateur
Pré-conditions      : L'utilisateur doit avoir un compte valide
Description         : L'utilisateur se connecte avec identifiant et mot de passe
Flux principal      :
  1. Utilisateur saisit identifiant
  2. Utilisateur saisit mot de passe
  3. Système vérifie les credentials
  4. Système génère un token UUID
  5. Système retourne le token
  6. Utilisateur est authentifié
Post-conditions     : Un token valide est généré et stocké côté serveur
Flux alternatif     :
  - Si credentials invalides : Message "Échec d'authentification"
  - Max 3 tentatives avant fermeture de l'app
```

### **2. Déclarer un Incident**
```
Nom                : Déclarer un Incident
Acteurs             : Utilisateur (authentifié)
Pré-conditions      : L'utilisateur doit être authentifié (avoir un token valide)
Description         : L'utilisateur crée un nouvel incident de support
Flux principal      :
  1. Utilisateur sélectionne "Déclarer un incident"
  2. Utilisateur saisit la catégorie
  3. Utilisateur saisit le titre
  4. Utilisateur saisit la description
  5. Système vérifie le token
  6. Système génère un ID unique pour l'incident
  7. Système crée l'incident (statut = OPEN)
  8. Système enregistre : catégorie, titre, description, utilisateur, date
  9. Système sauvegarde dans la base de données
  10. Système retourne l'incident créé
  11. Utilisateur reçoit confirmation
Post-conditions     : Un nouvel incident est créé et persisté
Flux alternatif     :
  - Si token invalide : Accès refusé
  - Si champ vide : Message d'erreur "Tous les champs sont obligatoires"
```

### **3. Consulter Mes Incidents**
```
Nom                : Consulter Mes Incidents
Acteurs             : Utilisateur (authentifié)
Pré-conditions      : L'utilisateur doit être authentifié
Description         : L'utilisateur visualise la liste de ses incidents déclarés
Flux principal      :
  1. Utilisateur sélectionne "Consulter mes incidents"
  2. Système vérifie le token
  3. Système récupère l'identité de l'utilisateur (à partir du token)
  4. Système filtre les incidents (seulement ceux de cet utilisateur)
  5. Système retourne la liste
  6. Utilisateur voit la liste (ID, titre, catégorie, statut, date)
Post-conditions     : Liste affichée ou message "Aucun incident"
Flux alternatif     :
  - Si token invalide : Accès refusé
  - Si aucun incident : "Aucun incident déclaré"
```

### **4. Consulter Détail d'un Incident**
```
Nom                : Consulter Détail d'un Incident
Acteurs             : Utilisateur (authentifié)
Pré-conditions      : L'utilisateur doit être authentifié
                      L'incident doit exister et appartenir à l'utilisateur
Description         : L'utilisateur consulte les détails complets d'un incident
Flux principal      :
  1. Utilisateur sélectionne "Consulter détail"
  2. Utilisateur saisit l'ID de l'incident
  3. Système vérifie le token
  4. Système cherche l'incident par ID
  5. Système vérifie que l'incident appartient à cet utilisateur
  6. Système retourne les détails : ID, titre, catégorie, description, 
                                    statut, utilisateur, date de création
  7. Utilisateur voit tous les détails
Post-conditions     : Détails affichés à l'écran
Flux alternatif     :
  - Si token invalide : Accès refusé
  - Si incident n'existe pas : "Incident introuvable"
  - Si incident ne lui appartient pas : "Incident introuvable ou non autorisé"
```

---

## 🔐 RÈGLES DE SÉCURITÉ

| Règle | Description |
|-------|-------------|
| **Token obligatoire** | Chaque cas d'utilisation (sauf authentification) nécessite un token valide |
| **Isolation des données** | Un utilisateur ne peut voir que SES incidents |
| **ID unique** | Chaque incident reçoit un ID auto-généré unique |
| **Statut initial** | Tout nouvel incident débute en état OPEN |
| **Persistance** | Les incidents sont sauvegardés dans un fichier |

---

## 📊 TABLEAU SYNTHÉTIQUE

| Cas d'Utilisation | Acteur | Pré-condition | Principal |
|-------------------|--------|---------------|-----------|
| S'Authentifier | Utilisateur | Compte valide | Générer token |
| Déclarer Incident | Utilisateur | Token valide | Créer incident OPEN |
| Consulter Mes Incidents | Utilisateur | Token valide | Lister ses incidents |
| Consulter Détail | Utilisateur | Token valide | Afficher détails |

---

## 🔄 FLUX GLOBAL D'INTERACTION

```
Démarrage
   │
   ▼
┌─────────────────────────┐
│  S'Authentifier         │ ← OBLIGATOIRE
├─────────────────────────┤
│ Entrée: username/pwd    │
│ Sortie: token           │
└────────┬────────────────┘
         │ (token obtenu)
         ▼
    ┌────────────────────┐
    │ Menu Principal      │
    └─┬──────┬──────┬────┘
      │      │      │
      │      │      └─► Quitter
      │      │
      ├─────┴─────┬───────────┐
      │           │           │
      ▼           ▼           ▼
┌──────────┐ ┌──────────┐ ┌──────────────┐
│Déclarer  │ │Consulter │ │Consulter     │
│Incident  │ │ Mes      │ │Détail        │
│          │ │Incidents │ │Incident      │
└──────────┘ └──────────┘ └──────────────┘
      │           │           │
      │           │           │
      └───────────┴───────────┘
              │
              ▼
         (Retour au menu)
              │
              ▼
           Quitter
```

---

## 📝 EXEMPLE D'EXÉCUTION

**Utilisateur : Alice**

```
1. Authentification
   - Saisit : alice / pass123
   - Reçoit : token = "f47ac10b-58cc-4372-a567-0e02b2c3d479"

2. Déclare un incident
   - Catégorie : Réseau
   - Titre : Pas d'accès WiFi
   - Description : Je n'arrive pas à me connecter au WiFi de l'entreprise
   - Résultat : Incident créé avec ID=1, statut=OPEN

3. Consulte ses incidents
   - Reçoit liste : [ID:1 | Pas d'accès WiFi | Réseau | OPEN | 2026-02-18]

4. Consulte détail de l'incident #1
   - Titre        : Pas d'accès WiFi
   - Catégorie    : Réseau
   - Description  : Je n'arrive pas à me connecter au WiFi de l'entreprise
   - Statut       : OPEN
   - Utilisateur  : alice
   - Date création: 2026-02-18 14:30:45

5. Quitter
```

---

## 🎓 NOTES POUR LE RAPPORT

- **Version 1 minimaliste** : Seul l'utilisateur "normal" est implémenté (pas encore de rôle AGENT)
- **Token basé sur UUID** : Sécurité robuste pour V1
- **Persistance simple** : Fichier sérialisé (évolutif vers BD en V2)
- **Isolation stricte** : Chaque user voit uniquement SES données
- **États incidents** : V1 n'a que "OPEN" (ASSIGNED et RESOLVED en V2)

