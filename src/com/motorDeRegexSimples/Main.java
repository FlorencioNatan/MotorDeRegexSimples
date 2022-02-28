package com.motorDeRegexSimples;

import java.util.Vector;

import com.motorDeRegexSimples.Algoritmos.ConstrucaoDeThompson;
import com.motorDeRegexSimples.Algoritmos.ProcessadorRegexConjuntoDeEstados;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Automato;

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

}