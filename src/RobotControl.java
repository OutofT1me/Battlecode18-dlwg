package src;

import java.util.*;
import bc.*;

public class RobotControl {

    public static void workerControl(Unit unit, GameController gc) {

        // Setting up the unit's location, planet, and id for ease of use
        MapLocation currentLoc = unit.location().mapLocation();
        Planet planet = currentLoc.getPlanet();
        int id = unit.id();
        VecUnit nearby = gc.senseNearbyUnitsByTeam(currentLoc, 2, gc.team());

        //Tries to harvest or blueprint in all directions
        for(Direction direction: Direction.values()) {
            if (gc.canHarvest(id, direction))
                gc.harvest(id, direction);
            else if (gc.canBlueprint(id, UnitType.Factory, direction)
                    && gc.isOccupiable(currentLoc.addMultiple(direction, 2)) > 0) {

                // Only 9 factories at once
                // TODO: Record destroyed units
                if (gc.getTeamArray(planet).get(0) <= 9) {
                    gc.blueprint(id, UnitType.Factory, direction);
                    //gc.writeTeamArray(0, gc.getTeamArray(planet).get(0) + 1);
                }
                // Only 2 rockets at once
                /*else if (gc.getTeamArray(planet).get(5) <= 2) {
                    gc.blueprint(id, UnitType.Rocket, direction);
                    gc.writeTeamArray();
                }*/
            }
        }

        //Tries to repair or build in all directions
        for (int i = 0; i < nearby.size(); i++) {
            if (gc.canRepair(id, nearby.get(i).id()))
                gc.repair(id, nearby.get(i).id());
            else if (gc.canBuild(id, nearby.get(i).id()))
                gc.build(id, nearby.get(i).id());
        }

        Direction direction = randDir();
        if (gc.canMove(id, direction)) gc.moveRobot(id, direction);
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

    // Chooses a random direction for a robot to move
    // Only use for testing
    public static Direction randDir() {
        return Direction.values()[(int)(Math.random()*8 +1)];
    }
}
