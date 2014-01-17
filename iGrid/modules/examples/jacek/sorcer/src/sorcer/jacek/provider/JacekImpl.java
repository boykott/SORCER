package sorcer.jacek.provider;

import java.rmi.RemoteException;

public class JacekImpl implements Jacek {
	
	//private Money balance;
	private Operations oper;

	public JacekImpl(Operations newOper) throws RemoteException {
		//balance = startingBalance;
		oper = newOper;
	}

	public double getResult() throws RemoteException {
		//return balance;
		return oper.getResult();
	}

	public void raisePower(double b) throws RemoteException {
		//balance.add(amount);
		oper.power(b);
		return;
	}

}
