package com.odonto.dto;

import java.io.Serializable;
import java.util.Date;

public class FechamentoOUT implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String dsDescricao;
	private String dtData;
	private String dsResponsavel;
	private String vlMovimentado;
	private String vlDebito;
	private String vlCredito;
	private String vlDinheiro;
	private String vlCheque;
	private String vlDespesa;
	private Date dtInclusao;

	public FechamentoOUT() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDsDescricao() {
		return dsDescricao;
	}

	public void setDsDescricao(String dsDescricao) {
		this.dsDescricao = dsDescricao;
	}

	public String getDtData() {
		return dtData;
	}

	public void setDtData(String dtData) {
		this.dtData = dtData;
	}

	public String getVlMovimentado() {
		return vlMovimentado;
	}

	public void setVlMovimentado(String vlMovimentado) {
		this.vlMovimentado = vlMovimentado;
	}

	public String getVlDebito() {
		return vlDebito;
	}

	public void setVlDebito(String vlDebito) {
		this.vlDebito = vlDebito;
	}

	public String getVlCredito() {
		return vlCredito;
	}

	public void setVlCredito(String vlCredito) {
		this.vlCredito = vlCredito;
	}

	public String getVlDinheiro() {
		return vlDinheiro;
	}

	public void setVlDinheiro(String vlDinheiro) {
		this.vlDinheiro = vlDinheiro;
	}

	public String getVlCheque() {
		return vlCheque;
	}

	public void setVlCheque(String vlCheque) {
		this.vlCheque = vlCheque;
	}

	public String getVlDespesa() {
		return vlDespesa;
	}

	public void setVlDespesa(String vlDespesa) {
		this.vlDespesa = vlDespesa;
	}

	public Date getDtInclusao() {
		return dtInclusao;
	}

	public void setDtInclusao(Date dtInclusao) {
		this.dtInclusao = dtInclusao;
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
		FechamentoOUT other = (FechamentoOUT) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getDsResponsavel() {
		return dsResponsavel;
	}

	public void setDsResponsavel(String dsResponsavel) {
		this.dsResponsavel = dsResponsavel;
	}

}