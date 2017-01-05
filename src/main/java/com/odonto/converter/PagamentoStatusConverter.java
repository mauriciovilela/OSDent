package com.odonto.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.odonto.BLL.PagamentoStatusBLL;
import com.odonto.model.TbPagamentoStatus;
import com.odonto.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = TbPagamentoStatus.class)
public class PagamentoStatusConverter implements Converter {

	private PagamentoStatusBLL bll;
	
	public PagamentoStatusConverter() {
		bll = CDIServiceLocator.getBean(PagamentoStatusBLL.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		TbPagamentoStatus retorno = null;
		
		if (value != null) {
			Integer id = new Integer(value);
			retorno = bll.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && !value.equals("")) {
            Integer id = (( TbPagamentoStatus) value).getId();
            return String.valueOf(id);
        }
		return "";
	}

}
