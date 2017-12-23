package com.odonto.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SaidaOUT implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String dsDescricao;
	private String dsFornecedor;
	private String dsSocioInss;
	private String dsFluxoCaixa;
	private BigDecimal total;
	private String vlTotal;
	private String vlPago;
	private Date dtInclusao;
	private String dtData;

	public SaidaOUT() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDsFornecedor() {
		return dsFornecedor;
	}

	public void setDsFornecedor(String dsFornecedor) {
		this.dsFornecedor = dsFornecedor;
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

	public String getDsDescricao() {
		return dsDescricao;
	}

	public void setDsDescricao(String dsDescricao) {
		this.dsDescricao = dsDescricao;
	}

	public String getDsSocioInss() {
		return dsSocioInss;
	}

	public void setDsSocioInss(String dsSocioInss) {
		this.dsSocioInss = dsSocioInss;
	}

	public String getDsFluxoCaixa() {
		return dsFluxoCaixa;
	}

	public void setDsFluxoCaixa(String dsFluxoCaixa) {
		this.dsFluxoCaixa = dsFluxoCaixa;
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
		SaidaOUT other = (SaidaOUT) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
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

}