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

import com.odonto.BLL.PacienteBLL;
import com.odonto.BLL.UsuarioBLL;
import com.odonto.controller.LoginMB;
import com.odonto.dto.ConfigOUT;
import com.odonto.dto.UsuarioOUT;
import com.odonto.model.TbAuditoria;
import com.odonto.model.TbPaciente;
import com.odonto.service.AuditoriaService;
import com.odonto.util.AppConfigProperties;
import com.odonto.util.Util;

@Scheduled(cronExpression = "0 0 10 * * ?")
public class JobAniversario implements org.quartz.Job {
	
	// */10 * * * * * = a cada 10 segundos
	// 0 0 19 * * ? = as 19 horas
	// 0/10 * * * * ? = A cada 10 segundos

	@Inject
	private UsuarioBLL usuarioBLL;

	@Inject
	private PacienteBLL pacienteBLL;

	@Inject
	private AuditoriaService auditoriaService;

	private static Logger logger = Logger.getLogger(LoginMB.class);

	private StringBuilder strAniversariantes = new StringBuilder();

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
		boolean temAniversario = false;
		List<TbPaciente> aniversariantes = pacienteBLL.listarAniversariantes();
		for (TbPaciente paciente : aniversariantes) {
			if (Util.getDiaMes(paciente.getDtNascimento()).equals(Util.getDiaMes(new Date()))) {
				if (StringUtils.isNotBlank(paciente.getDsEmail())) {
					temAniversario = true;
					enviarEmailPacienteAniversario(paciente);
				}
			}
		}
		if (temAniversario) {
			// Grava log de auditoria
			TbAuditoria audit = new TbAuditoria();
			String mensagem = "Email enviado p/ o(s) aniversariante(s): " + strAniversariantes.toString();
			audit.setDsDescricao(mensagem);
			auditoriaService.salvar(audit);
			enviarEmailSocios(mensagem);
		}
	}

	@SuppressWarnings("deprecation")
	private void enviarEmailPacienteAniversario(TbPaciente paciente) {
		try {
			strAniversariantes.append(paciente.getDsNome() + "/" + paciente.getDsEmail() + ", ");
			HtmlEmail email = new HtmlEmail();
			email.setHostName(config.getMailHost());
			email.setSmtpPort(config.getMailPort());
			email.addTo(paciente.getDsEmail(), paciente.getDsNome());
			email.setFrom(config.getMailEmail(), config.getMailNome());
			email.setSubject("Parabéns!");
			email.setHtmlMsg(getTemplateHtml());
			email.setSSL(true);
			email.setAuthentication(config.getMailUser(), config.getMailPassword());
			email.send();
		} catch (Exception e) {
			logger.error(e);
		}
	}

	private String getTemplateHtml() {
		StringBuilder strHtml = new StringBuilder();
		strHtml.append("<table align='center' border='0' cellpadding='0' cellspacing='0' style='width:661px'>");
		strHtml.append("	<tbody>");
		strHtml.append("		<tr>");
		strHtml.append(
				"			<td colspan='3'><img src='http://emailmarketingtemplates.com.br/templates/datas-comemorativas/aniversario-verde/1.jpg' border='0' style='display:block;'></td>");
		strHtml.append("		</tr>");
		strHtml.append("		<tr>");
		strHtml.append(
				"			<td style='width:167px'><img src='http://emailmarketingtemplates.com.br/templates/datas-comemorativas/aniversario-verde/2.jpg' border='0' style='display:block;'></td>");
		strHtml.append("			<td valign='top'  style='width:385px;'>");
		strHtml.append(
				"				<table border='0' align='center' cellpadding='0' cellspacing='0' height='465' width='370' style='width:370px;height:465px;'>");
		strHtml.append("					<tbody>");
		strHtml.append("						<tr>");
		strHtml.append(
				"							<td align='center' style='font-family:Arial;font-size:45px;color:#aa80b2;'>Feliz</td>");
		strHtml.append("						</tr>");
		strHtml.append("						<tr>");
		strHtml.append(
				"							<td align='center' style='font-family:Arial;font-size:45px;color:#01c08c;padding-bottom:15px;'>Aniversário</td>");
		strHtml.append("						</tr>");
		strHtml.append("						<tr>");
		strHtml.append(
				"							<td valign='top' align='center' height='270' style='height:270px;font-family:Arial;font-size:18px;color:#999999;'>");
		strHtml.append(
				"							Felicidades para você, por este dia tão especial que é o seu aniversário. Ass: Dr. Orestes e Dra. Simone");
		strHtml.append("							</td>");
		strHtml.append("						</tr>");
		strHtml.append("					</tbody>");
		strHtml.append("				</table>");
		strHtml.append("			</td>");
		strHtml.append(
				"			<td style='width:109px'><img src='http://emailmarketingtemplates.com.br/templates/datas-comemorativas/aniversario-verde/3.jpg' border='0' style='display:block;'></td>");
		strHtml.append("		</tr>");
		strHtml.append("		<tr>");
		strHtml.append(
				"			<td valign='top' colspan='3'><img src='http://emailmarketingtemplates.com.br/templates/datas-comemorativas/aniversario-verde/4.jpg' border='0' style='display:block;'></td>");
		strHtml.append("		</tr>");
		strHtml.append("	</tbody>");
		strHtml.append("</table>");
		return strHtml.toString();
	}

	@SuppressWarnings("deprecation")
	private void enviarEmailSocios(String mensagem) {
		try {
			List<UsuarioOUT> socios = usuarioBLL.listarDentitas();
			// E-mail dos sócios para lembrete dos vencimentos
			for (UsuarioOUT socio : socios) {
				// Verifica se tem e-mail
				if (StringUtils.isNotBlank(socio.getDsEmail())) {
					HtmlEmail email = new HtmlEmail();
					email.setHostName(config.getMailHost());
					email.setSmtpPort(config.getMailPort());
					email.addTo(socio.getDsEmail(), "Aniversariantes");
					email.setFrom(config.getMailEmail(), config.getMailNome());
					email.setSubject("Aniversariantes do dia");
					email.setHtmlMsg(mensagem);
					email.setSSL(true);
					email.setAuthentication(config.getMailUser(), config.getMailPassword());
					email.send();
				}
			}
		} catch (EmailException e) {
			logger.error(e);
		}
	}

}
