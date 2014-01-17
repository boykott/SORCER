package sorcer.jacek.provider;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.logging.Logger;

import net.jini.admin.Administrable;
import net.jini.core.transaction.Transaction;
import net.jini.core.transaction.TransactionException;
import sorcer.core.SorcerConstants;
import sorcer.core.provider.Provider;
import sorcer.core.proxy.Partnership;
import sorcer.core.proxy.RemotePartner;
import sorcer.service.Context;
import sorcer.service.Exertion;
import sorcer.service.ExertionException;
import sorcer.util.Log;

public class ServiceJacekImpl implements Jacek, ServiceJacek, SorcerConstants {

	private static Logger logger = Log.getTestLog();

	//private Money balance;
	private Operations oper;

	/*public Context getBalance(Context context) throws RemoteException {
		return process(context, ServiceJacek.BALANCE);
	}

	public Context makeDeposit(Context context) throws RemoteException {
		return process(context, ServiceJacek.DEPOSIT);
	}

	public Context makeWithdrawal(Context context) throws RemoteException {
		return process(context, ServiceJacek.WITHDRAWAL);
	}
	*/
	
	public Context getResult(Context context) throws RemoteException {
		return process(context, ServiceJacek.RESULT);
	}
	
	public Context raisePower(Context context) throws RemoteException {
		return process(context, ServiceJacek.POWER);
	}
	
	private Context process(Context context, String selector) throws RemoteException {
		try {
			logger.info("input context: \n" + context);

			//Money result = null, amount = null;
			double result = 0.2, b = 0.0, exp = 0.0;
			if (selector.equals(ServiceJacek.RESULT)) {
				result = getResult();
			} 
			else if (selector.equals(ServiceJacek.POWER)) {
				//amount = (Money) context.getValue(ServiceJacek.DEPOSIT + CPS + ServiceJacek.AMOUNT); // deposit/amount
				//makeDeposit(amount);
				//result = getBalance();
				b = (Double) context.getValue(ServiceJacek.POWER + CPS + ServiceJacek.BASE); 
				exp = (Double) context.getValue(ServiceJacek.POWER + CPS + ServiceJacek.EXPONENT);
				raisePower(exp);
				result = getResult();
				
				logger.info(selector + " ; base: "+b+" ; exp: "+exp+" ; result: "+ result);
			} 

			logger.info(selector + " result: \n" + result);
			String outputMessage = "processed by " + getHostname();
			
			context.putValue(ServiceJacek.POWER + CPS + ServiceJacek.RESULT, result);
			context.putValue(ServiceJacek.COMMENT, outputMessage);

		} 
		catch (Exception ex) {
			// ContextException, UnknownHostException
			throw new RemoteException(selector + " process exception", ex);
		}
		return context;
	}

	/*private Context process(Context context, String selector)
			throws RemoteException {
		try {
			logger.info("input context: \n" + context);

			Money result = null, amount = null;
			if (selector.equals(ServiceJacek.BALANCE)) {
				result = getBalance();
			} else if (selector.equals(ServiceJacek.DEPOSIT)) {
				amount = (Money) context.getValue(ServiceJacek.DEPOSIT + CPS + ServiceJacek.AMOUNT);
				makeDeposit(amount);
				result = getBalance();
			} else if (selector.equals(ServiceJacek.WITHDRAWAL)) {
				amount = (Money) context.getValue(ServiceJacek.WITHDRAWAL
						+ CPS + ServiceJacek.AMOUNT);
				makeWithdrawal(amount);
				result = getBalance();
			}

			logger.info(selector + " result: \n" + result);
			String outputMessage = "processed by " + getHostname();
			context.putValue(
					ServiceJacek.BALANCE + CPS + ServiceJacek.AMOUNT, result);
			context.putValue(ServiceJacek.COMMENT, outputMessage);

		} catch (Exception ex) {
			// ContextException, UnknownHostException
			throw new RemoteException(selector + " process execption", ex);
		}
		return context;
	}
	*/

	public ServiceJacekImpl(Operations newOper) throws RemoteException {
		oper = newOper;
	}

	public double getResult() throws RemoteException {
		return oper.getResult();
	}

	public void raisePower(double exp) throws RemoteException {
		oper.power(exp);
		return;
	}

	
	private Provider partner;

	private Administrable admin;

	/*
	 * (non-Javadoc)
	 * 
	 * @see sorcer.core.provider.proxy.Partnership#getPartner()
	 */
	public Remote getInner() throws RemoteException {
		return (Remote) partner;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sorcer.base.Service#service(sorcer.base.Exertion)
	 */
	public Exertion service(Exertion exertion, Transaction transaction)
			throws RemoteException, ExertionException, TransactionException {
		return partner.service(exertion, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.jini.admin.Administrable#getAdmin()
	 */
	public Object getAdmin() throws RemoteException {
		return admin;
	}

	public void setInner(Object provider) {
		partner = (Provider) provider;
	}

	public void setAdmin(Object admin) {
		this.admin = (Administrable) admin;
	}

	/**
	 * Returns name of the local host.
	 * 
	 * @return local host name
	 * @throws UnknownHostException
	 */
	private String getHostname() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostName();
	}
	
}
