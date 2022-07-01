/*
 * Klasse generiert ein PDF Dokument entsprechend den Eingaben
 */
package finanzmanager;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.JavaPostgres;
import modelclasses.Uebersicht;
import modelclasses.UserLogin;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Datum: 01.07.2022
 *
 * @author Sami Taieb
 * @version 1.7.2
 *
 * Zum erstellen des PDF Dokuments wird iText (vers. 5.5.13.2) verwendet.
 */
public class PDFGenerator {
    public static void pdfGenAusgaben(String speicherort, String name) throws SQLException {
        //User id, damit auch nur die Daten EINES Nutzers abgefragt und weiterverarbeitet werden
        int id = UserLogin.id;

        try {
            System.out.println("TEST: " + id);
            //spezifizieren der Dokumenteigenschaften
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(speicherort + "\\" + name + ".pdf"));
            writer.setPdfVersion(PdfWriter.VERSION_1_7);
            document.open();

            //Informationen, rest dient hauptsächlich der Kosmetik
            Paragraph ueberschrift = new Paragraph();
            ueberschrift.setFont(new Font(Font.FontFamily.HELVETICA, 20));
            Chunk c_ueberschrift = new Chunk("Finanzmanager 2022 - Übersicht: Ausgaben");
            ueberschrift.add(c_ueberschrift);
            ueberschrift.setSpacingBefore(10f);
            document.add(ueberschrift);

            Paragraph info = new Paragraph();
            info.setFont(new Font(Font.FontFamily.COURIER, 10));
            Chunk c_info = new Chunk("Ausgaben von: " + LoginController.getUserFullname());
            info.add(c_info);
            document.add(info);

            //Datenbankverbindung aufbauen

            JavaPostgres javaPostgres = new JavaPostgres();
            Connection connection = javaPostgres.getConnection();               //SQL Exception schon in Klasse JavaPostgres vorhanden

            try {
                System.out.println("TEST: " + id);
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM ausgaben WHERE user_ausgabenid=?");
                statement.setInt(1, id);
                ResultSet res = statement.executeQuery();

                //Formatieren des Tables in der PDF File
                PdfPTable table_ausgaben = new PdfPTable(3);
                table_ausgaben.setWidthPercentage(100);                              //Breite über die Seite in %
                table_ausgaben.setSpacingBefore(10f);                                //Abstand vorher
                table_ausgaben.setSpacingAfter(10f);                                 //Abstand nachher
                float[] colWidth = {33f, 33f, 33f};                                  //Relative Verteilung der Columns auf die Breite
                table_ausgaben.setWidths(colWidth);

                table_ausgaben.addCell("Datum");
                table_ausgaben.addCell("Ausgaben in €");
                table_ausgaben.addCell("Bezeichnung");

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

                Paragraph ausgaben = new Paragraph();
                ausgaben.setFont(new Font(Font.FontFamily.COURIER, 10));
                Chunk c_ausgaben = new Chunk("Gesamte Ausgaben: " + Uebersicht.ausgabenZusammenRechnen() + "€");
                ausgaben.add(c_ausgaben);
                document.add(ausgaben);

                Paragraph kontostand = new Paragraph();
                kontostand.setFont(new Font(Font.FontFamily.COURIER, 10));
                Chunk c_kontostand = new Chunk("Aktueller Kontostand: " + Uebersicht.aktuellerKontostandZusammen() + "€");
                kontostand.add(c_kontostand);
                document.add(kontostand);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            document.close();
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void pdfGenDauerauftrag(String speicherort, String name) throws SQLException {
        //User id, damit auch nur die Daten EINES Nutzers abgefragt und weiterverarbeitet werden
        int id = UserLogin.id;

        try {

            //spezifizieren der Dokumenteigenschaften
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(speicherort + "\\" + name + ".pdf"));
            writer.setPdfVersion(PdfWriter.VERSION_1_7);
            document.open();

            //Informationen, rest dient hauptsächlich der Kosmetik
            Paragraph ueberschrift = new Paragraph();
            ueberschrift.setFont(new Font(Font.FontFamily.HELVETICA, 20));
            Chunk c_ueberschrift = new Chunk("Finanzmanager 2022 - Übersicht: Daueraufträge");
            ueberschrift.add(c_ueberschrift);
            ueberschrift.setSpacingBefore(10f);
            document.add(ueberschrift);

            Paragraph info = new Paragraph();
            info.setFont(new Font(Font.FontFamily.COURIER, 10));
            Chunk c_info = new Chunk("Daueraufträge von: " + LoginController.getUserFullname());
            info.add(c_info);
            document.add(info);

            //Datenbankverbindung aufbauen

            JavaPostgres javaPostgres = new JavaPostgres();
            Connection connection = javaPostgres.getConnection();               //SQL Exception schon in Klasse JavaPostgres vorhanden

            try {
                System.out.println("TEST: " + id);
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM dauerauftrag WHERE user_dauerauftragid=?");
                statement.setInt(1, id);
                ResultSet res = statement.executeQuery();



                //Formatieren des Tables in der PDF File
                PdfPTable table_ausgaben = new PdfPTable(4);
                table_ausgaben.setWidthPercentage(100);                               //Breite über die Seite in %
                table_ausgaben.setSpacingBefore(10f);                                 //Abstand vorher
                table_ausgaben.setSpacingAfter(10f);                                  //Abstand nachher
                float[] colWidth = {25f, 25f, 25f, 25f};                              //Relative Verteilung der Columns auf die Breite
                table_ausgaben.setWidths(colWidth);

                table_ausgaben.addCell("Erste Ausführung");
                table_ausgaben.addCell("Ausgaben in €");
                table_ausgaben.addCell("Bezeichnung");
                table_ausgaben.addCell("Einnahme/Ausgabe");

                while (res.next()) {

                    PdfPCell c = new PdfPCell();

                    boolean einnahme_ausgabe = res.getBoolean("dauerauftrag_ausgabe_einnahme");

                    Paragraph datum = new Paragraph();
                    datum.setFont(new Font(Font.FontFamily.COURIER, 9));
                    Chunk c_datum = new Chunk(res.getString("dauerauftrag_datum"));
                    datum.add(c_datum);
                    c = new PdfPCell(datum);
                    table_ausgaben.addCell(c);


                    Paragraph betrag = new Paragraph();
                    betrag.setFont(new Font(Font.FontFamily.COURIER, 9));
                    Chunk c_betrag = new Chunk(res.getString("dauerauftrag_betrag") + "€");
                    betrag.add(c_betrag);
                    c = new PdfPCell(betrag);
                    table_ausgaben.addCell(c);

                    Paragraph bezeichnung = new Paragraph();
                    bezeichnung.setFont(new Font(Font.FontFamily.COURIER, 9));
                    Chunk c_bezeichnung = new Chunk(res.getString("dauerauftrag_bezeichnung"));
                    bezeichnung.add(c_bezeichnung);
                    c = new PdfPCell(bezeichnung);
                    table_ausgaben.addCell(c);

                    Paragraph einAus = new Paragraph();
                    einAus.setFont(new Font(Font.FontFamily.COURIER, 9));

                    Chunk c_einAus;
                    if (einnahme_ausgabe) {
                        c_einAus = new Chunk("Einnahme");
                    } else {
                        c_einAus = new Chunk("Ausgabe");
                    }

                    einAus.add(c_einAus);
                    c = new PdfPCell(einAus);
                    table_ausgaben.addCell(c);

                }
                document.add(table_ausgaben);

                Paragraph kontostand = new Paragraph();
                kontostand.setFont(new Font(Font.FontFamily.COURIER, 10));
                Chunk c_kontostand = new Chunk("Aktueller Kontostand: " + Uebersicht.aktuellerKontostandZusammen() + "€");
                kontostand.add(c_kontostand);
                document.add(kontostand);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            document.close();
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void pdfGenEinnahmen(String speicherort, String name) throws SQLException {
        int id = UserLogin.id;                                                //Holt user ID für DB

        try {

            //spezifizieren der Dokumenteigenschaften
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(speicherort + "\\" + name + ".pdf"));
            writer.setPdfVersion(PdfWriter.VERSION_1_7);
            document.open();

            //Informationen, rest dient hauptsächlich der Kosmetik
            Paragraph ueberschrift = new Paragraph();
            ueberschrift.setFont(new Font(Font.FontFamily.HELVETICA, 20));
            Chunk c_ueberschrift = new Chunk("Finanzmanager 2022 - Übersicht: Einnahmen");
            ueberschrift.add(c_ueberschrift);
            ueberschrift.setSpacingBefore(10f);
            document.add(ueberschrift);

            Paragraph info = new Paragraph();
            info.setFont(new Font(Font.FontFamily.COURIER, 10));
            Chunk c_info = new Chunk("Einnahmen von: " + LoginController.getUserFullname());
            info.add(c_info);
            document.add(info);

            //Datenbankverbindung aufbauen

            JavaPostgres javaPostgres = new JavaPostgres();
            Connection connection = javaPostgres.getConnection();               //SQL Exception schon in Klasse JavaPostgres vorhanden, System.out fehlt (In JavaPostgres). -> PostgreSQL v.42.4.0

            try {
                System.out.println("TEST: " + id);
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM einnahmen WHERE user_einnahmenid=?");        //Welcher User
                statement.setInt(1, id);
                ResultSet res = statement.executeQuery();

                //Formatieren des Tables in der PDF File
                PdfPTable table_ausgaben = new PdfPTable(3);
                table_ausgaben.setWidthPercentage(100);                               //Breite über die Seite in %
                table_ausgaben.setSpacingBefore(10f);                                //Abstand vorher
                table_ausgaben.setSpacingAfter(10f);                                 //Abstand nachher
                float[] colWidth = {33f, 33f, 33f};                           //Relative Verteilung der Columns auf die Breite
                table_ausgaben.setWidths(colWidth);

                table_ausgaben.addCell("Datum");
                table_ausgaben.addCell("Einnahmen in €");
                table_ausgaben.addCell("Bezeichnung");

                while (res.next()) {

                    PdfPCell c = new PdfPCell();

                    Paragraph datum = new Paragraph();
                    datum.setFont(new Font(Font.FontFamily.COURIER, 9));
                    Chunk c_datum = new Chunk(res.getString("einnahmen_datum"));
                    datum.add(c_datum);
                    c = new PdfPCell(datum);
                    table_ausgaben.addCell(c);


                    Paragraph betrag = new Paragraph();
                    betrag.setFont(new Font(Font.FontFamily.COURIER, 9));
                    Chunk c_betrag = new Chunk(res.getString("einnahmen_betrag") + "€");
                    betrag.add(c_betrag);
                    c = new PdfPCell(betrag);
                    table_ausgaben.addCell(c);

                    Paragraph bezeichnung = new Paragraph();
                    bezeichnung.setFont(new Font(Font.FontFamily.COURIER, 9));
                    Chunk c_bezeichnung = new Chunk(res.getString("einnahmen_bezeichnung"));
                    bezeichnung.add(c_bezeichnung);
                    c = new PdfPCell(bezeichnung);
                    table_ausgaben.addCell(c);
                }
                document.add(table_ausgaben);

                Paragraph ausgaben = new Paragraph();
                ausgaben.setFont(new Font(Font.FontFamily.COURIER, 10));
                Chunk c_ausgaben = new Chunk("Gesamte Einnahmen: " + Uebersicht.einnahmenZusammenRechnen() + "€");
                ausgaben.add(c_ausgaben);
                document.add(ausgaben);

                Paragraph kontostand = new Paragraph();
                kontostand.setFont(new Font(Font.FontFamily.COURIER, 10));
                Chunk c_kontostand = new Chunk("Aktueller Kontostand: " + Uebersicht.aktuellerKontostandZusammen() + "€");
                kontostand.add(c_kontostand);
                document.add(kontostand);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            document.close();
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
