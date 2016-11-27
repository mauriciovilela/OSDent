package com.odonto.BLL;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.odonto.dto.PacienteIN;
import com.odonto.model.TbPaciente;
import com.odonto.security.SessionContext;
import com.odonto.service.NegocioException;
import com.odonto.util.jpa.Transactional;

public class PacienteBLL implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public TbPaciente porId(Integer id) {
		return manager.find(TbPaciente.class, id);
	}

	public TbPaciente guardar(TbPaciente item) {
		try {
			item.setTbUsuario(SessionContext.getInstance().getUsuarioLogado());
			if (item.getId() == null)
				item.setDtCadastro(new Date());
			return manager.merge(item);			
		} catch (PersistenceException e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				throw new NegocioException("Paciente já está cadastrado.");
			}
			throw new NegocioException("Erro na manutenção.");
		}
	}
	
	@Transactional
	public void remover(TbPaciente item) {
		try {
			item = porId(item.getId());
			manager.remove(item);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Registro não pode ser excluído.");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TbPaciente> filtrados(PacienteIN filtro) {
		Criteria criteria = filtradosCriteria(filtro);
		criteria.addOrder(Order.asc("dsNome"));
		criteria.setFirstResult(filtro.getFirst());
		criteria.setMaxResults(filtro.getPageSize());
		return criteria.list();
	}
	
	public Integer filtradosQTD(PacienteIN filtro) {
		Criteria criteria = filtradosCriteria(filtro);
		criteria.setProjection(Projections.rowCount());
		return ((Number) criteria.uniqueResult()).intValue();
	}	
	
	private Criteria filtradosCriteria(PacienteIN filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPaciente.class);
		if (StringUtils.isNotBlank(filtro.getFone())) {
			criteria.add(Restrictions.ilike("dsFone", filtro.getFone(), MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("dsNome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		return criteria;
	}

	@SuppressWarnings("unchecked")
	public List<TbPaciente> porNome(String nome) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPaciente.class);
		criteria.add(Restrictions.ilike("dsNome", nome, MatchMode.ANYWHERE));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<TbPaciente> listarAniversariantes() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPaciente.class);
		criteria.add(Restrictions.isNotNull("dtNascimento"));
		criteria.addOrder(Order.asc("dsNome"));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TbPaciente> listar() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPaciente.class);
		criteria.addOrder(Order.asc("dsNome"));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TbPaciente> listarPorNome(String query) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPaciente.class);
		criteria.add(Restrictions.ilike("dsNome", query, MatchMode.START));
		criteria.setMaxResults(10);
		return criteria.list();
	}	
	
}
