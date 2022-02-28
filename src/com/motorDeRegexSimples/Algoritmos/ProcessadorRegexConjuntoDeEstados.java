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
		HashSet<Integer> proximosEstados = new HashSet<>();

		Transicoes transicoes = automato.getTransicoesDoEstado(estado);
		if (transicoes == null) {
			return proximosEstados;
		}

		for (Transicao transicao : transicoes.getListaTransicoes()) {
			if (transicao.getSimbolo() instanceof ExpressaoVazia) {
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
						if (possivelTransicao.getSimbolo() instanceof ExpressaoVazia) {
							possiveisEstados.add(possivelTransicao.getEstadoDestino());
						} else if (!(simbolo instanceof ExpressaoVazia) && possivelTransicao.getSimbolo().equals(simbolo)) {
							proximosEstados.add(possivelTransicao.getEstadoDestino());
						} else {
							proximosEstados.add(possivelEstadoDestino);
						}
					}
				}
			} else if (!(simbolo instanceof ExpressaoVazia) && simbolo.equals(transicao.getSimbolo())) {
				int estadoDestino = transicao.getEstadoDestino();
				SimboloFactory factory = new SimboloFactory();
				HashSet<Integer> estadosParaAdicionar = this.getConjuntoEstados(estadoDestino, factory.getSimbolo());
				proximosEstados.add(transicao.getEstadoDestino());
				for (Integer i: estadosParaAdicionar) {
					proximosEstados.add(i);
				}
			} else if (!(simbolo instanceof ExpressaoVazia) && !simbolo.equals(transicao.getSimbolo())) {
				proximosEstados.add(estado);
			} else {
				proximosEstados.add(estado);
			}
		}

		return proximosEstados;
	}

	private HashSet<Integer> getConjuntoEstados(Transicao transicao) {
		int estado = transicao.getEstadoDestino();
		Simbolo caractere = transicao.getSimbolo();

		return getConjuntoEstados(estado, caractere);
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
