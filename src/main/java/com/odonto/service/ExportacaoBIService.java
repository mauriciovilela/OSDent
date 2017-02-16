package com.odonto.service;

import java.io.IOException;
import java.io.Serializable;

import org.apache.log4j.Logger;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ExportacaoBIService implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ExportacaoBIService.class);

//	public static final String URL_API = "https://clinica-bi.herokuapp.com/osdent";
	public static final String URL_API = "http://localhost:8081/osdent";

	public String getUltimaExportacao(String rota) throws IOException {
		String urlRest = String.format("%s/%s/ultimaImportacao", URL_API, rota);
		Response response = executeRequestGET(urlRest);
		if (response.code() == 200) {
			logger.info(String.format("[200][OK] Sucesso: %s", urlRest));
			return response.body().string();
		}
		else {
			logger.error(String.format("Não foi possível executar: %s", urlRest));
			return null;
		}
	}

	public void exportarAgenda(String postData) {
		String urlRest = String.format("%s/agenda", URL_API);
		Response response = executeRequestPOST(urlRest, postData);
		if (response.code() == 200) {
			logger.info(String.format("[200][OK] Sucesso: %s", urlRest));
		} else {
			logger.error(String.format("Não foi possível executar: %s", urlRest));
		}
	}

	public void exportarDespesa(String postData) {
		String urlRest = String.format("%s/despesa", URL_API);
		Response response = executeRequestPOST(urlRest, postData);
		if (response.code() == 200) {
			logger.info(String.format("[200][OK] Sucesso: %s", urlRest));
		} else {
			logger.error(String.format("Não foi possível executar: %s", urlRest));
		}
	}

	public void exportarPagamento(String postData) {
		String urlRest = String.format("%s/pagamento", URL_API);
		Response response = executeRequestPOST(urlRest, postData);
		if (response.code() == 200) {
			logger.info(String.format("[200][OK] Sucesso: %s", urlRest));
		} else {
			logger.error(String.format("Não foi possível executar: %s", urlRest));
		}
	}

	public void exportarUsuario(String postData) {
		String urlRest = String.format("%s/usuario", URL_API);
		Response response = executeRequestPOST(urlRest, postData);
		if (response.code() == 200) {
			logger.info(String.format("[200][OK] Sucesso: %s", urlRest));
		} else {
			logger.error(String.format("Não foi possível executar: %s", urlRest));
		}
	}

	private Response executeRequestPOST(String urlRest, String postData) {
		OkHttpClient client = new OkHttpClient();
		okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, postData);
		Request request = new Request.Builder().url(urlRest).post(body).addHeader("content-type", "application/json")
				.addHeader("cache-control", "no-cache")
				.addHeader("postman-token", "1d5b46f9-48bc-1e13-bdbf-52e2ffaaa1a1").build();
		Response response = null;
		try {
			response = client.newCall(request).execute();
		} catch (IOException e) {
			logger.error(String.format("[ERRO] Não foi possível executar: %s, dados = %s", urlRest, postData));
			e.printStackTrace();
		}
		return response;
	}

	private Response executeRequestGET(String urlRest) {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(urlRest).get().addHeader("content-type", "application/json")
				.addHeader("cache-control", "no-cache")
				.addHeader("postman-token", "1d5b46f9-48bc-1e13-bdbf-52e2ffaaa1a1").build();
		Response response = null;
		try {
			response = client.newCall(request).execute();
		} catch (IOException e) {
			logger.error(String.format("[ERRO] Não foi possível executar: %s", urlRest));
			e.printStackTrace();
		}
		return response;
	}

}
