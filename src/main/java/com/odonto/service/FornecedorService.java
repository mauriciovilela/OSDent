package com.odonto.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.odonto.BLL.FornecedorBLL;
import com.odonto.model.TbFornecedor;
import com.odonto.util.jpa.Transactional;

public class FornecedorService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FornecedorBLL bll;
	
	@Transactional
	public TbFornecedor salvar(TbFornecedor item) {	
		return bll.guardar(item);
	}
	
}
