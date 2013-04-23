package com.genworth.poc.camel.dsljava;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.camel.Consume;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.log4j.Logger;

import com.genworth.poc.camel.dsljava.model.CreditScoreResponse;

/**
 * @author 501296262
 * Use Camel to poll the 'src' directory for new files and consume the input file 
 * representing the credit score request and push it to the work queue for routing.
 * 
 * If the Genworth credit requests are already on a message queue then we don't
 * really need this.
 */
public class CreditScoreRequestToQueueBean {
	Logger logger = Logger.getLogger(CreditScoreRequestToQueueBean.class); 
    @Produce(uri = "activemq:creditscorereqs")
    ProducerTemplate creditScoreProducer;
    
    @SuppressWarnings("unchecked")
	@Consume(uri = "file:src/data?noop=true&initialDelay=100&delay=100")
    /*
     * @param String
     * Process the form data added to the message queue.
     */
    public void onFileSendToQueue(String body) {
    	logger.info("New message pushed to the queue.");
    	creditScoreProducer.sendBody(body);
    	Future futureObj = creditScoreProducer.asyncRequestBody(creditScoreProducer.getDefaultEndpoint(), body);
    	try {
    		// wait for 5 secs and fetch reply asynchronously
    		Thread.sleep(5000);
    		List<CreditScoreResponse> response = creditScoreProducer.extractFutureBody(futureObj, List.class);
    		if (response != null && response.size() > 0) {
    			Iterator<CreditScoreResponse> it = response.iterator();
    			while (it.hasNext()) {
    				CreditScoreResponse scoreResp = (CreditScoreResponse)it.next();
    				logger.info("Your borrower application form id: " + scoreResp.getBorrowerFormId());
    				logger.info("Your application credit score(s): " + scoreResp.getCreditScore());
    			}
    		} else
    			logger.info("Your application credit score(s): 0");
    	} catch (Exception e) {
    		logger.error("Error processing message response!");
    	}
    }
}