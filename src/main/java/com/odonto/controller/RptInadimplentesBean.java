package com.odonto.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.odonto.BLL.PagamentoBLL;
import com.odonto.constants.Constants;
import com.odonto.model.TbPagamento;
import com.odonto.service.PagamentoService;

@Named
@ViewScoped
public class RptInadimplentesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PagamentoBLL pagamentoBLL;

	@Inject
	private PagamentoService pagamentoService;
	
	private List<TbPagamento> filtrados;
	
	private TbPagamento selecionado;

	@PostConstruct
	private void pageLoad() {
		setFiltrados(pagamentoBLL.inadimplentes());
	}
	
	public void confirmar() {
		selecionado.getTbPagamentoStatus().setId(Constants.TbStatusPgto.pago);
		pagamentoService.salvar(selecionado);
		pageLoad();
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

}