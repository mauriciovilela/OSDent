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
@Table(name="tb_pagamento")
public class TbPagamento implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_ENTRADA")
	@NotNull
	private Date dtEntrada;

	@Column(name="QT_PARCELAS")
	private int qtParcelas;

	@Column(name="VL_PARCELA")
	private BigDecimal vlParcela;

	@Column(name="VL_TOTAL")
	@NotNull
	private BigDecimal vlTotal;

	@Column(name="VL_PAGO")
	@NotNull
	private BigDecimal vlPago;

	@ManyToOne
	@JoinColumn(name="ID_TIPO_PGTO")
	@NotNull
	private TbTipoPgto tbTipoPgto;

	@ManyToOne
	@JoinColumn(name="ID_PACIENTE")
	@NotNull
	private TbPaciente tbPaciente;
	
	@ManyToOne
	@JoinColumn(name="ID_RESPONSAVEL")
	@NotNull
	private TbUsuario tbResponsavel;
	
	@ManyToOne
	@JoinColumn(name="ID_MAQUINA")
	private TbUsuario tbMaquina;
	
	@ManyToOne
	@JoinColumn(name="ID_DENTISTA")
	private TbUsuario tbDentista;

	@Column(name="DS_OBS", length=150)
	private String dsObservacao;
	
	@ManyToOne
	@JoinColumn(name="ID_USUARIO")
	private TbUsuario tbUsuario;
	
	@ManyToOne
	@JoinColumn(name="ID_STATUS")
	@NotNull
	private TbPagamentoStatus tbPagamentoStatus;
	
	public TbPagamento() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDtEntrada() {
		return this.dtEntrada;
	}

	public void setDtEntrada(Date dtEntrada) {
		this.dtEntrada = dtEntrada;
	}

	public int getQtParcelas() {
		return this.qtParcelas;
	}

	public void setQtParcelas(int qtParcelas) {
		this.qtParcelas = qtParcelas;
	}

	public BigDecimal getVlParcela() {
		return this.vlParcela;
	}

	public void setVlParcela(BigDecimal vlParcela) {
		this.vlParcela = vlParcela;
	}

	public BigDecimal getVlTotal() {
		return this.vlTotal;
	}

	public void setVlTotal(BigDecimal vlTotal) {
		this.vlTotal = vlTotal;
	}

	public TbTipoPgto getTbTipoPgto() {
		return this.tbTipoPgto;
	}

	public void setTbTipoPgto(TbTipoPgto tbTipoPgto) {
		this.tbTipoPgto = tbTipoPgto;
	}

	public TbPaciente getTbPaciente() {
		return this.tbPaciente;
	}

	public void setTbPaciente(TbPaciente tbPaciente) {
		this.tbPaciente = tbPaciente;
	}

	public BigDecimal getVlPago() {
		return vlPago;
	}

	public void setVlPago(BigDecimal vlPago) {
		this.vlPago = vlPago;
	}

	public TbUsuario getTbResponsavel() {
		return tbResponsavel;
	}

	public void setTbResponsavel(TbUsuario tbResponsavel) {
		this.tbResponsavel = tbResponsavel;
	}

	public TbUsuario getTbDentista() {
		return tbDentista;
	}

	public void setTbDentista(TbUsuario tbDentista) {
		this.tbDentista = tbDentista;
	}

	public TbUsuario getTbMaquina() {
		return tbMaquina;
	}

	public void setTbMaquina(TbUsuario tbMaquina) {
		this.tbMaquina = tbMaquina;
	}

	public String getDsObservacao() {
		return dsObservacao;
	}

	public void setDsObservacao(String dsObservacao) {
		this.dsObservacao = dsObservacao;
	}

	public TbUsuario getTbUsuario() {
		return tbUsuario;
	}

	public void setTbUsuario(TbUsuario tbUsuario) {
		this.tbUsuario = tbUsuario;
	}

	public TbPagamentoStatus getTbPagamentoStatus() {
		return tbPagamentoStatus;
	}

	public void setTbPagamentoStatus(TbPagamentoStatus tbPagamentoStatus) {
		this.tbPagamentoStatus = tbPagamentoStatus;
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
		TbPagamento other = (TbPagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}