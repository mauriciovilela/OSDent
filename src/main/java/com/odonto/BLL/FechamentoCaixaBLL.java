package com.odonto.BLL;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.odonto.dto.FechCaixaOUT;
import com.odonto.model.TbFechamentoCaixa;
import com.odonto.service.NegocioException;
import com.odonto.util.Util;
import com.odonto.util.jpa.Transactional;

public class FechamentoCaixaBLL implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public TbFechamentoCaixa porId(Integer id) {
		return manager.find(TbFechamentoCaixa.class, id);
	}

	public TbFechamentoCaixa guardar(TbFechamentoCaixa item) {
		return manager.merge(item);
	}
	
	@Transactional
	public void remover(TbFechamentoCaixa item) {
		try {
			item = porId(item.getId());
			manager.remove(item);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Registro não pode ser excluído.");
		}
	}
	
	@SuppressWarnings("unchecked")
	public TbFechamentoCaixa listarPorData(Date data) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbFechamentoCaixa.class);
		criteria.add(Restrictions.between("dtFechamento", Util.getDataHora(data, true), Util.getDataHora(data, false)));	
		List<TbFechamentoCaixa> list = criteria.list();
		if (list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<TbFechamentoCaixa> relatorioFechamentoCaixa(Date dataInicial, Date dataFinal) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbFechamentoCaixa.class);
		criteria.add(Restrictions.between("dtFechamento", dataInicial, dataFinal));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public FechCaixaOUT relatorioFechamentoCaixaConsolidado(Date dataInicial, Date dataFinal) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbFechamentoCaixa.class);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.sum("vlMovimentado"), "vlMovimentado")
				.add(Projections.sum("vlDinheiro"), "vlDinheiro")
				.add(Projections.sum("vlDebito"), "vlDebito")
				.add(Projections.sum("vlCredito"), "vlCredito")
				.add(Projections.sum("vlCheque"), "vlCheque")
				.add(Projections.sum("vlDespesa"), "vlDespesa") );
		criteria.add(Restrictions.between("dtFechamento", dataInicial, dataFinal));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(FechCaixaOUT.class));
		List<FechCaixaOUT> list = criteria.list();
		if (list.isEmpty()){
			return null;
		}
		return list.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<TbFechamentoCaixa> porDataExportacao(Date data) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbFechamentoCaixa.class);
		criteria.add(Restrictions.gt("dtInclusao", data));
//		criteria.addOrder(Order.asc("id"));
		return criteria.list();
	}
}
