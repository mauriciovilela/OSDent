package com.odonto.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.odonto.BLL.PagamentoBLL;
import com.odonto.model.TbPagamento;
import com.odonto.security.SessionContext;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PagamentoHistoricoMDBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PagamentoBLL bll;
	
	private List<TbPagamento> historico;

	public void inicializar(ComponentSystemEvent ev) {
		if (FacesUtil.isNotPostback()) {
			Integer id = SessionContext.getInstance().getID();
			historico = bll.porPaciente(id);
		}
	}

	public List<TbPagamento> getHistorico() {
		return historico;
	}

	public void setHistorico(List<TbPagamento> historico) {
		this.historico = historico;
	}
	
}

