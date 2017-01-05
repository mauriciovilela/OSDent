package com.odonto.dto;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.odonto.model.TbFornecedor;

public class SaidaIN implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotEmpty
	private String vlTotal;
	
	@NotEmpty
	private String dsDescricao;
	
	private Date dtCompraInicio;
	private Date dtCompraFim;
	
	private TbFornecedor fornecedor;

	private Integer idTipoDespesa;

	public String getVlTotal() {
		return vlTotal;
	}

	public void setVlTotal(String vlTotal) {
		this.vlTotal = vlTotal;
	}

	public String getDsDescricao() {
		return dsDescricao;
	}

	public void setDsDescricao(String dsDescricao) {
		this.dsDescricao = dsDescricao;
	}

	public TbFornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(TbFornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Integer getIdTipoDespesa() {
		return idTipoDespesa;
	}

	public void setIdTipoDespesa(Integer idTipoDespesa) {
		this.idTipoDespesa = idTipoDespesa;
	}

	public Date getDtCompraInicio() {
		return dtCompraInicio;
	}

	public void setDtCompraInicio(Date dtCompraInicio) {
		this.dtCompraInicio = dtCompraInicio;
	}

	public Date getDtCompraFim() {
		return dtCompraFim;
	}

	public void setDtCompraFim(Date dtCompraFim) {
		this.dtCompraFim = dtCompraFim;
	}
	
}
