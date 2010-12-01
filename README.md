Contador de acessos a URL
=========================

Projeto Java que permite criar uma contagem das URLs mais acessadas.

Utilização
==========

Crie um arquivo chamado "mais-acessados.properties" no seu classpath (no / do projeto)
Coloque as URLs que deseja que sejam contadas, juntamente com sua descrição.
Ex.:
    /=Página Inicial
    /home=Home
    /contato.html=Contato
    /admin/pessoa.html=Contato

Depois, adicione o filtro no web.xml:
	<filter>
		<filter-name>Mais Acessados</filter-name>
		<filter-class>br.com.constantini.MaisAcessadosFilter</filter-class>
	</filter>

	<filter-mapping>
		<filter-name>Mais Acessados</filter-name>
		<url-pattern>/*</url-pattern>
		<dispatcher>REQUEST</dispatcher>
	</filter-mapping>


Examplos de utilização
======================

    //recupere o request
    HttpServletRequest request = getHttpServletRequest();

    //numero max de resultados 10 - Nao lista as URLs com zero acessos
    List<UrlGerenciada> urlMaisAcessadas = MaisAcessados.getCurrentContext( request ).getUrlGerenciadaListOrdenado(10);

ou

    //numero max de resultados 10
    //Mas listando pelo menos 10 URLs, mesmo com zero acessos (ordena alfabeticamente para as com zero acessos)
    List<UrlGerenciada> urlMaisAcessadas = MaisAcessados.getCurrentContext( request ).getUrlGerenciadaListOrdenadoComAcessos(10);




_Copyright (c) 2010 Ulisses Constantini, released under the MIT license_

