package com.odonto.controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.odonto.BLL.PacienteBLL;
import com.odonto.constants.Constants;
import com.odonto.model.TbPaciente;
import com.odonto.service.PacienteService;
import com.odonto.util.Util;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PacienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PacienteService pacienteService;

	@Inject
	private PacienteBLL pacienteBLL;
	
	private TbPaciente paciente;
	
	private String aniversario;
	
	private Boolean panfleto;
	private String promocao;

	public PacienteBean() {
		limpar();
	}
	
	public void inicializar(ComponentSystemEvent ev) {
		if (FacesUtil.isNotPostback()) {
	        panfleto = paciente.isFlPanfleto();
	        if (paciente.getTbIndicacao() != null) {
	        	promocao = "I";
	        }
	        else if (paciente.getFlPasta() != null && paciente.getFlPasta()) {
	        	promocao = "P";
	        }
	        else {
	        	promocao = StringUtils.EMPTY;
	        }
	        if (paciente.getDtNascimento() != null)
	        	aniversario = Util.getDiaMes(paciente.getDtNascimento());
		}
	}
	
	private void limpar() {
		paciente = new TbPaciente();
		aniversario = StringUtils.EMPTY;
	}
	
	public void salvar() {
		if (StringUtils.isNotEmpty(aniversario)) {
			this.paciente.setDtNascimento(montaDadosAniversario());
		}
		this.paciente.setFlPanfleto(panfleto);
		this.paciente.setFlPasta((promocao != null && promocao.equals("P")));
		if (this.paciente.getFlPasta()) {
			this.paciente.setTbIndicacao(null);	
		}
		if (paciente.getTbIndicacao() != null) {
			this.paciente.setFlPasta(false);
		}
		this.paciente = pacienteService.salvar(this.paciente);
		limpar();		
		FacesUtil.addInfoMessage(Constants.msgSucesso);
	}
	
	public void atualizaPromocao() {
		
	}
	
	private Date montaDadosAniversario() {
		String[] arrNiver = aniversario.split("/");
		Calendar dtAniversario = Calendar.getInstance(); 
		dtAniversario.set(Calendar.YEAR, 1900); 
		dtAniversario.set(Calendar.MONTH, Integer.parseInt(arrNiver[1]) - 1);
		dtAniversario.set(Calendar.DAY_OF_MONTH, Integer.parseInt(arrNiver[0]));
		return dtAniversario.getTime();
	}

	public List<TbPaciente> pacientesPorNome(String query) {
		List<TbPaciente> registros = pacienteBLL.listarPorNome(query);
		return registros;
	}

	public TbPaciente getPaciente() {
		return paciente;
	}
	
	public void setPaciente(TbPaciente Paciente) {
		this.paciente = Paciente;	
	}

	public boolean isEditando() {
		return this.paciente.getId() != null;
	}

	public String getAniversario() {
		return aniversario;
	}

	public void setAniversario(String aniversario) {
		this.aniversario = aniversario;
	}

	public Boolean getPanfleto() {
		return panfleto;
	}

	public void setPanfleto(Boolean panfleto) {
		this.panfleto = panfleto;
	}
	
	public String getPromocao() {
		return promocao;
	}

	public void setPromocao(String promocao) {
		this.promocao = promocao;
	}

}
