package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.CaracteresEspeciais.Digitos;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.CaracteresEspeciais.ExpressaoVazia;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.CaracteresEspeciais.QualquerCaractereExcetoQuebraDeLinha;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores.AbreParenteses;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores.Concatenacao;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores.EstrelaDeKleene;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores.FechaParenteses;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores.Uniao;

public class SimboloFactory {

	private boolean escape = false;

	public Simbolo getSimbolo(char simbolo) {
		if (this.escape) {
			return this.getSimboloEscapado(simbolo);
		} else {
			return this.getSimboloPadrao(simbolo);
		}
	}

	public Simbolo getSimbolo() {
		return new ExpressaoVazia();
	}

	public void setEscape() {
		this.escape = true;
	}

	private Simbolo getSimboloEscapado(char simbolo) {
		this.escape = false;

		if (simbolo == 'd') {
			return new Digitos();
		}

		return new Caractere(simbolo);
	}

	private Simbolo getSimboloPadrao(char simbolo) {
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

		if (simbolo == '.') {
			return new QualquerCaractereExcetoQuebraDeLinha();
		}

		return new Caractere(simbolo);
	}

}
