package com.odonto.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.odonto.BLL.SaidaBLL;
import com.odonto.dto.SaidaIN;
import com.odonto.model.TbAuditoria;
import com.odonto.model.TbFornecedor;
import com.odonto.model.TbSaida;
import com.odonto.security.SessionContext;
import com.odonto.service.AuditoriaService;
import com.odonto.service.NegocioException;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class SaidaBeanPesquisa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private SaidaBLL saidaBLL;
	
	@Inject
	private AuditoriaService auditoria;

	private String texto;
	private List<TbSaida> filtrados;
	private Date dataDespesaInicio;
	private Date dataDespesaFim;
	private Integer idTipoDespesa;
	
	private TbSaida selecionado;
	
	private TbFornecedor fornecedor;
	private Boolean despesasHoje;

    @PostConstruct
	private void pageLoad() {
		if (FacesUtil.isNotPostback()) {
			fornecedor = new TbFornecedor();
			despesasHoje = true;
			dataDespesaInicio = new Date();
			dataDespesaFim = new Date();		
		}
	}
	
	public SaidaBeanPesquisa() {
		texto = StringUtils.EMPTY;
	}
	
	public void verifica() {
		if (despesasHoje) {
			dataDespesaInicio = new Date();
			dataDespesaFim = new Date();			
		}
		else {
			dataDespesaInicio = null;
			dataDespesaFim = null;			
		}
	}
	
	public void excluir() {
		saidaBLL.remover(selecionado);
		filtrados.remove(selecionado);	
		
		// Grava log de auditoria
		TbAuditoria audit = new TbAuditoria();
		audit.setDsDescricao("Despesa excluída. Usuario: " + 
				SessionContext.getInstance().getUsuarioLogado().getDsNome() + 
				", Valor total " + selecionado.getVlTotal() + 
				", Valor pago " + selecionado.getVlPago() + 
				", Tipo " +selecionado.getTbFornecedor().getDsNome());
		auditoria.salvar(audit);
		
		FacesUtil.addInfoMessage("Registro excluído com sucesso.");
	}
	
	public void pesquisar() {
		if (dataDespesaInicio == null && dataDespesaFim == null && idTipoDespesa == null) {
			throw new NegocioException("Selecione ao menos um filtro");
		}
		SaidaIN filtro = new SaidaIN();
		filtro.setDtCompraInicio(dataDespesaInicio);
		filtro.setDtCompraFim(dataDespesaFim);
		filtro.setIdTipoDespesa(idTipoDespesa);
		filtrados = saidaBLL.filtrados(filtro);
	}
	
	public List<TbSaida> getFiltrados() {
		return filtrados;
	}

	public void setFiltrados(List<TbSaida> filtrados) {
		this.filtrados = filtrados;
	}

	public TbSaida getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(TbSaida selecionado) {
		this.selecionado = selecionado;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public TbFornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(TbFornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Integer getIdTipoDespesa() {
		return idTipoDespesa;
	}

	public void setIdTipoDespesa(Integer idTipoDespesa) {
		this.idTipoDespesa = idTipoDespesa;
	}

	public Date getDataDespesaInicio() {
		return dataDespesaInicio;
	}

	public void setDataDespesaInicio(Date dataDespesaInicio) {
		this.dataDespesaInicio = dataDespesaInicio;
	}

	public Date getDataDespesaFim() {
		return dataDespesaFim;
	}

	public void setDataDespesaFim(Date dataDespesaFim) {
		this.dataDespesaFim = dataDespesaFim;
	}

	public Boolean getDespesasHoje() {
		return despesasHoje;
	}

	public void setDespesasHoje(Boolean despesasHoje) {
		this.despesasHoje = despesasHoje;
	}

	
}