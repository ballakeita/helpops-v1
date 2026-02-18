# 🚀 HELP'OPS - Plateforme de Gestion d'Incidents

**Version 1.0** | Master 1 STRI | MCPR Java Project

![License](https://img.shields.io/badge/license-MIT-green.svg)
![Java](https://img.shields.io/badge/java-21-blue.svg)
![RMI](https://img.shields.io/badge/protocol-RMI-orange.svg)

---

## 📋 Description

**HELP'OPS** est une plateforme logicielle distribuée permettant la **gestion des incidents et des interventions** au sein d'une organisation.

### Version 1 - Les fondations
Cette première version implémente les fonctionnalités de base:
- ✅ **Authentification** sécurisée avec token UUID
- ✅ **Déclaration d'incidents** (catégorie, titre, description)
- ✅ **Consultation** des incidents déclarés
- ✅ **Persistance** des données dans un fichier

---

## 🏗️ Architecture

### Stack Technologique
- **Langage**: Java 21+
- **Communication**: RMI (Remote Method Invocation)
- **Persistance**: Sérialisation Java (fichier `.dat`)
- **Interface**: Console (CLI)

### Structure du Projet
```
projetMCPR/
├── projet/
│   ├── src/
│   │   └── helpops/
│   │       ├── client/      # Interface utilisateur
│   │       ├── server/      # Logique métier + persistance
│   │       ├── model/       # Classes métier
│   │       └── rmi/         # Interface RMI
│   ├── bin/                 # Fichiers compilés
│   ├── helpops_donnees.dat  # Base de données (persistance)
│   └── README.md
├── .gitignore
├── DIAGRAMME_CAS_UTILISATION.md
├── RAPPORT_CAS_UTILISATION.md
└── DiagrammeCasUtilisation.svg
```

---

## 👥 Utilisateurs de Test

| Identifiant | Mot de passe | Rôle |
|-------------|--------------|------|
| alice       | pass123      | Utilisateur |
| bob         | pass456      | Utilisateur |
| charlie     | pass789      | Utilisateur |

---

## 🚀 Installation et Utilisation

### Prérequis
- **Java 21+** (JDK ou OpenJDK)
- **Git**

### 1. Cloner le repository
```bash
git clone https://github.com/votre-username/HELP-OPS.git
cd HELP-OPS/projet
```

### 2. Compiler le projet
```bash
# Windows (Command Prompt)
"C:\Program Files\Eclipse Adoptium\jdk-21.0.10.7-hotspot\bin\javac.exe" -encoding UTF-8 -d bin src/helpops/model/*.java src/helpops/rmi/*.java src/helpops/server/*.java src/helpops/client/*.java

# Linux/Mac
javac -encoding UTF-8 -d bin src/helpops/model/*.java src/helpops/rmi/*.java src/helpops/server/*.java src/helpops/client/*.java
```

### 3. Lancer le serveur
Ouvrir **Terminal 1**:
```bash
cd bin
java helpops.server.HelpOpsServer
```
Vous devriez voir:
```
[SERVEUR] Registry RMI créé sur le port 1099
[SERVEUR] Service 'HelpOps' enregistré dans le Registry
[SERVEUR] Serveur prêt. En attente de connexions...
```

### 4. Lancer le client
Ouvrir **Terminal 2**:
```bash
cd bin
java helpops.client.HelpOpsClient
```

### 5. Utiliser l'application
```
=== AUTHENTIFICATION ===
Utilisateurs de test : alice/pass123, bob/pass456, charlie/pass789

Identifiant : alice
Mot de passe : ••••••
Authentification réussie ! Bienvenue alice

=== HELP'OPS - Menu ===
1. Declarer un incident
2. Consulter mes incidents
3. Consulter le detail d'un incident
4. Quitter
Choix : 1

--- Declarer un incident ---
Categorie : Réseau
Titre : Pas d'accès WiFi
Description : Je n'arrive pas à me connecter
Incident cree avec succes !
```

---

## 🎯 Fonctionnalités V1

### 1. S'Authentifier (UC-001)
- Identifiant + Mot de passe
- Génération d'un token UUID
- Maximum 3 tentatives
- Token valide pour la session

### 2. Déclarer un Incident (UC-002)
- Catégorie (obligatoire)
- Titre (obligatoire)
- Description (obligatoire)
- Création automatique: ID unique, Statut=OPEN, Date, Utilisateur

### 3. Consulter Mes Incidents (UC-003)
- Liste de tous les incidents déclarés par l'utilisateur
- Affiche: ID, Titre, Catégorie, Statut, Date
- Isolation stricte: Chaque user voit uniquement SES incidents

### 4. Consulter Détail d'un Incident (UC-004)
- Recherche par ID
- Affiche tous les détails: ID, Titre, Catégorie, Description, Statut, Utilisateur, Date
- Vérification de propriété: Seul le déclarant peut voir l'incident

---

## 🔐 Sécurité

### Authentification
- ✅ Token basé sur UUID (identifiant unique et imprévisible)
- ✅ Vérification systématique du token à chaque requête
- ✅ Limite de 3 tentatives pour éviter brute force

### Autorisation
- ✅ Isolement des données par utilisateur
- ✅ Vérification de propriété avant chaque accès
- ✅ Messages d'erreur génériques (ne révèlent pas l'existance d'un incident)

### Intégrité
- ✅ ID auto-généré et unique
- ✅ Données sauvegardées instantanément
- ✅ Persistance en fichier sérialisé

---

## 📊 Diagrammes UML

### Diagramme de Cas d'Utilisation
Voir: [`DIAGRAMME_CAS_UTILISATION.md`](DIAGRAMME_CAS_UTILISATION.md)

```
                    ┌─────────────────────┐
                    │    UTILISATEUR      │
                    └───────────┬─────────┘
                                │
                ┌───────────────┼───────────────┐
                │               │               │
                ▼               ▼               ▼
            ┌─────────┐   ┌──────────┐   ┌──────────────┐
            │ S'authen│   │ Déclarer │   │  Consulter   │
            │ tifier  │   │ Incident │   │ Mes Incidents│
            └─────────┘   └──────────┘   └──────────────┘
                │               │               │
                └───────────────┼───────────────┘
                                │
                        ┌───────▼────────┐
                        │ HELP'OPS       │
                        │ SYSTEM         │
                        └────────────────┘
                        
                    ▲
              ┌─────┴──────┐
              │  Consulter │
              │  Détail    │
              │ Incident   │
              └────────────┘
```

### Diagramme de Classes

```
┌──────────────────────────┐
│      <<interface>>       │
│       IHelpOps           │
├──────────────────────────┤
│ + authentifier()         │
│ + verifierToken()        │
│ + signalerIncident()     │
│ + listerMesIncidents()   │
│ + consulterIncident()    │
│ + changerStatut()        │
│ + ping()                 │
└──────────────────────────┘
         △
         │ implémente
         │
┌────────────────────────────────────┐
│    HelpOpsServer                   │
├────────────────────────────────────┤
│ - utilisateurs: Map                │
│ - tokens: Map                      │
│ - incidents: List<Incident>        │
│ - compteurId: AtomicInteger        │
├────────────────────────────────────┤
│ + authentifier()                   │
│ + signalerIncident()               │
│ + consulterIncident()              │
│ + chargerDonnees()                 │
│ + sauvegarderDonnees()             │
└────────────────────────────────────┘
         △ utilise
         │
         │
    ┌────┴────────┐
    │             │
┌───────────┐  ┌──────────┐
│  Incident │  │   User   │
├───────────┤  ├──────────┤
│ - id      │  │ - nom    │
│ - titre   │  │ - email  │
│ - categorie   │ - role   │
│ - description │└──────────┘
│ - statut  │
│ - utilisateur │
│ - dateCreation│
└───────────┘
```

---

## 📝 Documentation

### Fichiers de Documentation

| Fichier | Description |
|---------|-------------|
| `DIAGRAMME_CAS_UTILISATION.md` | Cas d'utilisation détaillés (UC-001 à UC-004) |
| `RAPPORT_CAS_UTILISATION.md` | Rapport académique complet (Partie I: Conception) |
| `CAS_UTILISATION_ASCII.txt` | Visualisations ASCII du système |
| `DiagrammeCasUtilisation.puml` | Format PlantUML pour génération de diagrammes |
| `DiagrammeCasUtilisation.svg` | Diagramme graphique (ouvrir au navigateur) |

---

## 🗂️ Persistance des Données

Les incidents sont sauvegardés dans **`helpops_donnees.dat`** (répertoire `bin/`):
- Format: Sérialisation Java (ObjectOutputStream)
- Contenu: Liste d'incidents + dernier ID généré
- Comportement: Automatiquement chargé au démarrage du serveur

### Exemple
```
Avant redémarrage: 3 incidents existants
↓ Redémarrer serveur
Après redémarrage: 3 incidents restaurés (+ nouveau compteurId)
```

---

## 🔄 Évolution - Roadmap V2, V3, V4

### Version 2 - Quelqu'un va devoir le résoudre
- [ ] Rôles UTILISATEUR/AGENT
- [ ] Assignation d'incidents à un agent
- [ ] Passage de statut OPEN → ASSIGNED
- [ ] Consultation incidents assignés

### Version 3 - On s'y met
- [ ] Résolution d'incidents (ASSIGNED → RESOLVED)
- [ ] Traçabilité: agent résolvent + message résolution
- [ ] Statistiques (tickets, temps moyen, taux de pression)

### Version 4 - Supervision temps réel
- [ ] Event Streaming (création, assignation, résolution)
- [ ] Supervision en temps réel avec 2+ clients
- [ ] Options: "flux à partir de maintenant" ou "rattrapage + flux"

---

## 🧪 Tests

### Scénario Test 1: Authentification
```
Saisir: alice / pass123
Résultat attendu: ✓ Token généré, accès au menu
```

### Scénario Test 2: Créer un incident
```
Déclarer: Catégorie="Réseau", Titre="Pas WiFi", Desc="..."
Résultat attendu: ✓ Incident ID=1, Statut=OPEN, persisté
```

### Scénario Test 3: Isolation des données
```
Alice crée incident #1
Bob consulte: "Pas d'incidents"
Bob essaie accès incident #1: "Non autorisé"
Résultat attendu: ✓ Sécurité respectée
```

---

## 🐛 Bugs Connus / À Améliorer

- ⚠️ Tokens sans expiration (à ajouter)
- ⚠️ Base d'utilisateurs hardcodée (passer à BD réelle)
- ⚠️ Pas de tests unitaires
- ⚠️ Logs basés sur println (utiliser Log4j)
- ⚠️ Pas de gestion d'erreurs côté client (à enrichir)

---

## 🤝 Contribution

Ce projet est un travail académique d'équipe. Pour contribuer:
1. Fork le repository
2. Créer une branche (`git checkout -b feature/xxx`)
3. Commit vos changements (`git commit -m "Ajout de..."`)
4. Push la branche (`git push origin feature/xxx`)
5. Ouvrir une Pull Request

**Important**: Tous les changements doivent respecter les conventions de code et être documentés.

---

## 📄 Licence

MIT License - Voir [`LICENSE`](LICENSE) pour plus de détails.

---

## 👨‍💼 Auteurs

- **Keita Balla** - `ballakei070@gmail.com`
- **Équipe HELP'OPS** - Master 1 STRI

---

## 📞 Support

Pour toute question ou problème:
1. Vérifier la [documentation](DIAGRAMME_CAS_UTILISATION.md)
2. Consulter les [issues existantes](https://github.com/votre-username/HELP-OPS/issues)
3. Créer une nouvelle issue avec des détails complets

---

## 📅 Historique des Versions

| Version | Date | Statut | Notes |
|---------|------|--------|-------|
| **1.0** | 2026-02-18 | ✅ Complète | Fondations: auth, déclaration, consultation |
| 2.0 | Planifiée | 📋 À venir | Agents, assignation |
| 3.0 | Planifiée | 📋 À venir | Résolution, statistiques |
| 4.0 | Planifiée | 📋 À venir | Event streaming temps réel |

---

## 🎓 Contexte Académique

**Matière**: Projet JAVA - Sockets/RMI  
**Année**: 2025-2026  
**Établissement**: Master 1 STRI  
**Équipe**: Groupe de 4 étudiants  

**Objectif**: Concevoir et réaliser une plateforme logicielle distribuée de gestion d'incidents.

---

**Dernière mise à jour**: 18 février 2026

⭐ Si ce projet vous plaît, n'hésitez pas à mettre une star! ⭐
