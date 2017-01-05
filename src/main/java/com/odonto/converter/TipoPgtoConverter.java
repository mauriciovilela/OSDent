package com.odonto.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.odonto.BLL.TipoPgtoBLL;
import com.odonto.model.TbTipoPgto;
import com.odonto.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = TbTipoPgto.class)
public class TipoPgtoConverter implements Converter {

	private TipoPgtoBLL TipoPgtoBLL;
	
	public TipoPgtoConverter() {
		TipoPgtoBLL = CDIServiceLocator.getBean(TipoPgtoBLL.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		TbTipoPgto retorno = null;
		
		if (value != null) {
			Integer id = new Integer(value);
			retorno = TipoPgtoBLL.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && !value.equals("")) {
            Integer id = (( TbTipoPgto) value).getId();
            return String.valueOf(id);
        }
		return "";
	}

}
