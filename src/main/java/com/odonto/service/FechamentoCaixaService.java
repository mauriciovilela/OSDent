package com.odonto.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.odonto.BLL.FechamentoCaixaBLL;
import com.odonto.model.TbFechamentoCaixa;
import com.odonto.util.jpa.Transactional;

public class FechamentoCaixaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FechamentoCaixaBLL bll;
	
	@Transactional
	public TbFechamentoCaixa salvar(TbFechamentoCaixa item) {
		return bll.guardar(item);
	}
	
}
