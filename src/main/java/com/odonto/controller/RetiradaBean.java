package com.odonto.controller;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.odonto.constants.Constants;
import com.odonto.dto.FechCaixaOUT;
import com.odonto.model.TbRetirada;
import com.odonto.security.SessionContext;
import com.odonto.service.RetiradaService;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class RetiradaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private RetiradaService retiradaService;

	private TbRetirada retirada;
	
	public void inicializar(ComponentSystemEvent ev) {
		if (FacesUtil.isNotPostback()) {
			retirada.setDtRetirada(new Date());
			FechCaixaOUT consolidado = (FechCaixaOUT) SessionContext.getInstance().getAttribute("fechamentoConsolidado");
			if (consolidado != null) {		
				retirada.setVlBruto(consolidado.getVlMovimentado());
				retirada.setVlDespesa(consolidado.getVlDespesa());
				retirada.setVlLiquido(consolidado.getVlLiquido());
				retirada.setVlCartao(consolidado.getVlCredito());
				retirada.setVlCheque(consolidado.getVlCheque());
				retirada.setVlDinheiro(consolidado.getVlDinheiro());
				retirada.setDtInicio(consolidado.getDtInicio());
				retirada.setDtFim(consolidado.getDtFim());
			}
		}
	}
	
	public RetiradaBean() {
		limpar();
	}
	
	private void limpar() {
		setRetirada(new TbRetirada());
	}
	
	public void salvar() {
		retirada = retiradaService.salvar(this.retirada);
		limpar();		
		FacesUtil.addInfoMessage(Constants.msgSucesso);
	}

	public TbRetirada getRetirada() {
		return retirada;
	}

	public void setRetirada(TbRetirada retirada) {
		this.retirada = retirada;
	}

	public boolean isEditando() {
		return this.retirada.getId() != null;
	}

}
