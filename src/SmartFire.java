import java.util.ArrayList;
import java.util.*;
import java.awt.*;
import java.util.Random;
import java.util.TreeSet;
import java.util.LinkedList;

public class SmartFire extends Fire {

    public SmartFire(int tileX, int tileY, Color color) {
	super(tileX, tileY, color);
    }
    
    @Override
    
    public void direction(Maze m) {
	HuntPacman h = new HuntPacman();
	search(new MyQueue(), h);
    }

    public static Node search(MyQueue pile, HuntPacman p) {
        TreeSet<State> considered = new TreeSet<State>();
	State initialState = p.getStartState();
	Node initialNode = new Node(null, initialState);
	if (p.isGoal(initialState)){return initialNode;}
	pile.push(initialNode);
	considered.add(initialState);
	while (! pile.isEmpty()){
	    Node popped = pile.pop();
	    System.out.println(popped.s);
	    ArrayList<directionState> succs = popped.s.getSuccessors();
	    for (State s : succs){
		if (p.isGoal(s)){return new Node(popped, s);}
		else{
		    if (! considered.contains(s)){
			    pile.push(new Node(popped, s));
			    considered.add(s);
		    }
		}
	    }
	}
	return null;//no solution
    }
}

class HuntPacman {
    
    public State getStartState(int tileX, int tileY) {
	return new State(tileX, tileY);
    }
    public boolean isGoal(State s, PacMan p) {
	if (s.fireX == p.tileX && s.fireY == p.tileY) {
	    return true;
	}
	else return false;
    }
}

    
class State {
    int fireX;
    int fireY;
    //have action to get here in node (sequence of actions)
    public State (int fireX, int fireY) {
	this.fireX = fireX;
	this.fireY = fireY;
    }
    
    public ArrayList<directionState> getSuccessors(Maze m) {
	ArrayList<State> succs = new ArrayList<State>();
	if (fireY < 14) {
	    if (m[fireY + 1][fireX] == 0) {
		succs.add(new directionState(new State(fireX, fireY + 1), 0, 100));
	    }
	}
	if (fireY > 0) {
	    if (m[fireY - 1][fireX] == 0) {
		succs.add(new directionState(new State(fireX, fireY - 1), 0, -100));
	    }
	}
	if (fireX < 25) {
	    if (m[fireY][fireX + 1] == 0) {
		succs.add(new directionState(new State(fireX + 1, fireY), 100, 0));
	    }
	}
	if (fireX > 0) {
	    if (m[fireY][fireX - 1] == 0) {
		succs.add(new directionState(new State(fireX - 1, fireY), -100, 0));
	    }
	}
    }
}

    
class Node{
    Node parent;
    State s;
    int velX;
    int velY;
    
    public Node(Node parent, State s, int velX, int velY){
	this.parent = parent;
	this.s = s;
	this.velX = velX;
	this.velY = velY;
    }
    
    public String toString(){
	if (this.parent == null){
	    return this.s.toString();
	}
	else{
	    return this.parent.toString() + " -> " + this.s.toString();
	}	
    }
}

interface PileLike{
    public boolean isEmpty();
    public Node pop();
    public void push(Node s);
}



class MyQueue extends LinkedList<Node> implements PileLike{
    public Node pop(){
	return this.removeLast();
    }
}


class directionState{
    int velX;
    int velY;
    State s;
    
    public directionState(State s, int velX, int velY) {
	this.velX = velX;
	this.velY = velY;
	this.s = s;
    }
}

