package game;

import edu.monash.fit2099.engine.*;
import game.dinosaurs.Brachiosaur;
import game.dinosaurs.Stegosaur;
import game.grounds.*;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Class representing the Jurassic game world, including the locations of all Actors, the
 * player, and the playing grid.
 * <p>
 * It allows for map extensions by indicating the orientation.
 */
public class JurassicWorld extends World {

    /**
     * Constructor for JurassicWorld.
     *
     * @param display the Display that will display this World.
     */
    public JurassicWorld(Display display) {
        super(display);
    }

    /**
     * Adds a new map to the system.
     * Attaches the new map to the old map.
     *
     * @param orientation The orientation of the map, determines which orientation the new map will be added to the
     *                    old map.
     *                    E.G. if orientation is NORTH, then the new map will be added to the NORTH of the old map.
     * @param newGameMap  The new game map.
     * @param oldGameMap  The old game map.
     */
    private void attachMap(Orientation orientation, MyGameMap newGameMap, MyGameMap oldGameMap) {
        this.addGameMap(newGameMap);
        if (gameMaps.contains(newGameMap) && gameMaps.contains(oldGameMap)) {
            newGameMap.addMapEdges(orientation, oldGameMap);
        }
    }

    public void runGame() {

        // Gets player name
        System.out.println("Welcome to Jurassic World game\n------------------------------\nPlease enter your name");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        // Keeps the game running until player ends it
        while (true) {
            EcoPoint.setEcoPoint(0);

            FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Tree(), new Bush(), new Lake());
            List<String> map = Arrays.asList(
                    "..........................+++..................................~~~~~............",
                    "........................+++..................................~~..~~..~....+++...",
                    ".....#######............++++.......................................~.......++...",
                    ".....#_____#............++.....................~~~~~~..........~~~~.............",
                    ".....#_____#....................................................................",
                    ".....###.###.............~~~~~~~~................+++............................",
                    ".........++.......................................++............................",
                    "......................................+++...............~~......................",
                    ".......................................++++...........~~~~~~....................",
                    ".....~~~~~~~~......................+++++........................................",
                    ".+++.................................++++++.....................................",
                    "..++..................................+++.......................................",
                    ".++++...............++...............+++..................~~...~................",
                    "............................................................~~~~~~~~............",
                    "............+++...........................++.....................~~.............",
                    ".............+++++.........~~.............++....................................",
                    "...............++..........~~~~~~........................+++++..................",
                    ".............+++........~~.....~~~~~................++++++++....................",
                    "............+++.......................................+++.......................",
                    ".........................................~~~..~~................................",
                    "...........................++................~~~~........................++.....",
                    ".......................................~~~~~~....~~.....................++.++...",
                    "........~~~~~~~~.......................~~~~...~....................++....++++...",
                    ".................+++......................................................++....",
                    "................................................................................");
            MyGameMap gameMap = new MyGameMap(groundFactory, map);

            List<String> secondMap = Arrays.asList(
                    "................................................................................",
                    "................................................................................",
                    "................................................................................",
                    "................................................................................",
                    "................................................................................",
                    "...............+++++++..........................................................",
                    "................................................................................",
                    "......................................+++.......................................",
                    "................................................................................",
                    "...................................+++..........................................",
                    ".............................~~~~~~.............................................",
                    "......................................+++.......................................",
                    ".....................................+++........................................",
                    "................................................................................",
                    "................................................................................",
                    ".............+++++..............................................................",
                    "...............++...............................................................",
                    ".............+++................................................................",
                    "................................................................................",
                    "................................................................................",
                    "................................................................................",
                    ".........................................................+++....................",
                    "................................................................................",
                    "................................................................................",
                    "................................................................................");
            MyGameMap secondGameMap = new MyGameMap(groundFactory, secondMap);

            List<String> thirdMap = Arrays.asList(
                    "................................................................................",
                    "................................................................................",
                    "................................................................................",
                    "................................................................................",
                    "................................................................................",
                    "...............+++++++..........................................................",
                    "................................................................................",
                    "......................................+++.......................................",
                    "................................................................................",
                    "...................................+++..........................................",
                    "................................................................................",
                    "......................................+++.......................................",
                    ".....................................+++........................................",
                    "................................................................................",
                    "................................................................................",
                    ".............+++++..............................................................",
                    "...............++...............................................................",
                    ".............+++...............................~~~~.............................",
                    "................................................................................",
                    "................................................................................",
                    "................................................................................",
                    ".........................................................+++....................",
                    "................................................................................",
                    "................................................................................",
                    "................................................................................");
            MyGameMap thirdGameMap = new MyGameMap(groundFactory, thirdMap);
            this.addGameMap(gameMap);
            this.attachMap(Orientation.NORTH, secondGameMap, gameMap);
            this.attachMap(Orientation.WEST, thirdGameMap, gameMap);

            // FIRST MAP
            // Start of the game, player in first map
            Actor player = new Player(name, '@', 100);
            this.addPlayer(player, gameMap.at(9, 4));
            // Place a vending machine
            Ground vending = new VendingMachine();
            gameMap.at(10, 4).setGround(vending);

            // Place a pair of Stegosaurs in the middle of the map
            gameMap.at(30, 12).addActor(new Stegosaur(true, false));
            gameMap.at(32, 12).addActor(new Stegosaur(false, false));

            // Place two pairs of Brachiosaurs in the middle of the map
            gameMap.at(18, 15).addActor(new Brachiosaur(true, false));
            gameMap.at(20, 15).addActor(new Brachiosaur(true, false));
            gameMap.at(22, 15).addActor(new Brachiosaur(false, false));
            gameMap.at(24, 15).addActor(new Brachiosaur(false, false));

            // Testing testing
            //gameMap.at(6, 4).addActor(new Allosaur());
            //gameMap.at(7, 4).addActor(new Stegosaur(true, false));
            //gameMap.at(9, 6).addActor(new Pterodactyl(true, false));
            //gameMap.at(10, 6).addActor(new Pterodactyl(false, false));

            // SECOND MAP
            // Place a vending machine
            Ground newVending = new VendingMachine();
            secondGameMap.at(20, 19).setGround(newVending);

            // Choose game mode
            System.out.println("Please select mode\n1: Sandbox Mode");
            System.out.println("2: Challenge Mode");

            int selection = scanner.nextInt();

            while (selection != 1 && selection != 2) {
                System.out.println("Please choose the mode of play");
                selection = scanner.nextInt();
            }

            if (selection == 1) {
                ChallengeModeManager.setActive(false);
            } else {
                ChallengeModeManager.setActive(true);
                System.out.println("Enter the number of moves");
                int numMoves = scanner.nextInt();
                ChallengeModeManager.setMoves(numMoves);
                System.out.println("Enter the target number of EcoPoints");
                int numPoints = scanner.nextInt();
                ChallengeModeManager.setEcoPoints(numPoints);
            }
            this.run();

            System.out.println("Play again?");
            System.out.println("1) Play again");
            System.out.println("2) End");
            selection = scanner.nextInt();
            if (selection == 2) {
                break;
            } else if (selection != 1) {
                System.out.println("Play again?");
                System.out.println("1) Play again");
                System.out.println("2) End");
            }
        }
    }
}
