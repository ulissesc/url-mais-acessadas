package br.com.constantini;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


public class MaisAcessadosFilter implements Filter{

	private Logger logger = Logger.getLogger(MaisAcessadosFilter.class);
	
	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest)request;
			MaisAcessados.getCurrentContext(httpServletRequest).contarURI(httpServletRequest.getRequestURI());
		}catch (Exception e) {
			logger.error("[Falha ao processas 'Mais Acessados']", e);
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}

}
