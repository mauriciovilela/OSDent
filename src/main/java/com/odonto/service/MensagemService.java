package com.odonto.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.odonto.BLL.MensagemBLL;
import com.odonto.model.TbMensagem;
import com.odonto.util.jpa.Transactional;

public class MensagemService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MensagemBLL bll;
	
	@Transactional
	public TbMensagem salvar(TbMensagem item) {
		if (item.getDtInclusao() == null)
			item.setDtInclusao(new Date());
		return bll.guardar(item);
	}
	
}
