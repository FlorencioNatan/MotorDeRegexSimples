package com.motorDeRegexSimples.Algoritmos;

import java.util.HashSet;
import java.util.Stack;
import java.util.Vector;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Automato;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Transicao;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Transicoes;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Caractere;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.SimboloFactory;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.CaracteresEspeciais.ExpressaoVazia;

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

		ExpressaoVazia e = new ExpressaoVazia();
		HashSet<Integer> estadosIniciais = getConjuntoEstados(
			automato.getEstadoInicial(),
			e
		);
		EstadoBusca estadoInicial = new EstadoBusca(estadosIniciais, posicaoAtual);

		estadosParaVisitar.add(estadoInicial);
		while (!estadosParaVisitar.empty()) {
			EstadoBusca estadoAtual = estadosParaVisitar.pop();
			Simbolo charactereAtual;
			if (estadoAtual.posicao < texto.length()) {
				char charAtual = texto.charAt(estadoAtual.posicao);
				charactereAtual = new Caractere(charAtual);
			} else {
				charactereAtual = new ExpressaoVazia();
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
					if (!transicao.getSimbolo().equals(charactereAtual)) {
						continue;
					}

					int posicao = estadoAtual.posicao;
					if (!(transicao.getSimbolo() instanceof ExpressaoVazia)) {
						posicao++;
					}

					if (isConjuntoEstadosFinal(transicao.getEstadoDestino()) && posicao > posicaoFinal) {
						posicaoFinal = posicao < texto.length() ? posicao : texto.length();
					}

					if (charactereAtual instanceof ExpressaoVazia) {
						continue;
					}

					proximoConjuntoDeEstados.addAll(getConjuntoEstados(estado, transicao.getSimbolo()));
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

	private HashSet<Integer> getConjuntoEstados(int estado, Simbolo simbolo) {
		HashSet<Integer> estadosMarcados = new HashSet<>();
		return this.getConjuntoEstadosRecorrencia(estado, simbolo, estadosMarcados);
	}

	private HashSet<Integer> getConjuntoEstadosRecorrencia(int estado, Simbolo simbolo, HashSet<Integer> estadosMarcados) {
		HashSet<Integer> proximosEstados = new HashSet<>();
		boolean adicionarEstadoAtual = automato.isEstadoFinal(estado);

		estadosMarcados.add(estado);

		Transicoes transicoes = automato.getTransicoesDoEstado(estado);
		if (transicoes == null) {
			if (adicionarEstadoAtual) {
				proximosEstados.add(estado);
			}
			return proximosEstados;
		}

		for (Transicao transicao : transicoes.getListaTransicoes()) {
			if (simbolo instanceof ExpressaoVazia) {
				if(!(transicao.getSimbolo() instanceof ExpressaoVazia))
					adicionarEstadoAtual = true;
			}

			if((transicao.getSimbolo() instanceof ExpressaoVazia)) {
				HashSet<Integer> estadosRetorno = this.getConjuntoEstadosRecorrencia(transicao.getEstadoDestino(), simbolo, estadosMarcados);
				proximosEstados.addAll(estadosRetorno);
			} else if(!(simbolo instanceof ExpressaoVazia)) {
				if (simbolo.equals(transicao.getSimbolo())) {
					SimboloFactory factory = new SimboloFactory();
					HashSet<Integer> estadosRetorno = this.getConjuntoEstadosRecorrencia(transicao.getEstadoDestino(), factory.getSimbolo(), estadosMarcados);
					proximosEstados.addAll(estadosRetorno);
				}
			}
		}

		if (adicionarEstadoAtual) {
			proximosEstados.add(estado);
		}

		return proximosEstados;
	}

	private void adicionaEstadoAoConjutoDeEstados(int estado, HashSet<Integer> conjuntoEstados) {
		boolean possuiApenasTransicoesVazias = true;
		Transicoes transicoes = automato.getTransicoesDoEstado(estado);
		if (transicoes == null) {
			conjuntoEstados.add(estado);
			return;
		}

		for (Transicao transicao : transicoes.getListaTransicoes()) {
			if (!(transicao.getSimbolo() instanceof ExpressaoVazia)) {
				possuiApenasTransicoesVazias = false;
			}
		}

		if (!possuiApenasTransicoesVazias) {
			conjuntoEstados.add(estado);
		}
	}

	private boolean isConjuntoEstadosFinal(int estado) {
		Simbolo caractere = new ExpressaoVazia();
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
