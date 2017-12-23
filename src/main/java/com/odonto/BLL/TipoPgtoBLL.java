package com.odonto.BLL;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.odonto.model.TbTipoPgto;

public class TipoPgtoBLL implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public TbTipoPgto porId(Integer id) {
		return manager.find(TbTipoPgto.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<TbTipoPgto> listar() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbTipoPgto.class);
		return criteria.list();
	}
}
