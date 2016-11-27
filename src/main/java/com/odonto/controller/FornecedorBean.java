package com.odonto.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.odonto.BLL.FornecedorBLL;
import com.odonto.constants.Constants;
import com.odonto.model.TbFornecedor;
import com.odonto.service.FornecedorService;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class FornecedorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FornecedorService fornecedorService;

	private TbFornecedor fornecedor;
	
	private String aniversario;
	
	private List<TbFornecedor> fornecedores;
	private Boolean panfleto;

	@Inject
	private FornecedorBLL fornecedorBLL;

	@Inject
	private HomeMB homeMB;
	
	public FornecedorBean() {
		limpar();
	}
	
	public void inicializar(ComponentSystemEvent ev) {
		if (FacesUtil.isNotPostback()) {
			
		}
	}
	
	private void limpar() {
		fornecedor = new TbFornecedor();
		aniversario = StringUtils.EMPTY;
	}
	
	public void salvar() {
		if (this.fornecedor.getTbFornecedorPai() == null) {
			FacesUtil.addErrorMessage("Campo Categoria obrigatório");
		}
		else {
			this.fornecedor = fornecedorService.salvar(this.fornecedor);
			limpar();		
			FacesUtil.addInfoMessage(Constants.msgSucesso);
			// Atualiza os dados na sessao
			homeMB.setFornecedores(fornecedorBLL.listar());			
		}
	}

	public TbFornecedor getFornecedor() {
		return fornecedor;
	}
	
	public void setFornecedor(TbFornecedor Fornecedor) {
		this.fornecedor = Fornecedor;	
	}

	public boolean isEditando() {
		return this.fornecedor.getId() != null;
	}

	public String getAniversario() {
		return aniversario;
	}

	public void setAniversario(String aniversario) {
		this.aniversario = aniversario;
	}

	public List<TbFornecedor> getFornecedors() {
		return fornecedores;
	}

	public void setFornecedors(List<TbFornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public Boolean getPanfleto() {
		return panfleto;
	}

	public void setPanfleto(Boolean panfleto) {
		this.panfleto = panfleto;
	}

}
