import java.util.ArrayList;
import java.util.*;
import java.awt.*;
import java.util.Random;
import java.util.LinkedList;
import java.util.TreeSet;

public class SmartFire extends Fire {
    PacMan pacman;
    public SmartFire(int tileX, int tileY, Color color, PacMan p) {
	super(tileX, tileY, color);
	pacman = p;
    }
    
    @Override
    public void direction(Maze m) {
	HuntPacman h = new HuntPacman();
	Node n = search(new MyQueue(), h, tileX, tileY, m);
	velocityX = n.velX;
	velocityY = n.velY;
    }

    public Node search(MyQueue pile, HuntPacman p, int tileX, int tileY, Maze m) {
        TreeSet<State> considered = new TreeSet<State>();
	State initialState = p.getStartState(tileX, tileY);
	Node initialNode = new Node(null, initialState, tileX, tileY);
	if (p.isGoal(initialState, pacman)){
	    return initialNode;
	}
		pile.push(initialNode);
		considered.add(initialState);
		while (! pile.isEmpty()){
		    Node popped = pile.pop();
		    ArrayList<directionState> succs = popped.s.getSuccessors(m);
		    for (directionState s : succs){
			if (p.isGoal(s.s, pacman)){
			    return new Node(popped, s.s, s.velX, s.velY);
			}
			else{
			    if (! considered.contains(s.s)){
				pile.push(new Node(popped, s.s, s.velX, s.velY));
				considered.add(s.s);
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
	ArrayList<directionState> succs = new ArrayList<directionState>();
	if (fireY < 14) {
	    if (m.maze[fireY + 1][fireX] == 0) {
		succs.add(new directionState(new State(fireX, fireY + 1), 0, 100));
	    }
	}
	if (fireY > 0) {
	    if (m.maze[fireY - 1][fireX] == 0) {
		succs.add(new directionState(new State(fireX, fireY - 1), 0, -100));
	    }
	}
	if (fireX < 25) {
	    if (m.maze[fireY][fireX + 1] == 0) {
		succs.add(new directionState(new State(fireX + 1, fireY), 100, 0));
	    }
	}
	if (fireX > 0) {
	    if (m.maze[fireY][fireX - 1] == 0) {
		succs.add(new directionState(new State(fireX - 1, fireY), -100, 0));
	    }
	}
	return succs;
    }
    public int compareTo (State other) {
	int n = this.fireY + (this.fireX * 15);
	int oN = other.fireY + (other.fireX * 15);
	
	if (n < oN) {
	    return -1;
	}
	else if (n == oN) {
	    return 0;
	}
	else {
	    return 1;
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

