package com.jsf.demo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DemoService {

	public List<Recommendation> calculateRisk(DemoModel demoModel) {
		return createDummyRecommendations();
	}

	private List<Recommendation> createDummyRecommendations() {
		List<Recommendation> recommendations = new ArrayList<Recommendation>();
		recommendations.add(createDummyRecommendation("Carrier1", "Product1", BigDecimal.TEN));
		recommendations.add(createDummyRecommendation("Carrier2", "Product2", BigDecimal.TEN));
		return recommendations;
	}

	private Recommendation createDummyRecommendation(String carrierName, String productName, BigDecimal premium) {
		Recommendation recommendation = new Recommendation();
		recommendation.setCarrierName(carrierName);
		recommendation.setProductName(productName);
		recommendation.setPremium(premium);
		return recommendation;
	}
}
