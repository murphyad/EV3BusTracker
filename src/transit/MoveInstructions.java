package transit;

//This class handles storage and execution of movement commands.

import java.util.LinkedList;

import lejos.utility.Delay;

import java.util.Iterator;

public class MoveInstructions {
	LinkedList<Instruction> instructions = new LinkedList<>();
	Iterator<Instruction> iterator;
	
	//This class holds the information for each instruction
	private class Instruction{
		double angle;
		double distance;
		double timeToStopAfter;
		Instruction(double angle, double distance, double timeToStopAfter){
			this.angle = angle;
			this.distance = distance;
			this.timeToStopAfter = timeToStopAfter;
		}
	}
	
	//This adds a new instruction to the list
	public void addInstruction(double angle, double distance, double timeToStopAfter) {
		instructions.add(new Instruction(angle, distance, timeToStopAfter));
		iterator = instructions.iterator();
	}
	
	//This method performs all remaining Instructions in the list.
	public double perform() {
		double time = 0;
		while(iterator.hasNext()) {
			Instruction next = iterator.next();
			time += EV3.turn(next.angle);
			time += EV3.forward(next.distance);
			Delay.msDelay((long)next.timeToStopAfter);
			time += next.timeToStopAfter;
		}
		return time;
	}
	//This method performs the next n Instructions from the list.
	public double perform(int numberToPerform) {
		double time = 0;
		for(int i = 0; i < numberToPerform; i++) {
			Instruction next = iterator.next();
			time += EV3.turn(next.angle);
			time += EV3.forward(next.distance);
			Delay.msDelay((long)next.timeToStopAfter);
			time += next.timeToStopAfter;
		}
		return time;
	}
	
	public boolean hasNext() {
		return iterator.hasNext();
	}
}
