package com.motorDeRegexSimples.EstruturaDeDados.Automato;

import java.util.LinkedList;
import java.util.Vector;

public class Transicoes {

	private LinkedList<Transicao> listaTransicoes;

	public Transicoes() {
		listaTransicoes = new LinkedList<>();
	}

	public Vector<Integer> getEstadosDestidoPorCaractere(Caractere caractere) {
		Vector<Integer> estadosDestido = new Vector<>();
		for (Transicao transicao : listaTransicoes) {
			if (transicao.getCaractere().equals(caractere)) {
				estadosDestido.add(transicao.getEstadoDestino());
			}
		}
		return estadosDestido;
	}

	public Vector<Integer> getEstadosDestidoPorCaractere(char carac) {
		Caractere caractere = new Caractere(carac, false);
		Vector<Integer> estadosDestido = new Vector<>();
		for (Transicao transicao : listaTransicoes) {
			if (transicao.getCaractere().equals(caractere)) {
				estadosDestido.add(transicao.getEstadoDestino());
			}
		}
		return estadosDestido;
	}

	public void addTransicao(Caractere caracatere, int estadoDestino) {
		Transicao transicao = new Transicao(caracatere, estadoDestino);
		listaTransicoes.add(transicao);
	}

	public void addTransicao(Transicao transicao) {
		listaTransicoes.add(transicao);
	}

	public LinkedList<Transicao> getListaTransicoes() {
		return listaTransicoes;
	}

}
