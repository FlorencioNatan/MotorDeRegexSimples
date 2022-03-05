package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.CaracteresEspeciais;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.AbstractSimboloComAutomatoReconhecedorSimples;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;

public class ExpressaoVazia extends AbstractSimboloComAutomatoReconhecedorSimples implements Simbolo {

	private char valor = 'ε';

	public String getValor() {
		return Character.toString(this.valor);
	}

	@Override
	public boolean equals(Object obj) {
		return true;
	}

	public boolean isEquivalenteAoChar(char valor) {
		return true;
	}

}
