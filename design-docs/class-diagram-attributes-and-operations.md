**Class Diagram Attributes and Operations**
--

This file contains attributes and operations of the new and changed classes in the Jurassic World game.

**Dirt**
- attributes
- methods
  - tick():void // called once per turn, manages
  - turnIntoBush():void //1% chance, 10% if next to 2 bush, 0% if next to tree each turn
  - hasAdjacentBushes():bool // returns true if square has 2 bush adjacent
  - hasAdjacentTree():bool // returns true if square has tree adjacent

**GrowFruitInterface**
- Attributes
  - ArrayList<Fruit> fruit
- Methods
  - growFruit 
  
**Tree** // implements GrowFruit
- Attributes
  - int age
  - ArrayList<Fruit> fruit
- Methods
  - tick(): void
  - dropFruit(): void //iterates through fruit (ArrayList), 5% chance of remove ONTREE capability, move to location ArrayList
  - growFruit(): void // 50% chance of fruit each turn
  - increaseAge(): void // increase age by 1 each turn

**Bush** // implements GrowFruit
- Attributes
  - ArrayList<Fruit> fruit
- Methods
  - tick(): void
  - dropFruit(): void //10% chance of fruit each turn
  - turnIntoDirt(): void // If Brachiosaur is on the square, 50% chance to turn into dirt

**Dinosaur**
- Attributes
  - int Age
  - int unconsciousTurnsLimit // amount of turns dino can be unconscious before it dies
  - int gestationTurns // amount of turns before egg will be laid
  - ArrayList<Behaviour> behaviours // list of behaviours that will determine movement
  - rot_time // amount of time a corpse of the dinosaur will stay in the game
- Methods
  - tick(): void
  - increaseAge(): void // age += 1 each turn
  - layEgg(): void
  - isWellFed() : boolean
  - messageHunger():void // prints “[dino] at location () is hungry!”

**Stegosaur**
- Attributes
- Methods

**Brachiosaur**
- Attributes
- Methods

**Allosaur**
- Attributes
- Methods

**EcoPoint**
- Attributes
  - int currentEcoPoint
- Methods
  - increaseEcoPoint(): void // called when action produces points
  - decreaseEcoPoint(): void // called when points used to purchase from Vending Machine

**VendingMachine**
- Attributes
  - ArrayList<Fruit> fruits
  - ArrayList<VegMealKit> vegMealKits
  - ArrayList<CarnMealKit> carnMealKits
  - ArrayList<StegosaurEgg> stegoEggs
  - ArrayList<BrachiosaurEgg> brachioEggs
  - ArrayList<AllosaurEgg> alloEggs
  - ArrayList<LaserGun> laserGuns
  - int age
- Methods
  - tick(): void // increases age variable
  - purchaseItem(): void // removes item from vending machine and adds it to player inventory, decreases ecopoints
  - refill(): void //every 100 turns(?may be adjusted) //replaces all ArrayLists with full ones and reset age

**PortableItem**
- Attributes
    - boolean isHolding
    - String verb

**EdibleInterface**
- Methods
   - calculatePoints(actor): int    // calculates how much HP a dino will heal
   
**Fruit** //
- Attributes
  - int ROT_COUNTER
- Methods
  - tick(): void // check if fruit is on ground, count down ROT_COUNTER, remove fruit when ROT_COUNTER is 0(after 15 turns).
  - isOnTree: bool // check if fruit has ONTREE capability
  - isEdible(actor): boolean 
  - calculateHP(actor): void
  
**VegMealKit** // implements Edible
- Attributes
- Methods
  - isEdible(actor): boolean 
  - calculatePoints(actor): void  
  
**CarnMealKit** // implements Edible
- Attributes
- Methods
  - calculatePoints(actor): void  
  
**Egg** // implements 
- Attributes
    - int hatchTime
    - int age
- Methods
    - tick(): void //increases age, triggers hatch
    - hatch(): void //deletes egg, creates new dino in the location (removeItem(egg))
    - calculatePoints(actor): void   
   
**Corpse** //implements Edible
- Attributes
    - int rot_counter // starts at the rot_time of the dinosaur
- Methods
    - tick(): void //decreases rot_counter, removes the item from the 
    location if rot_counter == 0.
   
**LaserGun**
- Attributes
    - final int damage = 50
- String verb = fires

**Player**
- Attributes
    - ArrayList<Item> Inventory
- Methods

// Actions: an interaction between an actor and another class (Edible Item, other actor etc.)

**AttackAction** // existing class, actor attacks another actor 
- Attributes
- Methods

**EatAction** // actor eats an Edible item
- Attributes
- Methods
   - execute(): String // overwrite

**BreedAction** // male + female dino → makes female dino pregnant (Capability PREGNANT)
- Attributes
- Methods
   - execute(): String // overwrite
   
**PickFruitAction** // Player + Plant → remove fruit from ground/tree/bush, add to player inventory
// Different cases for different ground (chance of failure %)
// Ground
// Tree
// Bush
- Attributes
- Methods
   - execute(): String // overwrite

**FeedAction** // Dino action, when player is near → remove the item from inventory, increase HP
- Attributes
- Methods
   - execute(): String

**LayEggAction** // Female dino action, after certain number of turns, femaleDino remove the egg
                 // from its inventory and put the egg on ground.
- Attributes
- Methods
   - execute(): String // overwrite

// Behaviours

**FollowBehaviour**
- Attributes
- Methods

**WanderBehaviour**
- Attributes
- Methods

**HungerBehaviour**
- Attributes
- Methods

**Enum Status**
- BABY,      // default is adult
- MALE,      // default is female
- PREGNANT, // default is not pregnant
- ONTREE,    // default is not on tree
- CARNIVORE, // default is herbivore, applied to actors and food items
