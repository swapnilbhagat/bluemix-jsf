package com.talcraft.servlets;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.util.EntityUtils;

@WebServlet({ "demo/events", "demo/dilemmas" })
@MultipartConfig
public class DemoServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(DemoServlet.class.getName());
	private static final long serialVersionUID = 1L;

	private String baseURL = "https://gateway.watsonplatform.net/tradeoff-analytics/api";
	private String username = "a630a7b2-4a7a-43b7-aabd-752b973a3220";
	private String password = "SX4n53k3JMWd";

	/**
	 * Forward the request to the index.jsp file
	 *
	 * @param req
	 *            the req
	 * @param resp
	 *            the resp
	 * @throws ServletException
	 *             the servlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	/**
	 * Create and POST a request to the Watson service
	 *
	 * @param req
	 *            the Http Servlet request
	 * @param resp
	 *            the Http Servlet response
	 * @throws ServletException
	 *             the servlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {
		logger.info("doPost");

		req.setCharacterEncoding("UTF-8");
		try {
			String reqURI = req.getRequestURI();
			String endpoint = reqURI.substring(reqURI.lastIndexOf('/') + 1);
			String url = baseURL + "/v1/" + endpoint;
			URI uri = new URI(url).normalize();

			Request newReq = Request.Post(uri);
			newReq.addHeader("Accept", "application/json");

			String metadata = req.getHeader("x-watson-metadata");
			if (metadata != null) {
				metadata += "client-ip:" + req.getRemoteAddr();
				newReq.addHeader("x-watson-metadata", metadata);
			}

			InputStreamEntity entity = new InputStreamEntity(req.getInputStream());
			System.out.println("Request header-x-watson-metadata: " + metadata);
			String requestBody = EntityUtils.toString(entity, "UTF-8");
			System.out.println("Request body: " + requestBody);
			newReq.bodyString(requestBody, ContentType.APPLICATION_JSON);

			Executor executor = this.buildExecutor(uri);
			System.out.println("URI: " + uri);
			Response response = executor.execute(newReq);
			HttpResponse httpResponse = response.returnResponse();
			resp.setStatus(httpResponse.getStatusLine().getStatusCode());

			System.out.println("Response received is:");
			httpResponse.getEntity().writeTo(System.out);

			ServletOutputStream servletOutputStream = resp.getOutputStream();
			httpResponse.getEntity().writeTo(servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();
			System.out.println("******************Request processed********************");

		} catch (Exception e) {
			// Log something and return an error message
			logger.log(Level.SEVERE, "got error: " + e.getMessage(), e);
			resp.setStatus(HttpStatus.SC_BAD_GATEWAY);
		}
	}

	private Executor buildExecutor(URI url) {
		return Executor.newInstance().auth(username, password)
				.authPreemptive(new HttpHost(url.getHost(), url.getPort(), url.getScheme()));
	}
}
