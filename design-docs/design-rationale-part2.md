Design Rationale of Additional Functionality of Jurassic World Game Assignment 3
--
Version: 1.0

## Description
This document explains where refactoring was made to the specified functionalities in
the system of Jurassic World game compared to assignment 2, and how new functionalities 
are implemented the way they are.

## Design Goals
The design follows object-oriented principles.

## Design Rationale
**EcoPoint**  
EcoPoint were refactored to a static class so that dependencies are reduced. EcoPoint needs to be called in several 
classes. It can now be called without passing an instance of itself.

**HungerBehaviour**  
Instead of defining specific cases in HungerBehaviour, it was much more maintainable to use abstractions by passing in 
ArrayLists of preys, food source and edible locations. Now it follows open/close principles.

**MateBehaviour**  
Similar to HungerBehaviour, MateBehaviour now uses abstractions. It uses the actor who calls the behaviour to check if 
the potential mating partner is of the same actor but opposite sex, and passes a list of locations without specifying 
different cases, which follows object-oriented principles.

**ChallengeModeManager**  
The Challenge Mode Manager is a static class because Challenge Mode is a system-wide concept that doesn't change as the 
world runs, and doesn't need to have multiple instances. 