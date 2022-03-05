package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.CaracteresEspeciais;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.AbstractSimboloComAutomatoReconhecedorSimples;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Caractere;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;

public class Contrabarra extends AbstractSimboloComAutomatoReconhecedorSimples implements Simbolo {

	private String valor = "\\\\";

	public String getValor() {
		return this.valor;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Contrabarra) {
			return true;
		}
		if (obj instanceof ExpressaoVazia) {
			return obj.equals(this);
		}
		if (obj instanceof Caractere) {
			String valorString = ((Caractere) obj).getValor();
			char valor = valorString.charAt(0);
			return valor == '\\';
		}
		return false;
	}

	public boolean isEquivalenteAoChar(char valor) {
		return valor == '\\';
	}

}
