package com.genworth.poc.camel.dsljava.services;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.camel.CamelContext;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.Logger;

import com.genworth.poc.camel.dsljava.model.CreditScoreResponse;

@Path("/creditscore")
/**
 * @author 501296262
 * Expose a credit score service that requests the credit score based on the borrower
 * application sent as part of the request. For the poc, the borrower data has been
 * hard-coded.
 * 
 * Pushes an application request on to the queue and waits asynchronously for a response.
 * 
 * -- This is the main endpoint for the poc --
 */
public class CreditScoreResource {
	Logger logger = Logger.getLogger(CreditScoreResource.class);
	@Produce(uri = "activemq:creditscorereqs")
    ProducerTemplate creditScoreProducer;
	
	@GET()
	@Produces("text/plain")
	@SuppressWarnings("unchecked")
	public String getCreditScore() {
		String creditScore = "-1";
		CamelContext context = new DefaultCamelContext();
		String body = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><creditform id=\"1\"><firstName>David</firstName><lastName>Suzuki</lastName><city>Toronto</city><scoreType>OMNI</scoreType></creditform>";
		
		try {
			context.start();
			Thread.sleep(5000);
			creditScoreProducer = context.createProducerTemplate();
			creditScoreProducer.setDefaultEndpointUri("activemq:creditscorereqs");
			Future futureObj = creditScoreProducer.asyncRequestBody(creditScoreProducer.getDefaultEndpoint(), body);
			List<CreditScoreResponse> response = creditScoreProducer.extractFutureBody(futureObj, List.class);
    		if (response != null && response.size() > 0) {
    			Iterator<CreditScoreResponse> it = response.iterator();
    			while (it.hasNext()) {
    				CreditScoreResponse scoreResp = (CreditScoreResponse)it.next();
    				logger.info("Your borrower application form id: " + scoreResp.getBorrowerFormId());
    				logger.info("Your application credit score(s): " + scoreResp.getCreditScore());
    				creditScore = scoreResp.getCreditScore();
    			}
    		} else
    			logger.info("Your application credit score(s): 0");
			
		} catch (Exception e) {
			logger.error("Error processing credit score: " + e);
			e.printStackTrace();
		} finally {
			try {
			} catch (Exception ex) {ex.printStackTrace();}
		}
		
	    return "Your score: " + creditScore;
	}
}