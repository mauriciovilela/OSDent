package com.odonto.BLL;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.odonto.model.TbTipoProcedimento;

public class TipoProcedimentoBLL implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public TbTipoProcedimento porId(Integer id) {
		return manager.find(TbTipoProcedimento.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<TbTipoProcedimento> listar() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbTipoProcedimento.class);
		return criteria.list();
	}
}
