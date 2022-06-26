package finanzmanager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import database.JavaPostgres;
import modelclasses.UserLogin;

//import static database.Postgres.*;


/**
 * Zum erstellen des PDF Dokuments wird iText (vers. 5.5.13.2) verwendet.
 */


public class PDFGenerator {
    //public static void main(String[] args)
    public void pdfGenAusgaben() throws SQLException {

        int id = UserLogin.id;                                                //Holt user ID für DB
        Controller controller = new Controller();
       // controller.exportierenWoBtnPressed();


        try {


            //=============================================Dokument spezifizieren=============================================
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("ausgaben.pdf"));
            writer.setPdfVersion(PdfWriter.VERSION_1_7);
            document.open();

            //=============================================Überschrift=============================================
            Paragraph ueberschrift = new Paragraph();
            ueberschrift.setFont(new Font(Font.FontFamily.HELVETICA, 20));
            Chunk c_ueberschrift = new Chunk("Finanzmanager 2022 - Übersicht: Ausgaben");
            ueberschrift.add(c_ueberschrift);
            ueberschrift.setSpacingBefore(10f);
            document.add(ueberschrift);

            //=============================================Infos über den Auszug=============================================
            //Paragraph info = new Paragraph();
            //info.setFont(new Font(Font.FontFamily.COURIER, 10));
            //Chunk c_info = new Chunk("Einnahmen und Ausgaben von * im Zeitraum von * bis *");
            //info.add(c_info);
            //document.add(info);

            //=============================================Datenbank auslesen=============================================

            JavaPostgres javaPostgres = new JavaPostgres();
            Connection connection = javaPostgres.getConnection();               //SQL Exception schon in Klasse JavaPostgres vorhanden, System.out fehlt (In JavaPostgres). -> PostgreSQL v.42.4.0



            try {

                PreparedStatement statement = connection.prepareStatement("SELECT * FROM ausgaben WHERE user_ausgabenid=?");        //Welcher User
                statement.setInt(1, id);
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ausgaben ");
                ResultSet res = preparedStatement.executeQuery();

                //=============================================Table formatieren=============================================
                PdfPTable table_ausgaben = new PdfPTable(3);
                table_ausgaben.setWidthPercentage(100);                               //Breite über die Seite in %
                table_ausgaben.setSpacingBefore(10f);                                //Abstand vorher
                table_ausgaben.setSpacingAfter(10f);                                 //Abstand nachher
                float [] colWidth = {33f, 33f, 33f};                           //Relative Verteilung der Columns auf die Breite
                table_ausgaben.setWidths(colWidth);

                table_ausgaben.addCell("Datum");
                table_ausgaben.addCell("Ausgaben in €");
                table_ausgaben.addCell(" ");

                while (res.next()) {

                    PdfPCell c = new PdfPCell();

                    Paragraph datum = new Paragraph();
                    datum.setFont(new Font(Font.FontFamily.COURIER, 9));
                    Chunk c_datum = new Chunk(res.getString("ausgaben_datum"));
                    datum.add(c_datum);
                    c = new PdfPCell(datum);
                    table_ausgaben.addCell(c);


                    Paragraph betrag = new Paragraph();
                    betrag.setFont(new Font(Font.FontFamily.COURIER, 9));
                    Chunk c_betrag = new Chunk(res.getString("ausgaben_betrag") + "€");
                    betrag.add(c_betrag);
                    c = new PdfPCell(betrag);
                    table_ausgaben.addCell(c);

                    Paragraph bezeichnung = new Paragraph();
                    bezeichnung.setFont(new Font(Font.FontFamily.COURIER, 9));
                    Chunk c_bezeichnung = new Chunk(res.getString("ausgaben_bezeichnung"));
                    bezeichnung.add(c_bezeichnung);
                    c = new PdfPCell(bezeichnung);
                    table_ausgaben.addCell(c);



                }
                document.add(table_ausgaben);

            }

            catch (SQLException e) {
                e.printStackTrace();
            }

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

    public void pdfGenEinnahmen() throws SQLException {

        int id = UserLogin.id;                                                //Holt user ID für DB

        try {

            //=============================================Dokument spezifizieren=============================================
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("einnahmen.pdf"));
            writer.setPdfVersion(PdfWriter.VERSION_1_7);
            document.open();

            //=============================================Überschrift=============================================
            Paragraph ueberschrift = new Paragraph();
            ueberschrift.setFont(new Font(Font.FontFamily.HELVETICA, 20));
            Chunk c_ueberschrift = new Chunk("Finanzmanager 2022 - Übersicht: Einnahmen");
            ueberschrift.add(c_ueberschrift);
            ueberschrift.setSpacingBefore(10f);
            document.add(ueberschrift);

            //=============================================Infos über den Auszug=============================================
            //Paragraph info = new Paragraph();
            //info.setFont(new Font(Font.FontFamily.COURIER, 10));
            //Chunk c_info = new Chunk("Einnahmen und Ausgaben von * im Zeitraum von * bis *");
            //info.add(c_info);
            //document.add(info);

            //=============================================Datenbank auslesen=============================================

            JavaPostgres javaPostgres = new JavaPostgres();
            Connection connection = javaPostgres.getConnection();               //SQL Exception schon in Klasse JavaPostgres vorhanden, System.out fehlt (In JavaPostgres). -> PostgreSQL v.42.4.0



            try {

                PreparedStatement statement = connection.prepareStatement("SELECT * FROM einnahmen WHERE user_einnahmenid=?");        //Welcher User
                statement.setInt(1, id);
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM einnahmen");
                ResultSet res = preparedStatement.executeQuery();

                //=============================================Table formatieren=============================================
                PdfPTable table_einnahmen = new PdfPTable(3);
                table_einnahmen.setWidthPercentage(100);                               //Breite über die Seite in %
                table_einnahmen.setSpacingBefore(10f);                                //Abstand vorher
                table_einnahmen.setSpacingAfter(10f);                                 //Abstand nachher
                float [] colWidth = {33f, 33f, 33f};                           //Relative Verteilung der Columns auf die Breite
                table_einnahmen.setWidths(colWidth);

                table_einnahmen.addCell("Datum");
                table_einnahmen.addCell("Einnahmen in €");
                table_einnahmen.addCell(" ");

                while (res.next()) {

                    PdfPCell c = new PdfPCell();

                    Paragraph datum = new Paragraph();
                    datum.setFont(new Font(Font.FontFamily.COURIER, 9));
                    Chunk c_datum = new Chunk(res.getString("einnahmen_datum"));
                    datum.add(c_datum);
                    c = new PdfPCell(datum);
                    table_einnahmen.addCell(c);


                    Paragraph betrag = new Paragraph();
                    betrag.setFont(new Font(Font.FontFamily.COURIER, 9));
                    Chunk c_betrag = new Chunk(res.getString("einnahmen_betrag") + "€");
                    betrag.add(c_betrag);
                    c = new PdfPCell(betrag);
                    table_einnahmen.addCell(c);

                    Paragraph bezeichnung = new Paragraph();
                    bezeichnung.setFont(new Font(Font.FontFamily.COURIER, 9));
                    Chunk c_bezeichnung = new Chunk(res.getString("einnahmen_bezeichnung"));
                    bezeichnung.add(c_bezeichnung);
                    c = new PdfPCell(bezeichnung);
                    table_einnahmen.addCell(c);



                }
                document.add(table_einnahmen);

            }

            catch (SQLException e) {
                e.printStackTrace();
            }

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
