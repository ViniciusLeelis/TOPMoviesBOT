package br.com.filmes.model;

import java.text.SimpleDateFormat;

public class Historic {
	
	private long chatId;
	private String nomeFilme;
	private String generoFilme;
	private String diaPesquisado;
	
	
	public Historic(long chatId, String nomeFilme, String generoFilme, String diaPesquisado) {
		super();
		this.chatId = chatId;
		this.nomeFilme = nomeFilme;
		this.generoFilme = generoFilme;
		this.diaPesquisado = diaPesquisado;
	}
	
	public long getChatId() {
		return chatId;
	}
	public void setChatId(long chatId) {
		this.chatId = chatId;
	}
	public String getNomeFilme() {
		return nomeFilme;
	}
	public void setNomeFilme(String nomeFilme) {
		this.nomeFilme = nomeFilme;
	}
	public String getGeneroFilme() {
		return generoFilme;
	}
	public void setGeneroFilme(String generoFilme) {
		this.generoFilme = generoFilme;
	}

	public String getDiaPesquisado() {
		return diaPesquisado;
	}

	public void setDiaPesquisado(String diaPesquisado) {
		this.diaPesquisado = diaPesquisado;
	}
	
	
}
