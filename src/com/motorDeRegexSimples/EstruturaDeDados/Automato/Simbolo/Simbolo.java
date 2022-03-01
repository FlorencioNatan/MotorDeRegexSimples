package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo;

public interface Simbolo {

	public String getValor();

	public boolean equals(Object obj);

	public boolean isEquivalenteAoChar(char valor);

}
