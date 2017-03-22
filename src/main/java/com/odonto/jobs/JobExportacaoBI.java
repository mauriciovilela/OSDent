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
import com.odonto.BLL.FechamentoCaixaBLL;
import com.odonto.BLL.PagamentoBLL;
import com.odonto.BLL.SaidaBLL;
import com.odonto.BLL.UsuarioBLL;
import com.odonto.dto.AgendaOUT;
import com.odonto.dto.FechamentoOUT;
import com.odonto.dto.PagamentoOUT;
import com.odonto.dto.SaidaOUT;
import com.odonto.dto.UsuarioOUT;
import com.odonto.model.TbFechamentoCaixa;
import com.odonto.model.TbPagamento;
import com.odonto.model.TbSaida;
import com.odonto.model.TbUsuario;
import com.odonto.service.ExportacaoBIService;
import com.odonto.util.Util;

@Scheduled(cronExpression = "0 0/30 * * * ?")
public class JobExportacaoBI implements org.quartz.Job {

	// 0 0 19 * * ? = as 19 horas
	// 0/10 * * * * ? = A cada 10 segundos

	@Inject
	private SaidaBLL saidaBLL;

	@Inject
	private UsuarioBLL usuarioBLL;
	
	@Inject
	private PagamentoBLL pagamentoBLL;

	@Inject
	private AgendaBLL agendaBLL;

	@Inject
	private FechamentoCaixaBLL fechamentoBLL;
	
	@Inject
	private ExportacaoBIService exportacaoService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			exportarAgendaBI(2);
			exportarDespesasBI();
			exportarPagamentosBI();
			exportarUsuariosBI();
			exportarFechamentoCaixaBI();
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
		
		exportacaoService.exportarAgenda(postData);
	}

	public void exportarDespesasBI() throws IOException {
		
		String retorno = exportacaoService.getUltimaExportacao("despesa");
		
		Date data = (StringUtils.isEmpty(retorno) ? Util.getMinDate() : Util.convertJSONDate(retorno));

		List<TbSaida> lista = saidaBLL.porDataExportacao(data);

		if (lista.size() > 0) {

			SaidaOUT itemExport = null;
			List<SaidaOUT> result = new ArrayList<SaidaOUT>();

			DecimalFormat dfMoeda = new DecimalFormat("R$ #,##0.00");
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

			exportacaoService.exportarDespesa(postData);
		}

	}
	
	public void exportarPagamentosBI() throws IOException {
		
		String retorno = exportacaoService.getUltimaExportacao("pagamento");
		
		Date data = (StringUtils.isEmpty(retorno) ? Util.getMinDate() : Util.convertJSONDate(retorno));

		List<TbPagamento> lista = pagamentoBLL.porDataExportacao(data);

		if (lista.size() > 0) {

			PagamentoOUT itemExport = null;
			List<PagamentoOUT> result = new ArrayList<PagamentoOUT>();

			DecimalFormat dfMoeda = new DecimalFormat("R$ #,##0.00");
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

			exportacaoService.exportarPagamento(postData);
		}
		
	}

	public void exportarUsuariosBI() throws IOException {
		
		String retorno = exportacaoService.getUltimaExportacao("usuario");
		
		Date data = (StringUtils.isEmpty(retorno) ? Util.getMinDate() : Util.convertJSONDate(retorno));

		List<TbUsuario> lista = usuarioBLL.porDataExportacao(data);

		if (lista.size() > 0) {

			UsuarioOUT itemExport = null;
			List<UsuarioOUT> result = new ArrayList<UsuarioOUT>();
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
			for (TbUsuario item : lista) {
				itemExport = new UsuarioOUT();
				itemExport.setId(item.getId());
				itemExport.setDsNome(item.getDsNome());
				itemExport.setDsSenha(item.getDsSenha());
				itemExport.setDsEmail(item.getDsEmail());
				itemExport.setDsUsuario(item.getDsUsuario());
				itemExport.setDsPerfil(item.getDsPerfil());
				itemExport.setDtInclusao(item.getDtInclusao());
				itemExport.setDtData(df.format(item.getDtInclusao()));
				result.add(itemExport);
			}

			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String postData = gson.toJson(result);

			exportacaoService.exportarUsuario(postData);
		}

	}	

	public void exportarFechamentoCaixaBI() throws IOException {
		
		String retorno = exportacaoService.getUltimaExportacao("fechamento");
		
		Date data = (StringUtils.isEmpty(retorno) ? Util.getMinDate() : Util.convertJSONDate(retorno));

		List<TbFechamentoCaixa> lista = fechamentoBLL.porDataExportacao(data);

		if (lista.size() > 0) {

			FechamentoOUT itemExport = null;
			List<FechamentoOUT> result = new ArrayList<FechamentoOUT>();
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			DecimalFormat dfMoeda = new DecimalFormat("R$ #,##0.00");
			
			for (TbFechamentoCaixa item : lista) {
				itemExport = new FechamentoOUT();
				itemExport.setId(item.getId());
				itemExport.setDsDescricao(item.getDsDescricao());
				itemExport.setDsResponsavel(item.getTbResponsavel().getDsNome());
				itemExport.setVlCheque(dfMoeda.format(item.getVlCheque()));
				itemExport.setVlCredito(dfMoeda.format(item.getVlCredito()));
				itemExport.setVlDebito(dfMoeda.format(item.getVlDebito()));
				itemExport.setVlDespesa(dfMoeda.format(item.getVlDespesa()));
				itemExport.setVlDinheiro(dfMoeda.format(item.getVlDinheiro()));
				itemExport.setVlMovimentado(dfMoeda.format(item.getVlMovimentado()));
				itemExport.setDtInclusao(item.getDtInclusao());
				itemExport.setDtData(df.format(item.getDtFechamento()));
				result.add(itemExport);
			}

			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String postData = gson.toJson(result);

			exportacaoService.exportarFechamento(postData);
		}

	}
}
