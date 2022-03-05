package com.motorDeRegexSimples.Algoritmos.Testes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Vector;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import com.motorDeRegexSimples.Algoritmos.ConstrucaoDeThompson;
import com.motorDeRegexSimples.Algoritmos.ProcessadorRegexComBacktracking;
import com.motorDeRegexSimples.Algoritmos.ProcessadorRegexConjuntoDeEstados;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Automato;

class TesteProcessadorRegexComBacktrack {

	@Test
	void testSimples() {
		ConstrucaoDeThompson thompson = new ConstrucaoDeThompson();
		Automato automato = thompson.transformar("(Natan)|(Gama)");
		ProcessadorRegexComBacktracking processado = new ProcessadorRegexComBacktracking(automato);
		Vector<String> matches = processado.processar("Florencio Natan dos Santos Gama");

		Vector<String> expected = new Vector<>();
		expected.add("Natan");
		expected.add("Gama");

		assertEquals(expected, matches);
	}

	@Test
	void testSimples2() {
		ConstrucaoDeThompson thompson = new ConstrucaoDeThompson();
		Automato automato = thompson.transformar("c*|s|d");
		ProcessadorRegexComBacktracking processado = new ProcessadorRegexComBacktracking(automato);
		Vector<String> matches = processado.processar("cccccccdccasa");

		Vector<String> expected = new Vector<>();
		expected.add("ccccccc");
		expected.add("d");
		expected.add("cc");
		expected.add("s");

		assertEquals(expected, matches);
	}

	@Test
	void testTodasAsOperacoes() {
		ConstrucaoDeThompson thompson = new ConstrucaoDeThompson();
		Automato automato = thompson.transformar("(a|A)b|c*|do");
		ProcessadorRegexComBacktracking processado = new ProcessadorRegexComBacktracking(automato);
		Vector<String> matches = processado.processar("Abcasia do sul");

		Vector<String> expected = new Vector<>();
		expected.add("Ab");
		expected.add("c");
		expected.add("do");

		assertEquals(expected, matches);
	}

	@Test
	void testCPF() {
		ConstrucaoDeThompson thompson = new ConstrucaoDeThompson();
		Automato automato = thompson.transformar("(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9).(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9).(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)-(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)");
		ProcessadorRegexComBacktracking processado = new ProcessadorRegexComBacktracking(automato);
		Vector<String> matches = processado.processar("Meu CPF é: 012.345.678-90.");

		Vector<String> expected = new Vector<>();
		expected.add("012.345.678-90");

		assertEquals(expected, matches);
	}

	@Test
	void testDigitos() {
		ConstrucaoDeThompson thompson = new ConstrucaoDeThompson();
		Automato automato = thompson.transformar("\\d\\d\\d.\\d\\d\\d.\\d\\d\\d-\\d\\d");
		ProcessadorRegexConjuntoDeEstados processado = new ProcessadorRegexConjuntoDeEstados(automato);
		Vector<String> matches = processado.processar("Meu CPF é: 012.345.678-90.");

		Vector<String> expected = new Vector<>();
		expected.add("012.345.678-90");

		assertEquals(expected, matches);
	}

	@Test
	void testDesempenho1() {
		ConstrucaoDeThompson thompson = new ConstrucaoDeThompson();
		Automato automato = thompson.transformar("a*a*a*b");
		ProcessadorRegexComBacktracking processado = new ProcessadorRegexComBacktracking(automato);
		Vector<String> matches = processado.processar("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

		Vector<String> expected = new Vector<>();

		assertEquals(expected, matches);
	}

	@Ignore
	void testDesempenho2() {
		ConstrucaoDeThompson thompson = new ConstrucaoDeThompson();
		Automato automato = thompson.transformar("a*a*a*a*b");
		ProcessadorRegexComBacktracking processado = new ProcessadorRegexComBacktracking(automato);
		Vector<String> matches = processado.processar("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

		Vector<String> expected = new Vector<>();

		assertEquals(expected, matches);
	}

	@Ignore
	void testDesempenho3() {
		ConstrucaoDeThompson thompson = new ConstrucaoDeThompson();
		Automato automato = thompson.transformar("a*a*a*a*a*b");
		ProcessadorRegexComBacktracking processado = new ProcessadorRegexComBacktracking(automato);
		Vector<String> matches = processado.processar("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

		Vector<String> expected = new Vector<>();

		assertEquals(expected, matches);
	}

}
