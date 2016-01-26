package com.jsf.demo;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("demoController")
public class DemoController {

	private DemoModel demoModel = new DemoModel();
	private List<Recommendation> recommendations = new ArrayList<Recommendation>();

	private DemoService demoService;

	public void setDemoModel(DemoModel demoModel) {
		this.demoModel = demoModel;
	}

	public DemoModel getDemoModel() {
		return demoModel;
	}

	public List<Recommendation> getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(List<Recommendation> recommendations) {
		this.recommendations = recommendations;
	}

	@Autowired
	public void setDemoService(DemoService demoService) {
		this.demoService = demoService;
	}

	public DemoService getDemoService() {
		return demoService;
	}

	public void calculateRisk() {
		recommendations = demoService.calculateRisk(demoModel);
		FacesContext currentInstance = FacesContext.getCurrentInstance();
		currentInstance.getApplication().getNavigationHandler().handleNavigation(currentInstance, null, "/personal_risk_result.xhtml");
	}

}
