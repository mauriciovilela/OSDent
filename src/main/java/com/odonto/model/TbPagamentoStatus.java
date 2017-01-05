package com.odonto.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_pagamento_status")
public class TbPagamentoStatus implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column(name="DS_NOME")
	private String dsNome;

	@Column(name="FL_PAGAR")
	private boolean flPagar;

	@Column(name="FL_ATIVO")
	private boolean flAtivo;
		
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

	public boolean isFlPagar() {
		return flPagar;
	}

	public void setFlPagar(boolean flPagar) {
		this.flPagar = flPagar;
	}

	public boolean isFlAtivo() {
		return flAtivo;
	}

	public void setFlAtivo(boolean flAtivo) {
		this.flAtivo = flAtivo;
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
		TbPagamentoStatus other = (TbPagamentoStatus) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}