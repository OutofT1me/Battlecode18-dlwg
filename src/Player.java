package src;

// import the API.
// See https://s3.amazonaws.com/battlecode-2018/api/java/bc/package-summary.html for the javadocs.
import java.util.*;
import bc.*;

public class Player {

    public static void main(String[] args) {

        // Starts game and initializes processes
        GameController gc = new GameController();
        queueUpgrades(gc);

        while (true) {
            System.out.println("Current round: "+gc.round());

            VecUnit units = gc.myUnits();
            for (int i = 0; i < units.size(); i++) {
                Unit unit = units.get(i);

                // Most methods on gc take unit IDs, instead of the unit objects themselves.
                if (gc.isMoveReady(unit.id()) && gc.canMove(unit.id(), Direction.Southeast)) {
                    gc.moveRobot(unit.id(), Direction.Southeast);
                }
            }

            // Submit the actions we've done, and wait for our next turn.
            gc.nextTurn();
        }
    }

    // Queues upgrades at the start of the game
    public static void queueUpgrades(GameController gc) {

        // For most important units
        for (int lvl = 1; lvl <= 3; lvl++) {
            gc.queueResearch(UnitType.Worker);
            gc.queueResearch(UnitType.Ranger);
            gc.queueResearch(UnitType.Rocket);
        }
        gc.queueResearch(UnitType.Worker); // Extra Worker lvl4 upgrade

        // For less important units
        for (int lvl = 1; lvl <= 3; lvl++) {
            gc.queueResearch(UnitType.Knight);
            gc.queueResearch(UnitType.Mage);
            gc.queueResearch(UnitType.Healer);
        }
        gc.queueResearch(UnitType.Mage); // Extra Mage lvl4 upgrade

    }
    
    // Generates a basic path for a robot
    // Gets blocked by obstacles, so don't use in its current state
    public LinkedList basicPath(Unit robot, MapLocation Destination, GameController gc) {
        LinkedList<MapLocation> path = new LinkedList();
        MapLocation currentLoc = robot.location().mapLocation();
        path.add(currentLoc);
        int pathLength = 0;
        while (!currentLoc.equals(Destination)) {
            if (gc.isOccupiable(currentLoc.add(currentLoc.directionTo(Destination))) > 0){
                currentLoc = currentLoc.add(currentLoc.directionTo(Destination));
                path.add(currentLoc);
            }
            else return null;
            pathLength++;
        }

        return path;
    }
}