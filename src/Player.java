package src;

// import the API.
// See https://s3.amazonaws.com/battlecode-2018/api/java/bc/package-summary.html for the javadocs.
import java.util.*;
import bc.*;

public class Player {

    public static void main(String[] args) {

        GameController gc = new GameController();
        initialize(gc);

        while (true) {
            System.out.println("Current round: "+gc.round());

            // Runs proper control methods for each unit on the board
            VecUnit units = gc.myUnits();
            for (int i = 0; i < units.size(); i++) {
                Unit unit = units.get(i);
                if (unit.unitType().equals(UnitType.Worker)) RobotControl.workerControl(unit, gc);
                else if (unit.unitType().equals(UnitType.Factory)) StructureControl.factoryControl(unit, gc);
            }

            gc.nextTurn();
        }
    }

    // Makes initial preparations for game
    public static void initialize(GameController gc) {

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

        // Registers initial number of workers
        gc.writeTeamArray(6, (int)gc.myUnits().size());
    }
}