package br.com.constantini;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class MaisAcessadosFilter implements Filter{

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest)request;
			MaisAcessados.getCurrentContext(httpServletRequest).contarURI(httpServletRequest.getRequestURI());
		}catch (Exception e) {
			System.out.println("[Falha ao processas 'Mais Acessados']");
			e.printStackTrace();
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}

}
