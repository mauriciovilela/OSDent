package com.odonto.model;

import java.io.Serializable;
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
@Table(name="tb_mensagem")
public class TbMensagem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@NotBlank
	@Column(name="DS_DESCRICAO", length=300)
	private String dsDescricao;

	@NotNull
	@ManyToOne
	@JoinColumn(name="ID_SOLICITANTE")
	private TbUsuario tbSolicitante;

	@ManyToOne
	@JoinColumn(name="ID_USUARIO")
	private TbUsuario tbUsuario;

	@NotNull
	@Column(name="FL_CONCLUIDO")
	private Boolean flConcluido;

	@NotNull
	@Column(name="FL_MENSAGEM_FLUXO")
	private Boolean flMensagemFluxo;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_INCLUSAO")
	private Date dtInclusao;

	public TbMensagem() {
	}

	public Integer getId() {
		return this.id;
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

	public TbUsuario getTbSolicitante() {
		return tbSolicitante;
	}

	public void setTbSolicitante(TbUsuario tbSolicitante) {
		this.tbSolicitante = tbSolicitante;
	}

	public TbUsuario getTbUsuario() {
		return tbUsuario;
	}

	public void setTbUsuario(TbUsuario tbUsuario) {
		this.tbUsuario = tbUsuario;
	}

	public Boolean getFlConcluido() {
		return flConcluido;
	}

	public void setFlConcluido(Boolean flConcluido) {
		this.flConcluido = flConcluido;
	}

	public Date getDtInclusao() {
		return dtInclusao;
	}

	public void setDtInclusao(Date dtInclusao) {
		this.dtInclusao = dtInclusao;
	}

	public Boolean getFlMensagemFluxo() {
		return flMensagemFluxo;
	}

	public void setFlMensagemFluxo(Boolean flMensagemFluxo) {
		this.flMensagemFluxo = flMensagemFluxo;
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
		TbMensagem other = (TbMensagem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
