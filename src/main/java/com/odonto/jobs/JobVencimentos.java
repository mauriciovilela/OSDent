package com.odonto.jobs;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.deltaspike.scheduler.api.Scheduled;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;

import com.odonto.BLL.ContaBLL;
import com.odonto.BLL.UsuarioBLL;
import com.odonto.dto.ConfigOUT;
import com.odonto.dto.UsuarioOUT;
import com.odonto.model.TbAuditoria;
import com.odonto.model.TbConta;
import com.odonto.service.AuditoriaService;
import com.odonto.util.AppConfigProperties;
import com.odonto.util.Util;

@Scheduled(cronExpression = "0 0 9 * * ?")
public class JobVencimentos implements org.quartz.Job {
	
	// */10 * * * * * = a cada 10 segundos
	// 0 0 19 * * ? = as 19 horas
	// 0/10 * * * * ? = A cada 10 segundos

	@Inject
	private ContaBLL contaBLL;

	@Inject
	private UsuarioBLL usuarioBLL;

	@Inject
	private AuditoriaService auditoriaService;

	private static Logger logger = Logger.getLogger(JobVencimentos.class);

	private StringBuilder strContas = new StringBuilder();

	private ConfigOUT config = null; 

	@Override
	public void execute(JobExecutionContext context) {
		AppConfigProperties prop = new AppConfigProperties();
		config = prop.init();
		if (config.isMailEnvio()) {
			enviaEmail();
		}
	}

	private void enviaEmail() {
		boolean temVencimento = false;
		List<TbConta> vencimentos = contaBLL.listar();
		for (TbConta conta : vencimentos) {
			if (Util.getDiaMes(conta.getDtVencimento()).equals(Util.getDiaMes(new Date()))) {
				temVencimento = true;
				enviarEmailSocios(conta);
			}
		}
		if (temVencimento) {
			// Grava log de auditoria
			TbAuditoria audit = new TbAuditoria();
			audit.setDsDescricao("Email enviado com os vencimentos: " + strContas.toString());
			auditoriaService.salvar(audit);
		}
	}

	@SuppressWarnings("deprecation")
	private void enviarEmailSocios(TbConta conta) {
		try {
			List<UsuarioOUT> socios = usuarioBLL.listarDentitas();
			// E-mail dos sócios para lembrete dos vencimentos
			for (UsuarioOUT socio : socios) {
				// Verifica se tem e-mail
				if (StringUtils.isNotBlank(socio.getDsEmail())) {
					strContas.append(conta.getDsDescricao() + ", ");
					HtmlEmail email = new HtmlEmail();
					email.setHostName(config.getMailHost());
					email.setSmtpPort(config.getMailPort());
					email.addTo(socio.getDsEmail(), "Contas");
					email.setFrom(config.getMailEmail(), config.getMailNome());
					email.setSubject("Contas a vencer - Clínica");
					email.setHtmlMsg("Atenção, a conta " + conta.getDsDescricao() + " irá vencer no dia "
							+ Util.getData(conta.getDtVencimento()));
					email.setSSL(true);
					email.setAuthentication(config.getMailUser(), config.getMailPassword());
					email.send();
				}
			}
		} catch (EmailException e) {
			logger.error("Erro ao enviar e-mail vencimentos: " + e);
		}
	}

}
