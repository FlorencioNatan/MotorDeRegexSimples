package com.motorDeRegexSimples.EstruturaDeDados.Automato;

public class Transicao {

	private Caractere caractere;
	private int estadoDestino;

	public Transicao(Caractere caractere, int estadoDestino) {
		this.caractere = caractere;
		this.estadoDestino = estadoDestino;
	}

	public Caractere getCaractere() {
		return this.caractere;
	}

	public int getEstadoDestino() {
		return this.estadoDestino;
	}

	public void setEstadoDestino(int estadoDestino) {
		this.estadoDestino = estadoDestino;
	}

}
