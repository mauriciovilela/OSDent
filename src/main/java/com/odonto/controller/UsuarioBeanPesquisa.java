package com.odonto.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.odonto.BLL.UsuarioBLL;
import com.odonto.constants.Constants;
import com.odonto.dto.UsuarioIN;
import com.odonto.dto.UsuarioOUT;
import com.odonto.model.TbUsuario;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class UsuarioBeanPesquisa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioBLL usuarioBLL;
	
	private UsuarioIN filtro;
	private List<UsuarioOUT> filtrados;
	
	private TbUsuario selecionado;

	@PostConstruct
	private void pageLoad() {
		pesquisar();
	}

	public void excluir() {
		filtrados.remove(selecionado);		
		FacesUtil.addInfoMessage("Registro excluído com sucesso.");
	}
	
	public void pesquisar() {
		filtrados = usuarioBLL.listar(Constants.TODOS);
	}

	public UsuarioIN getFiltro() {
		return filtro;
	}

	public List<UsuarioOUT> getFiltrados() {
		return filtrados;
	}

	public void setFiltrados(List<UsuarioOUT> filtrados) {
		this.filtrados = filtrados;
	}

	public TbUsuario getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(TbUsuario selecionado) {
		this.selecionado = selecionado;
	}

	public void setFiltro(UsuarioIN filtro) {
		this.filtro = filtro;
	}

	
}