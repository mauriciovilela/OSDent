package com.odonto.service;

import java.io.Serializable;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.odonto.BLL.PacienteBLL;
import com.odonto.jobs.JobAgenda;
import com.odonto.model.TbPaciente;
import com.odonto.util.jpa.Transactional;

public class PacienteService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PacienteBLL usuarios;

	private static Logger logger = Logger.getLogger(JobAgenda.class);

	@Transactional
	public TbPaciente salvar(TbPaciente item) {	
		try {			
			return usuarios.guardar(item);
		}
		catch (Exception ex) {
			logger.error(ex);
			if (ex.getMessage().contains("Duplicate entry")) {
				throw new NegocioException("Paciente já está cadastrado.");	
			}
			throw new NegocioException("Ocorreu um erro ao cadastrar o Paciente.");	
		}
	}
	
}
