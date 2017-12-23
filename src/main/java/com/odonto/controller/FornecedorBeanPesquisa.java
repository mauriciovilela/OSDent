package com.odonto.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.odonto.BLL.FornecedorBLL;
import com.odonto.dto.FornecedorIN;
import com.odonto.model.TbFornecedor;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class FornecedorBeanPesquisa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private FornecedorBLL fornecedorBLL;
	
	private FornecedorIN filtro;
	private List<TbFornecedor> filtrados;
	
	private TbFornecedor selecionado;
	
	public FornecedorBeanPesquisa() {
		filtro = new FornecedorIN();
	}
	
	public void excluir() {
		fornecedorBLL.remover(selecionado);
		filtrados.remove(selecionado);		
		FacesUtil.addInfoMessage("Registro excluído com sucesso.");
	}
	
	public void pesquisar() {
		filtrados = fornecedorBLL.filtrados(filtro);
	}

	public FornecedorIN getFiltro() {
		return filtro;
	}

	public List<TbFornecedor> getFiltrados() {
		return filtrados;
	}

	public void setFiltrados(List<TbFornecedor> filtrados) {
		this.filtrados = filtrados;
	}

	public TbFornecedor getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(TbFornecedor selecionado) {
		this.selecionado = selecionado;
	}

	public void setFiltro(FornecedorIN filtro) {
		this.filtro = filtro;
	}

	
}