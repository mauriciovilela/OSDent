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
@Table(name = "tb_retirada")
public class TbRetirada implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@Column(name = "DS_DESCRICAO")
	private String dsDescricao;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_RETIRADA")
	private Date dtRetirada;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_INICIO")
	private Date dtInicio;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_FIM")
	private Date dtFim;

	@NotNull
	@Column(name = "VL_BRUTO")
	private BigDecimal vlBruto;

	@NotNull
	@Column(name = "VL_DESPESA")
	private BigDecimal vlDespesa;

	@NotNull
	@Column(name = "VL_LIQUIDO")
	private BigDecimal vlLiquido;

	@NotNull
	@Column(name = "VL_CARTAO")
	private BigDecimal vlCartao;

	@NotNull
	@Column(name = "VL_CHEQUE")
	private BigDecimal vlCheque;

	@NotNull
	@Column(name = "VL_DINHEIRO")
	private BigDecimal vlDinheiro;

	@NotNull
	@Column(name = "VL_EXTRATO_BANCARIO")
	private BigDecimal vlExtratoBancario;
	
	@NotNull
	@Column(name = "VL_CAIXA")
	private BigDecimal vlCaixa;

	@NotNull
	@Column(name = "VL_CAIXA_ANTERIOR")
	private BigDecimal vlCaixaAnterior;

	@NotNull
	@Column(name = "VL_CAIXA_ATUAL")
	private BigDecimal vlCaixaAtual;

	@NotNull
	@Column(name = "VL_RETIRADA")
	private BigDecimal vlRetirada;

	@NotNull
	@Column(name = "VL_RETIRADA_INDIVIDUAL")
	private BigDecimal vlRetiradaIndividual;

	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private TbUsuario tbUsuario;

	public TbRetirada() {
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

	public Date getDtRetirada() {
		return dtRetirada;
	}

	public void setDtRetirada(Date dtRetirada) {
		this.dtRetirada = dtRetirada;
	}

	public Date getDtInicio() {
		return dtInicio;
	}

	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}

	public Date getDtFim() {
		return dtFim;
	}

	public void setDtFim(Date dtFim) {
		this.dtFim = dtFim;
	}

	public BigDecimal getVlBruto() {
		return vlBruto;
	}

	public void setVlBruto(BigDecimal vlBruto) {
		this.vlBruto = vlBruto;
	}

	public BigDecimal getVlDespesa() {
		return vlDespesa;
	}

	public void setVlDespesa(BigDecimal vlDespesa) {
		this.vlDespesa = vlDespesa;
	}

	public BigDecimal getVlLiquido() {
		return vlLiquido;
	}

	public void setVlLiquido(BigDecimal vlLiquido) {
		this.vlLiquido = vlLiquido;
	}

	public BigDecimal getVlCartao() {
		return vlCartao;
	}

	public void setVlCartao(BigDecimal vlCartao) {
		this.vlCartao = vlCartao;
	}

	public BigDecimal getVlCheque() {
		return vlCheque;
	}

	public void setVlCheque(BigDecimal vlCheque) {
		this.vlCheque = vlCheque;
	}

	public BigDecimal getVlDinheiro() {
		return vlDinheiro;
	}

	public void setVlDinheiro(BigDecimal vlDinheiro) {
		this.vlDinheiro = vlDinheiro;
	}

//	public BigDecimal getVlRestanteDinheiro() {
//		return vlRestanteDinheiro;
//	}
//
//	public void setVlRestanteDinheiro(BigDecimal vlRestanteDinheiro) {
//		this.vlRestanteDinheiro = vlRestanteDinheiro;
//	}

	public BigDecimal getVlCaixa() {
		return vlCaixa;
	}

	public BigDecimal getVlExtratoBancario() {
		return vlExtratoBancario;
	}

	public void setVlExtratoBancario(BigDecimal vlExtratoBancario) {
		this.vlExtratoBancario = vlExtratoBancario;
	}

	public void setVlCaixa(BigDecimal vlCaixa) {
		this.vlCaixa = vlCaixa;
	}

	public BigDecimal getVlCaixaAnterior() {
		return vlCaixaAnterior;
	}

	public void setVlCaixaAnterior(BigDecimal vlCaixaAnterior) {
		this.vlCaixaAnterior = vlCaixaAnterior;
	}

	public BigDecimal getVlCaixaAtual() {
		return vlCaixaAtual;
	}

	public void setVlCaixaAtual(BigDecimal vlCaixaAtual) {
		this.vlCaixaAtual = vlCaixaAtual;
	}

	public BigDecimal getVlRetirada() {
		return vlRetirada;
	}

	public void setVlRetirada(BigDecimal vlRetirada) {
		this.vlRetirada = vlRetirada;
	}

	public BigDecimal getVlRetiradaIndividual() {
		return vlRetiradaIndividual;
	}

	public void setVlRetiradaIndividual(BigDecimal vlRetiradaIndividual) {
		this.vlRetiradaIndividual = vlRetiradaIndividual;
	}

	public TbUsuario getTbUsuario() {
		return tbUsuario;
	}

	public void setTbUsuario(TbUsuario tbUsuario) {
		this.tbUsuario = tbUsuario;
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
		TbRetirada other = (TbRetirada) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}