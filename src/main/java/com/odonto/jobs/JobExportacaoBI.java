package com.odonto.jobs;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.scheduler.api.Scheduled;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.odonto.BLL.AgendaBLL;
import com.odonto.BLL.PagamentoBLL;
import com.odonto.BLL.SaidaBLL;
import com.odonto.dto.AgendaOUT;
import com.odonto.dto.PagamentoOUT;
import com.odonto.dto.SaidaOUT;
import com.odonto.model.TbPagamento;
import com.odonto.model.TbSaida;
import com.odonto.util.Util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Scheduled(cronExpression = "0/15 * * * * ?")
public class JobExportacaoBI implements org.quartz.Job {

	// 0 0 19 * * ? = as 19 horas
	// 0/10 * * * * ? = A cada 10 segundos

	@Inject
	private SaidaBLL saidaBLL;

	@Inject
	private PagamentoBLL pagamentoBLL;

	@Inject
	private AgendaBLL agendaBLL;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			exportarAgendaBI(1);
			exportarDespesasBI();
			exportarPagamentosBI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void exportarAgendaBI(Integer idDentista) throws IOException {

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
			item.setDtInclusao(item.getDtInclusao());
			item.setHoraInicio(smHM.format(item.getDtInicio()));
			item.setHoraFim(smHM.format(item.getDtFim()));
			item.setDtDia(smD.format(item.getDtInicio()));
			item.setDsDescricao(item.getDsDescricao());
			item.setDsProcedimento(item.getDsProcedimento());
			item.setIdDentista(idDentista);
			item.setDtInicio(null);
			item.setDtFim(null);
		}

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
		String postData = gson.toJson(result);

		OkHttpClient client = new OkHttpClient();

		okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, postData);
		Request request = new Request.Builder().url("https://clinica-bi.herokuapp.com/osdent/agenda").post(body)
				.addHeader("content-type", "application/json").addHeader("cache-control", "no-cache")
				.addHeader("postman-token", "1d5b46f9-48bc-1e13-bdbf-52e2ffaaa1a1").build();

		Response response = client.newCall(request).execute();
		System.out.println(response.code());
	}

	public void exportarDespesasBI() throws IOException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url("https://clinica-bi.herokuapp.com/osdent/despesa/ultimaImportacao").get()
				.addHeader("content-type", "application/json").addHeader("cache-control", "no-cache")
				.addHeader("postman-token", "1d5b46f9-48bc-1e13-bdbf-52e2ffaaa1a1").build();

		Response response = client.newCall(request).execute();
		String retorno = response.body().string();
		Date data = (StringUtils.isEmpty(retorno) ? Util.getMinDate() : Util.convertJSONDate(retorno));

		List<TbSaida> lista = saidaBLL.porDataExportacao(data);

		if (lista.size() > 0) {

			SaidaOUT itemExport = null;
			List<SaidaOUT> result = new ArrayList<SaidaOUT>();

			DecimalFormat dfMoeda = new DecimalFormat("R$ 0.##");
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

			for (TbSaida item : lista) {
				itemExport = new SaidaOUT();
				itemExport.setId(item.getId());
				itemExport.setDsDescricao(item.getDsDescricao());
				itemExport.setDsFornecedor(item.getTbFornecedor().getDsNome());
				if (item.getTbSocioInss() != null)
					itemExport.setDsSocioInss(item.getTbSocioInss().getDsNome());
				itemExport.setDsFluxoCaixa(item.getFlFluxoCaixa() ? "Sim" : "Não" );
				itemExport.setDtInclusao(item.getDtInclusao());
				itemExport.setVlTotal(dfMoeda.format(item.getVlTotal()));
				itemExport.setVlPago(dfMoeda.format(item.getVlPago()));
				itemExport.setDtData(df.format(item.getDtInclusao()));
				result.add(itemExport);
			}

			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String postData = gson.toJson(result);

			client = new OkHttpClient();

			okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, postData);
			request = new Request.Builder().url("https://clinica-bi.herokuapp.com/osdent/despesa").post(body)
					.addHeader("content-type", "application/json").addHeader("cache-control", "no-cache")
					.addHeader("postman-token", "1d5b46f9-48bc-1e13-bdbf-52e2ffaaa1a1").build();

			response = client.newCall(request).execute();
			System.out.println(response.code());
		}

	}
	
	public void exportarPagamentosBI() throws IOException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url("https://clinica-bi.herokuapp.com/osdent/pagamento/ultimaImportacao").get()
				.addHeader("content-type", "application/json").addHeader("cache-control", "no-cache")
				.addHeader("postman-token", "1d5b46f9-48bc-1e13-bdbf-52e2ffaaa1a1").build();

		Response response = client.newCall(request).execute();
		String retorno = response.body().string();
		Date data = (StringUtils.isEmpty(retorno) ? Util.getMinDate() : Util.convertJSONDate(retorno));

		List<TbPagamento> lista = pagamentoBLL.porDataExportacao(data);

		if (lista.size() > 0) {

			PagamentoOUT itemExport = null;
			List<PagamentoOUT> result = new ArrayList<PagamentoOUT>();

			DecimalFormat dfMoeda = new DecimalFormat("R$ 0.##");
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

			for (TbPagamento item : lista) {
				itemExport = new PagamentoOUT();
				itemExport.setId(item.getId());
				itemExport.setDsObservacao(item.getDsObservacao());
				itemExport.setDtInclusao(item.getDtInclusao());
				if (item.getTbDentista() != null)
					itemExport.setDsDentista(item.getTbDentista().getDsNome());
				itemExport.setDsPaciente(item.getTbPaciente().getDsNome());
				itemExport.setDsTipoPagamento(item.getTbTipoPgto().getDsNome());
				itemExport.setDsResponsavel(item.getTbResponsavel().getDsNome());
				if (item.getTbMaquina() != null)
					itemExport.setDsMaquina(item.getTbMaquina().getDsNome());
				itemExport.setVlTotal(dfMoeda.format(item.getVlTotal()));
				if (item.getVlParcela() != null)
					itemExport.setVlParcela(dfMoeda.format(item.getVlParcela()));
				itemExport.setQtParcelas(item.getQtParcelas());
				itemExport.setDtData(df.format(item.getDtInclusao()));
				result.add(itemExport);
			}

			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String postData = gson.toJson(result);

			client = new OkHttpClient();

			okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, postData);
			request = new Request.Builder().url("https://clinica-bi.herokuapp.com/osdent/pagamento").post(body)
					.addHeader("content-type", "application/json").addHeader("cache-control", "no-cache")
					.addHeader("postman-token", "1d5b46f9-48bc-1e13-bdbf-52e2ffaaa1a1").build();

			response = client.newCall(request).execute();
			System.out.println(response.code());
		}
		
	}
}
