package com.odonto.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.odonto.BLL.UsuarioBLL;
import com.odonto.constants.Constants;
import com.odonto.dto.UsuarioOUT;
import com.odonto.model.TbMensagem;
import com.odonto.security.SessionContext;
import com.odonto.service.MensagemService;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class MensagemBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MensagemService mensagemService;
	
	@Inject
	private UsuarioBLL usuarioBLL;

	private TbMensagem mensagem;
	
	@NotBlank
	private String texto;
	
	@NotNull
	private Integer idUsuario;

	private List<UsuarioOUT> usuarios;

	public MensagemBean() {
		limpar();
	}
	
	public void inicializar(ComponentSystemEvent ev) {
		if (FacesUtil.isNotPostback()) {
			mensagem = new TbMensagem();
	        // Usuario
			setUsuarios(usuarioBLL.listar(Constants.ATIVOS));
		}
	}
	
	private void limpar() {
		mensagem = new TbMensagem();
	}
	
	public void salvar() {
		mensagem.setTbUsuario(usuarioBLL.porId(idUsuario));
		mensagem.setFlConcluido(false);
		mensagem.setFlMensagemFluxo(false);
		mensagem.setTbSolicitante(SessionContext.getInstance().getUsuarioLogado());
		mensagem.setDsDescricao(texto);
		mensagemService.salvar(mensagem);
		limpar();		
		FacesUtil.addInfoMessage(Constants.msgSucesso);
	}

	public List<UsuarioOUT> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioOUT> usuarios) {
		this.usuarios = usuarios;
	}

	public TbMensagem getMensagem() {
		return mensagem;
	}

	public void setMensagem(TbMensagem mensagem) {
		this.mensagem = mensagem;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
