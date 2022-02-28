package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.CaracteresEspeciais.ExpressaoVazia;

public class Caractere implements Simbolo {

	private char valor = ' ';

	public Caractere(char valor) {
		this.valor = valor;
	}

	public char getValor() {
		return this.valor;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Caractere) {
			if (obj instanceof ExpressaoVazia) {
				return obj.equals(this);
			}
			if (((Caractere) obj).valor == this.valor) {
				return true;
			}
		}
		return false;
	}

}
