package com.odonto.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.odonto.BLL.FechamentoCaixaBLL;
import com.odonto.BLL.PagamentoBLL;
import com.odonto.BLL.SaidaBLL;
import com.odonto.BLL.UsuarioBLL;
import com.odonto.constants.Constants.TbTipoPgto;
import com.odonto.dto.ConfigOUT;
import com.odonto.dto.UsuarioOUT;
import com.odonto.model.TbFechamentoCaixa;
import com.odonto.model.TbPagamento;
import com.odonto.model.TbSaida;
import com.odonto.security.SessionContext;
import com.odonto.service.FechamentoCaixaService;
import com.odonto.util.Util;

@Named
@ViewScoped
public class FechamentoCaixaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date data;

	@NotNull
	private Date dataFechamento;

	@Inject
	private SaidaBLL saidaBLL;

	@Inject
	private UsuarioBLL usuarioBLL;

	@Inject
	private PagamentoBLL pagamentoBLL;

	@Inject
	private FechamentoCaixaBLL fechamentoCaixaBLL;

	@Inject
	private FechamentoCaixaService fechamentoCaixaService;

	private List<TbPagamento> pagamentos;
	private List<TbSaida> saidasComFluxo;
	private List<TbSaida> saidasSemFluxo;

	private BigDecimal vlTotalEntrada;
	private BigDecimal vlTotalSaidaComFluxo;
	private BigDecimal vlTotalSaidaSemFluxo;
	private BigDecimal vlLiquido;
	private BigDecimal vlLiquidoDinheiro;
	private BigDecimal vlBrutoCartoes;
	private BigDecimal vlBrutoDebito;
	private BigDecimal vlBrutoCredito;
	private BigDecimal vlBrutoDinheiro;
	private BigDecimal vlBrutoCheque;
	private String nomeArquivoGerado;

	private static Logger logger = Logger.getLogger(FechamentoCaixaBean.class);

	@NotNull
	private Integer idResponsavel;

	private String textoMensagem;

	private int acumulaAUX = 0;

	private TbFechamentoCaixa caixaEncontrado = new TbFechamentoCaixa();

	private ConfigOUT config = SessionContext.getInstance().getConfig();

	public int getValorAcumulado() {
		int aux = acumulaAUX;
		acumulaAUX = 0;
		return aux;
	}

	public void acumulaValor(int idTipoPgto, int value) {
		acumulaAUX += value;
	}

	public FechamentoCaixaBean() {
		limpar();
	}

	private void limpar() {
		data = Util.getDataHora(new Date(), true);
		textoMensagem = StringUtils.EMPTY;
	}

	@PostConstruct
	private void pageLoad() throws Exception {
		pesquisar(true);
	}

	public void pesquisar(boolean consultaFluxo) {
		caixaEncontrado = fechamentoCaixaBLL.listarPorData(data);
		saidasComFluxo = saidaBLL.porData(data, true);
		saidasSemFluxo = saidaBLL.porData(data, false);
		pagamentos = pagamentoBLL.porData(data);
		calculaTotais();
		if (caixaEncontrado != null) {
			if (consultaFluxo) {
				criarArquivoPDF(false);
			}
		} else {
			dataFechamento = data;
			textoMensagem = StringUtils.EMPTY;
			idResponsavel = SessionContext.getInstance().getUsuarioLogado().getId();
		}
	}

	private void calculaTotais() {

		vlTotalSaidaComFluxo = new BigDecimal(0);
		vlTotalSaidaSemFluxo = new BigDecimal(0);

		for (TbSaida saida : saidasComFluxo) {
			vlTotalSaidaComFluxo = vlTotalSaidaComFluxo.add(saida.getVlPago());
		}
		for (TbSaida saida : saidasSemFluxo) {
			vlTotalSaidaSemFluxo = vlTotalSaidaSemFluxo.add(saida.getVlPago());
		}

		vlTotalEntrada = new BigDecimal(0);
		BigDecimal vlTotalDinheiro = new BigDecimal(0);
		vlBrutoDinheiro = new BigDecimal(0);
		vlBrutoCheque = new BigDecimal(0);
		vlBrutoCartoes = new BigDecimal(0);
		vlBrutoDebito = new BigDecimal(0);
		vlBrutoCredito = new BigDecimal(0);

		for (TbPagamento entrada : pagamentos) {
			// Soma o total de dinheiro
			if (entrada.getTbTipoPgto().getId().equals(TbTipoPgto.dinheiro)) {
				vlTotalDinheiro = vlTotalDinheiro.add(entrada.getVlPago());
			}
			vlTotalEntrada = vlTotalEntrada.add(entrada.getVlPago());
			// Soma o total dos cartões
			if (entrada.getTbTipoPgto().getId().equals(TbTipoPgto.debito)) {
				vlBrutoDebito = vlBrutoDebito.add(entrada.getVlPago());
			} else if (entrada.getTbTipoPgto().getId().equals(TbTipoPgto.credito)) {
				vlBrutoCredito = vlBrutoCredito.add(entrada.getVlPago());
			} else if (entrada.getTbTipoPgto().getId().equals(TbTipoPgto.dinheiro)) {
				vlBrutoDinheiro = vlBrutoDinheiro.add(entrada.getVlPago());
			} else if (entrada.getTbTipoPgto().getId().equals(TbTipoPgto.cheque)) {
				vlBrutoCheque = vlBrutoCheque.add(entrada.getVlPago());
			}
		}

		vlBrutoCartoes = vlBrutoDebito.add(vlBrutoCredito);

		vlLiquido = vlTotalEntrada.subtract(vlTotalSaidaComFluxo);
		vlLiquidoDinheiro = vlTotalDinheiro.subtract(vlTotalSaidaComFluxo);
	}

	public void reabrirCaixa() {
		fechamentoCaixaBLL.remover(caixaEncontrado);
		pesquisar(true);
	}

	public void salvarCaixa() {

		data = dataFechamento;
		criarArquivoPDF(true);
		pesquisar(false);

		// Persiste
		TbFechamentoCaixa fechamentoCaixa = new TbFechamentoCaixa();
		fechamentoCaixa.setDtInclusao(new Date());
		fechamentoCaixa.setDtFechamento(dataFechamento);
		fechamentoCaixa.setDsDescricao(textoMensagem);
		fechamentoCaixa.setTbResponsavel(usuarioBLL.porId(idResponsavel));
		// valores
		fechamentoCaixa.setVlMovimentado(vlTotalEntrada);
		fechamentoCaixa.setVlDespesa(vlTotalSaidaComFluxo.add(vlTotalSaidaSemFluxo));
		fechamentoCaixa.setVlDinheiro(vlBrutoDinheiro);
		fechamentoCaixa.setVlDebito(vlBrutoDebito);
		fechamentoCaixa.setVlCredito(vlBrutoCredito);
		fechamentoCaixa.setVlCheque(vlBrutoCheque);
		fechamentoCaixaService.salvar(fechamentoCaixa);
		caixaEncontrado = fechamentoCaixa;

		// Mensagem
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Caixa foi fechado com sucesso."));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void criarArquivoPDF(boolean envioEmailCaixa) {

		VelocityEngine ve = new VelocityEngine();
		Properties velocityProp = new Properties();
		velocityProp.setProperty("file.resource.loader.path", getCaminhoTemplateVelocity());
		velocityProp.setProperty("input.encoding", "UTF-8");
		velocityProp.setProperty("output.encoding", "UTF-8");
		ve.init(velocityProp);

		ArrayList mapPagamentos = new ArrayList();
		ArrayList mapDespesas = new ArrayList();
		ArrayList mapDespesasSemFluxo = new ArrayList();
		Map map = new HashMap();

		DecimalFormat dfMoeda = new DecimalFormat("R$ 0.##");

		for (TbPagamento item : pagamentos) {
			map = new HashMap();
			map.put("paciente", item.getTbPaciente().getDsNome());
			map.put("dentista", item.getTbDentista().getDsNome());
			if (item.getTbTipoPgto().getId().equals(TbTipoPgto.credito)) {
				map.put("total", dfMoeda.format(item.getVlTotal()) + " " + item.getQtParcelas() + "x");
			} else {
				map.put("total", dfMoeda.format(item.getVlTotal()));
			}
			mapPagamentos.add(map);
		}

		for (TbSaida item : saidasComFluxo) {
			map = new HashMap();
			map.put("despesa", item.getTbFornecedor().getDsNome());
			map.put("obs", item.getDsDescricao());
			map.put("total", dfMoeda.format(item.getVlTotal()));
			mapDespesas.add(map);
		}

		for (TbSaida item : saidasSemFluxo) {
			map = new HashMap();
			map.put("despesa", item.getTbFornecedor().getDsNome());
			map.put("obs", item.getDsDescricao());
			map.put("total", dfMoeda.format(item.getVlPago()));
			mapDespesasSemFluxo.add(map);
		}

		VelocityContext context = new VelocityContext();
		context.put("data", "");
		context.put("responsavel", "");
		Date dataArquivo = null;
		if (caixaEncontrado != null) {
			dataArquivo = caixaEncontrado.getDtFechamento();
			context.put("data", Util.getData(caixaEncontrado.getDtFechamento()));
			context.put("responsavel", caixaEncontrado.getTbResponsavel().getDsNome());
			context.put("obs", caixaEncontrado.getDsDescricao());
		} else {
			dataArquivo = dataFechamento;
			context.put("data", Util.getData(dataFechamento));
			context.put("responsavel", usuarioBLL.porId(idResponsavel).getDsNome());
			context.put("obs", textoMensagem);
		}
		context.put("vlMovimentado", dfMoeda.format(vlTotalEntrada));
		context.put("vlLiquido", dfMoeda.format(vlLiquido));
		context.put("vlLiquidoDinheiro", dfMoeda.format(vlLiquidoDinheiro));
		context.put("vlDespesas", dfMoeda.format(vlTotalSaidaComFluxo));
		context.put("vlDespesasSemFluxo", dfMoeda.format(vlTotalSaidaSemFluxo));
		context.put("vlBrutoCartoes", dfMoeda.format(vlBrutoCartoes));
		context.put("vlBrutoDinheiro", dfMoeda.format(vlBrutoDinheiro));
		context.put("vlBrutoCheque", dfMoeda.format(vlBrutoCheque));

		context.put("pagamentos", mapPagamentos);
		context.put("saidasComFluxo", mapDespesas);
		context.put("saidasSemFluxo", mapDespesasSemFluxo);
		context.put("vlTotalDespesas", dfMoeda.format(vlTotalSaidaComFluxo.add(vlTotalSaidaSemFluxo)));

		Template t = ve.getTemplate("TFechamentoCaixa.html");
		StringWriter writer = new StringWriter();
		t.merge(context, writer);

		try {
			final String arquivoGerar = getPdfGerado("FluxoCaixa", dataArquivo);
			File file = new File(arquivoGerar);
			if (file.exists()) {
				file.delete();
			}
			ITextRenderer iTextRenderer = new ITextRenderer();
			iTextRenderer.setDocumentFromString(writer.toString());
			iTextRenderer.layout();
			FileOutputStream fileOutputStream = new FileOutputStream(arquivoGerar);
			iTextRenderer.createPDF(fileOutputStream);
			fileOutputStream.close();

			if (envioEmailCaixa) {
				Thread thread = new Thread() {
					public void run() {
						enviarEmail(arquivoGerar, data);
					}
				};
				thread.start();
			}

		} catch (Exception ex) {
			logger.error("Erro no fechamento de caixa: " + ex);
		}
	}

	@SuppressWarnings("deprecation")
	private void enviarEmail(String caminho, Date dataFechamento) {
		try {
			List<UsuarioOUT> socios = usuarioBLL.listarSocios();
			for (UsuarioOUT socio : socios) {
				// Verifica se tem e-mail
				if (StringUtils.isNotBlank(socio.getDsEmail())) {
					HtmlEmail email = new HtmlEmail();
					email.setHostName(config.getMailHost());
					email.setSmtpPort(config.getMailPort());
					email.addTo(socio.getDsEmail(), config.getMailNome());
					email.setFrom(config.getMailEmail(), config.getMailNome());

					EmailAttachment attachment = new EmailAttachment();
					attachment.setPath(caminho);
					attachment.setDisposition(EmailAttachment.ATTACHMENT);
					email.attach(attachment);

					// Adicione um assunto
					email.setSubject("Fechamento caixa " + Util.getData(dataFechamento));
					// Adicione a mensagem do email
					email.setHtmlMsg("Fluxo de caixa fechado: " + Util.getData(dataFechamento));
					// Para autenticar no servidor é necessário chamar os dois
					// métodos
					// abaixo
					email.setSSL(true);
					email.setAuthentication(config.getMailUser(), config.getMailPassword());
					email.send();
				}
			}
		} catch (Exception ex) {
			logger.error("Erro no envio de e-mail (Fechamento caixa): " + ex);
		}
	}

	private String getPdfGerado(String nomeArquivo, Date dataArquivo) {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		nomeArquivoGerado = nomeArquivo + Util.getDataDDMMYY(dataArquivo) + ".pdf";
		return externalContext.getRealPath("/resources/gerado/") + "/" + nomeArquivoGerado;
	}

	private String getCaminhoTemplateVelocity() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		return externalContext.getRealPath("/WEB-INF/velocity/");
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public List<TbSaida> getSaidasComFluxo() {
		return saidasComFluxo;
	}

	public void setSaidasComFluxo(List<TbSaida> saidas) {
		this.saidasComFluxo = saidas;
	}

	public BigDecimal getVlTotalEntrada() {
		return vlTotalEntrada;
	}

	public void setVlTotalEntrada(BigDecimal vlTotalEntrada) {
		this.vlTotalEntrada = vlTotalEntrada;
	}

	public BigDecimal getVlLiquido() {
		return vlLiquido;
	}

	public void setVlLiquido(BigDecimal vlLiquido) {
		this.vlLiquido = vlLiquido;
	}

	public BigDecimal getVlLiquidoDinheiro() {
		return vlLiquidoDinheiro;
	}

	public void setVlLiquidoDinheiro(BigDecimal vlLiquidoDinheiro) {
		this.vlLiquidoDinheiro = vlLiquidoDinheiro;
	}

	public String getTextoMensagem() {
		return textoMensagem;
	}

	public void setTextoMensagem(String textoMensagem) {
		this.textoMensagem = textoMensagem;
	}

	public List<TbSaida> getSaidasSemFluxo() {
		return saidasSemFluxo;
	}

	public void setSaidasSemFluxo(List<TbSaida> saidasSemFluxo) {
		this.saidasSemFluxo = saidasSemFluxo;
	}

	public BigDecimal getVlTotalSaidaComFluxo() {
		return vlTotalSaidaComFluxo;
	}

	public void setVlTotalSaidaComFluxo(BigDecimal vlTotalSaidaComFluxo) {
		this.vlTotalSaidaComFluxo = vlTotalSaidaComFluxo;
	}

	public BigDecimal getVlTotalSaidaSemFluxo() {
		return vlTotalSaidaSemFluxo;
	}

	public void setVlTotalSaidaSemFluxo(BigDecimal vlTotalSaidaSemFluxo) {
		this.vlTotalSaidaSemFluxo = vlTotalSaidaSemFluxo;
	}

	public List<TbPagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(List<TbPagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	public BigDecimal getVlBrutoCartoes() {
		return vlBrutoCartoes;
	}

	public void setVlBrutoCartoes(BigDecimal vlBrutoCartoes) {
		this.vlBrutoCartoes = vlBrutoCartoes;
	}

	public Integer getIdResponsavel() {
		return idResponsavel;
	}

	public void setIdResponsavel(Integer idResponsavel) {
		this.idResponsavel = idResponsavel;
	}

	public Date getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(Date dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public TbFechamentoCaixa getCaixaEncontrado() {
		return caixaEncontrado;
	}

	public void setCaixaEncontrado(TbFechamentoCaixa caixaEncontrado) {
		this.caixaEncontrado = caixaEncontrado;
	}

	public BigDecimal getVlBrutoDinheiro() {
		return vlBrutoDinheiro;
	}

	public void setVlBrutoDinheiro(BigDecimal vlBrutoDinheiro) {
		this.vlBrutoDinheiro = vlBrutoDinheiro;
	}

	public BigDecimal getVlBrutoCheque() {
		return vlBrutoCheque;
	}

	public void setVlBrutoCheque(BigDecimal vlBrutoCheque) {
		this.vlBrutoCheque = vlBrutoCheque;
	}

	public BigDecimal getVlBrutoDebito() {
		return vlBrutoDebito;
	}

	public void setVlBrutoDebito(BigDecimal vlBrutoDebito) {
		this.vlBrutoDebito = vlBrutoDebito;
	}

	public BigDecimal getVlBrutoCredito() {
		return vlBrutoCredito;
	}

	public void setVlBrutoCredito(BigDecimal vlBrutoCredito) {
		this.vlBrutoCredito = vlBrutoCredito;
	}

	public String getNomeArquivoGerado() {
		return nomeArquivoGerado;
	}

	public void setNomeArquivoGerado(String nomeArquivoGerado) {
		this.nomeArquivoGerado = nomeArquivoGerado;
	}

}
