package caso3;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el valor de hash: ");
        String pHash = scanner.nextLine();

        System.out.print("Ingrese el valor de salt: ");
        String pSalt = scanner.nextLine();

        System.out.print("Ingrese el valor de algorithm (ejemplo: SHA-256): ");
        String pAlgorithm = scanner.nextLine();

        System.out.print("Ingrese el número de threads (1 o 2): ");
        int numThreads = scanner.nextInt();

        scanner.close();

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
        	System.out.println("La contraseña encontrada fue: "+ rastreador.getPassword());
        }else {
        	System.out.println("No fue posible encontrar la contraseña :(");
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Tiempo de ejecucion: " + (endTime - startTime) + " ms");
        System.out.println("Tiempo de ejecucion: " + (endTime - startTime) / 1000 + " s");
    }
}