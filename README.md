# Immolink

## Description du projet
Ce projet vise à développer une application de gestion de biens immobiliers destinée aux propriétaires privés. L'application simplifie la gestion locative en centralisant des informations importantes (coordonnées des locataires, diagnostics, relevés de compteurs, etc.), en automatisant des processus clés (régularisation des charges, déclaration fiscale).

### Objectifs :
1. **Simplifier la gestion locative** : Centraliser les informations essentielles et automatiser les processus comme la déclaration fiscale et la régularisation des charges.
2. **Suivi des locataires** : Faciliter le suivi des locataires actuels et passés avec un historique complet et des statistiques.
3. **Gestion documentaire et conformité** : Fournir un espace sécurisé pour stocker et organiser les documents liés à la location, tels que les baux, diagnostics, et états des lieux.

## Fonctionnalités principales
- **Gestion des locataires** : Gestion des locataires actuels et archivage des anciens locataires.
- **Automatisation des charges** : Régularisation automatique des charges annuelles, génération de factures et remboursements.
- **Déclaration fiscale** : Automatisation de la déclaration fiscale avec les informations nécessaires (revenus locatifs, travaux, assurances).
- **Règles de gestion des baux** : Augmentation automatique des loyers en fonction de l'indice de l'État et gestion des fins de baux.

## Apperçu visuel de l'application
### Locataires
Voici notre page d'accueil, sur celle-ci il est possible de consulter nos différents locataires.
![Image page d'Accueil](images-for-readme/page-accueil.png)


Il est aussi possible d'observer si ils ont payé ou non les différents loyers des baux.
![Image page Locataire](images-for-readme/locataire.png)

Nous pouvons aussi créer des locataires pour pouvoir ensuite les insérer dans les différents baux
![Image create Locataire](images-for-readme/add-locataire.png)

### Biens louables
Voici notre page pour consulter nos différents biens louables.
![Image page biens louables](images-for-readme/mes-biens.png)

Il est aussi possible d'en créer de nouveaux, nous pouvons créer 3 types de biens différents, un **Batiment** où l'on pourra mettre à l'intérieur des **Appartements**, des **Maisons** et des **Garages**.
![Image page de création d'un bien louable](images-for-readme/add-bien.png)

Il est aussi possible de consulter un bien louable en particulier pour pouvoir y apporter des modifications : 
- *modification d'un diagnostic périmé*
- *ajout d'un travail*
- *liaison d'un bien à un garage*
- *accès aux différents diagnostics*

![Image page de détail d'un bien louable](images-for-readme/appart.png)

### Baux
Voici notre page pour consulter nos différens baux (Il est possible d'en insérer plus qu'un bien évidemment).
![Image page des différents baux](images-for-readme/mes-baux.png)

Nous pouvons aussi créer un bail !
Nous pouvons y préciser toutes les informations importantes utiles lors de la gestion d'un bail :
- *Précison du loyer, Provision pour charge, Dépot de garantie, ICC, Index Eau*
- *Précision des locataires et de leur quotité*

![Image creation de bail](images-for-readme/new-bail.png)

Il est aussi possible de consulter celui-ci pour accéder à différentes actions :
- *modification de la prévision pour charges*
- *modification de la présence des locataires (ajout/suppression)*
- *modification du loyer à l'anniversaire du bail*
- *accès à la gestion des charges du bail en question*

![Image page de détail d'un bail](images-for-readme/mon-bail.png)

### Charges
Depuis votre bail, vous pouvez consulter les différentes charges de celui-ci
![Image page bail](images-for-readme/charges.png)

Il est possible de faire plusieurs actions comme consulter l'historique des différentes factures de notre bail
![Image consultation historique des factures de notre bail](images-for-readme/liste-factures-charges.png)

Ou encore pouvoir ajouter une charge
![Image ajout d'une charge](images-for-readme/add-charge.png)

La régularisation des charges de notre bail sur une année donnée
![Image régularisation des charges 2025](images-for-readme/regularisation-charge.png)

### Notifications
Lorsqu'un bail est terminé, ou qu'un diagnostic est périmé, une notification apparaîtra dans cette page-ci, vous pourrez donc facilement consulter les différents baux ou bien louables à aller modifier
![Image page notification](images-for-readme/notifications.png)
## Installation
### Prérequis
- Java 8
- Git
- Créer la base de donnée MySQL à l'aide du script SQL à la racine du projet, nommer la base de donnée **immolink**
- Utiliser un outil comme **WAMP** ou **XAMPP** pour activer le serveur en local MYSQL

## Membres de l'équipe de développement
1. **BLATEAU Indi** 
2. **COLSON Arthur**
3. **REVERBEL Clément**
4. **REYNIER Zyad**

## Licence d'utilisation
La licence est disponible à la racine du projet
