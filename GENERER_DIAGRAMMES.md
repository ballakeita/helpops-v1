# 🖼️ GÉNÉRER LES DIAGRAMMES EN IMAGE PNG/SVG

## Option 1: PLANTUML EN LIGNE (Gratuit - Recommandé ⭐)

### Étape 1: Ouvrir PlantUML Online
1. Allez sur **http://www.plantuml.com/plantuml/uml/**
2. Vous verrez un éditeur avec un exemple

### Étape 2: Copier le code
1. Ouvrez le fichier `DiagrammeCasUtilisation_SIMPLE.puml` (dans votre dossier projet)
2. Copiez **tout le contenu**

### Étape 3: Coller dans PlantUML
1. Sélectionnez **tout** dans l'éditeur PlantUML (Ctrl+A)
2. Collez votre code (Ctrl+V)
3. Le diagramme se génère **automatiquement** à droite

### Étape 4: Exporter en PNG
1. Clic droit sur le diagramme généré
2. **"Save as image..."** ou **"Copy image"**
3. Enregistrez en tant que `DiagrammeCasUtilisation_SIMPLE.png`

**Avantages:**
- ✅ Gratuit
- ✅ Pas d'installation
- ✅ Résultat instantané
- ✅ Qualité excellente
- ✅ Supporte tous les formats

---

## Option 2: PlantUML VS Code (Plus simple si vous utilisez VS Code)

### Prérequis:
- VS Code installé
- Extension PlantUML installée

### Étape 1: Installer l'extension
1. VS Code → Extensions (Ctrl+Shift+X)
2. Recherchez **"PlantUML"**
3. Installez l'extension par **jgraph** ou **eladkarako**

### Étape 2: Ouvrir le fichier
1. VS Code → Ouvrir `DiagrammeCasUtilisation_SIMPLE.puml`

### Étape 3: Prévisualiser
1. Clic droit sur le fichier
2. **"Preview Diagram"** OU **Ctrl+Shift+P** → "PlantUML: Preview"

### Étape 4: Exporter
1. Dans la prévisualisation → Clic droit
2. **"Export..."**
3. Choisissez PNG ou SVG
4. Enregistrez

**Avantages:**
- ✅ Intégré dans VS Code
- ✅ Prévisualisation en temps réel
- ✅ Plusieurs formats disponibles
- ✅ Peut automatiser avec scripts

---

## Option 3: Ligne de Commande (Pour les développeurs)

### Prérequis:
- Java installé (version 8+)

### Étape 1: Télécharger PlantUML
```bash
# Téléchargez depuis https://plantuml.com/download
# Ou utilisez le gestionnaire de paquets:
# Windows (avec Chocolatey):
choco install plantuml

# Mac (avec Homebrew):
brew install plantuml

# Linux (avec apt):
sudo apt install plantuml
```

### Étape 2: Générer l'image
```bash
# Allez dans le dossier du projet
cd "c:\Users\balla\Desktop\Paul_Sab\STRI_M1\Projet MPCR\projetMCPR"

# Générer PNG
plantuml DiagrammeCasUtilisation_SIMPLE.puml

# Générer SVG
plantuml -tsvg DiagrammeCasUtilisation_SIMPLE.puml

# Générer PDF
plantuml -tpdf DiagrammeCasUtilisation_SIMPLE.puml
```

### Résultat:
```
✓ DiagrammeCasUtilisation_SIMPLE.png
✓ DiagrammeCasUtilisation_SIMPLE.svg
✓ DiagrammeCasUtilisation_SIMPLE.pdf
```

**Avantages:**
- ✅ Automatisable
- ✅ Tous les formats
- ✅ Qualité maximale
- ✅ Batch processing possible

---

## Option 4: Docker (Pro)

Si vous avez Docker:

```bash
# Générer l'image avec Docker
docker run -v "$(pwd)":/app plantuml/plantuml:latest \
  java -jar /plantuml.jar /app/DiagrammeCasUtilisation_SIMPLE.puml

# Résultat: DiagrammeCasUtilisation_SIMPLE.png créé
```

---

## 📋 COMPARAISON DES OPTIONS

| Option | Installation | Facilité | Gratuit | Qualité | Recommandation |
|--------|--------------|----------|---------|---------|---|
| **En ligne** | Aucune | ⭐⭐⭐⭐⭐ | ✅ | Excellente | **MEILLEURE** |
| **VS Code** | Extension | ⭐⭐⭐⭐ | ✅ | Excellente | Bonne |
| **Ligne de commande** | Java + PlantUML | ⭐⭐⭐ | ✅ | Excellente | Pour pros |
| **Docker** | Docker | ⭐⭐ | ✅ | Excellente | Avancé |

---

## 🎯 ÉTAPES RAPIDES (Recommandé)

### Pour générer `DiagrammeCasUtilisation_SIMPLE.png` en 2 minutes:

1. **Allez sur**: http://www.plantuml.com/plantuml/uml/

2. **Collez ce code entier:**
   ```
   @startuml HelpOps_V1_UseCases_Simple
   !define ARROWCOLOR #333333

   skinparam usecase {
       BackgroundColor #DDEEF8
       BorderColor #0066CC
       ArrowColor #333333
   }

   skinparam actor {
       BackgroundColor #FFFFCC
       BorderColor #FF9900
   }

   skinparam rectangle {
       BackgroundColor #FFFFFF
       BorderColor #333333
       BorderThickness 2
   }

   left to right direction

   actor "Utilisateur" as USER #FFFFCC

   rectangle "HELP'OPS v1.0" {
       usecase "S'Authentifier" as UC1 #DDEEF8
       usecase "Déclarer\nun Incident" as UC2 #DDEEF8
       usecase "Consulter\nMes Incidents" as UC3 #DDEEF8
       usecase "Consulter\nDétail d'Incident" as UC4 #DDEEF8
       
       usecase "Vérifier\nToken" as CHECK #E8F8D8
       usecase "Générer\nToken" as GEN #E8F8D8
       usecase "Créer\nIncident" as CREATE #E8F8D8
       usecase "Filtrer\nDonnées" as FILTER #E8F8D8
       usecase "Sauvegarder" as SAVE #E8F8D8
       
       USER --> UC1
       USER --> UC2
       USER --> UC3
       USER --> UC4
       
       UC1 .> GEN : <<include>>
       UC2 .> CHECK : <<include>>
       UC2 .> CREATE : <<include>>
       UC2 .> SAVE : <<include>>
       UC3 .> CHECK : <<include>>
       UC3 .> FILTER : <<include>>
       UC4 .> CHECK : <<include>>
       UC4 .> FILTER : <<include>>
   }
   @enduml
   ```

3. **Clic droit sur le diagramme** → "Save image" ou "Copy"

4. **Enregistrez en tant que**: `DiagrammeCasUtilisation_SIMPLE.png`

5. **Voilà !** ✅ Vous avez votre image PNG

---

## 🎨 EXEMPLE DE RÉSULTAT

Voici ce que vous obtiendrez (approximativement):

```
┌────────────────────────────────────────────┐
│         HELP'OPS v1.0                      │
│                                            │
│  Utilisateur                               │
│       ∘                                    │
│      /│\                                   │
│       │                                    │
│      / \                                   │
│      │                                     │
│  ┌───┴──────────┬──────────┬──────────┐   │
│  │              │          │          │   │
│  ▼              ▼          ▼          ▼   │
│ S'Auth    Déclarer   Consulter   Consulter│
│          Incident      Incidents    Détail│
│  │         │    ├─►    │  ├─►       │    │
│  ├─►       ├─►  │      ├─►│        ├─►  │
│  │         │    │      │  │        │    │
│  ▼         ▼    ▼      ▼  ▼        ▼    │
│ Générer  Vérifier  Créer  Filtrer   (etc)│
│ Token    Token   Incident Données        │
│                                            │
└────────────────────────────────────────────┘
```

---

## 📝 INCLURE L'IMAGE DANS VOTRE RAPPORT

### Format Word (.docx):
```
1. Insérer → Images → Choisir le PNG
2. Redimensionner pour la lisibilité
3. Ajouter une caption: "Figure 1: Diagramme de cas d'utilisation HELP'OPS V1"
```

### Format Markdown:
```markdown
## Diagramme de Cas d'Utilisation

![Diagramme de Cas d'Utilisation](./DiagrammeCasUtilisation_SIMPLE.png)

*Figure 1: Diagramme montrant les 4 cas d'utilisation principaux et les 5 fonctions système.*
```

### Format LaTeX/PDF:
```latex
\begin{figure}[h]
  \centering
  \includegraphics[width=0.8\textwidth]{DiagrammeCasUtilisation_SIMPLE.png}
  \caption{Diagramme de cas d'utilisation HELP'OPS V1}
  \label{fig:usecase}
\end{figure}
```

---

## ✅ CHECKLIST FINALE

- [ ] Diagramme généré en PNG
- [ ] Image nommée `DiagrammeCasUtilisation_SIMPLE.png`
- [ ] Image sauvegardée dans le dossier projet
- [ ] Image incluse dans le rapport
- [ ] Caption/légende ajoutée
- [ ] Explications écrites à côté
- [ ] Diagramme clair et lisible

---

## 🆘 DÉPANNAGE

### Le diagramme ne s'affiche pas
- Vérifiez que le code PlantUML est valide
- Rechargez la page plantuml.com
- Essayez un autre navigateur

### L'image est floue
- Augmentez la taille avant d'exporter
- Utilisez SVG plutôt que PNG
- Zoomez à 100% ou plus

### Les accents ne s'affichent pas
- Assurez-vous que le fichier est en UTF-8
- Placez ceci au début: `!define ARROWCOLOR #333333`

---

## 🎓 POUR VOTRE RAPPORT

**Recommandation finale:**
1. Utilisez **PlantUML en ligne** (Option 1)
2. Exportez en **PNG haute résolution**
3. Incluez dans votre rapport
4. Ajoutez une légende claire

**Résultat:** Diagramme professionnel et académique ✨

---

**Questions ?** Consultez la [documentation PlantUML](https://plantuml.com/guide)

**Besoin d'autres formats** (PDF, SVG, EPS) ?
→ PlantUML supporte tous les formats via les options `-t[format]`
