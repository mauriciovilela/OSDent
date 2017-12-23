package com.odonto.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.odonto.BLL.ContaBLL;
import com.odonto.dto.ContaIN;
import com.odonto.model.TbConta;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ContaBeanPesquisa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ContaBLL contaBLL;
	
	private ContaIN filtro;
	private List<TbConta> filtrados;
	
	private TbConta selecionado;
	
	public ContaBeanPesquisa() {
		filtro = new ContaIN();
	}

	@PostConstruct
	private void pageLoad() {
		filtrados = contaBLL.filtrados(new ContaIN());
	}
	
	public void excluir() {
		contaBLL.remover(selecionado);
		filtrados.remove(selecionado);		
		FacesUtil.addInfoMessage("Registro excluído com sucesso.");
	}
	
	public void pesquisar() {
		filtrados = contaBLL.filtrados(filtro);
	}

	public ContaIN getFiltro() {
		return filtro;
	}

	public List<TbConta> getFiltrados() {
		return filtrados;
	}

	public void setFiltrados(List<TbConta> filtrados) {
		this.filtrados = filtrados;
	}

	public TbConta getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(TbConta selecionado) {
		this.selecionado = selecionado;
	}

	public void setFiltro(ContaIN filtro) {
		this.filtro = filtro;
	}

	
}