package caso3;


public class Main {

	public static void main(String[] args) throws InterruptedException{

		Rastreador rastreador = new Rastreador();
		PasswordCracker pc = new PasswordCracker("137fc6107cc60cc069a8a245b2933df3b6d80d0d0bd02a633e609918adcf2e06", "xy", "SHA-256", 1, 3, rastreador);
		PasswordCracker pc2 = new PasswordCracker("137fc6107cc60cc069a8a245b2933df3b6d80d0d0bd02a633e609918adcf2e06", "xy", "SHA-256", 4, 7, rastreador);

		long startTime = System.currentTimeMillis();

		pc.start();
		pc2.start();

		pc.join();
		pc2.join();

		long endTime = System.currentTimeMillis();

		System.out.println("Tiempo de ejecucion: " + (endTime - startTime) + " ms");
		System.out.println("Tiempo de ejecucion: " + (endTime - startTime)/1000 + " s");
	}
}