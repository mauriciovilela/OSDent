package com.odonto.BLL;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.odonto.dto.ContaIN;
import com.odonto.model.TbConta;
import com.odonto.security.SessionContext;
import com.odonto.service.NegocioException;
import com.odonto.util.jpa.Transactional;

public class ContaBLL implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public TbConta porId(Integer id) {
		return manager.find(TbConta.class, id);
	}

	public TbConta guardar(TbConta item) {
		item.setTbUsuario(SessionContext.getInstance().getUsuarioLogado());
		return manager.merge(item);
	}
	
	@Transactional
	public void remover(TbConta item) {
		try {
			item = porId(item.getId());
			manager.remove(item);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Registro não pode ser excluído.");
		}
	}

	@SuppressWarnings("unchecked")
	public List<TbConta> filtrados(ContaIN filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbConta.class);
		if (StringUtils.isNotBlank(filtro.getDescricao())) {
			criteria.add(Restrictions.ilike("dsDescricao", filtro.getDescricao(), MatchMode.ANYWHERE));
		}
		if (filtro.getData() != null) {
			criteria.add(Restrictions.eq("dtVencimento", filtro.getData()));
		}
		criteria.addOrder(Order.asc("dsDescricao"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<TbConta> listar() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbConta.class);
		criteria.addOrder(Order.asc("dsDescricao"));
		return criteria.list();
	}
	
}
