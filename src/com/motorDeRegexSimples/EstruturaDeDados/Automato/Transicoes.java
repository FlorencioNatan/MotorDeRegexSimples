package com.motorDeRegexSimples.EstruturaDeDados.Automato;

import java.util.LinkedList;
import java.util.Vector;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Caractere;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;

public class Transicoes {

	private LinkedList<Transicao> listaTransicoes;

	public Transicoes() {
		listaTransicoes = new LinkedList<>();
	}

	public Vector<Integer> getEstadosDestidoPorCaractere(Caractere caractere) {
		Vector<Integer> estadosDestido = new Vector<>();
		for (Transicao transicao : listaTransicoes) {
			if (transicao.getSimbolo().equals(caractere)) {
				estadosDestido.add(transicao.getEstadoDestino());
			}
		}
		return estadosDestido;
	}

	public Vector<Integer> getEstadosDestidoPorCaractere(char carac) {
		Caractere caractere = new Caractere(carac);
		Vector<Integer> estadosDestido = new Vector<>();
		for (Transicao transicao : listaTransicoes) {
			if (transicao.getSimbolo().equals(caractere)) {
				estadosDestido.add(transicao.getEstadoDestino());
			}
		}
		return estadosDestido;
	}

	public void addTransicao(Simbolo simbolo, int estadoDestino) {
		Transicao transicao = new Transicao(simbolo, estadoDestino);
		listaTransicoes.add(transicao);
	}

	public void addTransicao(Transicao transicao) {
		listaTransicoes.add(transicao);
	}

	public LinkedList<Transicao> getListaTransicoes() {
		return listaTransicoes;
	}

}
