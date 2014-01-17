package sorcer.jacek.provider;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.logging.Logger;

import sorcer.core.SorcerConstants;
import sorcer.core.provider.ServiceTasker;
import sorcer.service.Context;
import sorcer.util.Log;

import com.sun.jini.start.LifeCycle;

@SuppressWarnings("rawtypes")
public class JacekProvider extends ServiceTasker implements Jacek, ServiceJacek, SorcerConstants {

	private static Logger logger = Log.getTestLog();

	//private Money balance;
	private Operations oper;
	private double base = 1.0;

	/**
	 * Constructs an instance of the SORCER jacek provider implementing
	 * SorcerJacek and Jacek. This constructor is required by Jini 2 life
	 * cycle management.
	 * 
	 * @param args
	 * @param lifeCycle
	 * @throws Exception
	 */
	public JacekProvider(String[] args, LifeCycle lifeCycle) throws Exception {
		super(args, lifeCycle);
		//String cents = getProperty("provider.balance");
		//balance = new Money(Integer.parseInt(cents));
		String thebase = getProperty("provider.base");
		//base = Double.parseDouble(thebase);
		base = 1.0;
		logger.info("[JACEK] JacekProvider started");
	}

	public Context getResult(Context context) throws RemoteException, JacekException {
		return process(context, ServiceJacek.RESULT);
		//getResult();
		//return context;
	}

	public Context raisePower(Context context) throws RemoteException, JacekException {
		return process(context, ServiceJacek.POWER);
		
		/*double exponent = (Operations) context.getValue("power/exponent");
		context.putValue("power/base", base);
		
		raisePower(exponent);
		
		context.putValue("power/result", getResult() );
		logger.info("context: "+context+" ; base: "+base+" ; exponent: "+exponent+" ; result: "+getResult());*/
	}
	
	private Context process(Context context, String selector) throws RemoteException, JacekException {
		try {
			logger.info("input context: \n" + context);

			//Money result = null, amount = null;
			double result = 0.1, exponent = 0.0;
			
			if (selector.equals(ServiceJacek.RESULT)) {
				//result = getBalance();
				result = getResult();
			} 
			else if (selector.equals(ServiceJacek.POWER)) {
				//amount = (Money) context.getValue(ServiceJacek.DEPOSIT + CPS + ServiceJacek.AMOUNT);
				exponent = (Double) context.getValue(ServiceJacek.POWER + CPS + ServiceJacek.EXPONENT);
				raisePower(exponent);
				//makeDeposit(amount);
				result = getResult();
			} 
			
			// set return value
			if (context.getReturnPath() != null) {
				context.setReturnValue(result);
			}
			
			logger.info(selector + " result: \n" + result);
			String outputMessage = "processed by " + getHostname();
			
			context.putValue(selector + CPS + ServiceJacek.POWER + CPS + ServiceJacek.RESULT, result);
			context.putValue(ServiceJacek.COMMENT, outputMessage);

		} catch (Exception ex) {
			throw new JacekException(ex);
		}
		return context;
	}

	/*private Context process(Context context, String selector) throws RemoteException, JacekException {
		try {
			logger.info("input context: \n" + context);

			//Money result = null, amount = null;
			double result,exponent;
			
			if (selector.equals(ServiceJacek.RESULT)) {
				//result = getBalance();
				result = getResult();
			} 
			else if (selector.equals(ServiceJacek.POWER)) {
				//amount = (Money) context.getValue(ServiceJacek.DEPOSIT + CPS + ServiceJacek.AMOUNT);
				exponent = context.getValue(ServiceJacek.DEPOSIT + CPS + ServiceJacek.AMOUNT);
				raisePower(exponent);
				//makeDeposit(amount);
				//result = getBalance();
			} 
			
			// set return value
			if (context.getReturnPath() != null) {
				context.setReturnValue(result);
			}
			
			logger.info(selector + " result: \n" + result);
			String outputMessage = "processed by " + getHostname();
			
			context.putValue(selector + CPS + ServiceJacek.RESULT + CPS + ServiceJacek.AMOUNT, result);
			context.putValue(ServiceJacek.COMMENT, outputMessage);

		} catch (Exception ex) {
			throw new JacekException(ex);
		}
		return context;
	}
	*/

	public double getResult() throws RemoteException {
		return oper.getResult();
	}

	public void raisePower(double b) throws RemoteException {
		oper.power(b);
		logger.info("raisePower called in JacekProvider with exponent value of "+b+" and result of "+oper.getResult());
		//return;
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
