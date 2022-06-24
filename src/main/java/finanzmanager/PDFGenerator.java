package finanzmanager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.JavaPostgres;

//import static database.Postgres.*;


/** Zum erstellen des PDF Dokuments wird iText (vers. 5.5.13.2) verwendet.
 */

public class PDFGenerator {



    public static void main(String[] args) throws SQLException {

        JavaPostgres javaPostgres = new JavaPostgres();
        Connection connection = javaPostgres.getConnection();

        //PreparedStatement statement = connection.prepareStatement();
        //ResultSet res = statement.executeQuery();
        //res.next();

        try {

            //=============================================Dokument spezifizieren=============================================
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("test.pdf"));
            writer.setPdfVersion(PdfWriter.VERSION_1_7);
            document.open();

            //=============================================Überschrift=============================================
            Paragraph ueberschrift = new Paragraph();
            ueberschrift.setFont(new Font(Font.FontFamily.HELVETICA, 20));
            Chunk c_ueberschrift = new Chunk("Finanzmanager 2022");
            ueberschrift.add(c_ueberschrift);
            ueberschrift.setSpacingBefore(10f);
            document.add(ueberschrift);

            //=============================================Infos über den Auszug=============================================
            Paragraph info = new Paragraph();
            info.setFont(new Font(Font.FontFamily.COURIER, 10));
            Chunk c_info = new Chunk("Einnahmen und Ausgaben von * im Zeitraum von * bis *");
            info.add(c_info);
            document.add(info);


            //=============================================PDF Table=============================================
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);                               //Breite über die Seite in %
            table.setSpacingBefore(10f);                                //Abstand vorher
            table.setSpacingAfter(10f);                                 //Abstand nachher

            float [] colWidth = {25f, 25f, 25f, 25f};                 //Relative Verteilung der Columns auf die Breite
            table.setWidths(colWidth);
            PdfPCell c1 = new PdfPCell(new Paragraph("Datum"));
            PdfPCell c2 = new PdfPCell(new Paragraph("Einnahmen"));
            PdfPCell c3 = new PdfPCell(new Paragraph("Datum"));
            PdfPCell c4 = new PdfPCell(new Paragraph("Ausgaben"));
            PdfPCell c5 = new PdfPCell(new Paragraph(""));
            PdfPCell c6 = new PdfPCell();
            PdfPCell c7 = new PdfPCell();
            PdfPCell c8 = new PdfPCell();




            table.addCell(c1);
            table.addCell(c2);
            table.addCell(c3);
            table.addCell(c4);
            table.addCell(c5);
            table.addCell(c6);
            table.addCell(c7);
            table.addCell(c8);

            document.add(table);

            document.close();
            writer.close();
        }
        catch (DocumentException e) {
            e.printStackTrace();
        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        }





    }
}
