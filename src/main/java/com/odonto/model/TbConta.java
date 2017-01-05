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

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "tb_conta")
public class TbConta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@NotNull
	@NotBlank
	@Column(name = "DS_DESCRICAO", length = 100)
	private String dsDescricao;

	@NotNull
	@Column(name = "FL_CONTA_FIXA")
	private Boolean contaFixa;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_VENCIMENTO")
	private Date dtVencimento;

	@Column(name="VL_TOTAL")
	private BigDecimal vlTotal;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private TbUsuario tbUsuario;

	public TbConta() {
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

	public Boolean getContaFixa() {
		return contaFixa;
	}

	public void setContaFixa(Boolean contaFixa) {
		this.contaFixa = contaFixa;
	}

	public Date getDtVencimento() {
		return dtVencimento;
	}

	public void setDtVencimento(Date dtVencimento) {
		this.dtVencimento = dtVencimento;
	}

	public TbUsuario getTbUsuario() {
		return tbUsuario;
	}

	public void setTbUsuario(TbUsuario tbUsuario) {
		this.tbUsuario = tbUsuario;
	}

	public BigDecimal getVlTotal() {
		return vlTotal;
	}

	public void setVlTotal(BigDecimal vlTotal) {
		this.vlTotal = vlTotal;
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
		TbConta other = (TbConta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}