package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores;

import java.util.LinkedList;
import java.util.Stack;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Automato;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;

public class Uniao implements Simbolo, OperadorNaoPermiteConcatenacaoAnterior, OperadorNaoPermiteConcatenacaoPosterior {

	@Override
	public String getValor() {
		return "|";
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
	public Automato getAutomatoReconhecedor(int contadorDeEstados, Stack<Automato> pilhaDeAutomatos) {
		Automato segundoOperando = pilhaDeAutomatos.pop();
		Automato primeiroOperando = pilhaDeAutomatos.pop();

		Automato resultado = primeiroOperando.unirCom(segundoOperando);
		return resultado;
	}

	@Override
	public void processarShuntingYard(Stack<Simbolo> pilhaOperadores, LinkedList<Simbolo> regexPostfix) {
		if (!pilhaOperadores.empty()) {
			Simbolo ultimoOperador = pilhaOperadores.pop();
			if (ultimoOperador instanceof Concatenacao) {
				regexPostfix.add(ultimoOperador);
			} else {
				pilhaOperadores.add(ultimoOperador);
			}
		}
		pilhaOperadores.add(this);
	}

}
