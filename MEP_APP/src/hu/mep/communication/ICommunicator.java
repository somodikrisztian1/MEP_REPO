package hu.mep.communication;

import hu.mep.datamodells.User;

public interface ICommunicator {
	
	
	public User authenticateUser(String username, String password);
}
