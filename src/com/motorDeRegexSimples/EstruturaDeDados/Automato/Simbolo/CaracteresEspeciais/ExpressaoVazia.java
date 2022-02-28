package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.CaracteresEspeciais;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;

public class ExpressaoVazia implements Simbolo {

	private char valor = 'ε';

	public char getValor() {
		return this.valor;
	}

	@Override
	public boolean equals(Object obj) {
		return true;
	}

}
