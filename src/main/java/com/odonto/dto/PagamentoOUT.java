package com.odonto.dto;

import java.io.Serializable;
import java.util.Date;

public class PagamentoOUT implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String dsObservacao;
	private String dsTipoPagamento;
	private String dsPaciente;
	private String dsDentista;
	private String dsResponsavel;
	private String dsMaquina;
	private String vlTotal;
	private int qtParcelas;
	private String vlParcela;
	private String vlPago;
	private Date dtInclusao;
	private String dtData;

	public PagamentoOUT() {

	}

	public String getDsTipoPagamento() {
		return dsTipoPagamento;
	}

	public void setDsTipoPagamento(String dsTipoPagamento) {
		this.dsTipoPagamento = dsTipoPagamento;
	}

	public String getDsPaciente() {
		return dsPaciente;
	}

	public void setDsPaciente(String dsPaciente) {
		this.dsPaciente = dsPaciente;
	}

	public String getDsResponsavel() {
		return dsResponsavel;
	}

	public void setDsResponsavel(String dsResponsavel) {
		this.dsResponsavel = dsResponsavel;
	}

	public int getQtParcelas() {
		return qtParcelas;
	}

	public void setQtParcelas(int qtParcelas) {
		this.qtParcelas = qtParcelas;
	}

	public String getVlParcela() {
		return vlParcela;
	}

	public void setVlParcela(String vlParcela) {
		this.vlParcela = vlParcela;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVlTotal() {
		return vlTotal;
	}

	public void setVlTotal(String vlTotal) {
		this.vlTotal = vlTotal;
	}

	public String getVlPago() {
		return vlPago;
	}

	public void setVlPago(String vlPago) {
		this.vlPago = vlPago;
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
		PagamentoOUT other = (PagamentoOUT) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Date getDtInclusao() {
		return dtInclusao;
	}

	public void setDtInclusao(Date dtInclusao) {
		this.dtInclusao = dtInclusao;
	}

	public String getDtData() {
		return dtData;
	}

	public void setDtData(String dtData) {
		this.dtData = dtData;
	}

	public String getDsObservacao() {
		return dsObservacao;
	}

	public void setDsObservacao(String dsObservacao) {
		this.dsObservacao = dsObservacao;
	}

	public String getDsMaquina() {
		return dsMaquina;
	}

	public void setDsMaquina(String dsMaquina) {
		this.dsMaquina = dsMaquina;
	}

	public String getDsDentista() {
		return dsDentista;
	}

	public void setDsDentista(String dsDentista) {
		this.dsDentista = dsDentista;
	}

}