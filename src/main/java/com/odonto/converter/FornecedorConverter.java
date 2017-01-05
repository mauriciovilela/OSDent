package com.odonto.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.odonto.BLL.FornecedorBLL;
import com.odonto.model.TbFornecedor;
import com.odonto.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = TbFornecedor.class)
public class FornecedorConverter implements Converter {

	private FornecedorBLL bll;
	
	public FornecedorConverter() {
		bll = CDIServiceLocator.getBean(FornecedorBLL.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		TbFornecedor retorno = null;
		
		if (value != null) {
			Integer id = new Integer(value);
			retorno = bll.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && !value.equals("")) {
            Integer id = (( TbFornecedor) value).getId();
            return String.valueOf(id);
        }
		return "";
	}

}
