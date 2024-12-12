import java.util.Scanner;

public class Arreglos {
    public static void main(String[] args) {
        int[] numeros = new int[5];
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < numeros.length; i++) {
            System.out.print("Ingresa un número para la posición " + i + ": ");
            numeros[i] = scanner.nextInt();
        }

        System.out.println("Números ingresados:");
        for (int numero : numeros) {
            System.out.println(numero);
        }
        scanner.close();
    }
}
