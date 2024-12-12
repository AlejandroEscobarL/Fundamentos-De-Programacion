package com.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.classic.methods.HttpGet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Service {

    private static final String API_URL = "https://fakestoreapi.com/products/";
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static List<Producto> productosLocales = new ArrayList<>();

    /**
     * Inicializa los productos desde la API si no se han cargado aún.
     */
    public static void inicializarProductos() {
        if (productosLocales.isEmpty()) {
            try {
                HttpGet request = new HttpGet(API_URL);
                try (CloseableHttpResponse response = httpClient.execute(request)) {
                    if (response.getCode() == 200) {
                        productosLocales = objectMapper.readValue(
                                response.getEntity().getContent(),
                                new TypeReference<List<Producto>>() {}
                        );
                        System.out.println("Productos cargados exitosamente.");
                    } else {
                        System.err.println("Error al consumir la API. Código: " + response.getCode());
                    }
                }
            } catch (IOException e) {
                System.err.println("Error al consumir la API: " + e.getMessage());
            }
        } else {
            System.out.println("Los productos ya están inicializados.");
        }
    }

    public static void imprimirProductosLocales() {
        if (productosLocales.isEmpty()) {
            System.out.println("No se han cargado productos en productosLocales.");
        } else {
            System.out.println("Productos cargados:");
            for (Producto producto : productosLocales) {
                System.out.println("ID: " + producto.getId() + ", Nombre: " + producto.getTitle());
            }
        }
    }

    public static Producto buscarProductoPorId(int id) {
        return productosLocales.stream()
                .filter(producto -> producto.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static void agregarProducto(Producto producto) {
        productosLocales.add(producto);
        System.out.println("Producto agregado.");
    }

    public static boolean eliminarProducto(int id) {
        Producto producto = buscarProductoPorId(id);
        if (producto != null) {
            productosLocales.remove(producto);
            System.out.println("Producto eliminado.");
            return true;
        } else {
            System.err.println("No se ha encontrado un producto con el ID proporcionado.");
            return false;
        }
    }

    public static ArrayList<String> obtenerCategorias() {
        ArrayList<String> categorias = new ArrayList<>();
        for (Producto producto : productosLocales) {
            if (!categorias.contains(producto.getCategory())) {
                categorias.add(producto.getCategory());
            }
        }
        return categorias;
    }

    public static ArrayList<Producto> obtenerProductosPorCategoria(String categoria) {
        ArrayList<Producto> productosPorCategoria = new ArrayList<>();
        for (Producto producto : productosLocales) {
            if (producto.getCategory().equalsIgnoreCase(categoria)) {
                productosPorCategoria.add(producto);
            }
        }
        return productosPorCategoria;
    }

    public static ArrayList<Producto> buscarProductosPorNombre(String nombre) {
        ArrayList<Producto> productosPorNombre = new ArrayList<>();
        String nombreLower = nombre.toLowerCase();
        for (Producto producto : productosLocales) {
            if (producto.getTitle().toLowerCase().contains(nombreLower)) {
                productosPorNombre.add(producto);
            }
        }
        return productosPorNombre;
    }
}