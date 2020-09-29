package com.webflux.dashboard.reactivesbmdashboard.webFluxRestApi;

import java.util.List;

import com.webflux.dashboard.reactivesbmdashboard.webFluxRestApi.models.Job;
import com.webflux.dashboard.reactivesbmdashboard.webFluxRestApi.models.Root;

public class ClientNotifier {

	private static final int SERVER_STARTED = -1, SAME_DATA = 1, DIFF_DATA = 0;

	public int isNotifiable(List<Root> oldData, List<Root> newData) {
		int toggle = DIFF_DATA;
		if (null == oldData) {
			return SERVER_STARTED;
		}
		for (int i = 0; i < oldData.size(); i++) {
			if (true == compareJobs(oldData.get(i).getJobs(), newData.get(i).getJobs())) {
				toggle = SAME_DATA;
			} else {
				toggle = DIFF_DATA;
				break;
			}
		}
		return toggle;
	}

	private Boolean compareJobs(List<Job> oldJobs, List<Job> newJobs) {
		for (int i = 0; i < oldJobs.size(); i++) {
			if (true == oldJobs.get(i).getColor().equalsIgnoreCase(newJobs.get(i).getColor())) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

}
