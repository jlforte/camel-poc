package com.genworth.poc.camel.dsljava.router;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Consume;
import org.apache.camel.RecipientList;
import org.apache.camel.language.XPath;
import org.apache.log4j.Logger;

/**
 * @author 501296262
 * Process messages from the request message queue and route to the appropriate channel.
 * 
 * -- This is where the routing magic happens --
 */
public class CreditScoreRouterBean {
	Logger logger = Logger.getLogger(CreditScoreRouterBean.class);
	
	@Consume(uri = "activemq:creditscorereqs")
    @RecipientList
    /*
     * @param String
     * @return List<String>
     */
    public List<String> route(@XPath("/creditform/scoreType/text()") String type) {
		List<String> routes = new ArrayList<String>();
		
		if (type.equals("OMNI")) {
			routes.add("activemq:creditscorereqs.omniscore");
			logger.info("Found OMNI message on the queue, routed appropriately.");
		} else {
			routes.add("activemq:creditscorereqs.bureauscore");
			logger.info("Found credit bureau message on the queue, routed appropriately.");
		}
		return routes;
	}
}