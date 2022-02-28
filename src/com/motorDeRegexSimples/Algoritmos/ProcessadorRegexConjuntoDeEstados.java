package com.motorDeRegexSimples.Algoritmos;

import java.util.HashSet;
import java.util.Stack;
import java.util.Vector;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Automato;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Caractere;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Transicao;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Transicoes;

public class ProcessadorRegexConjuntoDeEstados {

	private Automato automato;

	public ProcessadorRegexConjuntoDeEstados(Automato automato) {
		this.automato = automato;
	}

	public Vector<String> processar(String texto) {
		Vector<String> matches = new Vector<>();
		Transicoes transicoes = automato.getTransicoesDoEstado(automato.getEstadoInicial());

		for (int i = 0; i < texto.length(); i++) {
			char caractereAutal = texto.charAt(i);
			Vector<Integer> estadosDestino = transicoes.getEstadosDestidoPorCaractere(caractereAutal);
			if (!estadosDestino.isEmpty()) {
				String resutlado = buscarComAutomato(texto, i);
				if (!resutlado.isEmpty()) {
					i += resutlado.length() - 1;
					matches.add(resutlado);
				}
			}
		}

		return matches;
	}

	private String buscarComAutomato(String texto, int posicaoAtual) {
		Stack<EstadoBusca> estadosParaVisitar = new Stack<>();
		int posicaoFinal = posicaoAtual;

		Caractere e = new Caractere('ε', true);
		HashSet<Integer> estadosIniciais = getConjuntoEstados(
			automato.getEstadoInicial(),
			e
		);
		EstadoBusca estadoInicial = new EstadoBusca(estadosIniciais, posicaoAtual);

		estadosParaVisitar.add(estadoInicial);
		while (!estadosParaVisitar.empty()) {
			EstadoBusca estadoAtual = estadosParaVisitar.pop();
			Caractere charactereAtual;
			if (estadoAtual.posicao < texto.length()) {
				char charAtual = texto.charAt(estadoAtual.posicao);
				charactereAtual = new Caractere(charAtual, false);
			} else {
				charactereAtual = new Caractere('ε', true);
				continue;
			}

//			System.out.println("Conjunto de estados atual: " + estadoAtual.estados);
//			System.out.println("Posicao: " + estadoAtual.posicao);
//			System.out.println("Caractere atual: " + charactereAtual.getValor());

			HashSet<Integer> proximoConjuntoDeEstados = new HashSet<>();
			for (int estado : estadoAtual.estados) {
				Transicoes transicoes = automato.getTransicoesDoEstado(estado);
				if (transicoes == null) {
					continue;
				}

				for (Transicao transicao : transicoes.getListaTransicoes()) {
					if (!transicao.getCaractere().equals(charactereAtual)) {
						continue;
					}

					int posicao = estadoAtual.posicao;
					if (!transicao.getCaractere().isTransacaoVazia()) {
						posicao++;
					}

					if (isConjuntoEstadosFinal(transicao.getEstadoDestino()) && posicao > posicaoFinal) {
						posicaoFinal = posicao < texto.length() ? posicao : texto.length();
					}

					if (charactereAtual.isTransacaoVazia()) {
						continue;
					}

					proximoConjuntoDeEstados.addAll(getConjuntoEstados(transicao));
				}
			}
			if (!proximoConjuntoDeEstados.isEmpty()) {
				int posicao = estadoAtual.posicao + 1;
				EstadoBusca proximoEstadoDeBusca = new EstadoBusca(proximoConjuntoDeEstados, posicao);
				estadosParaVisitar.add(proximoEstadoDeBusca);
			}
		}
		return texto.substring(posicaoAtual, posicaoFinal);
	}

	private HashSet<Integer> getConjuntoEstados(int estado, Caractere caractere) {
		HashSet<Integer> proximosEstados = new HashSet<>();

		Transicoes transicoes = automato.getTransicoesDoEstado(estado);
		if (transicoes == null) {
			return proximosEstados;
		}

		for (Transicao transicao : transicoes.getListaTransicoes()) {
			if (transicao.getCaractere().isTransacaoVazia()) {
				Stack<Integer> possiveisEstados = new Stack<>();
				possiveisEstados.add(transicao.getEstadoDestino());
				while (!possiveisEstados.empty()) {
					int possivelEstadoDestino = possiveisEstados.pop();
					Transicoes possiveisTransicoes = automato.getTransicoesDoEstado(possivelEstadoDestino);

					if (automato.isEstadoFinal(possivelEstadoDestino)) {
						proximosEstados.add(possivelEstadoDestino);
					}

					if (possiveisTransicoes == null) {
						continue;
					}
					for (Transicao possivelTransicao : possiveisTransicoes.getListaTransicoes()) {
						if (possivelTransicao.getCaractere().isTransacaoVazia()) {
							possiveisEstados.add(possivelTransicao.getEstadoDestino());
						} else if (!caractere.isTransacaoVazia() && possivelTransicao.getCaractere().equals(caractere)) {
							proximosEstados.add(possivelTransicao.getEstadoDestino());
						} else {
							proximosEstados.add(possivelEstadoDestino);
						}
					}
				}
			} else if (!caractere.isTransacaoVazia() && caractere.equals(transicao.getCaractere())) {
				proximosEstados.add(transicao.getEstadoDestino());
			} else if (!caractere.isTransacaoVazia() && !caractere.equals(transicao.getCaractere())) {
				proximosEstados.add(estado);
			}
		}

		return proximosEstados;
	}

	private HashSet<Integer> getConjuntoEstados(Transicao transicao) {
		int estado = transicao.getEstadoDestino();
		Caractere caractere = transicao.getCaractere();

		return getConjuntoEstados(estado, caractere);
	}

	private boolean isConjuntoEstadosFinal(int estado) {
		Caractere caractere = new Caractere('ε', true);
		HashSet<Integer> conjuntoEstados = getConjuntoEstados(estado, caractere);
		conjuntoEstados.add(estado);
		for (int possivelEstadoFinal : conjuntoEstados) {
			if (automato.isEstadoFinal(possivelEstadoFinal)) {
				return true;
			}
		}

		return false;
	}

	private class EstadoBusca {
		public HashSet<Integer> estados = new HashSet<>();
		public int posicao;

		public EstadoBusca(HashSet<Integer> estados, int posicao) {
			this.estados = estados;
			this.posicao = posicao;
		}

	}

}
