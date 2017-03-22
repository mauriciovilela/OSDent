package com.odonto.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.primefaces.context.RequestContext;

import com.odonto.BLL.AgendaBLL;
import com.odonto.BLL.FechamentoCaixaBLL;
import com.odonto.BLL.PagamentoBLL;
import com.odonto.BLL.ProcedimentoBLL;
import com.odonto.BLL.UsuarioBLL;
import com.odonto.constants.Constants;
import com.odonto.dto.AgendaOUT;
import com.odonto.model.TbAgenda;
import com.odonto.model.TbFechamentoCaixa;
import com.odonto.model.TbPagamento;
import com.odonto.security.SessionContext;
import com.odonto.service.NegocioException;
import com.odonto.service.PagamentoService;
import com.odonto.util.Util;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PagamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PagamentoService pagamentoService;

	@Inject
	private PagamentoBLL bllPagamento;

	@Inject
	private FechamentoCaixaBLL fechamentoCaixaBLL;
	
	@Inject
	private AgendaBLL bllAgenda;

	@Inject
	private UsuarioBLL usuarioBLL;

	@Inject
	private ProcedimentoBLL procedimentoBLL;
	
	private TbPagamento pagamento;
	private TbAgenda agenda;
	private Integer idMaquina;

	@NotNull
	private Integer idResponsavel;

	@NotNull
	private Integer idDentista;
	private Integer idProcedimento;

	private boolean tipoPgtoMaquina;
	private boolean tipoCredito;
	private boolean pgtoObrigatorio;

	private List<TbPagamento> filtradosPgto;
	private List<AgendaOUT> agendaHoje;

	public PagamentoBean() {
		limpar();
	}

	public void inicializar(ComponentSystemEvent ev) {
		if (FacesUtil.isNotPostback()) {
			if (agenda != null) {
				pagamento.setTbPaciente(agenda.getTbPaciente());
				idProcedimento = agenda.getTbProcedimento().getId();
				idDentista = agenda.getTbDentista().getId();
			}
			idResponsavel = SessionContext.getInstance().getUsuarioLogado().getId();
		}
	}

	public void carregaPagamentos() {
		if (pagamento.getTbPaciente() == null)
			throw new NegocioException("Selecione o paciente");
		modalHistoricoPagamento(pagamento.getTbPaciente().getId());
	}

	public void carregaPacientesHoje() {
		agendaHoje = bllAgenda.listarPorDia(new Date());
	}

	public void selecionarPacientes(Integer idAgenda) {
		TbAgenda agenda = bllAgenda.porId(idAgenda);
		pagamento.setTbPaciente(agenda.getTbPaciente());
		idDentista = agenda.getTbDentista().getId();
		RequestContext.getCurrentInstance().execute("PF('dlgPacientesHoje').hide();");
	}

	private void modalHistoricoPagamento(Integer idPaciente) {
		SessionContext.getInstance().setID(idPaciente);
		Util.abreModal("/restrita/pagamento/PagamentoHistoricoMD");
	}

	public void atualizaTotal() {
		if (pagamento.getQtParcelas() > 0 && pagamento.getVlParcela() != null) {
			pagamento.setVlTotal(pagamento.getVlParcela().multiply(new BigDecimal(pagamento.getQtParcelas())));
		}
		pagamento.setVlPago(pagamento.getVlTotal());
	}

	private void limpar() {
		setPagamento(new TbPagamento());
		pagamento.setQtParcelas(1);
		tipoCredito = false;
		pgtoObrigatorio = false;
		idMaquina = null;
		pagamento.setDtEntrada(new Date());
	}

	public void validarSalvar() {

		// Regra pagamento duplicado
		Integer idPaciente = pagamento.getTbPaciente().getId();
		Integer idTipoPgto = pagamento.getTbTipoPgto().getId();
		
		List<TbPagamento> lista = bllPagamento.porPacienteDataValorPgtoDentista(idPaciente, pagamento.getDtEntrada(),
				pagamento.getVlTotal(), idTipoPgto, idDentista);
		if (lista.size() > 0) {
			throw new NegocioException(
					"Já existe um pagamento deste valor para este paciente e dentista nesta data.");
		}

		// Regra lançamento em fluxo de caixa fechado 
		TbFechamentoCaixa caixa = fechamentoCaixaBLL.listarPorData(pagamento.getDtEntrada());
		if (caixa != null) {
			throw new NegocioException("Não é permitido lançar despesas em fluxo de caixa já fechado.");
		}

		// Regra de debito e credito
		if (tipoPgtoMaquina && idMaquina == null) {
			throw new NegocioException("Máquina Cartão deve ser informado.");
		}
		if (tipoCredito && (pagamento.getQtParcelas() == 0 || pagamento.getVlParcela() == null)) {
			throw new NegocioException("Parcelas e Valor parcela devem ser informados.");
		}

		// Inclui o pagamento
		salvar();
	}

	public void salvar() {
		if (!pgtoObrigatorio) {
			pagamento.setVlTotal(new BigDecimal(0));
			pagamento.setVlPago(new BigDecimal(0));
		}
		if (tipoPgtoMaquina) {
			if (idMaquina == null) {
				throw new NegocioException("Campo [Máquina Cartão] obrigatório quando crédito selecionado.");
			}
			pagamento.setTbMaquina(usuarioBLL.porId(idMaquina));
		}
		if (!this.pagamento.getVlTotal().equals(this.pagamento.getVlPago())) {
			pagamento.getTbPagamentoStatus().setId(Constants.TbStatusPgto.pendente);
		}
		pagamento.setTbProcedimento(procedimentoBLL.porId(idProcedimento));
		pagamento.setTbResponsavel(usuarioBLL.porId(idResponsavel));
		pagamento.setTbDentista(usuarioBLL.porId(idDentista));
		pagamento = pagamentoService.salvar(this.pagamento);
		limpar();
		FacesUtil.addInfoMessage(Constants.msgSucesso);
		filtradosPgto = new ArrayList<TbPagamento>();
	}

	public void verificaTipoPgto() {
		Integer idPgto = this.pagamento.getTbTipoPgto().getId();
		tipoPgtoMaquina = idPgto.equals(Constants.TbTipoPgto.credito) || idPgto.equals(Constants.TbTipoPgto.debito);
		tipoCredito = idPgto.equals(Constants.TbTipoPgto.credito);
	}

	public void verificaStatusPgto() {
		pgtoObrigatorio = pagamento.getTbPagamentoStatus().isFlPagar();
	}

	public TbPagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(TbPagamento pagamento) {
		this.pagamento = pagamento;
	}

	public boolean isTipoPgtoMaquina() {
		return tipoPgtoMaquina;
	}

	public void setTipoPgtoMaquina(boolean tipoPgtoMaquina) {
		this.tipoPgtoMaquina = tipoPgtoMaquina;
	}

	public List<TbPagamento> getFiltradosPgto() {
		return filtradosPgto;
	}

	public void setFiltradosPgto(List<TbPagamento> filtradosPgto) {
		this.filtradosPgto = filtradosPgto;
	}

	public Integer getIdResponsavel() {
		return idResponsavel;
	}

	public void setIdResponsavel(Integer idResponsavel) {
		this.idResponsavel = idResponsavel;
	}

	public TbAgenda getAgenda() {
		return agenda;
	}

	public void setAgenda(TbAgenda agenda) {
		this.agenda = agenda;
	}

	public List<AgendaOUT> getAgendaHoje() {
		return agendaHoje;
	}

	public void setAgendaHoje(List<AgendaOUT> agendaHoje) {
		this.agendaHoje = agendaHoje;
	}

	public Integer getIdDentista() {
		return idDentista;
	}

	public void setIdDentista(Integer idDentista) {
		this.idDentista = idDentista;
	}

	public boolean isTipoCredito() {
		return tipoCredito;
	}

	public void setTipoCredito(boolean tipoCredito) {
		this.tipoCredito = tipoCredito;
	}

	public boolean isPgtoObrigatorio() {
		return pgtoObrigatorio;
	}

	public void setPgtoObrigatorio(boolean pgtoObrigatorio) {
		this.pgtoObrigatorio = pgtoObrigatorio;
	}

	public Integer getIdMaquina() {
		return idMaquina;
	}

	public void setIdMaquina(Integer idMaquina) {
		this.idMaquina = idMaquina;
	}

	public Integer getIdProcedimento() {
		return idProcedimento;
	}

	public void setIdProcedimento(Integer idProcedimento) {
		this.idProcedimento = idProcedimento;
	}

}
