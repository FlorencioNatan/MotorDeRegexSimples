package com.motorDeRegexSimples.Algoritmos;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.SimboloFactory;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores.AbreParenteses;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores.Concatenacao;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores.FechaParenteses;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores.Opcao;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores.Uniao;

public class ShuntingYard {

	public LinkedList<Simbolo> processar(String regexInfix) {
		LinkedList<Simbolo> regexPostfix = new LinkedList<Simbolo>();
		Stack<Simbolo> pilhaOperadores = new Stack<Simbolo>();
		LinkedList<Simbolo> regexInfixConcatenacao = adicionarConcatenacaoImplicita(regexInfix);
		SimboloFactory factory = new SimboloFactory();

		for (Simbolo tokenAtual : regexInfixConcatenacao) {
			if (tokenAtual instanceof FechaParenteses) {
				Simbolo operador = pilhaOperadores.pop();
				while (!pilhaOperadores.empty() && !(operador instanceof AbreParenteses)) {
					regexPostfix.add(operador);
					operador = pilhaOperadores.pop();
				}
			} else if (tokenAtual instanceof AbreParenteses) {
				pilhaOperadores.add(factory.getSimbolo('('));
			} else if (tokenAtual instanceof Concatenacao) {
				pilhaOperadores.add(factory.getSimbolo('∘'));
			} else if (tokenAtual instanceof Uniao) {
				if (!pilhaOperadores.empty()) {
					Simbolo ultimoOperador = pilhaOperadores.pop();
					if (ultimoOperador instanceof Concatenacao) {
						regexPostfix.add(ultimoOperador);
					} else {
						pilhaOperadores.add(ultimoOperador);
					}
				}
				pilhaOperadores.add(factory.getSimbolo('|'));
			} else {
				regexPostfix.add(tokenAtual);
			}
		}

		while (!pilhaOperadores.empty()) {
			Simbolo operador = pilhaOperadores.pop();
			regexPostfix.add(operador);
		}
		return regexPostfix;
	}

	private LinkedList<Simbolo> adicionarConcatenacaoImplicita(String regexInfix) {
		SimboloFactory factory = new SimboloFactory();
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
		operadoresConcatDepois.add('?');

		LinkedList<Simbolo> regexInfixConcatenacao =  new LinkedList<Simbolo>();
		boolean ultimoTokenLetra = false;
		for (int i = 0; i < regexInfix.length(); i++) {
			char tokenAtual = regexInfix.charAt(i);

			if (ultimoTokenLetra && !operadoresConcatDepois.contains(tokenAtual)) {
				regexInfixConcatenacao.add(factory.getSimbolo('∘'));
			}
			if (tokenAtual == '\\') {
				factory.setEscape();
				tokenAtual = regexInfix.charAt(++i);
			}
			regexInfixConcatenacao.add(factory.getSimbolo(tokenAtual));

			if (!operadoresConcatAntes.contains(tokenAtual)) {
				ultimoTokenLetra = true;
			} else {
				ultimoTokenLetra = false;
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
