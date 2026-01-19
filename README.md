# Library Management System

Système de gestion de bibliothèque développé en Java 21 LTS avec Maven et PostgreSQL.

##  Prérequis

- **Java 21 LTS** ou supérieur
- **Maven 3.9+**
- **PostgreSQL 12+** (base de données)

##  Installation

### 1. Cloner ou télécharger le projet

```bash
cd Desktop
# Naviguez vers le dossier Library-project
```

### 2. Configurer la base de données PostgreSQL

Assurez-vous que PostgreSQL est en cours d'exécution et créez/configurez votre base de données.

Modifiez les paramètres de connexion dans `src/DBConnection.java` si nécessaire (URL, utilisateur, mot de passe).

### 3. Vérifier l'installation de Java 21

```powershell
java -version
```

Vous devriez voir : `openjdk version "21.x.x"` ou `Java 21 LTS`

### 4. Vérifier Maven

```powershell
mvn -v
```

##  Utilisation

### Compiler le projet

Depuis le dossier `lib` :

```powershell
cd lib
mvn -DskipTests package
```

Ou depuis la racine du projet :

```powershell
mvn -f lib\pom.xml -DskipTests package
```

### Exécuter les tests

Depuis le dossier `lib` :

```powershell
mvn test
```

Ou depuis la racine du projet :

```powershell
mvn -f lib\pom.xml test
```

### Lancer l'application

Depuis le dossier `lib` :

```powershell
java -cp "target\classes;.\postgresql-42.7.9.jar" Main
```

L'application affichera un menu interactif avec les options suivantes :

1. **Ajouter un livre** — Enregistrer un nouveau livre dans la bibliothèque
2. **Rechercher un livre** — Trouver un livre par titre
3. **Inscrire un membre** — Ajouter un nouveau membre à la bibliothèque
4. **Enregistrer un emprunt** — Créer un nouvel emprunt
5. **Afficher les emprunts en retard** — Voir les livres non retournés à temps
6. **Retourner un livre** — Enregistrer le retour d'un livre emprunté
7. **Quitter** — Fermer l'application

##  Structure du projet

```
Library-project/
├── src/
│   ├── Main.java              # Classe principale (menu interactif)
│   ├── DBConnection.java      # Configuration de la connexion BD
│   ├── Livre.java             # Modèle Livre
│   ├── Membre.java            # Modèle Membre
│   ├── Emprunt.java           # Modèle Emprunt
│   ├── LivreDAO.java          # DAO pour les opérations Livre
│   ├── MembreDAO.java         # DAO pour les opérations Membre
│   └── EmpruntDAO.java        # DAO pour les opérations Emprunt
├── lib/
│   ├── pom.xml                # Configuration Maven (Java 21)
│   ├── postgresql-42.7.9.jar  # Driver PostgreSQL
│   └── target/
│       ├── classes/           # Fichiers .class compilés
│       └── library-management-1.0-SNAPSHOT.jar
└── resources/                 # Fichiers de ressources
```

##  Configuration de la base de données

Modifiez [src/DBConnection.java](../src/DBConnection.java) pour configurer l'accès à votre base PostgreSQL :

```java
private static final String URL = "jdbc:postgresql://localhost:5432/votre_base_de_donnees";
private static final String USER = "votre_utilisateur";
private static final String PASSWORD = "votre_mot_de_passe";
```

##  Vérification

Pour vérifier que tout fonctionne :

1. **Compiler sans erreurs** :

   ```powershell
   mvn -DskipTests package
   ```

   Devrait afficher `BUILD SUCCESS`

2. **Lancer l'application** :
   ```powershell
   java -cp "target\classes;.\postgresql-42.7.9.jar" Main
   ```
   Devrait afficher le menu et se connecter à la base de données avec le message `Connexion à la base de données réussie !`

##  Notes

- L'application utilise **Java 21 LTS** pour bénéficier des dernières améliorations et du support long terme
- Les emprunts supportent les formats de date `YYYY-MM-DD` et `DD-MM-YYYY`
- Des pénalités sont appliquées automatiquement pour les retards de retour
- Les classes **DAO** (Data Access Object) gèrent toutes les opérations sur la base de données

##  Dépannage

### Erreur : "POM file not found"

Assurez-vous d'être dans le dossier `lib` ou utilisez `-f lib\pom.xml` depuis la racine.

### Erreur : "No database connection"

Vérifiez que PostgreSQL est en cours d'exécution et que les paramètres de connexion dans `DBConnection.java` sont corrects.

### Erreur : "Class not found: Main"

Assurez-vous d'exécuter la commande depuis le dossier `lib` et que `target\classes` existe.

---

**Version Java** : 21 LTS  
**Gestionnaire de build** : Maven 3.9+  
**Base de données** : PostgreSQL 12+
