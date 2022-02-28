package com.motorDeRegexSimples.Algoritmos;

import java.util.Stack;
import java.util.Vector;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Automato;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Transicao;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Transicoes;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Caractere;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.CaracteresEspeciais.ExpressaoVazia;

public class ProcessadorRegexComBacktracking {

	private Automato automato;

	public ProcessadorRegexComBacktracking(Automato automato) {
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
		EstadoBusca estadoInicial = new EstadoBusca(automato.getEstadoInicial(), posicaoAtual);
		int posicaoFinal = posicaoAtual;

		estadosParaVisitar.add(estadoInicial);
		while (!estadosParaVisitar.empty()) {
			EstadoBusca estadoAtual = estadosParaVisitar.pop();
			Simbolo charactereAtual;
			if (estadoAtual.posicao < texto.length()) {
				char charAtual = texto.charAt(estadoAtual.posicao);
				charactereAtual = new Caractere(charAtual);
			} else {
				charactereAtual = new ExpressaoVazia();
			}

			Transicoes transicoes = automato.getTransicoesDoEstado(estadoAtual.estado);
			if (transicoes == null) {
				continue;
			}

			for (Transicao transicao : transicoes.getListaTransicoes()) {
				if (!transicao.getSimbolo().equals(charactereAtual)) {
					continue;
				}

				int posicao = estadoAtual.posicao;
				posicao = transicao.getSimbolo() instanceof ExpressaoVazia ? posicao: posicao + 1;

				if (automato.isEstadoFinal(transicao.getEstadoDestino()) && posicao > posicaoFinal) {
					posicaoFinal = posicao;
				}

				if (charactereAtual instanceof ExpressaoVazia) {
					continue;
				}

				EstadoBusca proximoEstadoDeBusca = new EstadoBusca(transicao.getEstadoDestino(), posicao);
				estadosParaVisitar.add(proximoEstadoDeBusca);
			}
		}
		return texto.substring(posicaoAtual, posicaoFinal);
	}

	private class EstadoBusca {
		public int estado;
		public int posicao;

		public EstadoBusca(int estado, int posicao) {
			this.estado = estado;
			this.posicao = posicao;
		}

	}

}
