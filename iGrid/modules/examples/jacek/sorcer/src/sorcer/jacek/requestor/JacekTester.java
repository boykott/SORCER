package sorcer.jacek.requestor;

import java.rmi.RMISecurityManager;
import java.util.logging.Logger;

import sorcer.jacek.provider.Operations;
import sorcer.jacek.provider.ServiceJacek;
import sorcer.core.context.ServiceContext;
import sorcer.core.exertion.NetJob;
import sorcer.core.exertion.NetTask;
import sorcer.core.signature.NetSignature;
import sorcer.service.Context;
import sorcer.service.Job;
import sorcer.util.Log;
import sorcer.util.Sorcer;

@SuppressWarnings("rawtypes")
public class JacekTester {

	private static Logger logger = Log.getTestLog();

	String CPS = "/";
	
	public static void main(String[] args) throws Exception {
		System.setSecurityManager(new RMISecurityManager());
		Job result = new JacekTester().test();
		logger.info("job context: \n" + result.getJobContext());
	}

	private Job test() throws Exception {
		Job result = (Job)getJob().exert();
		return result;
	}

	private Job getJob() throws Exception {
		//NetTask task1 = getDepositTask();
		//NetTask task2 = getWithdrawalTask();
		NetTask task1 = getPowerTask();
		NetJob job = new NetJob("jacek");
		job.addExertion(task1);
		//job.addExertion(task2);
		return job;
	}

	/*private NetTask getDepositTask() throws Exception {
		ServiceContext context = new ServiceContext(ServiceJacek.ACCOUNT);
		context.putValue(ServiceJacek.DEPOSIT + CPS + ServiceJacek.AMOUNT,
				new Money(10000)); // $100.00
		context.putValue(ServiceJacek.BALANCE + CPS + ServiceJacek.AMOUNT,
				Context.none);
		NetSignature signature = new NetSignature("makeDeposit",
				ServiceJacek.class, Sorcer.getActualName("Jacek1"));
		NetTask task = new NetTask("jacek-deposit", signature);
		task.setContext(context);
		return task;
	}
	*/

	private NetTask getPowerTask() throws Exception {
		ServiceContext context = new ServiceContext(ServiceJacek.POWER);
		context.putValue("base", 5.0); 
		context.putValue("exponent", 5.0);
		NetSignature signature = new NetSignature("raisePower", ServiceJacek.class, Sorcer.getActualName("Jacek1"));
		NetTask task = new NetTask("jacek-raisepower", signature);
		task.setContext(context);
		return task;
	}
}
