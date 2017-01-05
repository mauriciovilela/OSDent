package com.odonto.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.odonto.BLL.RetiradaBLL;
import com.odonto.model.TbRetirada;
import com.odonto.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = TbRetirada.class)
public class RetiradaConverter implements Converter {

	private RetiradaBLL bll;
	
	public RetiradaConverter() {
		bll = CDIServiceLocator.getBean(RetiradaBLL.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		TbRetirada retorno = null;
		
		if (value != null) {
			Integer id = new Integer(value);
			retorno = bll.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && !value.equals("")) {
            Integer id = (( TbRetirada) value).getId();
            return String.valueOf(id);
        }
		return "";
	}

}
