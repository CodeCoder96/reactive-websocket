package com.webflux.dashboard.reactivesbmdashboard.webFluxRestApi;

import com.webflux.dashboard.reactivesbmdashboard.webFluxRestApi.models.Root;

import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.gson.GsonDecoder;

public class JenkinsDataService {

	private static JenkinsDataService service;

	private Root devData;
	private Root prodData;
	private Root stableData;
	private Root stageData;

	public Root getProdData() {
		return prodData;
	}

	public Root getStableData() {
		return stableData;
	}

	public Root getStageData() {
		return stageData;
	}

	public Root getDevData() {
		return devData;
	}

	public static JenkinsDataService getInstance() {
		if (null == service) {
			service = new JenkinsDataService();
		}

		return service;
	}

	public void startService(String id, String pass) {
		RequestHandler requestHandler = Feign.builder().requestInterceptor(new BasicAuthRequestInterceptor(id, pass))
				.decoder(new GsonDecoder()).target(RequestHandler.class, "http://localhost:3000");

		devData = requestHandler.getDevData();
		prodData = requestHandler.getProdData();
		stableData = requestHandler.getStableData();
		stageData = requestHandler.getStageData();

	}
}
