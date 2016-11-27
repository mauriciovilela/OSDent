package com.odonto.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.odonto.BLL.ContaBLL;
import com.odonto.model.TbConta;
import com.odonto.util.jpa.Transactional;

public class ContaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ContaBLL bll;
	
	@Transactional
	public TbConta salvar(TbConta item) {	
		return bll.guardar(item);
	}
	
}
