package algoritmo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgoritmoRC4 {

	private String dictionary = ""; // Diccionario de encriptacion
	private String msg = ""; // mensaje
	private String key = ""; // clave
	private int bits = 5; // cantidad de bits
	private int sizeDict = 0; // Tamaño del diccionario

	Map<Character, Integer> d = new HashMap<>(); // Diccionario con identificacion numerica
	List<Integer> S = new ArrayList<>(); // Numero de cada caracter del diccionario
	List<Character> K = new ArrayList<>(); // lista de cada caracter que compone la clave
	List<Integer> KS = new ArrayList<>(); // lista con el KeyStream

	private void initialProcess() {
		for (int i = 0; i < getSizeDict(); i++) {
			S.add(i);
		}

		char[] dca = getDictionary().toCharArray();
		int j = 0;
		for (char c : dca) {
			d.put(c, j);
			j++;
		}

		char[] kca = getKey().toCharArray();
		for (char k : kca)
			K.add(k);

		PRGA();
	}

	private List<Integer> KSA() {
		List<Integer> S_KSA = new ArrayList<>();
		S_KSA.addAll(S);
		int j = 0;
		for (int i = 0; i < getSizeDict(); i++) {
			j = (j + S_KSA.get(i) + d.get(K.get(i % K.size()))) % getSizeDict();
			int temp = S_KSA.get(i);

			S_KSA.set(i, S_KSA.get(j));
			S_KSA.set(j, temp);
		}
		//System.out.println("S = " + S_KSA);
		return S_KSA;
	}

	private void PRGA() {
		List<Integer> S_KSA = new ArrayList<>();
		S_KSA.addAll(KSA());
		int i = 0;
		int j = 0;
		int k = 0;
		int t = 0;
		while (k < getMsg().length()) {
			i = (i + 1) % getSizeDict();
			j = (j + S_KSA.get(i)) % getSizeDict();
			int temp = S_KSA.get(i);

			S_KSA.set(i, S_KSA.get(j));
			S_KSA.set(j, temp);
			t = (S_KSA.get(i) + S_KSA.get(j)) % getSizeDict();

			KS.add(S_KSA.get(t));
			k++;
		}

		//System.out.println("KS = " + KS);
	}

	public String lectorMsg(List<Character> msgL, String type) {
		StringBuilder lector = new StringBuilder();
		for (Character character : msgL) {
			lector.append(character);
		}

		System.out.println("Este es el mensaje " + type + " = " + lector.toString());

		return lector.toString();
	}

	public String lectorMsgDes(List<Character> msgL) {
		StringBuilder lector = new StringBuilder();
		for (Character character : msgL) {
			lector.append(character);
		}

		return lector.toString();
	}

	public List<Character> encriptMsg(String diccionarioE, String claveE, String mensajeE, int bitsE) {

		setDictionary(diccionarioE);
		setMsg(mensajeE);
		setKey(claveE);
		setBits(bitsE);
		setSizeDict(diccionarioE.length());

		initialProcess();

		char[] dca = mensajeE.toCharArray();
		List<Integer> convDictMsg = new ArrayList<>();

		for (char c : dca) {
			convDictMsg.add(d.get(c));
		}

		return motorAlgorithm(convDictMsg, "encriptado");
	}

	public void desencriptMsg(String diccionarioD, String claveD, String msgEncriptD, int bitsD, Boolean forceB) {

		setDictionary(diccionarioD);
		setKey(claveD);
		setMsg(msgEncriptD);
		setBits(bitsD);
		setSizeDict(diccionarioD.length());
		initialProcess();

		char[] msgEncriptChar = msgEncriptD.toCharArray();

		List<Integer> msgEncCharToDec = new ArrayList<>();
		for (Character c : msgEncriptChar) {
			msgEncCharToDec.add(d.get(c));
		}

		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			if (forceB) {
				File file = new File("./contraseñas.txt");
				fw = new FileWriter(file.getAbsoluteFile(), true);
				bw = new BufferedWriter(fw);
				bw.write("CLAVE = " + claveD + " | MENSAJE = "
						+ lectorMsgDes(motorAlgorithm(msgEncCharToDec, "desencriptado")) + "\n");

			} else {
				motorAlgorithm(msgEncCharToDec, "desencriptado");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
				if (fw != null) {
					fw.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public List<Character> motorAlgorithm(List<Integer> convDictMsg, String type) {
		List<Character> msgDecToDic = new ArrayList<>(); // mensaje de decimal a diccionario de encriptacion
		StringBuilder msgBin = new StringBuilder();
		StringBuilder ksBin = new StringBuilder();

		for (int c : convDictMsg) {
			msgBin.append(String.format("%0" + getBits() + "d", Integer.parseInt(Integer.toBinaryString(c))));

		}
		for (int c : KS) {
			ksBin.append(String.format("%0" + getBits() + "d", Integer.parseInt(Integer.toBinaryString(c))));
		}

		System.out.println("KeyStream en Binario = " + ksBin.toString());
		char[] mBin = msgBin.toString().toCharArray();
		char[] kBin = ksBin.toString().toCharArray();
		StringBuilder msgBinResult = new StringBuilder();

		for (int i = 0; i < kBin.length; i++) {
			msgBinResult.append(mBin[i] == kBin[i] ? "0" : "1");
		}

		System.out.println("Resultado XOR = " + msgBinResult.toString());
		char[] mbr = msgBinResult.toString().toCharArray();
		List<String> divBinPerBit = new ArrayList<>();

		String b = "";
		for (int i = 0; i < mbr.length; i++) {
			b += String.valueOf(mbr[i]);
			if (b.length() == getBits()) {
				divBinPerBit.add(b);
				b = "";
			}
		}

		List<Integer> msgBinToDec = new ArrayList<>();

		for (int i = 0; i < divBinPerBit.size(); i++) {
			int decimal = Integer.parseInt(divBinPerBit.get(i), 2);
			msgBinToDec.add(decimal);
			msgDecToDic.add(getKey(d, decimal));

		}

		lectorMsg(msgDecToDic, type);
		d.clear();
		S.clear();
		K.clear();
		KS.clear();
		return msgDecToDic;
	}

	public static <K, V> K getKey(Map<K, V> map, V value) {
		return map.entrySet().stream().filter(entry -> value.equals(entry.getValue())).findFirst()
				.map(Map.Entry::getKey).orElse(null);
	}

	/**
	 * @return the dictionary
	 */
	public String getDictionary() {
		return dictionary;
	}

	/**
	 * @param dictionary the dictionary to set
	 */
	public void setDictionary(String dictionary) {
		this.dictionary = dictionary;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the bits
	 */
	public int getBits() {
		return bits;
	}

	/**
	 * @param bits the bits to set
	 */
	public void setBits(int bits) {
		this.bits = bits;
	}

	/**
	 * @return the sizeDict
	 */
	public int getSizeDict() {
		return sizeDict;
	}

	/**
	 * @param sizeDict the sizeDict to set
	 */
	public void setSizeDict(int sizeDict) {
		this.sizeDict = sizeDict;
	}

}
