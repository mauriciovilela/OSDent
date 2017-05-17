package com.odonto.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.odonto.BLL.PagamentoBLL;
import com.odonto.model.TbPagamento;
import com.odonto.util.jpa.Transactional;

public class PagamentoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PagamentoBLL bll;

	@Transactional
	public TbPagamento salvar(TbPagamento item) {
		item.setDtInclusao(new Date());
		TbPagamento pagamento = bll.guardar(item);
		return pagamento;
	}
	
}
