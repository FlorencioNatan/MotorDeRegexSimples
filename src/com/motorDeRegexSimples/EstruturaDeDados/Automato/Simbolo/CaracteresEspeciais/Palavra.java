package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.CaracteresEspeciais;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Caractere;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;

public class Palavra implements Simbolo {

	private String valor = "\\\\w";

	public String getValor() {
		return this.valor;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Palavra) {
			return true;
		}
		if (obj instanceof ExpressaoVazia) {
			return obj.equals(this);
		}
		if (obj instanceof Caractere) {
			String valorString = ((Caractere) obj).getValor();
			char valor = valorString.charAt(0);
			return (valor >= 'A' && valor <= 'Z') ||
					(valor >= 'a' && valor <= 'z') ||
					(valor == '_') ||
					(valor >= 'À' && valor <= 'Ö') ||
					(valor >= 'Ø' && valor <= 'ö') ||
					(valor >= 'ø' && valor <= 'ÿ') ||
					(valor >= '0' && valor <= '9');
		}
		return false;
	}

	public boolean isEquivalenteAoChar(char valor) {
		return (valor >= 'A' && valor <= 'Z') ||
				(valor >= 'a' && valor <= 'z') ||
				(valor == '_') ||
				(valor >= 'À' && valor <= 'Ö') ||
				(valor >= 'Ø' && valor <= 'ö') ||
				(valor >= 'ø' && valor <= 'ÿ') ||
				(valor >= '0' && valor <= '9');
	}

}
