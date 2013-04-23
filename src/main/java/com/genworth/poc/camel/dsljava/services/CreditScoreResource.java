package com.genworth.poc.camel.dsljava.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.Logger;

//import com.genworth.poc.camel.dsljava.routes.CreditScoreRouter;

@Path("/creditscore")
public class CreditScoreResource {
	Logger log = Logger.getLogger(CreditScoreResource.class);
	
	@GET()
	@Produces("text/plain")
	public String getCreditScore() {
		String creditScore = "-1";
		CamelContext context = new DefaultCamelContext();
		try {
			//context.addRoutes(new CreditScoreRouter());
			context.start();
			Thread.sleep(5000);
			creditScore = "758";
		} catch (Exception e) {
			log.error("Error processing credit score: " + e);
		} finally {
			try {
				context.stop();
			} catch (Exception ex) {ex.printStackTrace();}
		}
		
	    return "Your score: " + creditScore;
	}
}