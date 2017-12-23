package com.odonto.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.odonto.BLL.RetiradaBLL;
import com.odonto.model.TbRetirada;
import com.odonto.util.jpa.Transactional;

public class RetiradaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private RetiradaBLL bll;
	
	@Transactional
	public TbRetirada salvar(TbRetirada item) {	
		return bll.guardar(item);
	}
	
}
