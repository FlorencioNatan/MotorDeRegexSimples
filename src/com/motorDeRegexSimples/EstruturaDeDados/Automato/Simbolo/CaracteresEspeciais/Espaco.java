package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.CaracteresEspeciais;

import java.util.HashSet;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.AbstractSimboloPadrao;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Caractere;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;

public class Espaco extends AbstractSimboloPadrao implements Simbolo {

	private HashSet<Character> espacos;
	private String valor = "\\\\s";

	public Espaco() {
		espacos = new HashSet<Character>();
		espacos.add('\u0009'); //  Horizontal tab (HT)
		espacos.add('\u0020'); //  Space
		espacos.add('\u00A0'); //  Non-break space
		espacos.add('\u1680'); //  Ogham space mark
		espacos.add('\u180E'); //  Mongolian vowel separator
		espacos.add('\u2000'); //  En quad
		espacos.add('\u2001'); //  Em quad
		espacos.add('\u2002'); //  En space
		espacos.add('\u2003'); //  Em space
		espacos.add('\u2004'); //  Three-per-em space
		espacos.add('\u2005'); //  Four-per-em space
		espacos.add('\u2006'); //  Six-per-em space
		espacos.add('\u2007'); //  Figure space
		espacos.add('\u2008'); //  Punctuation space
		espacos.add('\u2009'); //  Thin space
		espacos.add('\u200A'); //  Hair space
		espacos.add('\u202F'); //  Narrow no-break space
		espacos.add('\u205F'); //  Medium mathematical space
		espacos.add('\u3000'); //  Ideographic space
		espacos.add('\n'); //  Linefeed (LF)
		espacos.add('\u000B'); //  Vertical tab (VT)
		espacos.add('\u000C'); //  Form feed (FF)
		espacos.add('\r'); //  Carriage return (CR)
		espacos.add('\u0085'); //  Next line (NEL)
		espacos.add('\u2028'); //  Line separator
		espacos.add('\u2029'); //  Paragraph separator
	}

	public String getValor() {
		return this.valor;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Espaco) {
			return true;
		}
		if (obj instanceof ExpressaoVazia) {
			return obj.equals(this);
		}
		if (obj instanceof Caractere) {
			String valorString = ((Caractere) obj).getValor();
			char valor = valorString.charAt(0);
			return espacos.contains(valor);
		}
		return false;
	}

	public boolean isEquivalenteAoChar(char valor) {
		return espacos.contains(valor);
	}

}
