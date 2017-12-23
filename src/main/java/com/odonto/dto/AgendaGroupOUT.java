package com.odonto.dto;

import java.io.Serializable;
import java.util.List;

public class AgendaGroupOUT implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String dtDia;
	private List<AgendaOUT> lstAgenda;

	public AgendaGroupOUT() {
		
	}

	public String getDtDia() {
		return dtDia;
	}

	public void setDtDia(String dtDia) {
		this.dtDia = dtDia;
	}

	public List<AgendaOUT> getLstAgenda() {
		return lstAgenda;
	}

	public void setLstAgenda(List<AgendaOUT> lstAgenda) {
		this.lstAgenda = lstAgenda;
	}
	
	

}