package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Operadores;

import java.util.Stack;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Automato;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.AbstractSimboloPadrao;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;

public class Quantificador extends AbstractSimboloPadrao implements Simbolo {

	private int minimo;
	private int maximo;

	@Override
	public String getValor() {
		if (minimo == maximo) {
			return "{" + minimo + "}";
		}
		return "{" + minimo + "," + maximo + "}";
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
		Automato resultado = null;
		contadorDeEstados = Math.max(contadorDeEstados, operando.getMaiorEstado() + 1);
		for (int i = minimo; i <= maximo; i++) {
			Automato concatenacao = operando.duplicar(contadorDeEstados);
			contadorDeEstados = concatenacao.getMaiorEstado() + 1;
			for (int j = 0; j < i - 1; j++) {
				concatenacao.concatenarCom(operando);
				contadorDeEstados = concatenacao.getMaiorEstado() + 1;
				operando = operando.duplicar(contadorDeEstados);
				contadorDeEstados = operando.getMaiorEstado() + 1;
			}
			if (resultado == null) {
				resultado = concatenacao;
			} else {
				resultado.unirCom(concatenacao);
				contadorDeEstados = resultado.getMaiorEstado() + 1;
				operando = operando.duplicar(contadorDeEstados);
				contadorDeEstados = operando.getMaiorEstado() + 1;
			}
		}
		return resultado;
	}

	public void setMinimoMaximo(int minimo, int maximo) {
		this.minimo = minimo;
		this.maximo = maximo;
	}

}
