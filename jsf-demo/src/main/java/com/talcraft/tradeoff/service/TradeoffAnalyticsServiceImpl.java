package com.talcraft.tradeoff.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;

import com.talcraft.tradeoff.domain.Options;
import com.talcraft.tradeoff.domain.Problem;
import com.talcraft.util.JsonHelper;

public class TradeoffAnalyticsServiceImpl implements TradeoffAnalyticsService {

	private static final String BASE_URL = "https://gateway.watsonplatform.net/tradeoff-analytics/api/v1/";
	private static final String username = "a630a7b2-4a7a-43b7-aabd-752b973a3220";
	private static final String password = "SX4n53k3JMWd";

	@Override
	public String getResolutionsForProblem(Problem problem, Options options) {
		try {
			URI uri = new URI(BASE_URL + "dilemmas").normalize();

			StringBuilder requestBuilder = new StringBuilder();
			String problemString = JsonHelper.jsonToString(problem);
			requestBuilder.append(problemString.substring(0, problemString.lastIndexOf("}")));
			String optionsString = JsonHelper.jsonToString(options);
			requestBuilder.append(",");
			requestBuilder.append(optionsString.substring(optionsString.indexOf("{") + 1));

			Request newReq = Request.Post(uri);
			newReq.addHeader("Accept", "application/json");
			String requestBody = requestBuilder.toString();

			newReq.bodyString(requestBody, ContentType.APPLICATION_JSON);
			Executor executor = Executor.newInstance().auth(username, password)
					.authPreemptive(new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme()));
			Response response = executor.execute(newReq);
			HttpResponse httpResponse = response.returnResponse();
			byte[] responseBytes = new byte[65000];
			StringBuilder stringBuilder = new StringBuilder();
			InputStream responseInputStream = httpResponse.getEntity().getContent();
			while (responseInputStream.available() > 0) {
				responseInputStream.read(responseBytes);
				stringBuilder.append(new String(responseBytes));
			}
			return stringBuilder.toString();
		} catch (IOException | URISyntaxException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public String loadProfile(String profileName) {
		return null;
	}

}
