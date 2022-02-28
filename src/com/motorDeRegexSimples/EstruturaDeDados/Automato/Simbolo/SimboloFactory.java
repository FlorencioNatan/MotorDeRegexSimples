package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.CaracteresEspeciais.ExpressaoVazia;

public class SimboloFactory {

	public Simbolo getSimbolo(char simbolo) {
		return new Caractere(simbolo);
	}

	public Simbolo getSimbolo() {
		return new ExpressaoVazia();
	}

}
