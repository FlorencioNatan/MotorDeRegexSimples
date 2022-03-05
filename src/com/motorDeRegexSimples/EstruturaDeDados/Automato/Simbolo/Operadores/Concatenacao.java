package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores;

import java.util.Stack;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Automato;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;

public class Concatenacao implements Simbolo {

	@Override
	public String getValor() {
		return "∘";
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

		return primeiroOperando.concatenarCom(segundoOperando);
	}

}
