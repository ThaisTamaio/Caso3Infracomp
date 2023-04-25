package caso3;


public class Main {

	public static void main(String[] args) throws InterruptedException{


		PasswordCracker pc = new PasswordCracker("c325da0495aed5eaef96c4e1f9ae96fff4c0d941a57d0e263f11ff0e140982af", "xy", "SHA-256");
		pc.searchAllPossibleHashes(7);
	}
}