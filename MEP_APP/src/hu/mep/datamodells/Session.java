package hu.mep.datamodells;

import hu.mep.communication.ICommunicator;
import hu.mep.communication.RealCommunicator;

public class Session {

	private static Session instance;
	private static User actualUser;
	private static ICommunicator actualCommunicationInterface;
	
	
	private Session() {
		actualCommunicationInterface = RealCommunicator.getInstance();
	}
	
	public static Session getInstance() {
		if(instance == null) {
			instance = new Session();
		}
		return instance;
	}
	
	public static User getActualUser() {
		return actualUser;
	}
	
	public static void setActualUser(User newUser) {
		actualUser = newUser;
	}
	
	public static ICommunicator getActualCommunicationInterface() {
		if(actualCommunicationInterface == null) {
			actualCommunicationInterface = RealCommunicator.getInstance();
		}
		return actualCommunicationInterface;
	}
	
}
