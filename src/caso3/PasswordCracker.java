package caso3;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordCracker extends Thread{

	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

	private MessageDigest md;
	private String hash;
	private String salt;
	private int inicioBusqueda;
	private int finBusqueda;
	private Rastreador rastreador;

	public PasswordCracker(String pHash, String pSalt, String pAlgorithm, int inicioBusqueda, int finBusqueda, Rastreador rastreador) {
		this.hash = pHash;
		this.salt = pSalt;
		try {
			this.md = MessageDigest.getInstance(pAlgorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		this.inicioBusqueda = inicioBusqueda;
		this.finBusqueda = finBusqueda;
		this.rastreador = rastreador;
	}

	public boolean searchAllPossibleHashes(int inicioBusqueda, int finBusqueda) {

		boolean found = false;

		for (int i = inicioBusqueda; !rastreador.getRastreador() && i <= finBusqueda; i++) {
			if(searchHashN(i, "")){
				rastreador.setRastreador(true);
				found = true;
			}
		}
		return found;
	}

	public boolean searchHashN(int pLength, String pPrefix) {
		boolean found = false;
		if (pLength == 0) {
			byte[] bytes = md.digest((pPrefix + salt).getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : bytes) {
				sb.append(String.format("%02x", b));
			}
			if (sb.toString().equals(hash)) {
				rastreador.setPassword(pPrefix);
				found = true;
			}
		} else {
			int i = 0;
			while (!found && i < ALPHABET.length() && !rastreador.getRastreador()) {
				found = searchHashN(pLength - 1, pPrefix + ALPHABET.charAt(i));
				i++;
			}
		}
		return found;
	}

	public void run()
	{
		searchAllPossibleHashes(inicioBusqueda, finBusqueda);
	}
}
