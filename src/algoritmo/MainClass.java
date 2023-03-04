package algoritmo;

import java.util.Scanner;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AlgoritmoRC4 algoritmoRC4 = new AlgoritmoRC4();
		String dictionary = ""; // Diccionario de encriptacion
		String msg = ""; // mensaje
		String enc = ""; // mensaje encriptado
		String key = ""; // clave
		int bits = 0; // cantidad de bits
		Scanner sc = new Scanner(System.in);
		int opc = 0;

		System.out.println("Inicio del programa");
		System.out.println("PARTE 1 - PUNTOS 1 Y 2");
		System.out.println("1- Encriptar");
		System.out.println("2- Desencriptar");
		System.out.println("PARTE 1 - PUNTOS 3");
		System.out.println("3- Encriptar");
		System.out.println("4- Desencriptar");
		System.out.println("PARTE 2 - Ataque de fuerza bruta");
		System.out.println("5- Encriptar");
		System.out.println("6- Desencriptar");
		System.out.println("7- Desencriptar por Fuerza Bruta");

		opc = sc.nextInt();

		switch (opc) {
		case 1:
			dictionary = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ12345";
			key = "CLAVE123";
			bits = 5;
			msg = "MENSAJEDEPRUEBARC4PARACRIPTOLOGIA";

			System.out.println("Procesando Encriptación");
			algoritmoRC4.encriptMsg(dictionary, key, msg, bits);
			break;
		case 2:
			dictionary = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ12345";
			key = "CLAVE123";
			bits = 5;
			enc = "I2LIJLZU5YJGMOBKHMLRBÑC2V5ENIEKE1";
			System.out.println("Procesando Desencriptación");
			algoritmoRC4.desencriptMsg(dictionary, key, enc, bits, false);
			break;
		case 3:
			dictionary = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ12345";
			key = "CLAVE321";
			bits = 5;
			msg = "MENSAJEDEPRUEBARC4PARACRIPTOLOGIA";

			System.out.println("Procesando Encriptación");
			algoritmoRC4.encriptMsg(dictionary, key, msg, bits);
			break;
		case 4:
			dictionary = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ12345";
			key = "CLAVE123";
			bits = 5;
			enc = "LKEREZINI3BESWTCDPOJJ14B1KÑYI4QH1";
			System.out.println("Procesando Desencriptación");
			algoritmoRC4.desencriptMsg(dictionary, key, enc, bits, false);
			break;
		case 5:
			dictionary = "EAOLSNDRUITCPMYQ";
			key = "LEON";
			bits = 4;
			msg = "SOLARIS";

			System.out.println("Procesando Encriptación");
			algoritmoRC4.encriptMsg(dictionary, key, msg, bits);
			break;
		case 6:
			dictionary = "EAOLSNDRUITCPMYQ";
			key = "LEON";
			bits = 4;
			enc = "ARUISYT";
			System.out.println("Procesando Desencriptación");
			algoritmoRC4.desencriptMsg(dictionary, key, enc, bits, false);
			break;
		case 7:
			String dicString = "EAOLSNDRUITCPMYQ";
			bits = 4;
			enc = "ARUISYT";
			System.out.println("Procesando Fuerza Bruta");
			BruteForce bruteForce = new BruteForce();
			bruteForce.algorithmBruteForce(dicString, enc, bits);
			break;
		default:
			System.out.println("No eligio alguna opción valida");
			break;
		}
		System.out.println("\n\n");
		sc.close();
	}

}
