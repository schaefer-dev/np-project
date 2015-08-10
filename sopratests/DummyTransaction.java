package de.unisaarland.cs.st.pirates.group5.tests;

import de.unisaarland.cs.st.pirates.logger.LogWriter.Entity;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Key;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Transaction;

public class DummyTransaction implements Transaction{
	Entity entity;
	int ID;
	DummyLogWriter dummy;
	
	public DummyTransaction(Entity ent, int id, DummyLogWriter d){
		entity = ent;
		ID = id;
		dummy = d;
	}

	@Override
	public Transaction set(Key arg0, int arg1) throws NullPointerException,
			IllegalArgumentException {
		dummy.notify(entity, ID, arg0, arg1);
		return this;
	}

}
