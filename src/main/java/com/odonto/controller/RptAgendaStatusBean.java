package com.odonto.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;

import com.odonto.BLL.AgendaBLL;
import com.odonto.dto.AgendaOUT;
import com.odonto.util.Util;

@Named
@ViewScoped
public class RptAgendaStatusBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private PieChartModel registros;

	@Inject
	private AgendaBLL agendaBLL;
	
	private Date dataIni;
	private Date dataFim;
	
	@PostConstruct
	private void pageLoad() {
		dataIni = Util.getDiaMes(true);
		dataFim = Util.getDiaMes(false);
	}

	public void pesquisar() {
		List<AgendaOUT> lista = agendaBLL.listarPorStatus(dataIni, dataFim);

		registros = new PieChartModel();
		
		for (AgendaOUT item: lista) {
	        registros.set(item.getDsDescricao(), item.getTotal());
		}

		registros.setShowDataLabels(true);
//		registros.setDataFormat("value");
        registros.setLegendPosition("w");
	}

	public PieChartModel getRegistros() {
		return registros;
	}

	public void setRegistros(PieChartModel registros) {
		this.registros = registros;
	}

	public Date getDataIni() {
		return dataIni;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
}