package com.motorDeRegexSimples.Algoritmos;

import java.util.Stack;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Automato;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Caractere;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.CaracteresEspeciais.ExpressaoVazia;

public class ConstrucaoDeThompson {

	private int contadorDeEstados;

	public ConstrucaoDeThompson() {
		contadorDeEstados = 0;
	}

	public Automato transformar(String regex) {
		ShuntingYard sy = new ShuntingYard();
		String regexPostFix = sy.processar(regex);

		if (regexPostFix.length() == 0) {
			return processarStringVazia();
		}

		Stack<Automato> pilhaDeAutomatos = new Stack<>();
		for (int i = 0; i < regexPostFix.length(); i++) {
			char tokenAtual = regexPostFix.charAt(i);
			Automato automato;
			if (tokenAtual == '∘') {
				Automato segundoOperando = pilhaDeAutomatos.pop();
				Automato primeiroOperando = pilhaDeAutomatos.pop();

				automato = processarConcatenacao(primeiroOperando, segundoOperando);
			} else if (tokenAtual == '|') {
				Automato segundoOperando = pilhaDeAutomatos.pop();
				Automato primeiroOperando = pilhaDeAutomatos.pop();

				automato = processarUniao(primeiroOperando, segundoOperando);
			} else if (tokenAtual == '*') {
				Automato operando = pilhaDeAutomatos.pop();
				automato = processarEstrelaDeKleene(operando);
			} else {
				automato = processarCaractereSimples(tokenAtual);
			}
			pilhaDeAutomatos.add(automato);
		}

		return pilhaDeAutomatos.pop();
	}

	private Automato processarStringVazia() {
		Automato automatoStringVazia = new Automato(0);
		ExpressaoVazia e = new ExpressaoVazia();
		automatoStringVazia.adicionarTransicao(0, 1, e);
		automatoStringVazia.adicionarEstadosFinais(1);
		return automatoStringVazia;
	}

	private Automato processarCaractereSimples(char caracter) {
		Automato automatoCaractereSimples = new Automato(contadorDeEstados);
		Caractere e = new Caractere(caracter);
		automatoCaractereSimples.adicionarTransicao(contadorDeEstados, contadorDeEstados + 1, e);
		automatoCaractereSimples.adicionarEstadosFinais(contadorDeEstados + 1);
		contadorDeEstados += 2;
		return automatoCaractereSimples;
	}

	private Automato processarConcatenacao(Automato primeiroOperando, Automato segundoOperando) {
		return primeiroOperando.concatenarCom(segundoOperando);
	}

	private Automato processarUniao(Automato primeiroOperando, Automato segundoOperando) {
		Automato resultado = primeiroOperando.unirCom(segundoOperando);
		contadorDeEstados = resultado.getMaiorEstado() + 1;
		return resultado;
	}

	private Automato processarEstrelaDeKleene(Automato operando) {
		Automato resultado = operando.estrelaDeKleene();
		contadorDeEstados = resultado.getMaiorEstado() + 1;
		return resultado;
	}

}
