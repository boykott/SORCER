package sorcer.jacek.provider;

import java.io.Serializable;

/** @author Jacek Swietochowski, s5577 */

public class Operations implements Serializable {
	
	protected double base,result;
	

	public Operations(double base) {
		this.base = base;
		this.result = 1;
	}
	
	
	public void setBaseVal(double base) {
		this.base = base;
	}
	
	public double getBaseVal() {
		return base;
	}
	
	public double getResult() {
		return result;
	}
	
	public void power(double exponent) {
		if (exponent == 1) result = 1;
		else {
			for (int i = 0; i < this.abs(exponent); i++) {
				result *= (exponent < 0) ? 1 / base : base;
			}
		}
	}
	
	public void sqrt() {
		for (int i = 0; i < base; i++) {
            result = 0.5 * (result + base / result);
		}
	}
	
	private double abs(double n) {
		return (n < 0) ? -n : n;
	}

	public String toString() {
		return "base value: "+base+" ";
	}

}
