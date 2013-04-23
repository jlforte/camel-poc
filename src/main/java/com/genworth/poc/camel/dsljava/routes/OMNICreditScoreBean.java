package com.genworth.poc.camel.dsljava.routes;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.camel.Consume;
import org.apache.log4j.Logger;

import com.genworth.poc.camel.dsljava.model.CreditForm;
import com.genworth.poc.camel.dsljava.model.CreditScoreResponse;

public class OMNICreditScoreBean {
	Logger logger = Logger.getLogger(OMNICreditScoreBean.class);

	@Consume(uri = "activemq:creditscorereqs.omniscore")
	/*
	 * @param String
	 * @return List
	 * This is where the processing happens. The messages in this queue are
	 * meant for OMNI. Send request to OMNI and return the borrower score.
	 */
	public List<CreditScoreResponse> processMessage(String body) {
		String score = "758";
		List<CreditScoreResponse> scores = new ArrayList<CreditScoreResponse>();
		
		try {
			JAXBContext jaxb = JAXBContext.newInstance(CreditForm.class);
			Unmarshaller unmarshaller = jaxb.createUnmarshaller();
			StringReader reader = new StringReader(body);
			CreditForm omniForm = (CreditForm) unmarshaller.unmarshal(reader);
			
			logger.info("Message popped off queue with id: " + omniForm.getId());
			logger.info(body);
			CreditScoreResponse omniScoreResp = new CreditScoreResponse(omniForm.getId());
			omniScoreResp.setCreditScore(score);
			scores.add(omniScoreResp);
		} catch (JAXBException ex) {
			logger.error("Error unmarshalling XML.");
			logger.error(ex);
		}
		return scores;
	}
}