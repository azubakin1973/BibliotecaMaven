# BibliotecaMaven

Système de gestion de bibliothèque développé avec Maven et SQLite.

## Description

Ce projet est un exercice pratique qui implémente un système de gestion de bibliothèque utilisant Maven comme gestionnaire de dépendances et SQLite comme base de données. Il permet de gérer une collection de livres avec leurs informations associées.

## Technologies Utilisées

- Java
- Maven
- SQLite
- JUnit pour les tests unitaires

## Prérequis

- JDK 11 ou supérieur
- Maven 3.6+
- Git

## Installation

1. Clonez le dépôt :
```bash
git clone https://github.com/azubakin1973/BibliotecaMaven.git
```

2. Accédez au répertoire du projet :
```bash
cd BibliotecaMaven
```

3. Compilez le projet avec Maven :
```bash
mvn clean install
```

## Structure du Projet

```
BibliotecaMaven/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── bibliotheque/
│   └── test/
│       └── java/
│           └── com/
│               └── bibliotheque/
├── BibliotecaMaven.db
├── pom.xml
└── .gitignore
```

## Base de Données

Le projet utilise une base de données SQLite (BibliotecaMaven.db) pour stocker les informations des livres.

## Tests

Pour exécuter les tests unitaires :

```bash
mvn test
```

## Comment Contribuer

1. Créez un fork du projet
2. Créez une branche pour votre fonctionnalité (`git checkout -b feature/NouvelleFeature`)
3. Committez vos changements (`git commit -m 'Ajout d'une nouvelle fonctionnalité'`)
4. Poussez vers la branche (`git push origin feature/NouvelleFeature`)
5. Ouvrez une Pull Request

## Bonnes Pratiques

- Écrire des tests unitaires pour les nouvelles fonctionnalités
- Suivre les conventions de code Java
- Documenter les changements
- Vérifier que tous les tests passent avant de soumettre une PR

---

Développé par [Alexei de Moraes Zubakin, Montreal, Canada, 2025-02-19]

