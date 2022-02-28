package com.motorDeRegexSimples.EstruturaDeDados.Automato;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;

public class RenderizadorDeAutomatos {

	public void renderizar(Automato automato) {
		String graphvizString = automato.getGraphvizString();

		LocalDateTime agora = LocalDateTime.now();
		String nomeDoArquivo;
		nomeDoArquivo = "/tmp/automato_" + agora.toString() + ".gv";

		try {
			File arquivo = new File(nomeDoArquivo);
			arquivo.createNewFile();
			arquivo.setWritable(true);

			FileWriter escritor = new FileWriter(arquivo);
			escritor.write(graphvizString);
			escritor.flush();
			escritor.close();

			String comando = "xdot " + nomeDoArquivo + "";
			Runtime.getRuntime().exec(comando);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
