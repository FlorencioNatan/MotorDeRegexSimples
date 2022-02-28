package com.motorDeRegexSimples.EstruturaDeDados.Automato;

public class Estado {

	int idEstado;
	Transicoes transicoes;

	public Estado(int idEstado) {
		this.idEstado = idEstado;
		transicoes = new Transicoes();
	}

	public int getIdEstado() {
		return idEstado;
	}

	public Transicoes getTransicoes() {
		return transicoes;
	}

}
