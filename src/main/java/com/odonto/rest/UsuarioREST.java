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

import com.odonto.BLL.UsuarioBLL;
import com.odonto.constants.Constants;
import com.odonto.dto.UsuarioOUT;
import com.odonto.model.TbUsuario;
import com.odonto.service.UsuarioService;

@Path("/usuarios")
public class UsuarioREST {

	@Inject
	private UsuarioBLL usuarioBLL;

	@Inject
	private UsuarioService usuarioService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar() {
		return Response.ok().entity(usuarioBLL.listar(Constants.TODOS)).build();
	}

	@GET
	@Path("/dentistas")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarDentistas() {
		return Response.ok().entity(usuarioBLL.listarDentitas()).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response inserir(UsuarioOUT item) {
		TbUsuario obj = new TbUsuario();
		obj.setDsSenha("698dc19d489c4e4db73e28a713eab07b");
		obj.setDsEmail(item.getDsEmail());
		obj.setDsNome(item.getDsNome());
		obj.setDsUsuario(item.getDsUsuario());
		obj.setFlDentista(item.getFlDentista());
		obj.setFlSocio(item.getFlSocio());
		obj = usuarioService.salvar(obj);
		return Response.status(Status.CREATED).entity(obj).build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response atualizar(UsuarioOUT item) {
		TbUsuario obj = usuarioBLL.porId(item.getId());
		obj.setDsEmail(item.getDsEmail());
		obj.setDsNome(item.getDsNome());
		obj.setDsUsuario(item.getDsUsuario());
		obj.setFlDentista(item.getFlDentista());
		obj.setFlSocio(item.getFlSocio());
		obj = usuarioService.salvar(obj);
		return Response.ok().entity(obj).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response porID(@PathParam("id") Integer id) {
		return Response.ok().entity(usuarioBLL.porId(id)).build();
	}

}
