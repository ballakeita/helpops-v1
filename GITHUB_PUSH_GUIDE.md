# 🚀 GUIDE: POUSSER VOTRE PROJET SUR GITHUB

## ✅ ÉTAPES COMPLÉTÉES

Vous avez déjà fait:
```bash
✓ git init                  # Repository Git créé localement
✓ git add .                 # Tous les fichiers ajoutés
✓ git commit -m "..."       # Premier commit effectué
✓ git remote add origin ...  # Remote GitHub configuré
```

## 📋 ÉTAPES RESTANTES

### 1️⃣ CRÉER LE REPOSITORY GITHUB

1. Allez sur https://github.com et connectez-vous
2. Cliquez sur **`+`** en haut à droite → **"New repository"**
3. Remplissez:
   - **Repository name**: `helpops-v1`
   - **Description**: "Plateforme de gestion d'incidents distribuée - Version 1 (Java RMI)"
   - **Visibility**: `Public`
   - ❌ **NE cochez pas** "Initialize with README" (on le fait localement)
4. Cliquez **"Create repository"**

**Votre URL sera**: `https://github.com/ballakeita/helpops-v1.git`

---

### 2️⃣ CONFIGURER L'AUTHENTIFICATION

#### Option A: Token Personnel (Recommandé)

1. Allez sur https://github.com/settings/tokens/new
2. Configurez:
   - **Token name**: `git-helpops`
   - **Expiration**: 90 days (ou plus)
   - **Scopes**: Cochez `repo` (full control of private repositories)
3. Cliquez "Generate token"
4. **COPIEZ le token** (vous ne pourrez pas le revoir)
5. Gardez-le à portée de main pour l'étape 3

#### Option B: SSH Key (Plus sécurisé mais plus complexe)

Vous pouvez utiliser SSH si vous avez déjà une clé configurée:
```bash
git remote set-url origin git@github.com:ballakeita/helpops-v1.git
```

---

### 3️⃣ POUSSER LE CODE

Exécutez dans votre terminal:

```bash
cd "c:\Users\balla\Desktop\Paul_Sab\STRI_M1\Projet MPCR\projetMCPR"
git push -u origin main
```

**Quand vous êtes demandé:**
- **Username**: Votre pseudo GitHub (ex: `ballakeita`)
- **Password**: Collez le token personnel que vous avez copié à l'étape 2

---

### 4️⃣ VÉRIFIER LE SUCCÈS

Si tout fonctionne, vous verrez:
```
Enumerating objects: 15, done.
Counting objects: 100% (15/15), done.
Delta compression using up to 8 threads
Compressing objects: 100% (11/11), done.
Writing objects: 100% (15/15), 45.23 KB | 5.64 MB/s, done.
Total 15 (delta 0), reused 0 (delta 0), pack-reused 0
To https://github.com/ballakeita/helpops-v1.git
 * [new branch]      main -> main
Branch 'main' set up to track remote branch 'main' from 'origin'.
```

Ensuite, allez sur https://github.com/ballakeita/helpops-v1 pour voir votre repo! 🎉

---

## 📊 VOS FICHIERS SERONT POUSSÉS

```
helpops-v1/
├── .gitignore                          ← Fichiers ignorés
├── LICENSE                             ← Licence MIT
├── README.md                           ← Documentation principale ⭐
├── DIAGRAMME_CAS_UTILISATION.md       ← Documentation UML
├── RAPPORT_CAS_UTILISATION.md         ← Rapport académique complet
├── CAS_UTILISATION_ASCII.txt          ← Visualisations ASCII
├── DiagrammeCasUtilisation.puml       ← Format PlantUML
├── DiagrammeCasUtilisation.svg        ← Diagramme SVG
└── projet/
    ├── README.md
    ├── src/
    │   └── helpops/
    │       ├── client/
    │       │   └── HelpOpsClient.java
    │       ├── server/
    │       │   └── HelpOpsServer.java
    │       ├── model/
    │       │   ├── Incident.java
    │       │   └── User.java
    │       └── rmi/
    │           └── IHelpOps.java
    └── .gitignore
```

---

## 🔄 COMMANDES UTILES APRÈS LE PUSH

```bash
# Vérifier que tout est synchro
git status
# Output: On branch main, Your branch is up to date with 'origin/main'

# Voir l'historique
git log --oneline
# Output: 7c9fd12 Initial commit: HELP'OPS V1 - Fondations...

# Cloner le repo (pour teste depuis un autre dossier)
git clone https://github.com/ballakeita/helpops-v1.git helpops-test
cd helpops-test
```

---

## ⚠️ TROUBLESHOOTING

### Problème: "fatal: unable to access repository"
**Solution**: 
- Vérifiez que le repository GitHub existe (vous l'avez créé)
- Vérifiez l'URL: `git remote -v`
- Réauthentifiez avec votre token

### Problème: "Updates were rejected because the tip of your current branch is behind"
**Solution** (si vous avez créé le repo avec README):
```bash
git pull origin main --allow-unrelated-histories
git push -u origin main
```

### Problème: Token expiré
**Solution**:
1. Allez générer un nouveau token sur GitHub
2. `git config --unset credential.helper` (réinitialise)
3. Essayez de pousser à nouveau

---

## 📝 CHECKLIST FINALE

- [ ] Repository créé sur GitHub
- [ ] Token personnel généré
- [ ] Local repository configuré avec le remote
- [ ] Code poussé avec succès
- [ ] Visible sur GitHub (https://github.com/ballakeita/helpops-v1)
- [ ] Fichiers source compilables (`.java`)
- [ ] Documentation complète (README, diagrammes)
- [ ] License MIT présente

---

## 🎓 APRÈS LE PUSH

Maintenant, vous pouvez:

1. **Partager le lien**: https://github.com/ballakeita/helpops-v1
2. **Collaborer** avec votre équipe (ajouter des collaborateurs)
3. **Tracker les tâches** (GitHub Issues)
4. **Documenter** le développement (GitHub Discussions)
5. **Faire des releases** (pour les versions)
6. **Protéger la branche** `main` (paramètres du repo)

---

## 🚀 POUR LES COMMITS FUTURS

Une fois le remote configuré, c'est simple:

```bash
# Faire des changements...
git add .
git commit -m "Description du changement"
git push
```

C'est tout! 🎉

---

**Questions ?** Consultez la [documentation GitHub](https://docs.github.com/en)

**Besoin d'aide SSH ?** Voir https://docs.github.com/en/authentication/connecting-to-github-with-ssh
