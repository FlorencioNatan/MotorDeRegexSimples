package com.motorDeRegexSimples.EstruturaDeDados.Automato;

import java.util.HashMap;
import java.util.Vector;

public class Automato {

	private HashMap<Integer, Transicoes> tabelaDeTransicoes;
	private int estadoInicial;
	private Vector<Integer> estadosFinais;
	private int menorEstado;
	private int maiorEstado;

	public Automato(int estadoInicial) {
		this.tabelaDeTransicoes = new HashMap<>();
		this.estadosFinais = new Vector<>();
		this.estadoInicial = estadoInicial;
		this.menorEstado = estadoInicial;
		this.maiorEstado = estadoInicial;
	}

	public String getGraphvizString() {
		Vector<Integer> ef = this.estadosFinais;
		String grafo = "digraph finite_state_machine {\n";
		grafo += "rankdir=LR\n";
		grafo += "size=\"8,5\"\n";

		String strEstadosFinais = "";
		for (Integer estadoFinal : ef) {
			strEstadosFinais += " " + estadoFinal.toString();
		}

		grafo += "node [shape = doublecircle];" + strEstadosFinais + ";\n";
		grafo += "node [shape = circle];\n";
		grafo += "-1 [ label = \"\", style = invis ]\n";
		grafo += "-1 -> " + estadoInicial + ";\n";

		for (Integer estadoOrigem : tabelaDeTransicoes.keySet()) {
			Transicoes transicoes = tabelaDeTransicoes.get(estadoOrigem);
			for (Transicao transicao : transicoes.getListaTransicoes()) {
				Caractere letra = transicao.getCaractere();
				int estadoDestino = transicao.getEstadoDestino();
				char valorLetra = ' ';
				if (!letra.isTransacaoVazia()) {
					valorLetra = letra.getValor();
				} else {
					valorLetra = 'ε';
				}
				String strTransicao;
				strTransicao = estadoOrigem + " -> " + estadoDestino + " [label = \"" + valorLetra + "\"];\n";
				grafo += strTransicao;
			}
		}

		grafo += "}";
		return grafo;
	}

	public void adicionarTransicao(int estadoDeOrigem, int estadoDeDestino, Caractere caractere) {
		atualizarMaiorEMenorEstado(estadoDeOrigem);
		atualizarMaiorEMenorEstado(estadoDeDestino);

		if (!this.tabelaDeTransicoes.containsKey(estadoDeOrigem)) {
			Transicoes transicoes = new Transicoes();
			transicoes.addTransicao(caractere, estadoDeDestino);
			this.tabelaDeTransicoes.put(estadoDeOrigem, transicoes);
			return;
		}

		this.tabelaDeTransicoes.get(estadoDeOrigem).addTransicao(caractere, estadoDeDestino);
	}

	public void adicionarTransicao(int estadoDeOrigem, Transicao transicao) {
		atualizarMaiorEMenorEstado(estadoDeOrigem);
		atualizarMaiorEMenorEstado(transicao.getEstadoDestino());

		if (!this.tabelaDeTransicoes.containsKey(estadoDeOrigem)) {
			Transicoes transicoes = new Transicoes();
			transicoes.addTransicao(transicao);
			this.tabelaDeTransicoes.put(estadoDeOrigem, transicoes);
			return;
		}

		this.tabelaDeTransicoes.get(estadoDeOrigem).addTransicao(transicao);
	}

	public Transicoes getTransicoesDoEstado(int estado) {
		return this.tabelaDeTransicoes.get(estado);
	}

	private void atualizarMaiorEMenorEstado(int estado) {
		atualizarMenorEstado(estado);
		atualizarMaiorEstado(estado);
	}
	private void atualizarMenorEstado(int estado) {
		if (estado < menorEstado) {
			menorEstado = estado;
		}
	}

	private void atualizarMaiorEstado(int estado) {
		if (estado > maiorEstado) {
			maiorEstado = estado;
		}
	}

	public void adicionarEstadosFinais(int estadoFinal) {
		this.estadosFinais.add(estadoFinal);
	}

	public boolean isEstadoFinal(int estado) {
		return this.estadosFinais.contains(estado);
	}

	public int getMaiorEstado() {
		return maiorEstado;
	}

	public int getEstadoInicial() {
		return estadoInicial;
	}

	public Automato concatenarCom(Automato segundoOperando) {
		Automato resultado = new Automato(this.estadoInicial);
		for (Integer estadoOrigem : this.tabelaDeTransicoes.keySet()) {
			Transicoes transicoes = this.tabelaDeTransicoes.get(estadoOrigem);
			for (Transicao transicao : transicoes.getListaTransicoes()) {
				int estadoDestino = transicao.getEstadoDestino();
				Caractere caractere = transicao.getCaractere();

				if (this.estadosFinais.contains(estadoOrigem)) {
					resultado.adicionarTransicao(segundoOperando.estadoInicial, estadoDestino, caractere);
				} else if (this.estadosFinais.contains(estadoDestino)) {
					resultado.adicionarTransicao(estadoOrigem, segundoOperando.estadoInicial, caractere);
				} else {
					resultado.adicionarTransicao(estadoOrigem, transicao);
				}
			}
		}
		for (Integer estadoOrigem : segundoOperando.tabelaDeTransicoes.keySet()) {
			Transicoes transicoes = segundoOperando.tabelaDeTransicoes.get(estadoOrigem);
			resultado.tabelaDeTransicoes.put(estadoOrigem, transicoes);
		}

		resultado.estadosFinais = segundoOperando.estadosFinais;
		resultado.atualizarMaiorEstado(this.maiorEstado);
		resultado.atualizarMaiorEstado(segundoOperando.maiorEstado);
		resultado.atualizarMenorEstado(this.menorEstado);
		resultado.atualizarMenorEstado(segundoOperando.menorEstado);

		return resultado;
	}

	public Automato unirCom(Automato segundoOperando) {
		int novoEstadoInicial = this.maiorEstado > segundoOperando.maiorEstado
			? this.maiorEstado + 1 : segundoOperando.maiorEstado + 1;
		int novoEstadoFinal = novoEstadoInicial + 1;

		Automato resultado = new Automato(novoEstadoInicial);
		for (Integer estadoOrigem : this.tabelaDeTransicoes.keySet()) {
			Transicoes transicoes = this.tabelaDeTransicoes.get(estadoOrigem);
			resultado.tabelaDeTransicoes.put(estadoOrigem, transicoes);
		}
		for (Integer estadoOrigem : segundoOperando.tabelaDeTransicoes.keySet()) {
			Transicoes transicoes = segundoOperando.tabelaDeTransicoes.get(estadoOrigem);
			resultado.tabelaDeTransicoes.put(estadoOrigem, transicoes);
		}

		//resultado.maiorEstado + 1
		Caractere e = new Caractere('ε', true);
		resultado.adicionarTransicao(novoEstadoInicial, this.estadoInicial, e);
		resultado.adicionarTransicao(novoEstadoInicial, segundoOperando.estadoInicial, e);
		for (int estadoFinal: this.estadosFinais) {
			resultado.adicionarTransicao(estadoFinal, novoEstadoFinal, e);
		}
		for (int estadoFinal: segundoOperando.estadosFinais) {
			resultado.adicionarTransicao(estadoFinal, novoEstadoFinal, e);
		}
		resultado.adicionarEstadosFinais(novoEstadoFinal);
		return resultado;
	}

	public Automato estrelaDeKleene() {
		int novoEstadoInicial = this.maiorEstado + 1;
		int novoEstadoFinal = this.maiorEstado + 2;
		Automato resultado = new Automato(novoEstadoInicial);

		for (Integer estadoOrigem : this.tabelaDeTransicoes.keySet()) {
			Transicoes transicoes = this.tabelaDeTransicoes.get(estadoOrigem);
			resultado.tabelaDeTransicoes.put(estadoOrigem, transicoes);
		}

		Caractere e = new Caractere('ε', true);
		resultado.adicionarTransicao(novoEstadoInicial, this.estadoInicial, e);
		resultado.adicionarTransicao(novoEstadoInicial, novoEstadoFinal, e);
		for (int estadoFinal : this.estadosFinais) {
			resultado.adicionarTransicao(estadoFinal, this.estadoInicial, e);
			resultado.adicionarTransicao(estadoFinal, novoEstadoFinal, e);
		}
		resultado.adicionarEstadosFinais(novoEstadoFinal);
		return resultado;
	}

}
