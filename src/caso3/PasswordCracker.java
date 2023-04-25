package caso3;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordCracker {

	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

	private MessageDigest md;
	private String hash;
	private String salt;

	public PasswordCracker(String pHash, String pSalt, String pAlgorithm) {
		this.hash = pHash;
		this.salt = pSalt;
		try {
			this.md = MessageDigest.getInstance(pAlgorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public boolean searchAllPossibleHashes(int pLenght) {
		boolean found = false;
		for (int i = 0; !found && i <= pLenght; i++) {
			if(searchHashN(i, "")){
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
				System.out.println(pPrefix);
				found = true;
			}
		} else {
			int i = 0;
			while (!found && i < ALPHABET.length()) {
				found = searchHashN(pLength - 1, pPrefix + ALPHABET.charAt(i));
				i++;
			}
		}
		return found;
	}
}
