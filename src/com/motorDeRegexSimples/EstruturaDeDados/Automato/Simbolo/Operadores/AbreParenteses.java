package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores;


import java.util.LinkedList;
import java.util.Stack;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.AbstractSimboloPadrao;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;

public class AbreParenteses extends AbstractSimboloPadrao implements Simbolo, OperadorNaoPermiteConcatenacaoPosterior {

	@Override
	public String getValor() {
		return "(";
	}

	@Override
	public boolean equals(Object obj) {
		return false;
	}

	@Override
	public boolean isEquivalenteAoChar(char valor) {
		return false;
	}

	@Override
	public void processarShuntingYard(Stack<Simbolo> pilhaOperadores, LinkedList<Simbolo> regexPostfix) {
		pilhaOperadores.add(this);
	}

}
