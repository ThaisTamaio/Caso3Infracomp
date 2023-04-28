package caso3;

import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Main {

    static String respuesta = "";
    static Integer contador = 0;

	public static void main(String[] args) throws InterruptedException {
		
		
		String[] algorithms = {"SHA-256", "SHA-512"};
		String[] salts = {"ab", "yz"};

		for (int i = 1; i <= 6; i++) {
			for (int j = 0; j < salts.length; j++) {
				for (int j2 = 0; j2 < algorithms.length; j2++) {
					for (int k = 1; k <= 2; k++) {
						for (int k2 = 0; k2 < 2; k2++) {
							String input ;

							if (k2 == 0) {
								char[] chars = new char[i];
							    Arrays.fill(chars, 'a');
							    input = new String(chars)+salts[j];
							}else {
								char[] chars = new char[i];
							    Arrays.fill(chars, 'z');
							    input = new String(chars)+salts[j];
							}
							byte[] hash = null;
							try {
								MessageDigest digest = MessageDigest.getInstance(algorithms[j2]);
								hash = digest.digest(input.getBytes());
							} catch (NoSuchAlgorithmException e) {
								e.printStackTrace();
							}
                            respuesta += "Longitud: "+i+", Sal: "+salts[j]+", Algoritmo: "+algorithms[j2]+ ", Threads: "+k+" Input "+input.substring(0, input.length()-2) + "\n";
							runTest(bytesToHex(hash), algorithms[j2], salts[j], k);

                            contador ++;
                            System.out.println("Vamos " + contador + " casos corridos.");
						}
					}
				}
			}
		}
		
		// Crear un objeto FileWriter y escribir el contenido de la variable respuesta en el archivo respuesta.txt
		try {
			FileWriter writer = new FileWriter("respuesta.txt");
			writer.write(respuesta);
			writer.close();
			System.out.println("La respuesta se ha guardado correctamente en el archivo respuesta.txt.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void runTest(String pHash, String pAlgorithm, String pSalt, int numThreads) throws InterruptedException {

		Rastreador rastreador = new Rastreador();
		int inicioBusqueda1 = 1;
		int finBusqueda1 = 3;
		int inicioBusqueda2 = 4;
		int finBusqueda2 = 7;

		if (numThreads == 1) {
			finBusqueda1 = finBusqueda2;
		}

		PasswordCracker pc = new PasswordCracker(pHash, pSalt, pAlgorithm, inicioBusqueda1, finBusqueda1, rastreador);
		PasswordCracker pc2 = new PasswordCracker(pHash, pSalt, pAlgorithm, inicioBusqueda2, finBusqueda2, rastreador);

		long startTime = System.currentTimeMillis();

		pc.start();
		if (numThreads == 2) {
			pc2.start();
			pc2.join();
		}

		pc.join();

		if(rastreador.getRastreador()) {
            respuesta += "La contraseña encontrada fue: "+ rastreador.getPassword() + "\n";
		}else {
            respuesta += "No fue posible encontrar la contraseña :(" + "\n";
		}

		long endTime = System.currentTimeMillis();

        respuesta += "Tiempo de ejecucion: " + (endTime - startTime) + " ms" + "\n";
        respuesta += "Tiempo de ejecucion: " + (endTime - startTime) / 1000 + " s" + "\n";
	}

	private static String bytesToHex(byte[] bytes) {
		StringBuilder result = new StringBuilder();
		for (byte b : bytes) {
			result.append(String.format("%02x", b));
		}
		return result.toString();
	}
}