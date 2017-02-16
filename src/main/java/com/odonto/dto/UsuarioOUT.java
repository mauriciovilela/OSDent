package com.odonto.dto;

import java.io.Serializable;
import java.util.Date;

public class UsuarioOUT implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String dsNome;
	private String dsEmail;
	private String dsUsuario;
	private Boolean flSocio;
	private Boolean flDentista;
	private Boolean flAtivo;
	
	private String dsSenha;
	private String dsPerfil;
	private String dsTipo;
	private Date dtInclusao;

	public UsuarioOUT() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDsNome() {
		return dsNome;
	}

	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}

	public String getDsEmail() {
		return dsEmail;
	}

	public void setDsEmail(String dsEmail) {
		this.dsEmail = dsEmail;
	}

	public String getDsUsuario() {
		return dsUsuario;
	}

	public void setDsUsuario(String dsUsuario) {
		this.dsUsuario = dsUsuario;
	}

	public Boolean getFlSocio() {
		return flSocio;
	}

	public void setFlSocio(Boolean flSocio) {
		this.flSocio = flSocio;
	}

	public Boolean getFlDentista() {
		return flDentista;
	}

	public void setFlDentista(Boolean flDentista) {
		this.flDentista = flDentista;
	}

	public Boolean getFlAtivo() {
		return flAtivo;
	}

	public void setFlAtivo(Boolean flAtivo) {
		this.flAtivo = flAtivo;
	}

	public String getDsSenha() {
		return dsSenha;
	}

	public void setDsSenha(String dsSenha) {
		this.dsSenha = dsSenha;
	}

	public String getDsPerfil() {
		return dsPerfil;
	}

	public void setDsPerfil(String dsPerfil) {
		this.dsPerfil = dsPerfil;
	}

	public String getDsTipo() {
		return dsTipo;
	}

	public void setDsTipo(String dsTipo) {
		this.dsTipo = dsTipo;
	}

	public Date getDtInclusao() {
		return dtInclusao;
	}

	public void setDtInclusao(Date dtInclusao) {
		this.dtInclusao = dtInclusao;
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
		UsuarioOUT other = (UsuarioOUT) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}