package com.odonto.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.odonto.BLL.PagamentoBLL;
import com.odonto.model.TbAuditoria;
import com.odonto.model.TbPagamento;
import com.odonto.security.SessionContext;
import com.odonto.service.AuditoriaService;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PagamentoBeanPesquisa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PagamentoBLL pagamentoBLL;
	
	@Inject
	private AuditoriaService auditoria;

	private String texto;
	private List<TbPagamento> filtrados;
	private Date data;
	
	private TbPagamento selecionado;
	
    @PostConstruct
	private void pageLoad() {
		if (FacesUtil.isNotPostback()) {
		}
	}
	
	public PagamentoBeanPesquisa() {
		texto = StringUtils.EMPTY;
		data = new Date();
	}
	
	public void excluir() {
		pagamentoBLL.remover(selecionado);
		filtrados.remove(selecionado);	
		
		// Grava log de auditoria
		TbAuditoria audit = new TbAuditoria();
		audit.setDsDescricao("Pagamento excluído. Usuario: " + 
				SessionContext.getInstance().getUsuarioLogado().getDsNome() + 
				", Valor total " + selecionado.getVlTotal() + 
				", Valor pago " + selecionado.getVlPago() + 
				", Data " + selecionado.getDtEntrada() + 
				", Paciente " + selecionado.getTbPaciente().getDsNome() + 
				", Tipo " + selecionado.getTbTipoPgto().getDsNome());
		auditoria.salvar(audit);

		FacesUtil.addInfoMessage("Registro excluído com sucesso.");
	}
	
	public void pesquisar() {
		filtrados = pagamentoBLL.porData(data);
	}
	
	public List<TbPagamento> getFiltrados() {
		return filtrados;
	}

	public void setFiltrados(List<TbPagamento> filtrados) {
		this.filtrados = filtrados;
	}

	public TbPagamento getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(TbPagamento selecionado) {
		this.selecionado = selecionado;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}


	
}