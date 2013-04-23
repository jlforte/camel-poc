package com.genworth.poc.camel.dsljava.routes;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.camel.CamelContext;
import org.apache.camel.Consume;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.Logger;

import com.genworth.poc.camel.dsljava.model.CreditForm;
import com.genworth.poc.camel.dsljava.model.CreditScoreResponse;

public class BureauCreditScoreBean {
	Logger logger = Logger.getLogger(BureauCreditScoreBean.class);

	@Consume(uri = "activemq:creditscorereqs.bureauscore")
	//@Produce(uri = "http://www.google.com?proxyHost=proxy-http.genworth.net&proxyPort=80")
	//ProducerTemplate wsProducer;
	
	/*
	 * @param String
	 * @return List
	 * This is where all the processing logic resides. Messages in this queue should be directed
	 * towards the both credit bureau services: Equifax and TransUnion.
	 * 
	 * Return the resulting borrower score from each.
	 */
	public List<CreditScoreResponse> processMessage(String body) {
		String equiScore = "700";
		String tuScore = "719";	
		List<CreditScoreResponse> scores = new ArrayList<CreditScoreResponse>();
		
		try {
			JAXBContext jaxb = JAXBContext.newInstance(CreditForm.class);
			Unmarshaller unmarshaller = jaxb.createUnmarshaller();
			StringReader reader = new StringReader(body);
			CreditForm omniForm = (CreditForm) unmarshaller.unmarshal(reader);
			
			logger.info("Message popped off queue with id. Application ID: " + omniForm.getId());
			logger.info(body);
			
			// ** invoke web services here **
			logger.info("Invoking Web Service at: http://www.google.com/search?proxyHost=proxy-http.genworth.net&proxyPort=80");
			CamelContext camelContext = new DefaultCamelContext();
	        ProducerTemplate template = camelContext.createProducerTemplate();
			Exchange exchange = template.send("http://www.google.com/search?proxyHost=proxy-http.genworth.net&proxyPort=80", new Processor() {
	            public void process(Exchange exchange) throws Exception {
	                exchange.getIn().setHeader(Exchange.HTTP_QUERY, "hl=en&q=activemq");
	            }
	        });

	        Message out = exchange.getOut();
	        logger.info("WS status header: " + out.getHeader(Exchange.HTTP_RESPONSE_CODE));
			
			CreditScoreResponse equiScoreResp = new CreditScoreResponse(omniForm.getId());
			equiScoreResp.setCreditScore(equiScore);
			CreditScoreResponse tuScoreResp = new CreditScoreResponse(omniForm.getId());
			tuScoreResp.setCreditScore(tuScore);
			scores.add(equiScoreResp);
			scores.add(tuScoreResp);
		} catch (JAXBException ex) {
			logger.error("Error unmarshalling XML.");
			logger.error(ex);
		}
		return scores;
	}
}