// import the API.
// See xxx for the javadocs.
import java.util.LinkedList;
import java.util.List;

import bc.*;

public class Player {
	

    public static void main(String[] args) {
    	
        // MapLocation is a data structure you'll use a lot.
        MapLocation loc = new MapLocation(Planet.Earth, 10, 20);
        System.out.println("loc: "+loc+", one step to the Northwest: "+loc.add(Direction.Northwest));
        System.out.println("loc x: "+loc.getX());

        // One slightly weird thing: some methods are currently static methods on a static class called bc.
        // This will eventually be fixed :/
        System.out.println("Opposite of " + Direction.North + ": " + bc.bcDirectionOpposite(Direction.North));

        // Connect to the manager, starting the game
       GameController gc = new GameController();
       
     
        while (true) {
            System.out.println("Current round: "+gc.round());
            // VecUnit is a class that you can think of as similar to ArrayList<Unit>, but immutable.
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
    Direction[] directions = {Direction.North, Direction.Northeast, Direction.East, Direction.Southeast, Direction.South, Direction.Southwest, Direction.West, Direction.Northwest};
    public Direction RotateLeft(Direction initial){
   		if (initial == Direction.North) return Direction.Northwest;
   		for (int i=1; i<=8;i++){
   			if (directions[i] == initial){
   				return directions[i-=1];
   			}
   		} return Direction.North;
   	}
   	public Direction RotateRight(Direction initial){
   		if (initial == Direction.Northwest) return Direction.North;
   		for (int i=0; i<8;i++){
   			if (directions[i] == initial){
   				return directions[i++];
   			}
   		} return Direction.North;
   	}
       public void goThere(Unit Robot, MapLocation Destination, GameController gc){
    	   while (gc.isMoveReady(Robot.id())){
    		   if (gc.canMove(Robot.id(), Robot.location().mapLocation().directionTo(Destination))){
    			   gc.moveRobot(Robot.id(), Robot.location().mapLocation().directionTo(Destination));
    		   }
    	   }
       }
       

       /*
       public LinkedList flutterPath (Unit Robot, MapLocation Destination, int maxlength, GameController gc){
   		list<MapLocation> path = new LinkedList();
   		MapLocation temp = Robot.mapLocation();
   		path.add(temp);
   		int n = 0;
   		while (temp.equals(Destination) == false& n <= maxlength){
   			if (gc.isOccupiable(temp.add(temp.directionTo(Destination)))==true){
   				temp = temp.add(temp.directionTo(Destination));
   				path.add(temp);
   			} else if (gc.isOccupiable(temp.add(RotateRight(temp.directionTo(Destination)))==true)){
   				temp = temp.add(RotateRight(temp.directionTo(Destination)));
   				path.add(temp);
   			} else if (gc.isOccupiable(temp.add(RotateLeft(temp.directionTo(Destination)))==true)){
   				temp = temp.add(RotateLeft(temp.directionTo(Destination)));
   				path.add(temp);
   			} else if (gc.isOccupiable(temp.add(RotateRight(RotateRight(temp.directionTo(Destination))))==true)){
   				temp = temp.add(RotateRight(RotateRight(temp.directionTo(Destination))));
   				path.add(temp);
   			} else if (gc.isOccupiable(temp.add(RotateLeft(RotateLeft(temp.directionTo(Destination))))==true)){
   				temp = temp.add(RotateLeft(RotateLeft(temp.directionTo(Destination))));
   				path.add(temp);
   			} else if (gc.isOccupiable(temp.add(RotateRight(RotateRight(temp.directionTo(Destination))))==true)){
   				temp = temp.add(RotateRight(RotateRight(temp.directionTo(Destination))));
   				path.add(temp);
   			}else if (gc.isOccupiable(temp.add(RotateRight(RotateRight(RotateRight(temp.directionTo(Destination)))))==true)){
   				temp = temp.add(RotateRight(RotateRight(RotateRight(temp.directionTo(Destination)))));
   				path.add(temp);
   			}else if (gc.isOccupiable(temp.add(RotateLeft(RotateLeft(RotateLeft(temp.directionTo(Destination)))))==true)){
   				temp = temp.add(RotateRight(RotateRight(RotateR(temp.directionTo(Destination)))));
   				path.add(temp);
   			} else return null;
   			n++;
   			}  if (n==maxlength) return null;
   			return path;
       } */
}