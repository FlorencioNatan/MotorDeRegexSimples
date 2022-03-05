package com.motorDeRegexSimples.Algoritmos;

import java.util.LinkedList;
import java.util.Stack;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Automato;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.CaracteresEspeciais.ExpressaoVazia;

public class ConstrucaoDeThompson {

	private int contadorDeEstados;

	public ConstrucaoDeThompson() {
		contadorDeEstados = 0;
	}

	public Automato transformar(String regex) {
		ShuntingYard sy = new ShuntingYard();
		LinkedList<Simbolo> regexPostFix = sy.processar(regex);

		if (regexPostFix.size() == 0) {
			return processarStringVazia();
		}

		Stack<Automato> pilhaDeAutomatos = new Stack<>();
		for (Simbolo tokenAtual : regexPostFix) {
			Automato automato = tokenAtual.getAutomatoReconhecedor(contadorDeEstados, pilhaDeAutomatos);
			contadorDeEstados = automato.getMaiorEstado() + 1;
			pilhaDeAutomatos.add(automato);
		}

		return pilhaDeAutomatos.pop();
	}

	private Automato processarStringVazia() {
		Automato automatoStringVazia = new Automato(0);
		ExpressaoVazia e = new ExpressaoVazia();
		automatoStringVazia.adicionarTransicao(0, 1, e);
		automatoStringVazia.adicionarEstadosFinais(1);
		return automatoStringVazia;
	}

}
