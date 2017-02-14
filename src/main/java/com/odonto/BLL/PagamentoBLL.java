package com.odonto.BLL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.odonto.constants.Constants;
import com.odonto.model.TbPagamento;
import com.odonto.security.SessionContext;
import com.odonto.service.NegocioException;
import com.odonto.util.jpa.Transactional;

public class PagamentoBLL implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public TbPagamento porId(Integer id) {
		return manager.find(TbPagamento.class, id);
	}

	public TbPagamento guardar(TbPagamento item) {
		item.setTbUsuario(SessionContext.getInstance().getUsuarioLogado());
		return manager.merge(item);			
	}

	@SuppressWarnings("unchecked")
	public List<TbPagamento> porPaciente(Integer idPaciente) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPagamento.class);
		criteria.add(Restrictions.eq("tbPaciente.id", idPaciente));
		criteria.addOrder(Order.desc("id"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<TbPagamento> porPacienteDataValorPgtoDentista(
			Integer idPaciente, Date data, BigDecimal valorTotal, Integer idTipoPgto, Integer idDentista) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPagamento.class);
		criteria.add(Restrictions.eq("tbPaciente.id", idPaciente));
		criteria.add(Restrictions.eq("tbTipoPgto.id", idTipoPgto));
		criteria.add(Restrictions.eq("tbDentista.id", idDentista));
		criteria.add(Restrictions.eq("dtEntrada", data));
		criteria.add(Restrictions.eq("vlTotal", valorTotal));
		criteria.addOrder(Order.desc("id"));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TbPagamento> porData(Date data) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPagamento.class);
		criteria.add(Restrictions.between("dtEntrada", data, data));
		criteria.addOrder(Order.asc("id"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<TbPagamento> porDataExportacao(Date data) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPagamento.class);
		criteria.add(Restrictions.gt("dtInclusao", data));
//		criteria.addOrder(Order.asc("id"));
		return criteria.list();
	}
	@SuppressWarnings("unchecked")
	public List<TbPagamento> inadimplentes() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPagamento.class);
		criteria.add(Restrictions.eq("tbPagamentoStatus.id", Constants.TbStatusPgto.pendente));
		criteria.addOrder(Order.asc("dtEntrada"));
		return criteria.list();
	}
	
	@Transactional
	public void remover(TbPagamento item) {
		try {
			item = porId(item.getId());
			manager.remove(item);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Registro não pode ser excluído.");
		}
	}
}
