package com.webflux.dashboard.reactivesbmdashboard.webFluxRestApi;

import java.util.ArrayList;
import java.util.List;

import com.webflux.dashboard.reactivesbmdashboard.regulation.ManupilateDataBeforeMonitoring;
import com.webflux.dashboard.reactivesbmdashboard.webFluxRestApi.models.DashboardDataModel;
import com.webflux.dashboard.reactivesbmdashboard.webFluxRestApi.models.Root;

public class RestMainC {

	private static final String ID = "admin";
	private static final String PASS = "11b72192a82459ace708106b1ed7adaa7f";
	private static DashboardDataModel[] dashboardDatas;
	private static List<Root> jenkinsDatas;
	private static int notifyValue = 2;

	private RestMainC() {

	}

	public static void showMeData() {

		JenkinsDataService service = JenkinsDataService.getInstance();
		service.startService(ID, PASS);

		List<Root> newestJenkinsData = new ArrayList<>();

		newestJenkinsData.add(service.getDevData());
		newestJenkinsData.add(service.getProdData());
		newestJenkinsData.add(service.getStableData());
		newestJenkinsData.add(service.getStageData());

		ClientNotifier notifier = new ClientNotifier();

		notifyValue = notifier.isNotifiable(jenkinsDatas, newestJenkinsData);

		jenkinsDatas = new ArrayList<>();
		jenkinsDatas.add(service.getDevData());
		jenkinsDatas.add(service.getProdData());
		jenkinsDatas.add(service.getStableData());
		jenkinsDatas.add(service.getStageData());

		ManupilateDataBeforeMonitoring data = ManupilateDataBeforeMonitoring.getInstance();
		dashboardDatas = data.manupilateData(service.getDevData().getJobs(), service.getProdData().getJobs(),
				service.getStableData().getJobs(), service.getStageData().getJobs());

	}

	public static DashboardDataModel[] getDashboardDatas() {
		return dashboardDatas;
	}

	public static int getNotifyValue() {
		return notifyValue;
	}

	public static List<Root> getJenkinsDatas() {
		return jenkinsDatas;
	}

}
