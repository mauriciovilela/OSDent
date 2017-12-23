package com.odonto.BLL;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.odonto.constants.Constants;
import com.odonto.dto.FiltroIN;
import com.odonto.model.TbMensagem;
import com.odonto.security.SessionContext;

public class MensagemBLL implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public TbMensagem porId(Integer id) {
		return manager.find(TbMensagem.class, id);
	}

	public TbMensagem guardar(TbMensagem item) {
		return manager.merge(item);
	}
	
	@SuppressWarnings("unchecked")
	public List<TbMensagem> listarNaoLidasPorUsuario() {
		Integer idUsuario = SessionContext.getInstance().getUsuarioLogado().getId();
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbMensagem.class);
		criteria.add(Restrictions.eq("tbUsuario.id", idUsuario));
		criteria.add(Restrictions.eq("flConcluido", Constants.NAO));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TbMensagem> listarMensagemFluxo(Date dtFluxo) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbMensagem.class);
		criteria.add(Restrictions.eq("flMensagemFluxo", Constants.SIM));
		criteria.add(Restrictions.between("dtInclusao", dtFluxo, dtFluxo));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TbMensagem> listarPorUsuario(FiltroIN filtro) {
		Criteria criteria = filtradosCriteria(filtro);
		criteria.addOrder(Order.desc("id"));
		criteria.setFirstResult(filtro.getFirst());
		criteria.setMaxResults(filtro.getPageSize());
		return criteria.list();
	}
	
	public Integer listarPorUsuarioQTD(FiltroIN filtro) {
		Criteria criteria = filtradosCriteria(filtro);
		criteria.setProjection(Projections.rowCount());
		return ((Number) criteria.uniqueResult()).intValue();
	}	
	
	private Criteria filtradosCriteria(FiltroIN filtro) {
		Integer idUsuario = SessionContext.getInstance().getUsuarioLogado().getId();
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbMensagem.class);
		criteria.add(Restrictions.eq("tbUsuario.id", idUsuario));
		criteria.add(Restrictions.eq("flConcluido", Constants.SIM));
		return criteria;
	}
	
}
