package sorcer.jacek.provider;

import java.io.Serializable;

/** @author Jacek Swietochowski, s5577 */

public class Number implements Serializable {
	
	protected double base,exponent,result;
	

	public Number(double base, double exponent) {
		this.base = base;
		this.exponent = exponent;
	}
	
	public Number() {
		this.base = 1;
		this.exponent = 1;	
	}
	
	public void setBaseVal(double base) {
		this.base = base;
	}
	
	public void setExponentVal(double exponent) {
		this.exponent = exponent;
	}

	public double getBaseVal() {
		return base;
	}
	
	public double getExponentVal() {
		return exponent;
	}
	
	public double getResult() {
		return result;
	}
	
	public void power(double base, double exponent) {
		result = 1;
		if (exponent == 1) result = 1;
		else {
			for (int i = 0; i < this.abs(exponent); i++) {
				result *= (exponent < 0) ? 1 / base : base;
			}
		}
	}
	
	public void sqrt(double base) {
		result = 1;
		for (int i = 0; i < base; i++) {
            result = 0.5 * (result + base / result);
		}
	}
	
	private double abs(double n) {
		return (n < 0) ? -n : n;
	}

	public String toString() {
		return "base: "+base+" ; exponent: "+exponent;
	}

}
