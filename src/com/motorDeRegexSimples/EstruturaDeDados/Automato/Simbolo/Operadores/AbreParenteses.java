package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;

public class AbreParenteses implements Simbolo {

	@Override
	public char getValor() {
		return '(';
	}

	@Override
	public boolean equals(Object obj) {
		return false;
	}

	@Override
	public boolean isEquivalenteAoChar(char valor) {
		return false;
	}

}
