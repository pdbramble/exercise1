package com.paul.ex1;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTranslatorTests {

	private static final String MAX_SIZE_VALUE = "1999999999.99";

	private static final String TOO_BIG_VALUE = "199999999999999999999999999999.99";
	
	private CurrencyTranslator currencyTranslator;
	
	@Before
	public void setup() {
		currencyTranslator = new CurrencyTranslator();
	}
	
	@Test
	public void testUnits() {
		InputToken units = InputToken.getInputToken("81230.25");
		
		assertEquals("81230", units.getDollars());
		assertEquals("25", units.getCents());
		assertEquals("dollars", units.getCurrencyDisplayName());
	}

	@Test
	public void testEmptyValueParse() {
		
		try {
			InputToken.getInputToken("");
			fail("Accepted dollar value of 81e30 as valid");
		} catch (IllegalArgumentException e){
			//expected case
		}
	}


	@Test
	public void testMissingDecimalInInput() {
		InputToken units = InputToken.getInputToken("81230");
		
		assertEquals("81230", units.getDollars());
		assertEquals("00", units.getCents());
		assertEquals("dollars", units.getCurrencyDisplayName());
	}
	
	@Test
	public void testMissingCentsInInput() {
		InputToken units = InputToken.getInputToken("81230.");
		
		assertEquals("81230", units.getDollars());
		assertEquals("00", units.getCents());
		assertEquals("dollars", units.getCurrencyDisplayName());
	}
	

	@Test
	public void testBadDollarValue() {
		try {
			InputToken.getInputToken("81e30.25");
			fail("Accepted dollar value of 81e30 as valid");
		} catch (IllegalArgumentException e){
			//expected case
		}
	}

	@Test
	public void testBadCentsValue() {
		try {
			InputToken.getInputToken("25.2e");
			fail("Accepted cents value of 2e as valid");
		} catch (IllegalArgumentException e){
			//expected case
		}
	}

	@Test
	public void testMissingDollarsInInput() {
		InputToken units = InputToken.getInputToken(".25");
		
		assertEquals("0", units.getDollars());
		assertEquals("25", units.getCents());
		assertEquals("dollars", units.getCurrencyDisplayName());
	}
	
	
	@Test
	public void testOnlyDecimalInInput() {
		try {
			InputToken.getInputToken(".");
			fail("Accepted cents value of 2e as valid");
		} catch (IllegalArgumentException e){
			//expected case
		}
	}
	
	
	@Test
	public void test000() {
		TripleDigitToken cell = new TripleDigitToken("000", Scale.ONES);
		
		assertEquals("", cell.renderToken());
		
	}	
	
	@Test
	public void test7() {
		TripleDigitToken cell = new TripleDigitToken("7", Scale.ONES);
		
		assertEquals("seven", cell.renderToken());
	}
	 
	@Test
	public void test07() {
		TripleDigitToken cell = new TripleDigitToken("07", Scale.ONES);
		
		assertEquals("seven", cell.renderToken());
	}
	
	
	@Test
	public void test10() {
		TripleDigitToken cell = new TripleDigitToken("10", Scale.ONES);
		
		assertEquals("ten", cell.renderToken());
	}
	
	
	@Test
	public void test011() {
		TripleDigitToken cell = new TripleDigitToken("011", Scale.ONES);
		
		assertEquals("eleven", cell.renderToken());
	}
	
	
	@Test
	public void test100() {
		TripleDigitToken cell = new TripleDigitToken("100", Scale.ONES);
		
		assertEquals( "one hundred", cell.renderToken());
	}
	
	
	@Test
	public void test101() {
		TripleDigitToken cell = new TripleDigitToken("101", Scale.ONES);
		
		assertEquals( "one hundred one", cell.renderToken());
	}
	
	@Test
	public void test110() {
		TripleDigitToken cell = new TripleDigitToken("110", Scale.ONES);
		assertEquals("one hundred ten", cell.renderToken());
	}
	
	
	@Test
	public void test111() {
		TripleDigitToken cell = new TripleDigitToken("111", Scale.ONES);
		
		assertEquals("one hundred eleven", cell.renderToken());
	}
	
	
	@Test
	public void test999() {
		TripleDigitToken cell = new TripleDigitToken("999", Scale.ONES);
		
		assertEquals("nine hundred ninty-nine", cell.renderToken());
	}
	
	@Test
	public void test714() {
		TripleDigitToken cell = new TripleDigitToken("714", Scale.ONES);
		
		assertEquals("seven hundred fourteen", cell.renderToken());
	}
	
	@Test
	public void translateTwoTwentyTwo() {
		String result = currencyTranslator.translateAmountToString("222");
		
		assertEquals("Two hundred twenty-two and 00/100 dollars", result);
	}
	
	
	@Test
	public void testHundredThousands() {
		String result = currencyTranslator.translateAmountToString("222333");
		
		assertEquals("Two hundred twenty-two thousand three hundred thirty-three and 00/100 dollars", result);
	}
	
	
	@Test
	public void translatePerSpec() {
		String result = currencyTranslator.translateAmountToString("2523.04");
		
		assertEquals("Two thousand five hundred twenty-three and 04/100 dollars", result);
	}
	
	
	@Test
	public void translateZero() {
		String result = currencyTranslator.translateAmountToString("0");
		
		assertEquals("Zero and 00/100 dollars", result);
	}
	
	
	@Test
	public void translatePointO555() {
		String result = currencyTranslator.translateAmountToString(".055");
		
		assertEquals("Zero and 06/100 dollars", result);
	}
	
	
	@Test
	public void translateIntegerSpecValue() {
		String result = currencyTranslator.translateAmountToString(new BigDecimal("2523.04"));
		
		assertEquals("Two thousand five hundred twenty-three and 04/100 dollars", result);
	}
	
	
	@Test
	public void translateFunkyValue() {
		String result = currencyTranslator.translateAmountToString(new BigDecimal("02050203.04"));
		assertEquals("Two million fifty thousand two hundred three and 04/100 dollars", result);
	}
	

	@Test
	public void testToBigAValue() {
		try {
			InputToken.getInputToken(TOO_BIG_VALUE);
			fail("Accepted input exceeding max size/value");
		} catch (IllegalArgumentException e){
			//expected case
		}
	}
	

	@Test
	public void testMaxSizeValue() {
		String result = currencyTranslator.translateAmountToString(MAX_SIZE_VALUE);
		assertEquals("One billion nine hundred ninty-nine million nine hundred ninty-nine thousand nine hundred ninty-nine and 99/100 dollars", result);
	}
}
