package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores;

import java.util.LinkedList;
import java.util.Stack;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.AbstractSimboloPadrao;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;

public class FechaChave extends AbstractSimboloPadrao implements Simbolo, OperadorNaoPermiteConcatenacaoAnterior {

	@Override
	public String getValor() {
		return "}";
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
		Simbolo operador = pilhaOperadores.pop();
		Quantificador quantificador = new Quantificador();

		int valorMaximo = this.getValorQuantificador(pilhaOperadores, operador);
		operador = pilhaOperadores.empty() ? null : pilhaOperadores.pop();
		if (operador instanceof SeparadorQuantificador) {
			operador = pilhaOperadores.pop();
			int valorMinimo = this.getValorQuantificador(pilhaOperadores, operador);
			quantificador.setMinimoMaximo(valorMinimo, valorMaximo);
		} else {
			quantificador.setMinimoMaximo(valorMaximo, valorMaximo);
		}

		regexPostfix.add(quantificador);
	}

	private int getValorQuantificador(Stack<Simbolo> pilhaOperadores, Simbolo operador) {
		int valorQuantificador = 0;
		int expoente = 1;

		while (!pilhaOperadores.empty() && !(operador instanceof AbreChave) && !(operador instanceof SeparadorQuantificador)) {
			String valorOperador = operador.getValor();
			valorQuantificador = valorQuantificador + Integer.parseInt(valorOperador) * expoente;
			expoente*=10;
			operador = pilhaOperadores.pop();
		}

		if (operador instanceof SeparadorQuantificador) {
			pilhaOperadores.push(operador);
		}

		return  valorQuantificador;
	}

}
