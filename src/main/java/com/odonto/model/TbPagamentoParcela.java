package com.odonto.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="tb_pagamento_parcela")
public class TbPagamentoParcela implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@Column(name="NR_PARCELA")
	@NotNull
	private int nrParcela;

	@Column(name="VL_PARCELA")
	@NotNull
	private BigDecimal vlParcela;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_CREDITO")
	@NotNull
	private Date dtCredito;

	@ManyToOne
	@JoinColumn(name="ID_PAGAMENTO")
	@NotNull
	private TbPagamento tbPagamento;

	public TbPagamentoParcela() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getNrParcela() {
		return nrParcela;
	}

	public void setNrParcela(int nrParcela) {
		this.nrParcela = nrParcela;
	}

	public BigDecimal getVlParcela() {
		return vlParcela;
	}

	public void setVlParcela(BigDecimal vlParcela) {
		this.vlParcela = vlParcela;
	}

	public Date getDtCredito() {
		return dtCredito;
	}

	public void setDtCredito(Date dtCredito) {
		this.dtCredito = dtCredito;
	}

	public TbPagamento getTbPagamento() {
		return tbPagamento;
	}

	public void setTbPagamento(TbPagamento tbPagamento) {
		this.tbPagamento = tbPagamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TbPagamentoParcela other = (TbPagamentoParcela) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}