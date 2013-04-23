package com.genworth.poc.camel.dsljava;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author 501296262
 * Main Application implementation based on JAX-RS. At the
 * very least we must define the application path for EAP6.
 */
@ApplicationPath("/genworthcredit")
public class CreditEngineApplication extends Application {
	public CreditEngineApplication() {
		super();
	}
}
