package com.odonto.BLL;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.odonto.model.TbRetirada;
import com.odonto.security.SessionContext;
import com.odonto.service.NegocioException;
import com.odonto.util.jpa.Transactional;

public class RetiradaBLL implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public TbRetirada porId(Integer id) {
		return manager.find(TbRetirada.class, id);
	}

	public TbRetirada guardar(TbRetirada item) {
		item.setTbUsuario(SessionContext.getInstance().getUsuarioLogado());
		return manager.merge(item);
	}
	
	@Transactional
	public void remover(TbRetirada item) {
		try {
			item = porId(item.getId());
			manager.remove(item);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Registro não pode ser excluído.");
		}
	}

	@SuppressWarnings("unchecked")
	public List<TbRetirada> filtrados() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbRetirada.class);
//		if (StringUtils.isNotBlank(filtro.getDsDescricao())) {
//			criteria.add(Restrictions.ilike("dsDescricao", filtro.getDsDescricao(), MatchMode.ANYWHERE));
//		}
//		if (filtro.getFornecedor() != null) {
//			criteria.add(Restrictions.eq("tbFornecedor.id", filtro.getFornecedor().getId()));
//		}
		criteria.addOrder(Order.asc("dsDescricao"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<TbRetirada> porData(Date data) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbRetirada.class);
		criteria.add(Restrictions.between("dtCompra", data, data));
		criteria.addOrder(Order.asc("id"));
		return criteria.list();
	}
}
