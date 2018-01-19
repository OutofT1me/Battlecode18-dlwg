import java.util.*;
import bc.*;

public class StructureControl {

    public static void factoryControl(Unit unit, GameController gc) {

        // Setting up location, planet, and id for ease of use
        MapLocation currentLoc = unit.location().mapLocation();
        Planet planet = currentLoc.getPlanet();
        int id = unit.id();

        if (gc.canProduceRobot(id, UnitType.Worker)) gc.produceRobot(id, UnitType.Worker);

        for (Direction direction: Direction.values()) {
            if (gc.canUnload(id, direction))
                gc.unload(id, direction);
        }
    }
}
