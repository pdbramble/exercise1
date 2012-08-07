package com.paul.ex1;


/**
 * 1) Instantiates a token for "three-digit" portions of the "dollars"
 *    part of the input, with  scale (hundreds, thousands, millions, etc.).  
 * 
 * 2) Renders the token into the equivalent string value for the group.
 *    For example, it would render a "thousands" token containing the digits 
 *    7, 7, 7 as "seven hundred seventy-seven thousand" .
 * 
 * @author Paul B
 *
 */
public class TripleDigitToken {
	private static final String EMPTY_SEPARATOR = "";
	private String hundredsValue;
	private String tensValue;
	private String onesValue;
	private Scale scale;
	
	private static final String [] tens = new String [100];
	
	//Set up "map" for translating digits to text.
	static {
		tens[0] = EMPTY_SEPARATOR;
		tens[1] =  "one";
		tens[2] =  "two";
		tens[3] =  "three";
		tens[4] =  "four";
		tens[5] =  "five";
		tens[6] =  "six";
		tens[7] =  "seven";
		tens[8] =  "eight";
		tens[9] =  "nine";
		tens[10] = "ten";
		tens[11] = "eleven";
		tens[12] = "tweleve";
		tens[13] = "thirteen";
		tens[14] = "fourteen";
		tens[15] = "fifteen";
		tens[16] = "sixteen";
		tens[17] = "seventeen";
		tens[18] =  "eighteen";
		tens[19] = "nineteen";

		tens[20] = "twenty";
		tens[30] = "thirty";
		tens[40] = "forty";
		tens[50] = "fifty";
		tens[60] = "sixty";
		tens[70] = "seventy";
		tens[80] = "eighty";
		tens[90] = "ninty";
		
		//Define 21 through 99 (except exact multiples of ten);
		for (int k = 20; k < 100; k += 10) {
			for (int i = 1; i < 10; i++) {
				tens[k + i] = tens[k] + "-" + tens[i];
			}
		}
	}

	public TripleDigitToken(String group, Scale scale) {
		onesValue = group.substring(group.length() - 1, group.length());
		tensValue = group.length() > 1 ?
					group.substring(group.length()-2, group.length() - 1):
					EMPTY_SEPARATOR;
					
		hundredsValue = group.length() > 2 ? group.substring(0, 1):EMPTY_SEPARATOR;
		
		this.scale = scale;
	}
	
	/** 
	 * Converts a numeric value into it's string equivalent.  Only shows
	 * the non-zero values (e.g. 007 would be seven, 000 would be empty.)
	 * 
	 * @return
	 */
	
	public String renderToken() {
		StringBuffer sb = new StringBuffer();
		
		String sep = EMPTY_SEPARATOR;
		
		//Process Hundreds.  Show nothing if zero or blank
		if (isTranslatable(hundredsValue)) {
			sb.append(tens[Integer.parseInt(hundredsValue)] + " hundred");
			sep = " ";
		}
		
		//Process tens and one together.  Show nothing if 0 or blank).
		if (isTranslatable(tensValue + onesValue)) {
			sb.append(sep + tens[Integer.parseInt(tensValue + onesValue)]);
		}
		
		//add scale.
		if (sb.length() > 0 && !scale.getScale().isEmpty()) {
			sb.append(" " + scale.getScale());
		}
		
		return sb.toString();
	}
	
	private boolean isTranslatable(String key) {
		return (key !=null && 
			!key.isEmpty() && 
			!tens[Integer.parseInt(key)].isEmpty());
	}
}