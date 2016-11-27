package com.odonto.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.odonto.BLL.PagamentoBLL;
import com.odonto.model.TbPagamento;
import com.odonto.util.jpa.Transactional;

public class PagamentoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PagamentoBLL bll;

//	@Inject
//	private PagamentoParcelaBLL bllParcela;
	
	@Transactional
	public TbPagamento salvar(TbPagamento item) {	
		TbPagamento pagamento = bll.guardar(item);
//		if (item.getQtParcelas() > 0) {
//			for (int nrParcela = 1; nrParcela <= item.getQtParcelas(); nrParcela++) {
//				TbPagamentoParcela itemParcela = new TbPagamentoParcela();
//				itemParcela.setNrParcela(nrParcela);
//				itemParcela.setVlParcela(item.getVlParcela());
//				itemParcela.setDtCredito(adicionaMes(item.getDtEntrada()));
//				itemParcela.setTbPagamento(pagamento);
//				bllParcela.guardar(itemParcela);
//			}
//		}
		return pagamento;
	}
	
//	private Date adicionaMes(Date data) {
//		Calendar calData = Calendar.getInstance();
//		calData.setTime(data);
//		calData.add(Calendar.MONTH, 1);
//		return calData.getTime();
//	}
	
}
