package com.odonto.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PacienteIRPFCpfOUT implements Serializable {

	private static final long serialVersionUID = 1L;

	private Short idFuncionalidadePai;
	private String cpf;
	private String nome;
	private BigDecimal valorTotal;
	private String data;
	private String dentista;

	public PacienteIRPFCpfOUT() {
	}

	public Short getIdFuncionalidadePai() {
		return idFuncionalidadePai;
	}

	public void setIdFuncionalidadePai(Short idFuncionalidadePai) {
		this.idFuncionalidadePai = idFuncionalidadePai;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDentista() {
		return dentista;
	}

	public void setDentista(String dentista) {
		this.dentista = dentista;
	}

}