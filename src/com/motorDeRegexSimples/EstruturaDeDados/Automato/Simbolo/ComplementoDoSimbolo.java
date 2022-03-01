package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo;

public class ComplementoDoSimbolo implements Simbolo {

	private Simbolo simbolo;

	public ComplementoDoSimbolo(Simbolo simbolo) {
		this.simbolo = simbolo;
	}

	@Override
	public String getValor() {
		return simbolo.getValor().toUpperCase();
	}

	@Override
	public boolean equals(Object obj) {
		return !simbolo.equals(obj);
	}

	@Override
	public boolean isEquivalenteAoChar(char valor) {
		return !simbolo.isEquivalenteAoChar(valor);
	}

}
