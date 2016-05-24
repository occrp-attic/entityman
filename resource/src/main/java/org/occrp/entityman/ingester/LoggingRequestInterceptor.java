package org.occrp.entityman.ingester;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor  {

	protected Logger logger = LogManager.getLogger(getClass().getName());

	int maxRequest = 2000;
	int maxResponse = 20000;
	
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        traceResponse(response);
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) throws IOException {
        logger.debug("===========================request begin================================================");

        logger.debug("URI : " + request.getURI());
        logger.debug("Method : " + request.getMethod());
        logger.debug("Request Body : " + new String(body, 0, maxRequest > body.length ? body.length : maxRequest, "UTF-8"));
//        logger.debug("Request Body : " + new String(body, "UTF-8"));
        logger.debug("==========================request end================================================");
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {
        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
        
        String line = bufferedReader.readLine();
        while (line != null) {
            inputStringBuilder.append(line);
            inputStringBuilder.append('\n');
            line = bufferedReader.readLine();
        }
        logger.debug("============================response begin==========================================");
        logger.debug("status code: " + response.getStatusCode());
        logger.debug("status text: " + response.getStatusText());
        logger.debug("Response Body : " + inputStringBuilder.substring(0, 
        		maxResponse > inputStringBuilder.length() ? inputStringBuilder.length() : maxResponse).toString());
//        logger.debug("Response Body : " + inputStringBuilder.toString());
        logger.debug("=======================response end=================================================");
    }

}