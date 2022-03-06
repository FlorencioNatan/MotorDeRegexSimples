package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores;

import java.util.LinkedList;
import java.util.Stack;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.AbstractSimboloPadrao;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;

public class DigitoQuantificador extends AbstractSimboloPadrao implements Simbolo, OperadorNaoPermiteConcatenacaoAnterior, OperadorNaoPermiteConcatenacaoPosterior {

	private String valor;

	public DigitoQuantificador(char valor) {
		this.valor = Character.toString(valor);
	}

	@Override
	public String getValor() {
		return this.valor;
	}

	@Override
	public boolean equals(Object obj) {
		return false;
	}

	@Override
	public boolean isEquivalenteAoChar(char valor) {
		return false;
	}

	public void processarShuntingYard(Stack<Simbolo> pilhaOperadores, LinkedList<Simbolo> regexPostfix) {
		pilhaOperadores.add(this);
	}

}
