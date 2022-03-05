package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.CaracteresEspeciais;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.AbstractSimboloComAutomatoReconhecedorSimples;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Caractere;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;

public class Digitos extends AbstractSimboloComAutomatoReconhecedorSimples implements Simbolo {

	private String valor = "\\\\d";

	public String getValor() {
		return this.valor;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Digitos) {
			return true;
		}
		if (obj instanceof ExpressaoVazia) {
			return obj.equals(this);
		}
		if (obj instanceof Caractere) {
			String valorString = ((Caractere) obj).getValor();
			char valor = valorString.charAt(0);
			return valor >= '0' && valor <= '9';
		}
		return false;
	}

	public boolean isEquivalenteAoChar(char valor) {
		return valor >= '0' && valor <= '9';
	}

}
