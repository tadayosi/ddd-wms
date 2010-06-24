package com.googlecode.dddwms.action;

import net.arnx.jsonic.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.dddwms.messagebean.ArrivalMessageBean;
import com.googlecode.dddwms.messagebean.ArrivalRequestMessageBean;
import com.googlecode.dddwms.messagebean.ShippingRequestMessageBean;

public class WmsAction {

	private Logger log = LoggerFactory.getLogger(WmsAction.class);

	public void arrivalRequest(String jsonMessage) {
		log.info("arrivalRequest:{}", jsonMessage);

		ArrivalRequestMessageBean arrivalRequestBean = JSON.decode(jsonMessage,
				ArrivalRequestMessageBean.class);
		
		// TODO サービスを呼び出し

	}

	public void arrival(String jsonMessage) {
		log.info("arrival:{}", jsonMessage);

		ArrivalMessageBean arrivalBean = JSON.decode(jsonMessage,
				ArrivalMessageBean.class);
		
		// TODO サービスを呼び出し
	}

	public void shippingRequest(String jsonMessage) {
		log.info("shippingRequest:{}", jsonMessage);

		ShippingRequestMessageBean shippingBean = JSON.decode(jsonMessage,
				ShippingRequestMessageBean.class);
		
		// TODO サービスを呼び出し
	}

}
