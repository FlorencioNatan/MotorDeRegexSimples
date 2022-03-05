package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores;

import java.util.Stack;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Automato;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;

public class MaisDeKleene implements Simbolo {

	@Override
	public String getValor() {
		return "+";
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
		Automato operando = pilhaDeAutomatos.pop();
		Automato segundoOperando = operando.duplicar(contadorDeEstados);
		segundoOperando.estrelaDeKleene();

		Automato resultado = operando.concatenarCom(segundoOperando);
		contadorDeEstados = resultado.getMaiorEstado() + 1;
		return resultado;
	}

}
