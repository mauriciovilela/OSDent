package com.odonto.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.odonto.BLL.UsuarioBLL;
import com.odonto.constants.Constants;
import com.odonto.dto.UsuarioOUT;
import com.odonto.model.TbUsuario;
import com.odonto.service.UsuarioService;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioService usuarioService;

	@Inject
	private UsuarioBLL usuarioBLL;
	
	@Inject
	private HomeMB homeMB;
	
	private TbUsuario usuario;

	private List<UsuarioOUT> usuarios;

	public UsuarioBean() {
		limpar();
	}
	
	public void inicializar(ComponentSystemEvent ev) {
		if (FacesUtil.isNotPostback()) {
			
		}
	}
	
	private void limpar() {
		usuario = new TbUsuario();
	}
	
	public void salvar() {
		
		this.usuario = usuarioService.salvar(this.usuario);
		
		homeMB.setUsuarios(usuarioBLL.listar(Constants.ATIVOS));
		homeMB.setDentistas(usuarioBLL.listarDentitas());
		homeMB.setSocios(usuarioBLL.listarSocios());
		
		limpar();		
		
		FacesUtil.addInfoMessage(Constants.msgSucesso);
	}
	
	public TbUsuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(TbUsuario Usuario) {
		this.usuario = Usuario;	
	}

	public boolean isEditando() {
		return this.usuario.getId() != null;
	}

	public List<UsuarioOUT> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioOUT> usuarios) {
		this.usuarios = usuarios;
	}

}
