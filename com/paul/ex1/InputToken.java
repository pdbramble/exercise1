package com.paul.ex1;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

/**
 * Validates input data and converts the input value to an object,
 * separating the value into the more significant part (i.e. dollars)
 * and the fractional portion (cents).
 * 
 * @author Paul B
 *
 */
public class InputToken {
	
	private static final String DEFAULT_CENTS = "00";

	private static final String DEFAULT_DOLLARS = "0";

	private static final int INPUT_SIZE_LIMIT = 27;

	private final String dollars;
	
	private final String cents;

	private static final String currencyDisplayName = "dollars"; 
	
	private static final int fractionalDigitLength;
	
	static {
		Currency myCurrency = Currency.getInstance(Locale.getDefault());
		fractionalDigitLength = myCurrency.getDefaultFractionDigits();
	}

	InputToken (String dollars, String cents) {
		this.dollars = dollars;
		this.cents = cents;
	}
	
	/**
	 * Factory method for converting input value to a usable format.
	 * @param value
	 * @return
	 */
	public static InputToken getInputToken(String value) {
		
		String dollars = DEFAULT_DOLLARS;
		String cents = DEFAULT_CENTS;
		
		validateInput(value);


		if (!value.equals(".")) {
			String [] values = value.split("[.]");
			dollars = (values[0].isEmpty()) ? DEFAULT_DOLLARS: values[0];
			
			if (dollars.length() > INPUT_SIZE_LIMIT) {
				throw new IllegalArgumentException("input exceeds size limit of "
					+ INPUT_SIZE_LIMIT + " characters");
			}
			
			String  myCents = (values.length > 1) ? values[1] : DEFAULT_DOLLARS;
			BigDecimal fraction = new BigDecimal("." + myCents).
				setScale(fractionalDigitLength, BigDecimal.ROUND_UP);
			cents = fraction.toString().substring(2);
		}

		return new InputToken(dollars, cents);
	}
	
	public static void validateInput(String value) {
		
		if (value == null || value.isEmpty()) {
			throw new IllegalArgumentException("missing value");
		}

		
		if (".".equals(value)) {
			throw new IllegalArgumentException("invalid value ");
		}

		
		try {
			new BigDecimal(value);
		} catch (Exception e){
			throw new IllegalArgumentException(
				"input value exceeds size limit of " + 
				INPUT_SIZE_LIMIT + " characters");
		}
	}
	
	public String getDollars() { return dollars;}
	
	public String getCents() { return cents; }
	
	public String getCurrencyDisplayName() { return currencyDisplayName; }	
}