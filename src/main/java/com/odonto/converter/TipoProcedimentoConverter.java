package com.odonto.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.odonto.BLL.TipoProcedimentoBLL;
import com.odonto.model.TbTipoProcedimento;
import com.odonto.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = TbTipoProcedimento.class)
public class TipoProcedimentoConverter implements Converter {

	private TipoProcedimentoBLL tipoProcedimentoBLL;
	
	public TipoProcedimentoConverter() {
		tipoProcedimentoBLL = CDIServiceLocator.getBean(TipoProcedimentoBLL.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		TbTipoProcedimento retorno = null;
		if (value != null) {
			Integer id = new Integer(value);
			retorno = tipoProcedimentoBLL.porId(id);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && !value.equals("")) {
            Integer id = (( TbTipoProcedimento) value).getId();
            return String.valueOf(id);
        }
		return "";
	}

}
