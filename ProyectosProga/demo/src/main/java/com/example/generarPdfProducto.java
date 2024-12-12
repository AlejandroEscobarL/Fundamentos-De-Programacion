package com.example;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
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

            // Estilo y diseño
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLUE);
            Font subtitleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            // Agregar un título al documento
            Paragraph title = new Paragraph("Detalles del Producto", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Crear una tabla para organizar los detalles del producto
            PdfPTable table = new PdfPTable(2); // 2 columnas
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(20f);
            
            // Encabezados de la tabla
            PdfPCell cellHeader1 = new PdfPCell(new Phrase("Atributo", subtitleFont));
            cellHeader1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cellHeader1.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell cellHeader2 = new PdfPCell(new Phrase("Valor", subtitleFont));
            cellHeader2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cellHeader2.setHorizontalAlignment(Element.ALIGN_CENTER);

            table.addCell(cellHeader1);
            table.addCell(cellHeader2);

            // Datos del producto
            table.addCell(new Phrase("Nombre del Producto:", normalFont));
            table.addCell(new Phrase(product.getTitle(), normalFont));

            table.addCell(new Phrase("Precio:", normalFont));
            table.addCell(new Phrase(product.getPrice() + " $", normalFont));

            table.addCell(new Phrase("Descripción:", normalFont));
            table.addCell(new Phrase(product.getDescription(), normalFont));

            table.addCell(new Phrase("Categoría:", normalFont));
            table.addCell(new Phrase(product.getCategory(), normalFont));

            table.addCell(new Phrase("Rating:", normalFont));
            table.addCell(new Phrase(product.getRating().getRate() + "/5 con " + product.getRating().getCount() + " votos", normalFont));

            document.add(table);

            // Descargar y agregar imagen desde una URL
            String imageUrl = product.getImage(); // URL de la imagen
            Image image = Image.getInstance(imageUrl);
            image.scaleToFit(300, 300); // Ajusta el tamaño máximo (ancho, alto)
            image.setAlignment(Element.ALIGN_CENTER);
            document.add(image);

            // Pie de página
            Paragraph footer = new Paragraph("Este documento fue generado automáticamente.", normalFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(30);
            document.add(footer);

            // Cerrar el documento
            document.close();

            System.out.println("PDF creado con éxito en: " + pdfPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
