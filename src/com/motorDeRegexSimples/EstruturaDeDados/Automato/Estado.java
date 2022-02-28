package com.motorDeRegexSimples.EstruturaDeDados.Automato;

public class Estado {

	private int idEstado;
	private Transicoes transicoes;
	private Estado proximoEstado;

	public Estado(int idEstado) {
		this.idEstado = idEstado;
		transicoes = new Transicoes();
		proximoEstado = null;
	}

	public int getIdEstado() {
		return idEstado;
	}

	public Transicoes getTransicoes() {
		return transicoes;
	}

	public void setTransicoes(Transicoes transicoes) {
		this.transicoes = transicoes;
	}

	public boolean hasProximoEstado() {
		return proximoEstado != null;
	}

	public Estado getProximoEstado() {
		return proximoEstado;
	}

	public void setProximoEstado(Estado proximoEstado) {
		this.proximoEstado = proximoEstado;
	}

}
