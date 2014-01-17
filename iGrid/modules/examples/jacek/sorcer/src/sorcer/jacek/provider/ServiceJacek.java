package sorcer.jacek.provider;

import java.rmi.Remote;
import java.rmi.RemoteException;

import sorcer.service.Context;

@SuppressWarnings("rawtypes")
public interface ServiceJacek extends Remote {

	public Context getResult(Context jacek) throws RemoteException,JacekException;

	public Context raisePower(Context jacek) throws RemoteException,JacekException;

	public final static String POWER = "power";

	public final static String BASE = "base";
	
	public final static String EXPONENT = "exponent";

	public final static String RESULT = "result";

	public final static String COMMENT = "comment";
}
