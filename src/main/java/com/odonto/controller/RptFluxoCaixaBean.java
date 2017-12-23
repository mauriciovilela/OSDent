package com.odonto.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;

import com.odonto.BLL.FechamentoCaixaBLL;
import com.odonto.dto.FechCaixaOUT;
import com.odonto.model.TbFechamentoCaixa;
import com.odonto.security.SessionContext;
import com.odonto.util.Util;

@Named
@ViewScoped
public class RptFluxoCaixaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private PieChartModel registrosConsolidado;
	private FechCaixaOUT consolidado;
	private List<TbFechamentoCaixa> registros;

	@Inject
	private FechamentoCaixaBLL caixaBLL;
	
	private BigDecimal vlLiquido; 
	
	private Date dataIni;
	private Date dataFim;
	
	private boolean temRegistros;
	
	@PostConstruct
	private void pageLoad() {
		dataIni = Util.getDiaMes(true);
		dataFim = Util.getDiaMes(false);
	}

	public void pesquisar() {
		registros = caixaBLL.relatorioFechamentoCaixa(dataIni, dataFim);
		consolidado = caixaBLL.relatorioFechamentoCaixaConsolidado(dataIni, dataFim);

		registrosConsolidado = new PieChartModel();
		
		temRegistros = (consolidado.getVlMovimentado() != null);
		if (temRegistros) {
			if (consolidado.getVlMovimentado().shortValue() > 0) {
				registrosConsolidado.set("Total Bruto", consolidado.getVlMovimentado());				
			}
			if (consolidado.getVlDinheiro().shortValue() > 0) {
				registrosConsolidado.set("Dinheiro", consolidado.getVlDinheiro());				
			}
			if (consolidado.getVlDebito().shortValue() > 0) {
				registrosConsolidado.set("Débito", consolidado.getVlDebito());				
			}
			if (consolidado.getVlCredito().shortValue() > 0) {
				registrosConsolidado.set("Crédito", consolidado.getVlCredito());				
			}
			if (consolidado.getVlCheque().shortValue() > 0) {
				registrosConsolidado.set("Cheque", consolidado.getVlCheque());				
			}
			if (consolidado.getVlDespesa().shortValue() > 0) {
				registrosConsolidado.set("Despesas", consolidado.getVlDespesa());				
			}
			vlLiquido = consolidado.getVlMovimentado().subtract(consolidado.getVlDespesa());
			consolidado.setVlLiquido(vlLiquido);
			consolidado.setDtInicio(dataIni);
			consolidado.setDtFim(dataFim);
		}

		registrosConsolidado.setShowDataLabels(true);
		registrosConsolidado.setDataFormat("value");
		registrosConsolidado.setDataLabelFormatString("%#.2f");
		registrosConsolidado.setLegendPosition("w");
	}
	
	public void gerarFechamento() {
		SessionContext.getInstance().setAttribute("fechamentoConsolidado", consolidado);
		Util.redirect("/restrita/caixa/Retirada.xhtml");
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

	public List<TbFechamentoCaixa> getRegistros() {
		return registros;
	}

	public void setRegistros(List<TbFechamentoCaixa> registros) {
		this.registros = registros;
	}

	public PieChartModel getRegistrosConsolidado() {
		return registrosConsolidado;
	}

	public void setRegistrosConsolidado(PieChartModel registrosConsolidado) {
		this.registrosConsolidado = registrosConsolidado;
	}

	public BigDecimal getVlLiquido() {
		return vlLiquido;
	}

	public void setVlLiquido(BigDecimal vlLiquido) {
		this.vlLiquido = vlLiquido;
	}

	public FechCaixaOUT getConsolidado() {
		return consolidado;
	}

	public void setConsolidado(FechCaixaOUT consolidado) {
		this.consolidado = consolidado;
	}

	public boolean isTemRegistros() {
		return temRegistros;
	}

	public void setTemRegistros(boolean temRegistros) {
		this.temRegistros = temRegistros;
	}
	
	
}