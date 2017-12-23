package com.odonto.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.odonto.BLL.RetiradaBLL;
import com.odonto.model.TbRetirada;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class RetiradaBeanPesquisa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private RetiradaBLL BLL;

	private String texto;
	private List<TbRetirada> filtrados;
	
	private TbRetirada selecionado;
	
    @PostConstruct
	private void pageLoad() {
		if (FacesUtil.isNotPostback()) {
			filtrados = BLL.filtrados();
		}
	}
	
	public RetiradaBeanPesquisa() {
		texto = StringUtils.EMPTY;
	}
	
	public void excluir() {
		BLL.remover(selecionado);
		filtrados.remove(selecionado);		
		FacesUtil.addInfoMessage("Registro excluído com sucesso.");
	}
	
	public void pesquisar() {
		filtrados = BLL.filtrados();
	}
	
	public List<TbRetirada> getFiltrados() {
		return filtrados;
	}

	public void setFiltrados(List<TbRetirada> filtrados) {
		this.filtrados = filtrados;
	}

	public TbRetirada getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(TbRetirada selecionado) {
		this.selecionado = selecionado;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
}