package com.motorDeRegexSimples.Algoritmos;

import java.util.LinkedList;
import java.util.Stack;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.SimboloFactory;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores.OperadorNaoPermiteConcatenacaoAnterior;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores.OperadorNaoPermiteConcatenacaoPosterior;

public class ShuntingYard {

	public LinkedList<Simbolo> processar(String regexInfix) {
		LinkedList<Simbolo> regexPostfix = new LinkedList<Simbolo>();
		Stack<Simbolo> pilhaOperadores = new Stack<Simbolo>();
		LinkedList<Simbolo> regexInfixConcatenacao = adicionarConcatenacaoImplicita(regexInfix);

		for (Simbolo tokenAtual : regexInfixConcatenacao) {
			tokenAtual.processarShuntingYard(pilhaOperadores, regexPostfix);
		}

		while (!pilhaOperadores.empty()) {
			Simbolo operador = pilhaOperadores.pop();
			regexPostfix.add(operador);
		}
		return regexPostfix;
	}

	private LinkedList<Simbolo> adicionarConcatenacaoImplicita(String regexInfix) {
		SimboloFactory factory = new SimboloFactory();
		LinkedList<Simbolo> regexInfixConcatenacao =  new LinkedList<Simbolo>();
		boolean permiteConcatenacaoNaPosicaoAtual = false;

		for (int i = 0; i < regexInfix.length(); i++) {
			char tokenAtual = regexInfix.charAt(i);
			Simbolo simboloAtual = factory.getSimbolo(tokenAtual);

			if (permiteConcatenacaoNaPosicaoAtual && !(simboloAtual instanceof OperadorNaoPermiteConcatenacaoAnterior)) {
				regexInfixConcatenacao.add(factory.getSimbolo('∘'));
			}

			if (tokenAtual == '\\') {
				factory.setEscape();
				tokenAtual = regexInfix.charAt(++i);
				simboloAtual = factory.getSimbolo(tokenAtual);
			}
			regexInfixConcatenacao.add(simboloAtual);

			if (!(simboloAtual instanceof OperadorNaoPermiteConcatenacaoPosterior)) {
				permiteConcatenacaoNaPosicaoAtual = true;
			} else {
				permiteConcatenacaoNaPosicaoAtual = false;
			}
		}

		return regexInfixConcatenacao;
	}

	private void imprimirListaSimbolos(LinkedList<Simbolo> lista) {
		for (Simbolo simbolo : lista) {
			System.out.println("Lista: " + simbolo.getValor());
		}
	}

}
