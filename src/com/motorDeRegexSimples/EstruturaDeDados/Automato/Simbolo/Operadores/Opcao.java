package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores;

import java.util.Stack;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Automato;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.AbstractSimboloPadrao;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.SimboloFactory;

public class Opcao extends AbstractSimboloPadrao implements Simbolo, OperadorNaoPermiteConcatenacaoAnterior {

	@Override
	public String getValor() {
		return "?";
	}

	@Override
	public boolean equals(Object obj) {
		return false;
	}

	@Override
	public boolean isEquivalenteAoChar(char valor) {
		return false;
	}

	@Override
	public Automato getAutomatoReconhecedor(int contadorDeEstados, Stack<Automato> pilhaDeAutomatos) {
		Automato operando = pilhaDeAutomatos.pop();
		SimboloFactory factory = new SimboloFactory();

		Automato expressaoVazia = new Automato(contadorDeEstados);
		expressaoVazia.adicionarTransicao(contadorDeEstados, contadorDeEstados + 1, factory.getSimbolo());
		expressaoVazia.adicionarEstadosFinais(contadorDeEstados + 1);
		contadorDeEstados += 2;

		return operando.unirCom(expressaoVazia);
	}

}
