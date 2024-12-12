package com.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static int ultimoIdProducto = 20;
    private static final Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {
        // Inicio del programa
        System.out.println("Iniciando el programa de gestión de productos...");

        // Explicación breve de las funcionalidades
        System.out.println("Este programa te permitirá gestionar productos fácilmente.");
        System.out.println("Podrás buscar, agregar, eliminar y generar documentos PDF relacionados con los productos.");
        System.out.println();

        // Inicializar los productos y mostrar mensaje
        System.out.println("Cargando productos desde la API...");
        Service.inicializarProductos();
        System.out.println("Productos cargados correctamente. Puedes gestionarlos desde el menú.");

        // Instrucciones iniciales para el usuario
        System.out.println("¡Bienvenido al sistema de gestión de productos!");
        System.out.println("Sigue las instrucciones que aparecerán en pantalla para interactuar con el sistema.");
        System.out.println("Si necesitas ayuda, selecciona una opción válida.");

        // Mostrar el menú principal
        mostrarMenuPrincipal();

        // Mensaje de cierre
        System.out.println("Gracias por usar el sistema de gestión de productos.");
        System.out.println("Cerrando el programa. ¡Que tengas un excelente día!");
    }

    private static void mostrarMenuPrincipal() {
        int opcion;
        do {
            // Mostrar opciones disponibles
            System.out.println("=======================================");
            System.out.println("= Gestión de Productos               =");
            System.out.println("= 1) Buscar Productos                =");
            System.out.println("= 2) Agregar Producto                =");
            System.out.println("= 3) Eliminar Producto               =");
            System.out.println("= 4) Cargar PDF                      =");
            System.out.println("= 0) Salir                           =");
            System.out.println("=======================================");
            System.out.print("Selecciona una opción: ");

            // Leer opción del usuario
            opcion = entrada.nextInt();

            // Llamar al método correspondiente según la opción elegida
            switch (opcion) {
                case 1 -> buscarProductos();
                case 2 -> agregarProducto();
                case 3 -> eliminarProducto();
                case 4 -> descargarpdf();
                case 0 -> System.out.println("Saliendo del sistema. Hasta pronto.");
                default -> System.out.println("Opción inválida. Inténtalo de nuevo.");
            }
        } while (opcion != 0);
    }

    private static void buscarProductos() {
        // Submenú para buscar productos
        System.out.println("a) Buscar por ID");
        System.out.println("b) Buscar por Categoría");
        System.out.println("c) Buscar por Nombre");
        System.out.print("Selecciona una opción: ");
        char opcion = entrada.next().charAt(0);

        // Ejecutar búsqueda según la opción seleccionada
        switch (opcion) {
            case 'a' -> buscarPorId();
            case 'b' -> buscarPorCategoria();
            case 'c' -> buscarPorNombre();
            default -> System.out.println("Opción inválida. Regresando al menú principal.");
        }
    }

    private static void agregarProducto() {
        // Solicitar datos del nuevo producto
        System.out.println("Introduce los detalles del nuevo producto:");
        System.out.print("Nombre: ");
        entrada.nextLine(); // Consumir nueva línea
        String nombre = entrada.nextLine();

        System.out.print("Precio: ");
        double precio = entrada.nextDouble();

        System.out.print("Descripción: ");
        entrada.nextLine(); // Consumir nueva línea
        String descripcion = entrada.nextLine();

        System.out.print("Categoría: ");
        String categoria = entrada.nextLine();

        System.out.print("URL Imagen: ");
        String urlImagen = entrada.nextLine();

        // Crear y agregar el producto
        ultimoIdProducto++;
        Producto nuevoProducto = new Producto(ultimoIdProducto, nombre, precio, descripcion, categoria, urlImagen, new Rating(4.5, 10));
        Service.agregarProducto(nuevoProducto);

        // Confirmar operación
        System.out.println("Producto agregado correctamente.");
        System.out.println("Total de productos actuales: " + (ultimoIdProducto - 20) + ".");
        System.out.println("Puedes volver al menú principal para gestionar más productos.");
    }

    private static void buscarPorId() {
        // Solicitar ID y buscar el producto
        System.out.print("ID: ");
        int id = entrada.nextInt();
        Producto producto = Service.buscarProductoPorId(id);

        // Mostrar el producto encontrado o mensaje de error
        mostrarProducto(producto);
        System.out.println("Búsqueda completada. Puedes realizar otra acción desde el menú.");
    }

    private static void buscarPorCategoria() {
        // Obtener y mostrar las categorías disponibles
        System.out.println("Obteniendo categorías disponibles...");
        ArrayList<String> categorias = Service.obtenerCategorias();
        for (int i = 0; i < categorias.size(); i++) {
            System.out.println((i + 1) + ") " + categorias.get(i));
        }
        System.out.print("Selecciona categoría: ");

        // Solicitar selección y mostrar productos
        int indice = entrada.nextInt() - 1;
        if (indice >= 0 && indice < categorias.size()) {
            ArrayList<Producto> productos = Service.obtenerProductosPorCategoria(categorias.get(indice));
            productos.forEach(Main::mostrarProducto);
        } else {
            System.out.println("Categoría no válida. Inténtalo de nuevo.");
        }
    }

    private static void buscarPorNombre() {
        // Solicitar el nombre del producto
        System.out.println("Introduce el nombre del producto que deseas buscar:");
        System.out.print("Nombre: ");
        entrada.nextLine(); // Consumir nueva línea
        String nombre = entrada.nextLine();

        // Buscar y mostrar productos que coincidan
        ArrayList<Producto> productos = Service.buscarProductosPorNombre(nombre);
        productos.forEach(Main::mostrarProducto);
        System.out.println("Búsqueda por nombre completada. Verifica los resultados mostrados.");
    }

    private static void eliminarProducto() {
        // Solicitar ID del producto a eliminar
        System.out.println("Introduce el ID del producto que deseas eliminar:");
        System.out.print("ID: ");
        int id = entrada.nextInt();

        // Intentar eliminar el producto y mostrar mensaje correspondiente
        if (Service.eliminarProducto(id)) {
            System.out.println("El producto con ID " + id + " fue eliminado correctamente.");
        } else {
            System.out.println("No se encontró un producto con el ID proporcionado.");
        }
        System.out.println("Eliminación completada. Puedes realizar más acciones desde el menú principal.");
    }

    private static void mostrarProducto(Producto producto) {
        // Mostrar detalles del producto si existe
        if (producto != null) {
            System.out.println("Detalles del producto encontrado:");
            System.out.println("Nombre: " + producto.getTitle());
            System.out.println("Precio: " + producto.getPrice());
            System.out.println("Categoría: " + producto.getCategory());
            System.out.println("Descripción: " + producto.getDescription());
            System.out.println("Imagen: " + producto.getImage());
        } else {
            System.out.println("Producto no encontrado.");
        }
        System.out.println("Consulta completada. Puedes realizar otra acción desde el menú.");
    }

    private static void descargarpdf() {
        // Solicitar ID del producto
        System.out.println("Introduce el ID del producto para generar su PDF:");
        System.out.print("ID: ");
        int id = entrada.nextInt();

        // Buscar el producto y generar PDF si existe
        Producto producto = Service.buscarProductoPorId(id);
        if (producto != null) {
            generarPdfProducto.guardaPDFDeProducto(producto);
            System.out.println("PDF generado correctamente para el producto con ID: " + id);
        } else {
            System.out.println("No se encontró el producto para generar el PDF.");
        }
        System.out.println("Operación completada. Puedes realizar más acciones desde el menú principal.");
    }
}
