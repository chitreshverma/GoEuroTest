package com.chitresh.GoEuroTest;

import com.chitresh.GoEuroTest.webservice.CallWebService;

/**
 * Hello world!
 *
 */
public class App {

	private CallWebService callWebService;

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.print("missing city name argument");
		} else {
			App app = new App();
			app.initialize();
			app.callService(args[0]);
		}
	}

	private void initialize() {

		callWebService = new CallWebService();

	}

	private void callService(String cityName) {

		callWebService.callService(cityName);

	}

}
