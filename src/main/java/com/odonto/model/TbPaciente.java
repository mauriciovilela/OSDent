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
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "tb_paciente", uniqueConstraints = @UniqueConstraint(columnNames = { "DS_NOME" }) )
public class TbPaciente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@NotBlank
	@Column(name = "DS_NOME", length = 100)
	private String dsNome;

	@Column(name = "NR_CPF", length = 14)
	private String nrCPF;

	@Column(name = "DS_FONE", length = 14)
	private String dsFone;

	@Column(name = "DS_ENDERECO", length = 100)
	private String dsEndereco;

	@Column(name = "DS_PROFISSAO", length = 100)
	private String dsProfissao;

	@Column(name = "DS_EMAIL", length = 100)
	private String dsEmail;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_NASCIMENTO")
	private Date dtNascimento;

	@Column(name = "FL_PANFLETO")
	private Boolean flPanfleto;

	@Column(name = "FL_PASTA")
	private Boolean flPasta;

	@Column(name = "FL_AVALIACAO")
	private Boolean flAvaliacao;

	@ManyToOne
	@JoinColumn(name = "ID_INDICACAO")
	private TbPaciente tbIndicacao;

	@Column(name = "DS_OBS", length = 200)
	private String dsObservacao;

	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private TbUsuario tbUsuario;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_CADASTRO")
	private Date dtCadastro;

	public TbPaciente() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNrCPF() {
		return nrCPF;
	}

	public void setNrCPF(String nrCPF) {
		this.nrCPF = nrCPF;
	}

	public String getDsNome() {
		return this.dsNome;
	}

	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}

	public String getDsFone() {
		return dsFone;
	}

	public void setDsFone(String dsFone) {
		this.dsFone = dsFone;
	}

	public String getDsEndereco() {
		return dsEndereco;
	}

	public void setDsEndereco(String dsEndereco) {
		this.dsEndereco = dsEndereco;
	}

	public String getDsProfissao() {
		return dsProfissao;
	}

	public void setDsProfissao(String dsProfissao) {
		this.dsProfissao = dsProfissao;
	}

	public String getDsEmail() {
		return dsEmail;
	}

	public void setDsEmail(String dsEmail) {
		this.dsEmail = dsEmail;
	}

	public Boolean getFlPanfleto() {
		return flPanfleto;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public TbPaciente getTbIndicacao() {
		return tbIndicacao;
	}

	public void setTbIndicacao(TbPaciente tbIndicacao) {
		this.tbIndicacao = tbIndicacao;
	}

	public String getDsObservacao() {
		return dsObservacao;
	}

	public void setDsObservacao(String dsObservacao) {
		this.dsObservacao = dsObservacao;
	}

	public Boolean isFlPanfleto() {
		return flPanfleto;
	}

	public void setFlPanfleto(Boolean flPanfleto) {
		this.flPanfleto = flPanfleto;
	}

	public TbUsuario getTbUsuario() {
		return tbUsuario;
	}

	public void setTbUsuario(TbUsuario tbUsuario) {
		this.tbUsuario = tbUsuario;
	}

	public Date getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public Boolean getFlPasta() {
		return flPasta;
	}

	public void setFlPasta(Boolean flPasta) {
		this.flPasta = flPasta;
	}

	public Boolean getFlAvaliacao() {
		return flAvaliacao;
	}

	public void setFlAvaliacao(Boolean flAvaliacao) {
		this.flAvaliacao = flAvaliacao;
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
		TbPaciente other = (TbPaciente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}