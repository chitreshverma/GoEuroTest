package com.chitresh.GoEuroTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

	ByteArrayOutputStream out = new ByteArrayOutputStream();
	PrintStream outPrintStream = new PrintStream(out);

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void test1App() {
		System.setOut(outPrintStream);

		App.main(new String[] { "Lucknow" });

		assertEquals(
				"File path: C:\\Documents and Settings\\HomePC\\workspaceLuna\\GoEuroTest\\cityName.csv",
				out.toString());
	}

	/**
	 * Rigourous Test :-)
	 */
	public void test2App() {

		System.setOut(outPrintStream);

		App.main(new String[] {});

		assertEquals("missing city name argument", out.toString());
	}

	/**
	 * Rigourous Test :-)
	 */
	public void test3App() {

		System.setOut(outPrintStream);

		try {
			App.main(new String[] { "" });
		} catch (RuntimeException ex) {
			assertTrue(true);
		}
	}

}
