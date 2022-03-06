package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.CaracteresEspeciais;

import java.util.HashSet;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.AbstractSimboloPadrao;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Caractere;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;

public class EspacoHorizontal extends AbstractSimboloPadrao implements Simbolo {

	private HashSet<Character> espacosHorizontais;
	private String valor = "\\\\h";

	public EspacoHorizontal() {
		espacosHorizontais = new HashSet<Character>();
		espacosHorizontais.add('\u0009'); //  Horizontal tab (HT)
		espacosHorizontais.add('\u0020'); //  Space
		espacosHorizontais.add('\u00A0'); //  Non-break space
		espacosHorizontais.add('\u1680'); //  Ogham space mark
		espacosHorizontais.add('\u180E'); //  Mongolian vowel separator
		espacosHorizontais.add('\u2000'); //  En quad
		espacosHorizontais.add('\u2001'); //  Em quad
		espacosHorizontais.add('\u2002'); //  En space
		espacosHorizontais.add('\u2003'); //  Em space
		espacosHorizontais.add('\u2004'); //  Three-per-em space
		espacosHorizontais.add('\u2005'); //  Four-per-em space
		espacosHorizontais.add('\u2006'); //  Six-per-em space
		espacosHorizontais.add('\u2007'); //  Figure space
		espacosHorizontais.add('\u2008'); //  Punctuation space
		espacosHorizontais.add('\u2009'); //  Thin space
		espacosHorizontais.add('\u200A'); //  Hair space
		espacosHorizontais.add('\u202F'); //  Narrow no-break space
		espacosHorizontais.add('\u205F'); //  Medium mathematical space
		espacosHorizontais.add('\u3000'); //  Ideographic space
	}

	public String getValor() {
		return this.valor;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EspacoHorizontal) {
			return true;
		}
		if (obj instanceof ExpressaoVazia) {
			return obj.equals(this);
		}
		if (obj instanceof Caractere) {
			String valorString = ((Caractere) obj).getValor();
			char valor = valorString.charAt(0);
			return espacosHorizontais.contains(valor);
		}
		return false;
	}

	public boolean isEquivalenteAoChar(char valor) {
		return espacosHorizontais.contains(valor);
	}

}
