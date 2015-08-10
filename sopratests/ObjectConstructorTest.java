package de.unisaarland.cs.st.pirates.group5.tests;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import de.unisaarland.cs.st.pirates.group5.commands.Drop;
import de.unisaarland.cs.st.pirates.group5.commands.Flipzero;
import de.unisaarland.cs.st.pirates.group5.commands.Goto;
import de.unisaarland.cs.st.pirates.group5.commands.If;
import de.unisaarland.cs.st.pirates.group5.commands.IfAll;
import de.unisaarland.cs.st.pirates.group5.commands.Move;
import de.unisaarland.cs.st.pirates.group5.commands.Pickup;
import de.unisaarland.cs.st.pirates.group5.commands.Sense;
import de.unisaarland.cs.st.pirates.group5.commands.Turn;
import de.unisaarland.cs.st.pirates.group5.controller.BoolComparison;
import de.unisaarland.cs.st.pirates.group5.controller.CellTypeComparison;
import de.unisaarland.cs.st.pirates.group5.controller.Command;
import de.unisaarland.cs.st.pirates.group5.controller.Comparison;
import de.unisaarland.cs.st.pirates.group5.controller.IntComparison;
import de.unisaarland.cs.st.pirates.group5.controller.Operator;
import de.unisaarland.cs.st.pirates.group5.model.CellType;
import de.unisaarland.cs.st.pirates.group5.model.Register;
 

public class ObjectConstructorTest {
	
	private List<String> tacticStringList;
	private List<Command> tacticCommandList;
	private Random randomGen;
	private final static Operator LESS = Operator.Less;
	private final static Operator GREATER = Operator.Greater;
	private final static Operator EQUAL = Operator.Equal;
	private final static Operator UNEQUAL = Operator.Unequal;
	
	
	public ObjectConstructorTest() {
		
		this.tacticCommandList = new ArrayList<Command>();
		this.tacticStringList = new ArrayList<String>();
		
		
		this.generateTacticStringList();
		this.generateTacticList();
	}
	
	private void generateTacticStringList(){
		/* creating single TacticLines in tacticStringList for later usage */
		tacticStringList.add("sense 0\n");
		tacticCommandList.add(new Sense(0));
		
		tacticStringList.add("ifall sense_treasure ship_load<4 else 4\n");
		List<Comparison> comps = new LinkedList<Comparison>();
		Comparison comparison = new BoolComparison(Register.sense_treasure, false);
		comps.add(comparison);
		comparison = new IntComparison(LESS, Register.ship_load, 4);
		comps.add(comparison);
		tacticCommandList.add(new IfAll(comps, 4));
		
		tacticStringList.add("pickup 0 else 6\n");
		tacticCommandList.add(new Pickup(0, 6));
		
		tacticStringList.add("goto 14\n");
		tacticCommandList.add(new Goto(14));
		
		tacticStringList.add("if ship_load>3 else 6\n");
		comparison = new IntComparison(GREATER, Register.ship_load, 3);
		tacticCommandList.add(new If(comparison, 6));
		
		tacticStringList.add("goto 14\n");
		tacticCommandList.add(new Goto(14));
		
		tacticStringList.add("move else 8\n");
		tacticCommandList.add(new Move(8));
		
		tacticStringList.add("goto 0\n");
		tacticCommandList.add(new Goto(0));
		
		tacticStringList.add("flipzero 3 else 11\n");
		tacticCommandList.add(new Flipzero(3, 11));
		
		tacticStringList.add("turn left\n");
		tacticCommandList.add(new Turn(true));
		
		tacticStringList.add("goto 0\n");
		tacticCommandList.add(new Goto(0));
		
		tacticStringList.add("flipzero 2 else 13\n");
		tacticCommandList.add(new Flipzero(2, 13));
		
		tacticStringList.add("turn right\n");
		tacticCommandList.add(new Turn(false));
		
		tacticStringList.add("goto 0\n");
		tacticCommandList.add(new Goto(0));
		
		tacticStringList.add("sense 0\n");
		tacticCommandList.add(new Sense(0));
		
		tacticStringList.add("if sense_celltype==home else 19\n");
		comparison = new CellTypeComparison(EQUAL, Register.sense_celltype, CellType.Home);
		tacticCommandList.add(new If(comparison, 19));
		
		tacticStringList.add("move else 21\n");
		tacticCommandList.add(new Move(21));
		
		tacticStringList.add("drop\n");
		tacticCommandList.add(new Drop());
		
		tacticStringList.add("goto 0\n");
		tacticCommandList.add(new Goto(0));
		
		tacticStringList.add("move else 21\n");
		tacticCommandList.add(new Move(21));
		
		tacticStringList.add("goto 14\n");
		tacticCommandList.add(new Goto(14));
		
		tacticStringList.add("flipzero 3 else 24\n");
		tacticCommandList.add(new Flipzero(3, 24));
		
		tacticStringList.add("turn left\n");
		tacticCommandList.add(new Turn(true));
		
		tacticStringList.add("goto 14\n");
		tacticCommandList.add(new Goto(14));
		
		tacticStringList.add("flipzero 2 else 26\n");
		tacticCommandList.add(new Flipzero(2, 26));
		
		tacticStringList.add("turn right\n");
		tacticCommandList.add(new Turn(false));
		
		tacticStringList.add("goto 14");
		tacticCommandList.add(new Goto(14));
	}
	
	private void generateTacticList(){
		/* generates Tactic for every single Line in tacticStringList and a Tactic which contains all the tacticLines together in a big file*/
		String bigTactic = "";
		for (int i=0; i<tacticStringList.size(); i++){		//connects commandLines into big TacticString
			bigTactic += tacticStringList.get(i);
		}
		tacticStringList.add(bigTactic);		
	}
	public List<Command> getCommandList()
	{
		return tacticCommandList;
	}
	
	public List<String> getStringList()
	{
		return tacticStringList;
	}
}
