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
@Table(name="tb_saida")
public class TbSaida implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@Column(name="DS_DESCRICAO", length=150)
	private String dsDescricao;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_COMPRA")
	private Date dtCompra;

	@NotNull
	@Column(name="VL_TOTAL")
	private BigDecimal vlTotal;

	@NotNull
	@Column(name="VL_PAGO")
	private BigDecimal vlPago;

	@NotNull
	@ManyToOne
	@JoinColumn(name="ID_FORNECEDOR")
	private TbFornecedor tbFornecedor;

	@NotNull
	@Column(name="FL_FLUXO_CAIXA")
	private Boolean flFluxoCaixa;

	@ManyToOne
	@JoinColumn(name="ID_USUARIO")
	private TbUsuario tbUsuario;

	@ManyToOne
	@JoinColumn(name="ID_SOCIO_INSS")
	private TbUsuario tbSocioInss;
	
	public TbSaida() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDsDescricao() {
		return this.dsDescricao;
	}

	public void setDsDescricao(String dsDescricao) {
		this.dsDescricao = dsDescricao;
	}

	public Date getDtCompra() {
		return this.dtCompra;
	}

	public void setDtCompra(Date dtCompra) {
		this.dtCompra = dtCompra;
	}

	public BigDecimal getVlTotal() {
		return this.vlTotal;
	}

	public void setVlTotal(BigDecimal vlTotal) {
		this.vlTotal = vlTotal;
	}

	public TbFornecedor getTbFornecedor() {
		return this.tbFornecedor;
	}

	public void setTbFornecedor(TbFornecedor tbFornecedor) {
		this.tbFornecedor = tbFornecedor;
	}

	public Boolean getFlFluxoCaixa() {
		return flFluxoCaixa;
	}

	public void setFlFluxoCaixa(Boolean flFluxoCaixa) {
		this.flFluxoCaixa = flFluxoCaixa;
	}

	public TbUsuario getTbUsuario() {
		return tbUsuario;
	}

	public void setTbUsuario(TbUsuario tbUsuario) {
		this.tbUsuario = tbUsuario;
	}

	public BigDecimal getVlPago() {
		return vlPago;
	}

	public void setVlPago(BigDecimal vlPago) {
		this.vlPago = vlPago;
	}

	public TbUsuario getTbSocioInss() {
		return tbSocioInss;
	}

	public void setTbSocioInss(TbUsuario tbSocioInss) {
		this.tbSocioInss = tbSocioInss;
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
		TbSaida other = (TbSaida) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}