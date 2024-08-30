Design Rationale of Additional Functionality of Jurassic World Game
--
Version: 1.0

## Description
This document explains how the specified new functionality will be implemented in
the system of Jurassic World game.

This document will include the following:

- why new classes are added in the extended system
- why the roles and responsibilities of any new or significantly modified classes
are chosen.
- why these classes relate to and interact with the existing system the way they
were designed.

## Design Goals

- The design follows object-oriented principles such as Reduce dependencies (ReD),
Don’t repeat yourself (DRY), Fail Fast (FF), etc.
- The design is practical so that it can be implemented as described in the UML
design documentation.

## Design Rationale
**Ground**  
We chose to implement several classes (Dirt, Tree, Bush, Vending Machine) as 
subclasses of the Ground class. This allows us to use the inbuilt methods such 
as getExits() and getGround() in the location class to have the ground react to 
the squares around it.

**Vending Machine**  
We chose to implement the Vending Machine as a type of Ground as it will not move,
and doesn't need to be on top of a type of Ground. The class will store ArrayLists
of each type of item offered by the vending machine, so that there is a limit on
how many of each item can be purchased, introducing some scarcity. The vending machine
has the method purchaseItem() which handles exchanging EcoPoints for items. It also
has the method refill(), which refills each of the ArrayLists after a certain amount
of turns, possibly 100.

**Actors**  
The actors in our system are the player and the dinosaurs. They inherit from the 
Actor class as they move around the map and undertake actions.

**Dinosaur**
The dinosaur class has three subclasses, representing the different types of dinosaurs.
They are separate classes rather than having a type attribute as they have methods 
and actions that are unique to them.

**Portable Items**
There are many classes that inherit from the class PortableItem. This is because we 
wish to make use of the inbuilt methods in the Item class, and these items are portable.

**EcoPoints**  
We chose to implement EcoPoints as its own class, with methods to increase and decrease
the points. These methods are called by other classes' methods or Actions that result in
an increase or decrease in points.

### Interfaces
**Eggs**
There are three classes of eggs, each implements Egg Interface with hatch() Method.
StegosaurEgg and BrachiosaurEgg will also implement Food Interface. The choice was made
as Allosaurs should not eat its own eggs, otherwise Allosaurs would be extinct.

**Food**  
The food interface consists of a method that returns an integer value, the amount of 
HP a dinosaur will receive upon eating the food item. We made this an interface as it
has functionality that needs to be implemented by multiple classes, but in future food 
may not just be portable items.

**GrowFruit**  
The GrowFruit interface is implemented by the plants that grow fruit. We chose to design 
this using an interface rather than inheritance as it is more flexible, and the types of 
ground that grow fruit might not all fit under a label such as plants, or perhaps there 
might be non-fruiting plants.
 
### Actions
Actions are an interaction between an actor and something else, such as a food Item or
another actor. Therefore, we plan to have the following actions.  

**EatAction**  
 - an interaction between an actor and an item of food  
 
**BreedAction**  
- an interaction between two dinosaurs  

**PickFruitAction**  
- an interaction between the player and a tree or bush  

**FeedAction**  
- an interaction between the player and a dinosaur  

**LayEggAction**  
- an interaction between a dinosaur and an egg  
  
**Behaviours**   
The dinosaurs each have a collection of one or more behaviours, allowing them to behave
in different ways depending on different circumstances. For example, adult, well-fed
dinosaurs will look to breed with other dinosaurs of their own species.


**ChallengeModeManager**
The Challenge Mode Manager is a static class because Challenge Mode is a system-wide concept that doesn't change as the 
world runs, and doesn't need to have multiple instances. 