package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo;

import java.util.LinkedList;
import java.util.Stack;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Automato;

public interface Simbolo {

	public String getValor();

	public boolean equals(Object obj);

	public boolean isEquivalenteAoChar(char valor);

	/**
	 * !!Atenção esse método altera a pilhaDeAutomatos!!
	 */
	public Automato getAutomatoReconhecedor(int contadorDeEstados, Stack<Automato> pilhaDeAutomatos);

	public void processarShuntingYard(Stack<Simbolo> pilhaOperadores, LinkedList<Simbolo> regexPostfix);

}
