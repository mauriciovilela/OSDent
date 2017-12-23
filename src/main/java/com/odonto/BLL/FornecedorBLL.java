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

import com.odonto.dto.FornecedorIN;
import com.odonto.model.TbFornecedor;
import com.odonto.security.SessionContext;
import com.odonto.service.NegocioException;
import com.odonto.util.jpa.Transactional;

public class FornecedorBLL implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public TbFornecedor porId(Integer id) {
		return manager.find(TbFornecedor.class, id);
	}

	public TbFornecedor guardar(TbFornecedor item) {
		item.setTbUsuario(SessionContext.getInstance().getUsuarioLogado());
		return manager.merge(item);
	}
	
	@Transactional
	public void remover(TbFornecedor item) {
		try {
			item = porId(item.getId());
			manager.remove(item);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Registro não pode ser excluído.");
		}
	}

	@SuppressWarnings("unchecked")
	public List<TbFornecedor> filtrados(FornecedorIN filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbFornecedor.class);
		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("dsNome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		criteria.add(Restrictions.isNotNull("tbFornecedorPai"));
		criteria.addOrder(Order.asc("dsNome"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<TbFornecedor> listar() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbFornecedor.class);
		criteria.add(Restrictions.isNotNull("tbFornecedorPai"));
		criteria.addOrder(Order.asc("dsNome"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<TbFornecedor> listarCategorias() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbFornecedor.class);
		criteria.add(Restrictions.isNull("tbFornecedorPai"));
		criteria.addOrder(Order.asc("dsNome"));
		return criteria.list();
	}
}
