package br.com.constantini;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class MaisAcessados {

	private final List<UrlGerenciada> urlGerenciadaList = new ArrayList<UrlGerenciada>();
	private static final String MAIS_ACESSADOS = "_MAIS_ACESSADOS_";
	private static final Properties properties = new Properties();
	
	//log
	private Logger logger = Logger.getLogger(MaisAcessados.class);
	
	private HttpServletRequest request;
	
	private MaisAcessados(HttpServletRequest request){
		this.request = request;
		try {
			
			properties.load( this.getClass().getResourceAsStream ("/mais-acessados.properties") );
		
			/* load url gerenciada list */
			for(Object key : properties.keySet()){
				String keyString = String.valueOf(key); 
				urlGerenciadaList.add(new UrlGerenciada(
						keyString, properties.getProperty(keyString)
				));
			}
		
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		} 
		
	}
	
	protected void contarURI(String requestURI){
		
		String requestURILimpa = limpaURLdoRequest( requestURI );
		logger.info("URL ACESSADA:       " + requestURILimpa);

		UrlGerenciada indicadorUri = buscaNivel1( requestURILimpa );
		
		if (indicadorUri == null)
			indicadorUri = buscaNivel2( requestURILimpa );
		
		if (indicadorUri == null)
			return;
		
		indicadorUri.incrementaAcessos();
	}
	
	
	public final List<UrlGerenciada> getUrlGerenciadaListOrdenado(Integer... limit) {
		Collections.sort(urlGerenciadaList);
		if(limit[0] != null){
			int max = limit[0] >= 0 ? limit[0] : 0;
			if (urlGerenciadaList.size() > max)
				return urlGerenciadaList.subList(0, max);
		}
			
		return urlGerenciadaList;
	}
	
	public final List<UrlGerenciada> getUrlGerenciadaListOrdenadoComAcessos(Integer... limit) {
		List<UrlGerenciada> urlGerenciadaListComAcessos = new ArrayList<UrlGerenciada>();
		for (UrlGerenciada indicadorUri : urlGerenciadaList){
			if (indicadorUri.getAcessos() > 0)
				urlGerenciadaListComAcessos.add( indicadorUri );
		}
		
		Collections.sort( urlGerenciadaListComAcessos );
		
		if(limit[0] != null){
			int max = limit[0] >= 0 ? limit[0] : 0;
			if (urlGerenciadaListComAcessos.size() > max)
				return urlGerenciadaListComAcessos.subList(0, max);
		}
		
		return urlGerenciadaListComAcessos ;
	}
	
	/**
	 * Retorna a instancia corrente
	 * @return
	 */
	public static final MaisAcessados getCurrentContext(HttpServletRequest request) {
		MaisAcessados maisAcessados = (MaisAcessados) request.getSession().getAttribute(MAIS_ACESSADOS);
		if (maisAcessados == null) {
			maisAcessados = new MaisAcessados(request);
			request.getSession().setAttribute(MAIS_ACESSADOS, maisAcessados);
		}
		return maisAcessados;
	}
	
	/**
	 * Limpa URL (Deixa apenas o caminho apos o path da aplicacao)
	 * 
	 * @param urlCompleta
	 * @return
	 */
	private String limpaURLdoRequest(String urlCompleta){
		try {
			URI uri = new URI(urlCompleta.toString());
			
			String path = request.getContextPath() != null ? request.getContextPath() : "";
			
			String uriReal = uri.getPath().substring(path.length());
			return uriReal;
		} catch (URISyntaxException e) {
			logger.error("ERRO AO RECUPERAR URI", e);
		}
		return null;
	}
	
	
	
	/**
	 * Busca nivel 1
	 * @param uri
	 * @return
	 */
	private UrlGerenciada buscaNivel1(String uri){
		UrlGerenciada indicadorUriEncontrado = null;
		
		for(UrlGerenciada indicadorUri : urlGerenciadaList){
			if (indicadorUri.getChave().equals(uri)){
				logger.debug("BUSCA 1 - VALOR: "+ uri);
				indicadorUriEncontrado = indicadorUri;
				break;
			}
		}
		return indicadorUriEncontrado;
	}
	
	
	
	/**
	 * Busca nivel 2
	 * @param uri
	 * @return
	 */
	private UrlGerenciada buscaNivel2(String uri){
		UrlGerenciada indicadorUriEncontrado = null;
		
		String[] split = uri.split("/");
		if (split.length == 0)
			return null;
		
		String requestName = split[split.length - 1];
		
		split = requestName.split("\\.");
		requestName = split[0];
		
		for(UrlGerenciada indicadorUri : urlGerenciadaList){
			if (indicadorUri.getChave().toLowerCase().contains( requestName.toLowerCase() )){
				logger.debug("BUSCA 2 - VALOR: "+ requestName.toLowerCase());
				indicadorUriEncontrado = indicadorUri;
				break;
			}
		}
		return indicadorUriEncontrado;
	}
}

