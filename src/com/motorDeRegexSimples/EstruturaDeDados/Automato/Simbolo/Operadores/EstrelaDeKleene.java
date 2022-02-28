package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;

public class EstrelaDeKleene implements Simbolo {

	@Override
	public String getValor() {
		return "*";
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
