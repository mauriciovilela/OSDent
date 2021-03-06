package com.odonto.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.odonto.BLL.SaidaBLL;
import com.odonto.model.TbSaida;
import com.odonto.util.jpa.Transactional;

public class SaidaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private SaidaBLL bll;
	
	@Transactional
	public TbSaida salvar(TbSaida item) {	
		item.setDtInclusao(new Date());
		return bll.guardar(item);
	}
	
}
