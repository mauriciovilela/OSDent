package com.odonto.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.odonto.jobs.JobExportacaoBI;

public class ServletBI extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		// Servlet initialization code here
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		JobExportacaoBI job = new JobExportacaoBI();
		//job.exportarAgendaBI(Integer.parseInt(request.getParameter("idDentista")));
		job.exportarPagamentosBI();
		
		response.setContentType("text/html");
		response.getWriter().println("<h1>Servlet OK</h1>");
	}

	@Override
	public void destroy() {
		// resource release
		super.destroy();
	}
}
