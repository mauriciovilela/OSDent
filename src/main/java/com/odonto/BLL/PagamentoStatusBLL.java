package com.odonto.BLL;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.odonto.model.TbPagamentoStatus;

public class PagamentoStatusBLL implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public TbPagamentoStatus porId(Integer id) {
		return manager.find(TbPagamentoStatus.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<TbPagamentoStatus> listarAtivos() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPagamentoStatus.class);
		criteria.add(Restrictions.eq("flAtivo", true));
		return criteria.list();
	}
}
