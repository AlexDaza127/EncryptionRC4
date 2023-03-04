package algoritmo;

import java.io.File;

public class BruteForce {

	public void algorithmBruteForce(String dictionary, String msgEncript, int bits) {
		AlgoritmoRC4 algoritmoRC4 = new AlgoritmoRC4();
		char[] posChar = dictionary.toCharArray();
		System.out.println("cantidad de contraseñas = 2^" + posChar.length);

		File fileDel = new File("./contraseñas.txt");
		if (fileDel.delete()) {
			System.out.println("Se elimino el archivo: contraseñas.txt");
		} else {
			System.out.println("No se elimino el archivo: contraseñas.txt");
		}

		long inicio = System.currentTimeMillis();
		for (Character i : posChar) {
			for (Character j : posChar) {
				for (Character k : posChar) {
					for (Character l : posChar) {

						String posibleClave = String.valueOf(i) + String.valueOf(j) + String.valueOf(k)
								+ String.valueOf(l);
						algoritmoRC4.desencriptMsg(dictionary, posibleClave, msgEncript, bits, true);
					}
				}
			}
		}
		
		long fin = System.currentTimeMillis();
		double tiempoTotal = (double) ((fin - inicio));
		System.out.println("Tiempo total de ejecución: " + tiempoTotal + " ms");
		System.out.println("Fin del proceso");
	}

}
