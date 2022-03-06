package com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.CaracteresEspeciais;

import java.util.HashSet;

import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.AbstractSimboloPadrao;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Caractere;
import com.motorDeRegexSimples.EstruturaDeDados.Automato.Simbolo.Simbolo;

public class EspacoVertical extends AbstractSimboloPadrao implements Simbolo {

	private HashSet<Character> espacosVerticais;
	private String valor = "\\\\v";

	public EspacoVertical() {
		espacosVerticais = new HashSet<Character>();
		espacosVerticais.add('\n'); //  Linefeed (LF)
		espacosVerticais.add('\u000B'); //  Vertical tab (VT)
		espacosVerticais.add('\u000C'); //  Form feed (FF)
		espacosVerticais.add('\r'); //  Carriage return (CR)
		espacosVerticais.add('\u0085'); //  Next line (NEL)
		espacosVerticais.add('\u2028'); //  Line separator
		espacosVerticais.add('\u2029'); //  Paragraph separator
	}

	public String getValor() {
		return this.valor;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EspacoVertical) {
			return true;
		}
		if (obj instanceof ExpressaoVazia) {
			return obj.equals(this);
		}
		if (obj instanceof Caractere) {
			String valorString = ((Caractere) obj).getValor();
			char valor = valorString.charAt(0);
			return espacosVerticais.contains(valor);
		}
		return false;
	}

	public boolean isEquivalenteAoChar(char valor) {
		return espacosVerticais.contains(valor);
	}

}
