package com.paul.ex1;

import java.math.BigDecimal;
import java.util.Stack;

/**
 * Parses String or BigDecimal input and renders it into its 
 * appropriate string representation.
 * 
 * Example:
 *	Will convert an input value of 2523.04 to its string equivalent
 *  of "Two thousand five hundred twenty-three and 04/100 dollars" 
 * 
 * Assumptions:
 * 
 * Limiting scope to dollars and cents.  
 * 
 * Limited scope by ignoring punctuation in the input other than a 
 * decimal point, and by not adding localization.
 *   
 * @author Paul B
 * 
 *
 */

enum Scale { ONES(""), 
			 THOUSANDS ("thousand"), 
		 	 MILLIONS ("million"), 
		 	 BILLIONS ("billion"),
		 	 TRILLIONS("trillion"),
		 	 QUADRILLIONS ("quadrillion"),
		 	 QUINTILLIONS("trillion"),
		 	 SEXTILLIONS ("sextillion"),
		 	 SEPTILLIONS("septillion");

	private final String scale;

	Scale (String scale) {
    	this.scale = scale;
    }
    
    String getScale() { return scale; }
    
    static Scale getScaleByOrdinal(int ord) {
    	for (Scale scale : Scale.values()) {
    		if (scale.ordinal() == ord) {
    			return scale;
    		}
    	}
    	return null;
    }
}

public class CurrencyTranslator {
	
	private static final String CENTS_UNITS = "/100";
	private static final String ZERO_DOLLARS = "Zero ";
	private static final String GROUP_SEPARATOR = " ";

	public String translateAmountToString(BigDecimal value) {
		
		if (value == null) {
			throw new IllegalArgumentException("missing value");
		}
		
		return translateAmountToString(value == null ? "" : value.toString());
	}
	
	public String translateAmountToString(String value) {
		Stack <TripleDigitToken> stack = new Stack <TripleDigitToken> ();
		
		StringBuffer sb = new StringBuffer();
		
		// Tokenizes the input into groups of up to 3 digits and
		// saves in reverse order.
		InputToken units = InputToken.getInputToken(value);
		
		for (int i = units.getDollars().length() -1, k = 0; i >= 0; i -= 3, k++ ) {
			int end = i + 1;
			int start = i - 2;
			if (i < 2) {
				end = i + 1;
				start = 0;
			}
			
			//Only keep unit groups of non-zero values.
			String unitValue = units.getDollars().substring(start, end);
			if (!unitValue.isEmpty() && !"0".equals(unitValue)) {
				stack.push (new TripleDigitToken(unitValue,
												 Scale.getScaleByOrdinal(k)));
			}
		}
		
		if (stack.isEmpty()) {
			sb.append(ZERO_DOLLARS);
		} else {
			while (!stack.empty()) {
				sb.append(stack.pop().renderToken() + GROUP_SEPARATOR);
			}
		}

		//handle cents
		sb.append("and " + units.getCents() + CENTS_UNITS);
		
		//Convert first letter of result to upper case.
		String result = sb.toString().substring(0,1).toUpperCase() +
						sb.toString().substring(1) +  GROUP_SEPARATOR + 
						units.getCurrencyDisplayName();
		
		return result;
	}
}