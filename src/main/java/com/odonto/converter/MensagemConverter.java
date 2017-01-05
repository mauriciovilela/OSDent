package com.odonto.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.odonto.BLL.MensagemBLL;
import com.odonto.model.TbMensagem;
import com.odonto.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = TbMensagem.class)
public class MensagemConverter implements Converter {

	private MensagemBLL mensagemBLL;
	
	public MensagemConverter() {
		mensagemBLL = CDIServiceLocator.getBean(MensagemBLL.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		TbMensagem retorno = null;
		
		if (value != null) {
			Integer id = new Integer(value);
			retorno = mensagemBLL.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && !value.equals("")) {
            Integer id = (( TbMensagem) value).getId();
            return String.valueOf(id);
        }
		return "";
	}

}
