package com.odonto.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.odonto.constants.Constants;
import com.odonto.model.TbConta;
import com.odonto.security.SessionContext;
import com.odonto.service.ContaService;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ContaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ContaService contaService;

	private TbConta conta;
	
	private List<TbConta> contas;
	private Boolean panfleto;

//	@Inject
//	private ContaBLL contaBLL;
	
//	@Inject
//	private HomeMB homeMB;
	
	public ContaBean() {
		limpar();
	}
	
	public void inicializar(ComponentSystemEvent ev) {
		if (FacesUtil.isNotPostback()) {
			
		}
	}
	
	private void limpar() {
		conta = new TbConta();
	}
	
	public void salvar() {
		this.conta.setTbUsuario(SessionContext.getInstance().getUsuarioLogado());
		this.conta = contaService.salvar(this.conta);
		limpar();		
		FacesUtil.addInfoMessage(Constants.msgSucesso);
	}

	public TbConta getConta() {
		return conta;
	}
	
	public void setConta(TbConta Conta) {
		this.conta = Conta;	
	}

	public boolean isEditando() {
		return this.conta.getId() != null;
	}

	public List<TbConta> getContas() {
		return contas;
	}

	public void setContas(List<TbConta> contas) {
		this.contas = contas;
	}

	public Boolean getPanfleto() {
		return panfleto;
	}

	public void setPanfleto(Boolean panfleto) {
		this.panfleto = panfleto;
	}

}
