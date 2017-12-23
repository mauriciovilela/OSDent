package com.odonto.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.odonto.BLL.UsuarioBLL;
import com.odonto.model.TbUsuario;
import com.odonto.util.jpa.Transactional;

public class UsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioBLL usuarios;
	
	@Transactional
	public TbUsuario salvar(TbUsuario item) {
		item.setDtInclusao(new Date());
		return usuarios.guardar(item);
	}
	
}
