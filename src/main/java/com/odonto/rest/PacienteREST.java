package com.odonto.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.odonto.BLL.PacienteBLL;
import com.odonto.model.TbPaciente;
import com.odonto.service.PacienteService;

@Path("/pacientes")
public class PacienteREST {

	@Inject
	private PacienteBLL pacienteBLL;

	@Inject
	private PacienteService pacienteService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar() {
		return Response.ok().entity(pacienteBLL.listar()).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response inserir(TbPaciente item) {
		return Response.status(Status.CREATED).entity(pacienteService.salvar(item)).build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response atualizar(TbPaciente item) {
		return Response.ok().entity(pacienteService.salvar(item)).build();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response porID(@PathParam("id") Integer id) {
		return Response.ok().entity(pacienteBLL.porId(id)).build();
	}

}
