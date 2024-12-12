import java.util.Scanner;

public class CondicionesRepetitivas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int suma = 0;

        while (true) {
            System.out.print("Ingresa un número (0 para salir): ");
            int numero = scanner.nextInt();

            if (numero == 0) {
                break;
            }
            suma += numero;
        }
        System.out.println("La suma total es: " + suma);
        scanner.close();
    }
}
