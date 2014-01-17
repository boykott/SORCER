package sorcer.jacek.provider;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Jacek extends Remote {

	public double getResult() throws RemoteException;

	public void raisePower(double b) throws RemoteException;

}
