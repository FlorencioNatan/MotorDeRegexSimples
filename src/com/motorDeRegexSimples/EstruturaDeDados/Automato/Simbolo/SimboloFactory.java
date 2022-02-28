package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.CaracteresEspeciais.ExpressaoVazia;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores.AbreParenteses;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores.Concatenacao;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores.EstrelaDeKleene;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores.FechaParenteses;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores.Uniao;

public class SimboloFactory {

	public Simbolo getSimbolo(char simbolo) {

		if (simbolo == '∘') {
			return new Concatenacao();
		}

		if (simbolo == '|') {
			return new  Uniao();
		}

		if (simbolo == '*') {
			return new  EstrelaDeKleene();
		}

		if (simbolo == '(') {
			return new  AbreParenteses();
		}

		if (simbolo == ')') {
			return new  FechaParenteses();
		}

		return new Caractere(simbolo);
	}

	public Simbolo getSimbolo() {
		return new ExpressaoVazia();
	}

}
