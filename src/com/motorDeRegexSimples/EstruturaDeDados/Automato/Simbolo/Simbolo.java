package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo;

public interface Simbolo {

	public char getValor();

	@Override
	public boolean equals(Object obj);

	public boolean isEquivalenteAoChar(char valor);

}
