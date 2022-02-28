package com.motorDeRegexSimples;

import java.util.Vector;

import com.motorDeRegexSimples.Algoritmos.ConstrucaoDeThompson;
import com.motorDeRegexSimples.Algoritmos.ProcessadorRegexComBacktracking;
import com.motorDeRegexSimples.Algoritmos.ProcessadorRegexConjuntoDeEstados;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Automato;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.RenderizadorDeAutomatos;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Caractere;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.CaracteresEspeciais.ExpressaoVazia;

public class Main {

	public static void main(String[] args) {
		ConstrucaoDeThompson thompson = new ConstrucaoDeThompson();
		Automato automato = thompson.transformar("a*a*a*b");// "a*a*a*b" "ab|c" "ab|c*|d"

		ProcessadorRegexConjuntoDeEstados processado = new ProcessadorRegexConjuntoDeEstados(automato);
//		ProcessadorRegexComBacktracking processado = new ProcessadorRegexComBacktracking(automato);
		Vector<String> matches = processado.processar("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		System.out.println(matches);
//		RenderizadorDeAutomatos renderizador = new RenderizadorDeAutomatos();
//		renderizador.renderizar(automato);
		System.out.println("Terminou de Executar");
	}

	private static Automato exemplo1() {
		Automato automato = new Automato(0);
		Caractere a = new Caractere('a');
		Caractere b = new Caractere('b');
		ExpressaoVazia e = new ExpressaoVazia();
		ExpressaoVazia e2 = new ExpressaoVazia();

		automato.adicionarTransicao(0, 1, e);
		automato.adicionarTransicao(0, 3, e2);
		automato.adicionarTransicao(1, 1, a);
		automato.adicionarTransicao(1, 2, b);
		automato.adicionarTransicao(2, 1, a);
		automato.adicionarTransicao(2, 2, b);
		automato.adicionarTransicao(3, 4, a);
		automato.adicionarTransicao(3, 3, b);
		automato.adicionarTransicao(4, 4, a);
		automato.adicionarTransicao(4, 3, b);
		automato.adicionarTransicao(4, 5, e);

		automato.adicionarEstadosFinais(1);
		automato.adicionarEstadosFinais(3);
		return automato;
	}

	private static Automato exemplo2() {
		Automato automato = new Automato(0);
		Caractere a = new Caractere('a');
		Caractere b = new Caractere('b');

		automato.adicionarTransicao(0, 1, a);
		automato.adicionarTransicao(0, 0, b);
		automato.adicionarTransicao(1, 2, a);
		automato.adicionarTransicao(1, 0, b);
		automato.adicionarTransicao(2, 2, a);
		automato.adicionarTransicao(2, 2, b);

		automato.adicionarEstadosFinais(2);
		return automato;
	}

}