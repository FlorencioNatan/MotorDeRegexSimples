package com.motorDeRegexSimples.EstruturaDeDados.Automato;

import java.util.Vector;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.CaracteresEspeciais.ExpressaoVazia;

public class Automato {

	private Estado cabeca;
	private Estado cauda;
	private int estadoInicial;
	private Vector<Integer> estadosFinais;
	private int menorEstado;
	private int maiorEstado;

	public Automato(int estadoInicial) {
		this.cabeca = new Estado(estadoInicial);
		this.cauda = this.cabeca;
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

		Estado estadoOrigem = this.cabeca;
		do {
			Transicoes transicoes = estadoOrigem.getTransicoes();
			for (Transicao transicao : transicoes.getListaTransicoes()) {
				Simbolo letra = transicao.getSimbolo();
				int estadoDestino = transicao.getEstadoDestino();
				String valorLetra = " ";
				if (!(letra instanceof ExpressaoVazia)) {
					valorLetra = letra.getValor();
				} else {
					valorLetra = "ε";
				}
				String strTransicao;
				strTransicao = estadoOrigem.getIdEstado() + " -> " + estadoDestino + " [label = \"" + valorLetra + "\"];\n";
				grafo += strTransicao;
			}

			estadoOrigem = estadoOrigem.getProximoEstado();
		} while(estadoOrigem != null);

		grafo += "}";
		return grafo;
	}

	public void adicionarTransicao(int estadoDeOrigem, int estadoDeDestino, Simbolo caractere) {
		atualizarMaiorEMenorEstado(estadoDeOrigem);
		atualizarMaiorEMenorEstado(estadoDeDestino);

		if (!this.containsEstado(estadoDeOrigem)) {
			Transicoes transicoes = new Transicoes();
			transicoes.addTransicao(caractere, estadoDeDestino);
			Estado novoEstado = new Estado(estadoDeOrigem);
			novoEstado.setTransicoes(transicoes);
			this.cauda.setProximoEstado(novoEstado);
			this.cauda = novoEstado;
			return;
		}

		this.findEstado(estadoDeOrigem).getTransicoes().addTransicao(caractere, estadoDeDestino);
	}

	public void adicionarTransicao(int estadoDeOrigem, Transicao transicao) {
		atualizarMaiorEMenorEstado(estadoDeOrigem);
		atualizarMaiorEMenorEstado(transicao.getEstadoDestino());

		if (!this.containsEstado(estadoDeOrigem)) {
			Transicoes transicoes = new Transicoes();
			transicoes.addTransicao(transicao);
			Estado novoEstado = new Estado(estadoDeOrigem);
			novoEstado.setTransicoes(transicoes);
			this.cauda.setProximoEstado(novoEstado);
			this.cauda = novoEstado;
			return;
		}

		this.findEstado(estadoDeOrigem).getTransicoes().addTransicao(transicao);
	}

	public Transicoes getTransicoesDoEstado(int estado) {
		if (this.findEstado(estado) == null) {
			return null;
		}

		return this.findEstado(estado).getTransicoes();
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
		Estado estadoOrigem = cabeca;
		do {
			Transicoes transicoes = estadoOrigem.getTransicoes();
			for (Transicao transicao : transicoes.getListaTransicoes()) {
				int estadoDestino = transicao.getEstadoDestino();

				if (this.estadosFinais.contains(estadoDestino)) {
					transicao.setEstadoDestino(segundoOperando.estadoInicial);
				}
			}
			estadoOrigem = estadoOrigem.getProximoEstado();
		} while(estadoOrigem != null);

		this.cauda.setProximoEstado(segundoOperando.cabeca);
		this.cauda = segundoOperando.cauda;
		this.estadosFinais = segundoOperando.estadosFinais;

		this.atualizarMaiorEstado(this.maiorEstado);
		this.atualizarMaiorEstado(segundoOperando.maiorEstado);
		this.atualizarMenorEstado(this.menorEstado);
		this.atualizarMenorEstado(segundoOperando.menorEstado);

		return this;
	}

	public Automato unirCom(Automato segundoOperando) {
		int novoEstadoInicial = this.maiorEstado > segundoOperando.maiorEstado
			? this.maiorEstado + 1 : segundoOperando.maiorEstado + 1;
		int novoEstadoFinal = novoEstadoInicial + 1;

		this.cauda.setProximoEstado(segundoOperando.cabeca);
		this.cauda = segundoOperando.cauda;

		Simbolo e = new ExpressaoVazia();
		this.adicionarTransicao(novoEstadoInicial, this.estadoInicial, e);
		this.adicionarTransicao(novoEstadoInicial, segundoOperando.estadoInicial, e);
		for (int estadoFinal: this.estadosFinais) {
			this.adicionarTransicao(estadoFinal, novoEstadoFinal, e);
		}
		for (int estadoFinal: segundoOperando.estadosFinais) {
			this.adicionarTransicao(estadoFinal, novoEstadoFinal, e);
		}

		this.estadoInicial = novoEstadoInicial;
		this.estadosFinais = new Vector<Integer>();
		this.adicionarEstadosFinais(novoEstadoFinal);

		return this;
	}

	public Automato estrelaDeKleene() {
		int novoEstadoInicial = this.maiorEstado + 1;
		int novoEstadoFinal = this.maiorEstado + 2;

		Simbolo e = new ExpressaoVazia();
		this.adicionarTransicao(novoEstadoInicial, this.estadoInicial, e);
		this.adicionarTransicao(novoEstadoInicial, novoEstadoFinal, e);
		for (int estadoFinal : this.estadosFinais) {
			this.adicionarTransicao(estadoFinal, this.estadoInicial, e);
			this.adicionarTransicao(estadoFinal, novoEstadoFinal, e);
		}

		this.estadoInicial = novoEstadoInicial;
		this.estadosFinais = new Vector<Integer>();
		this.adicionarEstadosFinais(novoEstadoFinal);

		return this;
	}

	private boolean containsEstado(int estado) {
		Estado estadoAtual = this.cabeca;
		do {

			if (estadoAtual.getIdEstado() == estado) {
				return true;
			}

			estadoAtual = estadoAtual.getProximoEstado();
		} while(estadoAtual != null);

		return false;
	}

	private Estado findEstado(int estado) {
		Estado estadoAtual = this.cabeca;
		do {

			if (estadoAtual.getIdEstado() == estado) {
				return estadoAtual;
			}

			estadoAtual = estadoAtual.getProximoEstado();
		} while(estadoAtual != null);

		return null;
	}

}
