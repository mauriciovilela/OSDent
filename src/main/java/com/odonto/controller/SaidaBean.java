package com.odonto.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.odonto.BLL.FechamentoCaixaBLL;
import com.odonto.BLL.SaidaBLL;
import com.odonto.BLL.UsuarioBLL;
import com.odonto.constants.Constants;
import com.odonto.model.TbFechamentoCaixa;
import com.odonto.model.TbFornecedor;
import com.odonto.model.TbSaida;
import com.odonto.service.NegocioException;
import com.odonto.service.SaidaService;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class SaidaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private SaidaService saidaService;

	@Inject
	private SaidaBLL bllSaida;

	@Inject
	private UsuarioBLL bllUsuario;
	
	private TbSaida saida;
	
	@NotNull
	private TbFornecedor fornecedor;

	@Inject
	private FechamentoCaixaBLL fechamentoCaixaBLL;
	
	private BigDecimal valorPorcentagem;
	private BigDecimal valorPago;
	private Boolean temPorcentagem;
	private Boolean temSocioResponsavel;
	private Integer idSocioINSS;
	
	private Boolean fluxoCaixa;

	public void inicializar(ComponentSystemEvent ev) {
		if (FacesUtil.isNotPostback()) {
			fornecedor = new TbFornecedor();
			if (saida.getId() == null)
				fluxoCaixa = true;
			else
				fluxoCaixa = saida.getFlFluxoCaixa();
		}
	}
	
	public SaidaBean() {
		limpar();
	}
	
	private void limpar() {
		setSaida(new TbSaida());
		fornecedor = null;
		temSocioResponsavel = false;
	}
	
	public void atualizaValores() {
		if (valorPorcentagem != null && this.saida.getVlTotal() != null) {			
			valorPago = this.saida.getVlTotal().multiply(valorPorcentagem);
		}
	}
	
	public void salvar() {
		validaSalvar();
		this.saida.setFlFluxoCaixa(fluxoCaixa);
		if (valorPorcentagem == null) {
			this.saida.setVlPago(this.saida.getVlTotal());			
		}
		else {
			this.saida.setVlPago(this.saida.getVlTotal().multiply(valorPorcentagem));			
		}
		if (temSocioResponsavel) {
			this.saida.setTbSocioInss(bllUsuario.porId(idSocioINSS));
		}
		saida = saidaService.salvar(this.saida);
		limpar();		
		FacesUtil.addInfoMessage(Constants.msgSucesso);
	}
	
	private void validaSalvar() {
		// Regra de porcentagem
		if (temPorcentagem) {
			if (valorPorcentagem == null || this.valorPago == null) {
				throw new NegocioException("Informe a Porcentagem e Valor Pago.");
			}
		}
		if (temSocioResponsavel) {
			if (idSocioINSS == null) {
				throw new NegocioException("Informe o Sócio Responsável.");
			}
		}
		
		// Regra de duplicidade
		Integer idTipo = saida.getTbFornecedor().getId();
		List<TbSaida> lista = bllSaida.porTipoDataValor(idTipo, saida.getDtCompra(), saida.getVlTotal());
		if (lista.size() > 0) {
			throw new NegocioException("Já existe uma despesa cadastrada com estas mesmas informações. Verifique no fluxo de caixa.");
		}

		// Regra lançamento em fluxo de caixa fechado
		TbFechamentoCaixa caixa = fechamentoCaixaBLL.listarPorData(saida.getDtCompra());
		if (caixa != null) {
			throw new NegocioException("Não é permitido lançar despesas em fluxo de caixa já fechado.");
		}
	}
	
	public void carregaDados() {
		temPorcentagem = saida.getTbFornecedor().getFlPorcentagem();		
		if (!saida.getTbFornecedor().getFlPorcentagem()) {
			valorPorcentagem = null;
		}
		temSocioResponsavel = (saida.getTbFornecedor().getFlSocioResponsavel());
	}

	public TbSaida getSaida() {
		return saida;
	}

	public void setSaida(TbSaida saida) {
		this.saida = saida;
	}

	public boolean isEditando() {
		return this.saida.getId() != null;
	}

	public TbFornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(TbFornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Boolean getFluxoCaixa() {
		return fluxoCaixa;
	}

	public void setFluxoCaixa(Boolean fluxoCaixa) {
		this.fluxoCaixa = fluxoCaixa;
	}

	public BigDecimal getValorPorcentagem() {
		return valorPorcentagem;
	}

	public void setValorPorcentagem(BigDecimal valorPorcentagem) {
		this.valorPorcentagem = valorPorcentagem;
	}

	public Boolean getTemPorcentagem() {
		return temPorcentagem;
	}

	public void setTemPorcentagem(Boolean temPorcentagem) {
		this.temPorcentagem = temPorcentagem;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public Boolean getTemSocioResponsavel() {
		return temSocioResponsavel;
	}

	public void setTemSocioResponsavel(Boolean temSocioResponsavel) {
		this.temSocioResponsavel = temSocioResponsavel;
	}

	public Integer getIdSocioINSS() {
		return idSocioINSS;
	}

	public void setIdSocioINSS(Integer idSocioINSS) {
		this.idSocioINSS = idSocioINSS;
	}


	
}
