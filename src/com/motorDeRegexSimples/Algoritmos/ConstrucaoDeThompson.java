package com.motorDeRegexSimples.Algoritmos;

import java.util.LinkedList;
import java.util.Stack;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Automato;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.CaracteresEspeciais.ExpressaoVazia;

public class ConstrucaoDeThompson {

	private int contadorDeEstados;

	public ConstrucaoDeThompson() {
		contadorDeEstados = 0;
	}

	public Automato transformar(String regex) {
		ShuntingYard sy = new ShuntingYard();
		LinkedList<Simbolo> regexPostFix = sy.processar(regex);

		if (regexPostFix.size() == 0) {
			return processarStringVazia();
		}

		Stack<Automato> pilhaDeAutomatos = new Stack<>();
		for (Simbolo tokenAtual : regexPostFix) {
			Automato automato;
			if (tokenAtual.isEquivalenteAoChar('∘')) {
				Automato segundoOperando = pilhaDeAutomatos.pop();
				Automato primeiroOperando = pilhaDeAutomatos.pop();

				automato = processarConcatenacao(primeiroOperando, segundoOperando);
			} else if (tokenAtual.isEquivalenteAoChar('|')) {
				Automato segundoOperando = pilhaDeAutomatos.pop();
				Automato primeiroOperando = pilhaDeAutomatos.pop();

				automato = processarUniao(primeiroOperando, segundoOperando);
			} else if (tokenAtual.isEquivalenteAoChar('*')) {
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

	private Automato processarCaractereSimples(Simbolo caracter) {
		Automato automatoCaractereSimples = new Automato(contadorDeEstados);
		automatoCaractereSimples.adicionarTransicao(contadorDeEstados, contadorDeEstados + 1, caracter);
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
