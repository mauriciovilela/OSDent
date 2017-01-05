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
@Table(name = "tb_fechamento_caixa")
public class TbFechamentoCaixa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@NotNull
	@Column(name = "DS_DESCRICAO", length = 300)
	private String dsDescricao;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_FECHAMENTO")
	private Date dtFechamento;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_INCLUSAO")
	private Date dtInclusao;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_RESPONSAVEL")
	private TbUsuario tbResponsavel;

	@NotNull
	@Column(name="VL_MOVIMENTADO")
	private BigDecimal vlMovimentado;

	@NotNull
	@Column(name="VL_DEBITO")
	private BigDecimal vlDebito;

	@NotNull
	@Column(name="VL_CREDITO")
	private BigDecimal vlCredito;

	@NotNull
	@Column(name="VL_DINHEIRO")
	private BigDecimal vlDinheiro;

	@NotNull
	@Column(name="VL_CHEQUE")
	private BigDecimal vlCheque;

	@NotNull
	@Column(name="VL_DESPESA")
	private BigDecimal vlDespesa;

//	@NotNull
//	@Column(name="VL_DESPESA_FORA")
//	private BigDecimal vlDespesaFora;

	public TbFechamentoCaixa() {
		
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

	public Date getDtFechamento() {
		return dtFechamento;
	}

	public void setDtFechamento(Date dtFechamento) {
		this.dtFechamento = dtFechamento;
	}

	public Date getDtInclusao() {
		return dtInclusao;
	}

	public void setDtInclusao(Date dtInclusao) {
		this.dtInclusao = dtInclusao;
	}

	public TbUsuario getTbResponsavel() {
		return tbResponsavel;
	}

	public void setTbResponsavel(TbUsuario tbResponsavel) {
		this.tbResponsavel = tbResponsavel;
	}

	public BigDecimal getVlMovimentado() {
		return vlMovimentado;
	}

	public void setVlMovimentado(BigDecimal vlMovimentado) {
		this.vlMovimentado = vlMovimentado;
	}

	public BigDecimal getVlDebito() {
		return vlDebito;
	}

	public void setVlDebito(BigDecimal vlDebito) {
		this.vlDebito = vlDebito;
	}

	public BigDecimal getVlCredito() {
		return vlCredito;
	}

	public void setVlCredito(BigDecimal vlCredito) {
		this.vlCredito = vlCredito;
	}

	public BigDecimal getVlDinheiro() {
		return vlDinheiro;
	}

	public void setVlDinheiro(BigDecimal vlDinheiro) {
		this.vlDinheiro = vlDinheiro;
	}

	public BigDecimal getVlCheque() {
		return vlCheque;
	}

	public void setVlCheque(BigDecimal vlCheque) {
		this.vlCheque = vlCheque;
	}

	public BigDecimal getVlDespesa() {
		return vlDespesa;
	}

	public void setVlDespesa(BigDecimal vlDespesa) {
		this.vlDespesa = vlDespesa;
	}

//	public BigDecimal getVlDespesaFora() {
//		return vlDespesaFora;
//	}
//
//	public void setVlDespesaFora(BigDecimal vlDespesaFora) {
//		this.vlDespesaFora = vlDespesaFora;
//	}

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
		TbFechamentoCaixa other = (TbFechamentoCaixa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
