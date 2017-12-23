package com.odonto.rest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;

import com.google.gson.Gson;
import com.odonto.BLL.AgendaBLL;
import com.odonto.dto.AgendaGroupOUT;
import com.odonto.dto.AgendaOUT;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Path("/agenda")
public class AgendaREST {
	
	@Inject
	private AgendaBLL agendaBLL;

	@GET
	@Path("/dentista/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AgendaGroupOUT> listarPorDentista(@PathParam("id") Integer idDentista) throws ClientProtocolException, IOException {	
		
		Date dataInicial;
		Date dataFinal;
		
		// Calculando o perido da semana
		Calendar data = Calendar.getInstance();

		data.set(Calendar.HOUR_OF_DAY, 0);
		data.set(Calendar.MINUTE, 0);
		data.set(Calendar.SECOND, 0);
		data.add(Calendar.DATE, -40);
		dataInicial = data.getTime();
		
		// Resto da semana atual e proxima semana
		data.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		data.add(Calendar.DATE, 8);
		data.set(Calendar.HOUR_OF_DAY, 23);
		data.set(Calendar.MINUTE, 59);
		data.set(Calendar.SECOND, 59);
		dataFinal = data.getTime();
		
		// Realizando a consulta
		List<AgendaOUT> result = agendaBLL.listarPorDentista(idDentista, dataInicial, dataFinal, false);
		
		// Formatando a data
		SimpleDateFormat smHM = new SimpleDateFormat("HH:mm");
		SimpleDateFormat smD = new SimpleDateFormat("dd/MM/yyyy");
		
		for (AgendaOUT item : result) {
			item.setHoraInicio(smHM.format(item.getDtInicio()));
			item.setHoraFim(smHM.format(item.getDtFim()));
			item.setDtDia(smD.format(item.getDtInicio()));
			if (StringUtils.isNotBlank(item.getDsProcedimento()) && StringUtils.isNotBlank(item.getDsDescricao())) {
				item.setDsProcedimento(item.getDsProcedimento().concat(", " + item.getDsDescricao()));
			}
			else if (StringUtils.isNotBlank(item.getDsProcedimento())) {
				item.setDsProcedimento(item.getDsProcedimento());
			}
			else {
				item.setDsProcedimento(item.getDsDescricao());
			}
			item.setIdDentista(idDentista);
			item.setDtInicio(null);
			item.setDtFim(null);
		}

		String postData = new Gson().toJson(result);
		
		OkHttpClient client = new OkHttpClient();
		
		okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, postData);
		Request request = new Request.Builder()
		  .url("http://localhost:8081/osdent/agenda")
		  .post(body)
		  .addHeader("content-type", "application/json")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("postman-token", "1d5b46f9-48bc-1e13-bdbf-52e2ffaaa1a1")
		  .build();

		Response response = client.newCall(request).execute();
		System.out.println(response.code());

		return null;
		
//		List<AgendaGroupOUT> retorno = new ArrayList<>();
//		AgendaGroupOUT itemRet = new AgendaGroupOUT();
//		
//		Map<String,List<AgendaOUT>> lstAgrupadaData = result.stream().collect(Collectors.groupingBy(AgendaOUT::getDtDia));
//		
//		for ( String key : lstAgrupadaData.keySet() ) {
//			itemRet = new AgendaGroupOUT();
//			itemRet.setDtDia(key);
//			itemRet.setLstAgenda(lstAgrupadaData.get(key));
//			retorno.add(itemRet);
//		}
//		return retorno;
	}
	
}
