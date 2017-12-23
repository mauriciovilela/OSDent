package com.odonto.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.odonto.BLL.PacienteBLL;
import com.odonto.dto.PacienteIRPFCpfOUT;

@Named
@ViewScoped
public class RptIRPFPacienteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PacienteBLL pacienteBLL;
	
	private List<PacienteIRPFCpfOUT> filtrados;

	@PostConstruct
	private void pageLoad() {
		setFiltrados(pacienteBLL.listarPacientesCpfIRPF());
	}

	public List<PacienteIRPFCpfOUT> getFiltrados() {
		return filtrados;
	}

	public void setFiltrados(List<PacienteIRPFCpfOUT> filtrados) {
		this.filtrados = filtrados;
	}

}