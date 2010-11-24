package br.com.constantini;

public class UrlGerenciada implements Comparable<UrlGerenciada> {

	private int acessos = 0;
	private String chave;
	private String descricao;
	
	public UrlGerenciada(String chave, String descricao) {
		this.chave = chave;
		this.descricao = descricao;
	}
	public String getChave() {
		return chave;
	}
	public String getDescricao() {
		return descricao;
	}
	public int getAcessos() {
		return acessos;
	}
	public void incrementaAcessos(){
		acessos++;
	}
	public void resetaAcessos(){
		acessos++;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UrlGerenciada){
			return ((UrlGerenciada)obj).getChave().equals(this.chave);
		}
		return super.equals(obj);
	}
	
	
	@Override
	public int compareTo(UrlGerenciada o) {
		if (acessos != o.getAcessos())
			return o.getAcessos() - acessos;
		return chave.compareTo(o.getChave());
	}
}
