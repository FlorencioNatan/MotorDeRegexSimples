package com.motorDeRegexSimples.Algoritmos;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class ShuntingYard {

	public String processar(String regexInfix) {
		String regexPostfix = "";
		Stack<Character> pilhaOperadores = new Stack<Character>();
		String regexInfixConcatenacao = adicionarConcatenacaoImplicita(regexInfix);

		for (int i = 0; i < regexInfixConcatenacao.length(); i++) {
			char tokenAtual = regexInfixConcatenacao.charAt(i);

			if (tokenAtual == ')') {
				char operador = pilhaOperadores.pop();
				while (!pilhaOperadores.empty() && operador != '(') {
					regexPostfix += operador;
					operador = pilhaOperadores.pop();
				}
			} else if (tokenAtual == '(') {
				pilhaOperadores.add('(');
			} else if (tokenAtual == '∘') {
				pilhaOperadores.add('∘');
			} else if (tokenAtual == '|') {
				if (!pilhaOperadores.empty()) {
					char ultimoOperador = pilhaOperadores.pop();
					if (ultimoOperador == '∘') {
						regexPostfix += ultimoOperador;
					} else {
						pilhaOperadores.add(ultimoOperador);
					}
				}
				pilhaOperadores.add('|');
			} else {
				regexPostfix += tokenAtual;
			}
		}

		while (!pilhaOperadores.empty()) {
			char operador = pilhaOperadores.pop();
			regexPostfix += operador;
		}
		return regexPostfix;
	}

	private String adicionarConcatenacaoImplicita(String regexInfix) {
		Set<Character> operadoresConcatAntes;
		operadoresConcatAntes = new HashSet<Character>();
		operadoresConcatAntes.add('|');
		operadoresConcatAntes.add('∘');
		operadoresConcatAntes.add('(');

		Set<Character> operadoresConcatDepois;
		operadoresConcatDepois = new HashSet<Character>();
		operadoresConcatDepois.add('|');
		operadoresConcatDepois.add('∘');
		operadoresConcatDepois.add('*');
		operadoresConcatDepois.add(')');

		String regexInfixConcatenacao = "";
		boolean ultimoTokenLetra = false;
		for (int i = 0; i < regexInfix.length(); i++) {
			char tokenAtual = regexInfix.charAt(i);

			if (ultimoTokenLetra && !operadoresConcatDepois.contains(tokenAtual)) {
				regexInfixConcatenacao += '∘';
			}
			regexInfixConcatenacao += tokenAtual;

			if (!operadoresConcatAntes.contains(tokenAtual)) {
				ultimoTokenLetra = true;
			} else {
				ultimoTokenLetra = false;
			}
		}
		return regexInfixConcatenacao;
	}

}
