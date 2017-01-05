package com.odonto.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="tb_fornecedor")
public class TbFornecedor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@NotBlank
	@Column(name="DS_NOME")
	private String dsNome;

	@Column(name="FL_DENTAL")
	private Boolean flDental;
	
	@ManyToOne
	@JoinColumn(name="ID_USUARIO")
	private TbUsuario tbUsuario;

	@NotNull
	@Column(name="FL_PCT")
	private Boolean flPorcentagem;
	
	@ManyToOne
	@JoinColumn(name="ID_PAI")
	private TbFornecedor tbFornecedorPai;

	@NotNull
	@Column(name="FL_SOCIO_INSS")
	private Boolean flSocioResponsavel;
	
	public TbFornecedor() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDsNome() {
		return this.dsNome;
	}

	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}

	public Boolean getFlDental() {
		return this.flDental;
	}

	public void setFlDental(Boolean flDental) {
		this.flDental = flDental;
	}

	public TbUsuario getTbUsuario() {
		return tbUsuario;
	}

	public void setTbUsuario(TbUsuario tbUsuario) {
		this.tbUsuario = tbUsuario;
	}

	public Boolean getFlPorcentagem() {
		return flPorcentagem;
	}

	public void setFlPorcentagem(Boolean flPorcentagem) {
		this.flPorcentagem = flPorcentagem;
	}

	public TbFornecedor getTbFornecedorPai() {
		return tbFornecedorPai;
	}

	public void setTbFornecedorPai(TbFornecedor tbFornecedorPai) {
		this.tbFornecedorPai = tbFornecedorPai;
	}


	public Boolean getFlSocioResponsavel() {
		return flSocioResponsavel;
	}

	public void setFlSocioResponsavel(Boolean flSocioResponsavel) {
		this.flSocioResponsavel = flSocioResponsavel;
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
		TbFornecedor other = (TbFornecedor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}