# 📊 DIAGRAMME DE CAS D'UTILISATION - GUIDE DE LECTURE

## 🎨 COMPRENDRE LE FORMAT UML STANDARD

Voici comment lire le diagramme **HELP'OPS V1** :

---

## 🧑 LES ACTEURS (Les "Bonhommes Bâton")

```
        O
       /|\
        |
       / \
```

### Utilisateur
- **Qui ?** Les personnes qui utilisent HELP'OPS
- **Rôle** : Initier les actions (cliquer dans l'interface)
- **Position** : À gauche du diagramme
- **Comptes de test** : alice, bob, charlie

---

## 📦 LE SYSTÈME (Grand Rectangle)

```
┌──────────────────────────────────┐
│       HELP'OPS v1.0              │
│  (Tout le système à l'intérieur) │
└──────────────────────────────────┘
```

Le rectangle représente les **frontières du système** :
- ✅ Ce qui est **dedans** = ce que le système doit faire
- ❌ Ce qui est **dehors** = ce qui ne concerne pas le système

---

## 🎯 LES CAS D'UTILISATION (Ovales)

### Types de Cas (par couleur)

#### 🔵 BLEU = Cas d'Utilisation PRIMAIRE
- Ce que l'**utilisateur utilise directement**
- Ce que l'utilisateur **voit/fait** dans l'interface

**Exemples HELP'OPS:**
- ✅ S'Authentifier
- ✅ Déclarer un Incident
- ✅ Consulter Mes Incidents
- ✅ Consulter Détail d'Incident

#### 🟢 VERT = Cas d'Utilisation SECONDAIRE (Fonctions Internes)
- Ce que le **système fait en arrière-plan**
- Ce que l'utilisateur **ne voit pas ou peu**
- Exécutées **automatiquement par le système**

**Exemples HELP'OPS:**
- ✅ Vérifier Token (interne)
- ✅ Générer Token (interne)
- ✅ Créer Incident (système)
- ✅ Filtrer Données (système)
- ✅ Sauvegarder (système)

---

## 🔗 LES CONNEXIONS (Les Flèches)

### 1️⃣ Flèche Simple → (Connexion Acteur-Cas)

```
Utilisateur ----→ S'Authentifier
```

**Signification** : L'utilisateur **utilise** ce cas d'utilisation

**Exemple complet** :
```
        ┌─────────────┐
        │ Utilisateur │
        └──────┬──────┘
               │
               ├──→ S'Authentifier
               ├──→ Déclarer Incident
               ├──→ Consulter Incidents
               └──→ Consulter Détail
```

### 2️⃣ Flèche Pointillée <<include>> (Inclusion)

```
S'Authentifier ···→ Générer Token
               <<include>>
```

**Signification** : Chaque fois que "S'Authentifier" est exécuté, il **DOIT exécuter** "Générer Token"

**Type** : Relation obligatoire

**Exemple complet HELP'OPS:**

```
┌────────────────────────────────────────┐
│                                        │
│  S'Authentifier                        │
│       ├──► Générer Token (obligatoire) │
│       └──► Vérifier Token (obligatoire)│
│                                        │
│  Déclarer Incident                     │
│       ├──► Vérifier Token              │
│       ├──► Créer Incident              │
│       └──► Sauvegarder                 │
│                                        │
│  Consulter Mes Incidents               │
│       ├──► Vérifier Token              │
│       └──► Filtrer par Utilisateur     │
│                                        │
│  Consulter Détail d'Incident           │
│       ├──► Vérifier Token              │
│       └──► Vérifier Propriété          │
└────────────────────────────────────────┘
```

---

## 📋 COMMENT LIRE LE DIAGRAMME COMPLET

### Étape 1: Identifier les acteurs
```
❓ Qui utilise le système?
✅ L'Utilisateur (bonhomme à gauche)
```

### Étape 2: Identifier le système
```
❓ Qu'est-ce qu'on est en train de décrire?
✅ HELP'OPS v1.0 (le grand rectangle)
```

### Étape 3: Identifier les cas primaires (bleus)
```
❓ Quels sont les cas d'utilisation que l'utilisateur utilise?
✅ 4 cas:
  - S'Authentifier
  - Déclarer un Incident
  - Consulter Mes Incidents
  - Consulter Détail d'Incident
```

### Étape 4: Identifier les cas secondaires (verts)
```
❓ Quelles fonctions le système exécute en arrière-plan?
✅ 5 cas internes:
  - Vérifier Token (sécurité)
  - Générer Token (sécurité)
  - Créer Incident (métier)
  - Filtrer Données (données)
  - Sauvegarder (persistance)
```

### Étape 5: Lire les inclusions
```
❓ Quand "Déclarer un Incident" est exécuté, que se passe-t-il?
✅ Le système exécute automatiquement:
  1. Vérifier Token (<<include>>)
  2. Créer Incident (<<include>>)
  3. Sauvegarder (<<include>>)
```

---

## 🎬 EXEMPLE: FLUX COMPLET ALICE DÉCLARE UN INCIDENT

### Lecture du Diagramme:

```
┌─────────────────────────────────────────────────────┐
│                  HELP'OPS v1.0                      │
│                                                     │
│  Alice ──────────────┐                             │
│     (Utilisateur)    │                             │
│                      ▼                             │
│                 ┌──────────────┐                   │
│                 │  Déclarer    │                   │
│                 │   Incident   │                   │
│                 └──────┬───────┘                   │
│                        │                           │
│          ┌─────────────┼─────────────┐            │
│          ▼             ▼             ▼            │
│      ┌─────────┐  ┌─────────┐  ┌─────────┐      │
│      │ Vérifier│  │  Créer  │  │Sauvegarder   │      │
│      │ Token   │  │ Incident│  │         │      │
│      └─────────┘  └─────────┘  └─────────┘      │
│      <<include>>  <<include>>  <<include>>       │
│                                                     │
└─────────────────────────────────────────────────────┘
```

**Ce que ça signifie:**
1. Alice clique sur "Déclarer un Incident"
2. Le système **automatiquement** :
   - Vérifie que son token est valide
   - Crée l'incident en BD
   - Sauvegarde les données
3. Alice reçoit la confirmation

---

## 🔒 EXEMPLE: S'AUTHENTIFIER

```
┌─────────────────────────────────────────────────────┐
│                  HELP'OPS v1.0                      │
│                                                     │
│  Alice ──────────────┐                             │
│     (Utilisateur)    │                             │
│                      ▼                             │
│                 ┌──────────────┐                   │
│                 │      S'      │                   │
│                 │  Authentifier│                   │
│                 └──────┬───────┘                   │
│                        │                           │
│          ┌─────────────┴─────────────┐            │
│          ▼                           ▼            │
│      ┌──────────────┐           ┌──────────┐     │
│      │   Vérifier   │           │ Générer  │     │
│      │  Credentials │           │   Token  │     │
│      │(alice/pass123)           │   UUID   │     │
│      └──────────────┘           └──────────┘     │
│      <<include>>                <<include>>      │
│                                                     │
│  Résultat: Token = "f47ac10b-58cc-4372..."       │
│                                                     │
└─────────────────────────────────────────────────────┘
```

---

## 🔐 EXEMPLE: CONSULTER DÉTAIL INCIDENT

```
┌─────────────────────────────────────────────────────┐
│                  HELP'OPS v1.0                      │
│                                                     │
│  Alice ──────────────┐                             │
│     (Utilisateur)    │                             │
│                      ▼                             │
│         ┌────────────────────────┐                │
│         │ Consulter Détail       │                │
│         │ d'Incident (ID=1)      │                │
│         └────────────┬───────────┘                │
│                      │                            │
│          ┌───────────┴────────────┐               │
│          ▼                        ▼               │
│      ┌──────────┐          ┌─────────────┐       │
│      │ Vérifier │          │ Vérifier    │       │
│      │  Token   │          │ Propriété   │       │
│      │(Alice ok)│          │(Alice owner)│       │
│      └──────────┘          └─────────────┘       │
│      <<include>>           <<include>>           │
│                                                     │
│  SI Token OK + Alice propriétaire                │
│     → Afficher détails                           │
│  SINON                                           │
│     → Message "Non autorisé"                     │
│                                                     │
└─────────────────────────────────────────────────────┘
```

---

## 📝 TABLEAU RÉCAPITULATIF

| Cas Primaire | Couleur | Utilisateur le Voit? | Inclusions |
|---|---|---|---|
| S'Authentifier | 🔵 Bleu | OUI (interface) | Générer Token |
| Déclarer Incident | 🔵 Bleu | OUI (formulaire) | Vérif Token, Créer, Sauvegarder |
| Consulter Incidents | 🔵 Bleu | OUI (liste) | Vérif Token, Filtrer |
| Consulter Détail | 🔵 Bleu | OUI (détails) | Vérif Token, Vérif Propriété |
| **Vérifier Token** | 🟢 Vert | **NON** (arrière-plan) | Sécurité systématique |
| **Générer Token** | 🟢 Vert | **NON** (arrière-plan) | Sécurité systématique |
| **Créer Incident** | 🟢 Vert | **NON** (arrière-plan) | Métier |
| **Filtrer Données** | 🟢 Vert | **NON** (arrière-plan) | Isolation utilisateur |
| **Sauvegarder** | 🟢 Vert | **NON** (arrière-plan) | Persistance |

---

## ✅ RÉSUMÉ

### Lecture Simple:
1. **🧑 Utilisateur** (à gauche) utilise le système
2. **📦 HELP'OPS** (rectangle) contient tout le système
3. **🔵 Bleus** = Ce que l'utilisateur fait (interface)
4. **🟢 Verts** = Ce que le système fait (arrière-plan)
5. **→** = "utilise"
6. **···> <<include>>** = "exécute obligatoirement"

### Interprétation:
- Chaque cas bleu = une action visible pour l'utilisateur
- Chaque cas vert = une fonction système cachée mais obligatoire
- Les inclusions montrent la **dépendance entre cas**
- Les flèches montrent le **flux d'utilisation**

---

## 🎓 POUR VOTRE RAPPORT

Quand vous présentez ce diagramme:

**Introduction:**
> "Ce diagramme représente les **cas d'utilisation de HELP'OPS V1**. Il montre les **4 actions principales** que l'utilisateur peut effectuer, ainsi que les **5 fonctions système** qui s'exécutent en arrière-plan pour assurer la sécurité et la persistance des données."

**Description:**
> "L'utilisateur interagit avec 4 cas d'utilisation primaires (en bleu). Chacun de ces cas inclut automatiquement des fonctions système (en vert) : vérification du token pour la sécurité, création ou filtrage pour les données, et sauvegarde pour la persistance."

**Conclusion:**
> "Cette architecture garantit que chaque action est sécurisée (token vérifié), isolée (données filtrées par utilisateur), et persistante (sauvegardée)."

---

**Besoin d'aide pour générer le diagramme en image ?** 
- Utilisez [PlantUML Online](http://www.plantuml.com/plantuml/uml/)
- Collez le code `.puml` et exportez en PNG/SVG
