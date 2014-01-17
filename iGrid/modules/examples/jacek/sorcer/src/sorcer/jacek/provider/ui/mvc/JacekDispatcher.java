package sorcer.jacek.provider.ui.mvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.logging.Logger;
import sorcer.util.Log;

import sorcer.jacek.provider.Jacek;

public class JacekDispatcher implements ActionListener {

	private final static Logger logger = Log.getTestLog();

	private JacekModel model;

	private JacekView view;

	private Jacek jacek;

	public JacekDispatcher(JacekModel model, JacekView view, Jacek jacek) {
		this.model = model;
		this.view = view;
		this.jacek = jacek;
		logger.info("[JACEK] JacekDispatcher started");
	}

	
	public void getResult() {
		try {
			model.setResult(jacek.getResult());
		} 
		catch (RemoteException e) {
			logger.info("Error occurred while getting jacek result");
			logger.throwing(getClass().getName(), "getResult", e);
		}
	}
	
	private void raisePower() {
		try {
			//model.setWithdrawalAmount(view.getWithdrawalAmount());
			jacek.raisePower(model.getExponentValue() );
			getResult();
			logger.info("raisePower called in JacekDispatcher");
		} 
		catch (Exception exception) {
			System.out.println("Couldn't talk to jacek. Error was \n " + exception);
			exception.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		logger.info("actionPerformed>>action: " + action);
		if (action == JacekModel.POWER) 
			raisePower();
	}
	
	/*public void getBalance() {
		try {
			model.setBalance(jacek.getBalance());
		} 
		catch (RemoteException e) {
			logger.info("Error occurred while getting jacek balance");
			logger.throwing(getClass().getName(), "getBalance", e);
		}
	}

	private void makeWithdrawl() {
		try {
			model.setWithdrawalAmount(view.getWithdrawalAmount());
			jacek.makeWithdrawal(model.getWithdrawalAmount());
			getBalance();
		} 
		catch (Exception exception) {
			System.out.println("Couldn't talk to jacek. Error was \n " + exception);
			exception.printStackTrace();
		}
	}

	private void makeDeposit() {
		try {
			model.setDepositAmount(view.getDepositAmount());
			jacek.makeDeposit(model.getDepositAmount());
			getBalance();
		} 
		catch (Exception exception) {
			System.out.println("Couldn't talk to jacek. Error was \n " + exception);
			exception.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		logger.info("actionPerformed>>action: " + action);
		if (action == JacekModel.DEPOSIT)
			makeDeposit();
		else if (action == JacekModel.WITHDRAW)
			makeWithdrawl();
	}
	*/
	
}
