package com.odonto.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.odonto.BLL.FechamentoCaixaBLL;
import com.odonto.model.TbFechamentoCaixa;
import com.odonto.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = TbFechamentoCaixa.class)
public class FechamentoCaixaConverter implements Converter {

	private FechamentoCaixaBLL bll;
	
	public FechamentoCaixaConverter() {
		bll = CDIServiceLocator.getBean(FechamentoCaixaBLL.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		TbFechamentoCaixa retorno = null;
		
		if (value != null) {
			Integer id = new Integer(value);
			retorno = bll.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && !value.equals("")) {
            Integer id = (( TbFechamentoCaixa) value).getId();
            return String.valueOf(id);
        }
		return "";
	}

}
