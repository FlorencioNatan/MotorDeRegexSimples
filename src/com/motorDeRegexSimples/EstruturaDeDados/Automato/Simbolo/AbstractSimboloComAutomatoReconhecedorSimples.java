package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo;

import java.util.Stack;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Automato;

public abstract class AbstractSimboloComAutomatoReconhecedorSimples implements Simbolo {

	public Automato getAutomatoReconhecedor(int contadorDeEstados, Stack<Automato> pilhaDeAutomatos) {
		Automato automato = new Automato(contadorDeEstados);
		automato.adicionarTransicao(contadorDeEstados, contadorDeEstados + 1, this);
		automato.adicionarEstadosFinais(contadorDeEstados + 1);
		return automato;
	}

}
