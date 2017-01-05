package com.odonto.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.odonto.BLL.SaidaBLL;
import com.odonto.model.TbSaida;
import com.odonto.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = TbSaida.class)
public class SaidaConverter implements Converter {

	private SaidaBLL bll;
	
	public SaidaConverter() {
		bll = CDIServiceLocator.getBean(SaidaBLL.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		TbSaida retorno = null;
		
		if (value != null) {
			Integer id = new Integer(value);
			retorno = bll.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && !value.equals("")) {
            Integer id = (( TbSaida) value).getId();
            return String.valueOf(id);
        }
		return "";
	}

}
