package com.odonto.BLL;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.odonto.dto.PacienteIN;
import com.odonto.dto.PacienteIRPFCpfOUT;
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
	
	@SuppressWarnings("unchecked")
	public List<PacienteIRPFCpfOUT> listarPacientesCpfIRPF() {
		Session session = manager.unwrap(Session.class);
		StringBuilder strQ = new StringBuilder();
		strQ.append(" Select  ");
		strQ.append(" 	P.NR_CPF as cpf,  ");
		strQ.append(" 	P.DS_NOME as nome, ");
		strQ.append(" 		( select max(usu.DS_NOME)  ");
		strQ.append(" 		from tb_pagamento pg  ");
		strQ.append(" 		inner join tb_usuario usu on pg.ID_MAQUINA = usu.id ");
		strQ.append(" 		where pg.ID_PACIENTE = P.id  ");
		strQ.append(" 			and pg.ID_TIPO_PGTO in (2,3)  ");
		strQ.append(" 			and pg.DT_ENTRADA < '2017-01-01' ");
		strQ.append(" 	) as dentista, ");
		strQ.append(" 		( select sum(pg.VL_TOTAL)  ");
		strQ.append(" 		from tb_pagamento pg  ");
		strQ.append(" 		where pg.ID_PACIENTE = P.id  ");
		strQ.append(" 			and pg.ID_TIPO_PGTO in (2,3)  ");
		strQ.append(" 			and pg.DT_ENTRADA < '2017-01-01' ");
		strQ.append(" 	) as valorTotal, ");
		strQ.append(" 		( select DATE_FORMAT(max(pg.DT_ENTRADA),'%d/%m/%Y')  ");
		strQ.append(" 		from tb_pagamento pg  ");
		strQ.append(" 		where pg.ID_PACIENTE = P.id  ");
		strQ.append(" 			and pg.ID_TIPO_PGTO in (2,3)  ");
		strQ.append(" 			and pg.DT_ENTRADA < '2017-01-01' ");
		strQ.append(" 	) as data ");
		strQ.append(" From  ");
		strQ.append(" 	tb_paciente P  ");
		strQ.append(" Where  ");
		strQ.append(" 	P.NR_CPF <> '' and   ");
		strQ.append(" 	(select COUNT(1) from tb_pagamento pg where pg.ID_PACIENTE = P.id and pg.ID_TIPO_PGTO in (2,3) and pg.DT_ENTRADA < '2017-01-01') > 0 ");
		strQ.append(" order by p.DS_NOME ");
		Query query = session.createSQLQuery(strQ.toString()).addEntity(PacienteIRPFCpfOUT.class);
		return query.list();
	}
	
}
