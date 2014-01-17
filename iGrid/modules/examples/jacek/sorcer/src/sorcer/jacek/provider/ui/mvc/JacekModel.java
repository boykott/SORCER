package sorcer.jacek.provider.ui.mvc;

import java.util.Observable;

import sorcer.jacek.provider.Operations;
import java.util.logging.Logger;
import sorcer.util.Log;

public class JacekModel extends Observable {

	private final static Logger logger = Log.getTestLog();
	
	//private Money balance, withdrawalAmount, depositAmount;
	private Operations oper;
	private double result, b, exp;

	final static String RESULT = "result";

	final static String BASE = "base";

	final static String EXPONENT = "exponent";
	
	final static String POWER = "power";

	public JacekModel() {
		//this.balance = new Money(0);
		this.result = oper.getResult();
		logger.info("[JACEK] JacekModel started");
	}

	public JacekModel(Operations op) {
		this.oper = op;
	}
	
	public double getResult() {
		return result;
	}
	
	public double getBaseValue() {
		return b;
	}
	
	public double getExponentValue() {
		return exp;
	}
	
	public void setResult(double res) {
		this.result = res;
		setChanged();
		notifyObservers(RESULT);
	}
	
	public void setBaseValue(double base) {
		this.b = base;
		setChanged();
		notifyObservers(BASE);
	}
	
	public void setExponentValue(double exponent) {
		this.exp = exponent;
		setChanged();
		notifyObservers(EXPONENT);
	}

	/*public void setBalance(Money balance) {
		//this.balance = balance;
		setChanged();
		notifyObservers(BALANCE);
	}

	public Money getBalance() {
		return balance;
	}

	public Money getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Money depositAmount) {
		this.depositAmount = depositAmount;
		setChanged();
		notifyObservers(DEPOSIT);
	}

	public Money getWithdrawalAmount() {
		return withdrawalAmount;
	}

	public void setWithdrawalAmount(Money withdrawalAmount) {
		this.withdrawalAmount = withdrawalAmount;
		setChanged();
		notifyObservers(WITHDRAW);
	}
	*/
	
}