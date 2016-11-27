package com.odonto.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.odonto.BLL.ContaBLL;
import com.odonto.model.TbConta;
import com.odonto.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = TbConta.class)
public class ContaConverter implements Converter {

	private ContaBLL bll;
	
	public ContaConverter() {
		bll = CDIServiceLocator.getBean(ContaBLL.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		TbConta retorno = null;
		
		if (value != null) {
			Integer id = new Integer(value);
			retorno = bll.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && !value.equals("")) {
            Integer id = (( TbConta) value).getId();
            return String.valueOf(id);
        }
		return "";
	}

}
