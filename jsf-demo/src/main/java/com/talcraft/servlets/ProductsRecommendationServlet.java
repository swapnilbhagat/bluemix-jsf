package com.talcraft.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talcraft.tradeoff.domain.Column;
import com.talcraft.tradeoff.domain.Option;
import com.talcraft.tradeoff.domain.Options;
import com.talcraft.tradeoff.domain.Problem;
import com.talcraft.tradeoff.service.TradeoffAnalyticsService;
import com.talcraft.tradeoff.service.TradeoffAnalyticsServiceImpl;
import com.talcraft.util.JsonHelper;

@WebServlet("/problem/*")
@MultipartConfig
public class ProductsRecommendationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static TradeoffAnalyticsService tradeoffAnalyticsService = new TradeoffAnalyticsServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletOutputStream servletOutputStream = resp.getOutputStream();
		StringBuilder requestBuilder = new StringBuilder();
		String problemString = JsonHelper.jsonToString(getHealthInsuranceProblem());
		requestBuilder.append(problemString.substring(0, problemString.lastIndexOf("}")));
		String optionsString = JsonHelper.jsonToString(getOptions());
		requestBuilder.append(",");
		requestBuilder.append(optionsString.substring(optionsString.indexOf("{") + 1));
		servletOutputStream.write(requestBuilder.toString().getBytes());
		servletOutputStream.flush();
		servletOutputStream.close();
	}

	private Options getOptions() {
		Options options = new Options();
		Option[] option = new Option[3];

		Option licJeevanArogya = new Option();
		licJeevanArogya.setDescriptionHtml(
				"<img style='max-height: 100px; max-width: 100px;'  src='https://upload.wikimedia.org/wikipedia/en/a/aa/LIC_Logo.svg'/>");
		licJeevanArogya.setKey("lic_jeevan_arogya");
		licJeevanArogya.setName("LIC Jeevan Arogya");
		Map<String, Object> licHealthValues = new HashMap<>();
		licHealthValues.put("premium", 1000);
		licHealthValues.put("coverage", 100000);
		licJeevanArogya.setValues(licHealthValues);
		option[0] = licJeevanArogya;

		Option maxlifeInsurance = new Option();
		maxlifeInsurance.setDescriptionHtml(
				"<img style='max-height: 100px; max-width: 100px;'  src='http://www.maxlifeinsurance.com/images/mli-logo.gif'/>");
		maxlifeInsurance.setKey("max_life_health_insurance");
		maxlifeInsurance.setName("Max Health Plus");
		Map<String, Object> maxlifeInsuranceValues = new HashMap<>();
		maxlifeInsuranceValues.put("premium", 1500);
		maxlifeInsuranceValues.put("coverage", 120000);
		maxlifeInsurance.setValues(maxlifeInsuranceValues);
		option[1] = maxlifeInsurance;

		Option bajajAllianzInsurance = new Option();
		bajajAllianzInsurance.setDescriptionHtml(
				"<img style='max-height: 100px; max-width: 100px;'  src='http://d3t9qg8wxjimod.cloudfront.net/_media/iifl/img/article/2015-03/18/full/1426650849-7581.jpg'/>");
		bajajAllianzInsurance.setKey("bajaj_allianz_health_plus");
		bajajAllianzInsurance.setName("Bajaj Health Plus");
		Map<String, Object> bajajAllianzValues = new HashMap<>();
		bajajAllianzValues.put("premium", 1200);
		bajajAllianzValues.put("coverage", 100000);
		bajajAllianzInsurance.setValues(bajajAllianzValues);
		option[2] = bajajAllianzInsurance;

		options.setOptions(option);
		options.setSubject("Health Insurance");
		return options;
	}

	private Problem getHealthInsuranceProblem() {
		Problem problem = new Problem();
		problem.setSubject("Health Insurance");

		Column premium = new Column();
		premium.setKey("premium");
		premium.setFullName("Premium");
		premium.setIsObjective(true);
		premium.setType("numeric");
		premium.setFormat("€####0.00");
		premium.setGoal("min");

		Column coverage = new Column();
		coverage.setKey("coverage");
		coverage.setFullName("Coverage");
		coverage.setIsObjective(true);
		coverage.setType("numeric");
		coverage.setFormat("€####0.00");
		coverage.setGoal("max");

		Column[] columns = new Column[2];
		columns[0] = premium;
		columns[1] = coverage;
		problem.setColumns(columns);
		return problem;
	}

}
