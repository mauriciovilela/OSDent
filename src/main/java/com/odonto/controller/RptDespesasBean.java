package com.odonto.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;

import com.odonto.BLL.SaidaBLL;
import com.odonto.dto.SaidaOUT;
import com.odonto.util.Util;

@Named
@ViewScoped
public class RptDespesasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private PieChartModel registrosConsolidado;
	private List<SaidaOUT> registros;

	@Inject
	private SaidaBLL saidaBLL;
	
	private Date dataIni;
	private Date dataFim;
	
	@PostConstruct
	private void pageLoad() {
		dataIni = Util.getDiaMes(true);
		dataFim = Util.getDiaMes(false);
	}

	public void pesquisar() {
		registros = saidaBLL.relatorioDespesas(dataIni, dataFim);
		List<SaidaOUT> consolidados = saidaBLL.relatorioDespesasConsolidado(dataIni, dataFim);

		registrosConsolidado = new PieChartModel();
		
		for (SaidaOUT item: consolidados) {
			registrosConsolidado.set(item.getDsDescricao(), item.getTotal());
		}

		registrosConsolidado.setShowDataLabels(true);
		registrosConsolidado.setDataFormat("value");
		registrosConsolidado.setDataLabelFormatString("%#.2f");
		registrosConsolidado.setLegendPosition("w");
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

	public List<SaidaOUT> getRegistros() {
		return registros;
	}

	public void setRegistros(List<SaidaOUT> registros) {
		this.registros = registros;
	}

	public PieChartModel getRegistrosConsolidado() {
		return registrosConsolidado;
	}

	public void setRegistrosConsolidado(PieChartModel registrosConsolidado) {
		this.registrosConsolidado = registrosConsolidado;
	}
	
}