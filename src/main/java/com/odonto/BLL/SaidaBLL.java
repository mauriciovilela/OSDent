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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.odonto.dto.SaidaIN;
import com.odonto.dto.SaidaOUT;
import com.odonto.model.TbSaida;
import com.odonto.security.SessionContext;
import com.odonto.service.NegocioException;
import com.odonto.util.Util;
import com.odonto.util.jpa.Transactional;

public class SaidaBLL implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public TbSaida porId(Integer id) {
		return manager.find(TbSaida.class, id);
	}

	public TbSaida guardar(TbSaida item) {
		item.setTbUsuario(SessionContext.getInstance().getUsuarioLogado());
		item.setDtInclusao(new Date());
		return manager.merge(item);
	}
	
	@Transactional
	public void remover(TbSaida item) {
		try {
			item = porId(item.getId());
			manager.remove(item);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Registro não pode ser excluído.");
		}
	}

	@SuppressWarnings("unchecked")
	public List<TbSaida> filtrados(SaidaIN filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbSaida.class);
		if (filtro.getDtCompraInicio() != null && filtro.getDtCompraFim() != null) {
			criteria.add(Restrictions.between("dtCompra", 
					Util.getDataHora(filtro.getDtCompraInicio(), true), 
					Util.getDataHora(filtro.getDtCompraFim(), false)));
		}
		if (filtro.getIdTipoDespesa() != null)
			criteria.add(Restrictions.eq("tbFornecedor.id", filtro.getIdTipoDespesa()));
		criteria.addOrder(Order.desc("dtCompra"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<TbSaida> porData(Date data, Boolean fluxoCaixa) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbSaida.class);
		criteria.add(Restrictions.between("dtCompra", data, data));
		criteria.add(Restrictions.eq("flFluxoCaixa", fluxoCaixa));
		criteria.addOrder(Order.asc("id"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<TbSaida> porDataExportacao(Date data) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbSaida.class);
		criteria.add(Restrictions.gt("dtInclusao", data));
		criteria.addOrder(Order.asc("id"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<TbSaida> porTipoDataValor(Integer idTipoDespesa, Date data, BigDecimal valorTotal) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbSaida.class);
		criteria.add(Restrictions.eq("tbFornecedor.id", idTipoDespesa));
		criteria.add(Restrictions.eq("dtCompra", data));
		criteria.add(Restrictions.eq("vlTotal", valorTotal));
		criteria.addOrder(Order.desc("id"));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<SaidaOUT> relatorioDespesas(Date dataInicial, Date dataFinal) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbSaida.class);
		criteria.createAlias("tbFornecedor", "F");
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("F.dsNome"), "dsDescricao")
				.add(Projections.sum("vlPago"), "total"));
		criteria.add(Restrictions.between("dtCompra", dataInicial, dataFinal));
		criteria.add(Restrictions.isNotNull("F.tbFornecedorPai"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(SaidaOUT.class));
		criteria.addOrder(Order.desc("total"));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<SaidaOUT> relatorioDespesasConsolidado(Date dataInicial, Date dataFinal) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbSaida.class);
		criteria.createAlias("tbFornecedor", "F");
		criteria.createAlias("F.tbFornecedorPai", "FP");
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("FP.dsNome"), "dsDescricao")
				.add(Projections.sum("vlPago"), "total"));
		criteria.add(Restrictions.between("dtCompra", dataInicial, dataFinal));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(SaidaOUT.class));
		criteria.addOrder(Order.desc("total"));
		return criteria.list();
	}	
	
}
