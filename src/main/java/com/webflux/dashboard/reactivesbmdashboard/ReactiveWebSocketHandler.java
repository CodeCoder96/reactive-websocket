package com.webflux.dashboard.reactivesbmdashboard;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.webflux.dashboard.reactivesbmdashboard.regulation.JenkinsDataModelConfig;
import com.webflux.dashboard.reactivesbmdashboard.webFluxRestApi.RestMainC;
import reactor.core.publisher.Mono;

@Component
public class ReactiveWebSocketHandler implements WebSocketHandler {

	private Mono<String> socketMessage;

	private ObjectMapper objectMapper;

	@Override
	public Mono<Void> handle(WebSocketSession session) {
		objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String data = "Veri çekerken hata oluştu";
		try {
			data = objectMapper.writeValueAsString(JenkinsDataModelConfig.getModels(RestMainC.getDashboardDatas()));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		socketMessage = Mono.just(data);
		if (null == socketMessage) {
			return session.close();
		}
		return session.send(socketMessage.map(session::textMessage));
	}

}
