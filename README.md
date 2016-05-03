# Chip-Firing-Game-Simulateur

## Introduction

Le but du TER était d'améliorer les fonctionnalités du Simulateur de Chip-Firing-Game qui avait été bien commencé par 3 étudiants l'année passée.
Le Simulateur devrait nous permettre par la suite d'être un outil permettant la visualisation de l'évolution de graphes sur de grosses instances.
On désire pouvoir prédire le comportement des CFG en fonction des jetons, sommets et mode d'évolution associés.

## Librairies Externes

GraphStream - Une bibliothèque logicielle en java de manipulation de graphes dynamiques. La librairie est divisée en plusieurs modules :
	
	* gs-core : Contient la base de GraphStream et permet de modéliser, lire, écrire et afficher un graphe,
	* gs-alog : Est composé de différents algorithmes ainsi que de générateurs de graphes,
	* gs-ui : Permet un affichage avancé des graphes.

## Prérequis

* Git
* Java >= 1.8

## Installation

git clone https://github.com/SebastienGouverneur/Chip-Firing-Game-Simulateur.git
	ou
cliquez sur "Download ZIP"


## Développement

### Objectif Première Itération

* Ajout de la fonctionnalité de pause,
* Réfléchir au calcul du nombre du configuration possibles pour une instance d'un graphe avec n jetons et m sommets,
* Tester les boutons et vérifier qu'ils ont le comportement attendu,
* Faire en sorte que les graphes sur les cycles soient non-orientés.

### Résultat Première Itération

* Ajout du bouton pause et de sa fonctionnalité d'interruption du thread courant mais il n'est pas tout à fait fonctionnel,
* Ajout des fonctionnalités ergonomiques sur les boutons File->Save, Save As, Quit : une pop-up apparait si on tente de sauvegarder un graphe vide, une pop-up apparait si l'on souhaite quitter l'application
* Modification des graphes cycliques : ils sont maintenant non-pondérés

### Objectif Deuxième Itération

* Trouver et réparer le problème du bouton pause,
* Lors du choix du template (Grid, Tore, Cycle), le refresh du bouton "OK" n'est pas fait correctement sur le preview ainsi que sur la fenêtre d'affichage du graphe, trouver et fixer le problème,
* Supprimer le bouton "+" du simulateur et faire en sorte que chaque clique sur "Par/S" ouvre la fenêtre du choix du mode d'itération directement.
* Implémenter ce mode Asynchrone qui doit nous calculer toutes les configurations c' partant d'une configuration c. Faire correspondre au click du bouton une pop-up avec une barre d'évolution qui nous donne le pourcentage de calcul effectué. Lors de la terminaison, affiche un message disant que l'on ai a 100% du calcul et nous donne la possibilité de cliquer sur le bouton d'affichage des transition,
* Modifier le bouton des transitions pour le rendre plus visible et lui donner plus d'importance.
* Incorporer les issues à la doc du git,
* Le bouton K-Chips doit se lancer la fenêtre automatiquement sans que l'on soit obligé de cliquer sur le bouton de lecture,
* Améliorer l'affichage des transitions,
* Dans l'option Chip, enlever le bouton de validation et la présélection en rouge sur les "+", "-", "=" et faire correspondre le choix d'ajout ou de substraction de jetons aux action sur les cliques des boutons précédents si un sommet du graphe est pré-sélectionné.

### Résultat Deuxième Itération

* Modifier le bouton des transitions pour le rendre plus visible et lui donner plus d'importance.
* Supprimer le bouton "+" du simulateur et faire en sorte que chaque clique sur "Par/S" ouvre la fenêtre du choix du mode d'itération directement.
* Modifier le bouton des transitions pour le rendre plus visible et lui donner plus d'importance.

### Objectif Troisième Itération

##### Priorité : Asynchrone et K-Chips

* Implémenter le mode Asynchrone qui doit nous calculer toutes les configurations c' partant d'une configuration c. Faire correspondre au click du bouton une pop-up avec une barre d'évolution qui nous donne le pourcentage de calcul effectué. Lors de la terminaison, affiche un message disant que l'on ai a 100% du calcul et nous donne la possibilité de cliquer sur le bouton d'affichage des transition,

* Le bouton K-Chips doit se lancer la fenêtre automatiquement sans que l'on soit obligé de cliquer sur le bouton de lecture,

##### Ergonomie

* Améliorer l'affichage des transitions,
* Dans l'option Chip, enlever le bouton de validation et la présélection en rouge sur les "+", "-", "=" et faire correspondre le choix d'ajout ou de substraction de jetons aux action sur les cliques des boutons précédents si un sommet du graphe est pré-sélectionné.

##### Gestion des évènements/synchronisation

* Trouver et réparer le problème du bouton pause,
* Lors du choix du template (Grid, Tore, Cycle), le refresh du bouton "OK" n'est pas fait correctement sur le preview ainsi que sur la fenêtre d'affichage du graphe, trouver et fixer le problème,

##### Organisation

* Incorporer les issues à la doc du git,

### Résultat Deuxième Itération

* Le bouton K-Chips lance la fenêtre automatiquement sans que l'on soit obligé de cliquer sur le bouton de lecture,
* On a enlevé le bouton de validation et la présélection en rouge sur les "+", "-", "=" et fait correspondre le choix d'ajout ou de substraction de jetons aux action sur les cliques des boutons précédents si un sommet du graphe est pré-sélectionné,
* Lors du choix du template (Grid, Tore, Cycle), le refresh du bouton "OK" est maintenant fait correctement sur le preview ainsi que sur la fenêtre d'affichage du graphe,
