package com.motorDeRegexSimples.EstruturaDeDados.Automato;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;

public class Transicao {

	private Simbolo simbolo;
	private int estadoDestino;

	public Transicao(Simbolo simbolo, int estadoDestino) {
		this.simbolo = simbolo;
		this.estadoDestino = estadoDestino;
	}

	public Simbolo getSimbolo() {
		return this.simbolo;
	}

	public int getEstadoDestino() {
		return this.estadoDestino;
	}

	public void setEstadoDestino(int estadoDestino) {
		this.estadoDestino = estadoDestino;
	}

}
