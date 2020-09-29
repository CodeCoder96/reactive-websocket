package com.webflux.dashboard.reactivesbmdashboard.webFluxRestApi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webflux.dashboard.reactivesbmdashboard.regulation.JenkinsDataModelConfig;
import com.webflux.dashboard.reactivesbmdashboard.webFluxRestApi.models.DashboardDataModel;

import reactor.core.publisher.Flux;

@RestController
public class WebFluxController {

	@GetMapping("/api")
	public Flux<DashboardDataModel> handle() {
		Flux<DashboardDataModel> data = Flux.fromArray(JenkinsDataModelConfig.getModels(RestMainC.getDashboardDatas()));
		return data;
	}

}
