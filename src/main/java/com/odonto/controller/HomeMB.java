package com.odonto.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import com.odonto.BLL.FornecedorBLL;
import com.odonto.BLL.MensagemBLL;
import com.odonto.BLL.PagamentoStatusBLL;
import com.odonto.BLL.ProcedimentoBLL;
import com.odonto.BLL.TipoPgtoBLL;
import com.odonto.BLL.UsuarioBLL;
import com.odonto.constants.Constants;
import com.odonto.dto.AcessosOUT;
import com.odonto.dto.UsuarioOUT;
import com.odonto.model.TbFornecedor;
import com.odonto.model.TbMensagem;
import com.odonto.model.TbPagamentoStatus;
import com.odonto.model.TbProcedimento;
import com.odonto.model.TbTipoPgto;
import com.odonto.security.SessionContext;
import com.odonto.service.MensagemService;
import com.odonto.util.Util;
import com.odonto.util.jsf.FacesUtil;

@Named
@SessionScoped
public class HomeMB implements Serializable {    

	private static final long serialVersionUID = -8529695076388473379L;

	private List<TbMensagem> mensagens;

	@Inject
	private MensagemService mensagemService;
	
	@Inject
	private MensagemBLL mensagemBLL;
	
	/**
	 * Combos que irao ficar na Sessao
	 */
	private static Logger logger = Logger.getLogger(HomeMB.class);

	@Inject
	private FornecedorBLL fornecedorBLL;
	private List<TbFornecedor> fornecedores;
	private List<TbFornecedor> fornecedoresCategoria;

	@Inject
	private ProcedimentoBLL procedimentoBLL;
	private List<TbProcedimento> procedimentos;

	@Inject
	private TipoPgtoBLL tipoPgtoBLL;
	private List<TbTipoPgto> tipoPgtos;

	@Inject
	private PagamentoStatusBLL pagamentoStatusBLL;
	private List<TbPagamentoStatus> pagamentoStatus;
	
	@Inject
	private UsuarioBLL usuarioBLL;
	private List<UsuarioOUT> usuarios;
	private List<UsuarioOUT> dentistas;
	private List<UsuarioOUT> socios;
	private List<AcessosOUT> acessosMenu;
	
	private boolean perfilDentista;
	private boolean perfilSocio;
	private boolean perfilSecretaria;


	@PostConstruct
	private void pageLoad() {
		if (FacesUtil.isNotPostback()) {
			consultarMensagens();
			inicializarCombos();
			// Monta o menu a esquerda, somente com itens de menu
			acessosMenu = SessionContext.getInstance().getMenu();
			String perfilLogado = SessionContext.getInstance().getUsuarioLogado().getDsPerfil();
			perfilDentista = perfilLogado.equals("DENTISTA");
			perfilSocio = perfilLogado.equals("SOCIO");
			perfilSecretaria = perfilLogado.equals("SECRETARIA");

			if (SessionContext.getInstance().getUsuarioLogado().getDsSenha().equals("698dc19d489c4e4db73e28a713eab07b")) {
				Util.redirect("/restrita/usuario/UsuarioTrocaSenha.xhtml");
			}
		}
	}

	public void inicializarCombos() {
		if (fornecedores == null) {
			logger.info("[LOG] Carregando todos os combos pra sessao ...");
			setFornecedores(fornecedorBLL.listar());
			fornecedoresCategoria = fornecedorBLL.listarCategorias();
			setProcedimentos(procedimentoBLL.listar());
			setTipoPgtos(tipoPgtoBLL.listar());
			setUsuarios(usuarioBLL.listar(Constants.ATIVOS));
			setDentistas(usuarioBLL.listarDentitas());
			setSocios(usuarioBLL.listarSocios());
			setPagamentoStatus(pagamentoStatusBLL.listarAtivos());
			logger.info("[LOG] Objetos carregados.");
		}
	}
    
    public String getCabecalho(TbMensagem item) {
    	String data = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(item.getDtInclusao());
    	StringBuilder msgCabecalho = new StringBuilder();
    	msgCabecalho.append("Mensagem de: ");
    	msgCabecalho.append(item.getTbSolicitante().getDsNome());
    	msgCabecalho.append(" em ");
    	msgCabecalho.append(data);
    	return msgCabecalho.toString();
    }
    
    private void consultarMensagens() {
		mensagens = mensagemBLL.listarNaoLidasPorUsuario();    	
    }
    
    public void marcarComoLida(TbMensagem selecionado) {
    	selecionado.setFlConcluido(true);
    	mensagemService.salvar(selecionado);
    	consultarMensagens();
    }

	public void modalHistoricoMensagem() {
		Util.abreModal("/restrita/mensagem/MensagemHistoricoMD");
	}
	
	public void carregarPagina(String nomePagina) {
	 ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	 String contextPath = externalContext.getRequestContextPath();
	 try {
	        externalContext.redirect(contextPath + nomePagina + ".xhtml");
	     } catch (Exception e)
	       {
	           e.printStackTrace();
	       }
	}
	
	public List<TbMensagem> getMensagens() {
		return mensagens;
	}

	public void setMensagens(List<TbMensagem> mensagens) {
		this.mensagens = mensagens;
	}

	public List<TbFornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<TbFornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public List<TbProcedimento> getProcedimentos() {
		return procedimentos;
	}

	public void setProcedimentos(List<TbProcedimento> procedimentos) {
		this.procedimentos = procedimentos;
	}

	public List<TbTipoPgto> getTipoPgtos() {
		return tipoPgtos;
	}

	public void setTipoPgtos(List<TbTipoPgto> tipoPgtos) {
		this.tipoPgtos = tipoPgtos;
	}

	public List<UsuarioOUT> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioOUT> usuarios) {
		this.usuarios = usuarios;
	}

	public List<UsuarioOUT> getDentistas() {
		return dentistas;
	}

	public void setDentistas(List<UsuarioOUT> dentistas) {
		this.dentistas = dentistas;
	}

	public List<UsuarioOUT> getSocios() {
		return socios;
	}

	public void setSocios(List<UsuarioOUT> socios) {
		this.socios = socios;
	}

	public String getNomeUsuario() {
		return SessionContext.getInstance().getUsuarioLogado().getDsNome();
	}

	public List<AcessosOUT> getAcessosMenu() {
		return acessosMenu;
	}

	public void setAcessosMenu(List<AcessosOUT> acessosMenu) {
		this.acessosMenu = acessosMenu;
	}

	public boolean isPerfilDentista() {
		return perfilDentista;
	}

	public void setPerfilDentista(boolean perfilDentista) {
		this.perfilDentista = perfilDentista;
	}

	public boolean isPerfilSocio() {
		return perfilSocio;
	}

	public void setPerfilSocio(boolean perfilSocio) {
		this.perfilSocio = perfilSocio;
	}

	public boolean isPerfilSecretaria() {
		return perfilSecretaria;
	}

	public void setPerfilSecretaria(boolean perfilSecretaria) {
		this.perfilSecretaria = perfilSecretaria;
	}

	public List<TbPagamentoStatus> getPagamentoStatus() {
		return pagamentoStatus;
	}

	public void setPagamentoStatus(List<TbPagamentoStatus> pagamentoStatus) {
		this.pagamentoStatus = pagamentoStatus;
	}

	public List<TbFornecedor> getFornecedoresCategoria() {
		return fornecedoresCategoria;
	}

	public void setFornecedoresCategoria(List<TbFornecedor> fornecedoresCategoria) {
		this.fornecedoresCategoria = fornecedoresCategoria;
	}

}