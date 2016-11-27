package com.odonto.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.odonto.BLL.MensagemBLL;
import com.odonto.dto.FiltroIN;
import com.odonto.model.TbMensagem;
import com.odonto.security.SessionContext;

@Named
@ViewScoped
public class MensagemHistoricoMDBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MensagemBLL bll;
	
	private LazyDataModel<TbMensagem> historico;
	
	private FiltroIN filtro;
	
	public MensagemHistoricoMDBean() {
		
		filtro = new FiltroIN();
		
		setHistorico(new LazyDataModel<TbMensagem>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<TbMensagem> load(int first, int pageSize, String sortField, 
					SortOrder sortOrder, Map<String, Object> filters) {
				Integer idPaciente = SessionContext.getInstance().getID();
				filtro.setCodigo(idPaciente);
				filtro.setFirst(first);
				filtro.setPageSize(pageSize);
				setRowCount(bll.listarPorUsuarioQTD(filtro));
				return bll.listarPorUsuario(filtro);
			}
		});
	}

	public LazyDataModel<TbMensagem> getHistorico() {
		return historico;
	}

	public void setHistorico(LazyDataModel<TbMensagem> historico) {
		this.historico = historico;
	}

	public FiltroIN getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroIN filtro) {
		this.filtro = filtro;
	}
	
}
