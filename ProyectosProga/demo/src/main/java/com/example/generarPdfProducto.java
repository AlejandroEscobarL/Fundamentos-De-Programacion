package com.example;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class generarPdfProducto {

    public static void guardaPDFDeProducto(Producto product) {
        // Nombre del archivo PDF que se generará
        String pdfPath = "producto" + product.getId() + ".pdf";

        try {
            // Creación del documento PDF
            Document document = new Document();

            // Crear el escritor de PDF
            PdfWriter.getInstance(document, new java.io.FileOutputStream(pdfPath));

            // Abrir el documento para agregar contenido
            document.open();

            // Fuentes
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            // Título del documento
            Paragraph title = new Paragraph("Detalles del Producto", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(10);
            document.add(title);

            // Tabla de detalles
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            // Datos del producto
            table.addCell("Nombre del Producto:");
            table.addCell(product.getTitle());

            table.addCell("Precio:");
            table.addCell(product.getPrice() + " $");

            table.addCell("Descripción:");
            table.addCell(product.getDescription());

            table.addCell("Categoría:");
            table.addCell(product.getCategory());

            table.addCell("Rating:");
            table.addCell(product.getRating().getRate() + "/5 con " + product.getRating().getCount() + " votos");

            document.add(table);

            // Agregar imagen del producto
            String imageUrl = product.getImage(); // URL de la imagen
            Image image = Image.getInstance(imageUrl);
            image.scaleToFit(300, 300); // Ajusta el tamaño máximo (ancho, alto)
            image.setAlignment(Element.ALIGN_CENTER);
            document.add(image);

            // Cerrar el documento
            document.close();

            System.out.println("PDF creado con éxito en: " + pdfPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

