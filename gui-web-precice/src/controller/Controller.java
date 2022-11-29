package controller;

import java.awt.Desktop;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Stranice;

/**
 *
 * @author Almir
 */
public class Controller {

    Stranice str = new Stranice();
    ObservableList<Stranice> stranice = FXCollections.<Stranice>observableArrayList();

    Connection conn = null;

    Optional<String> unosID;
    Optional<String> unosLink;

    @FXML
    private TextField unos;
    @FXML
    private TextArea talista;
    @FXML
    private Button otvoriws;

     public Controller() {

        try {
            try {
                conn = Konekcija.konekcija();
            } catch (FileNotFoundException | SQLException ex) {
            }
        } catch (IOException ex) {
        }
    }

    @FXML
    private void otvoriWeb() throws ClassNotFoundException, SQLException, URISyntaxException, IOException {

        stranice = FXCollections.observableArrayList();
        String textid = null;
        try {
            java.sql.Statement st = conn.createStatement();

            st.executeQuery("select idweba, linkweba from stranice");
            ResultSet rs = st.getResultSet();

            while (rs.next()) {
                stranice.add(new Stranice(
                        rs.getString("idweba"),
                        rs.getString("linkweba")
                ));
                textid = rs.getString("idweba");

                if (textid.equals(unos.getText())) {
                    Desktop.getDesktop().browse(new URI(rs.getString("linkweba")));
                }
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }

    }

    @FXML
    private void dodajNovog(ActionEvent event) {

        TextInputDialog dialog = new TextInputDialog("otvori google");
        TextInputDialog dialog2 = new TextInputDialog("www.google.ba");

        dialog.setTitle("Unesite ID web stranice");
        dialog.setHeaderText("Unesite precicu do web/internet stranice?");
        dialog.setContentText("Precica linka:");

        unosID = dialog.showAndWait();
        if (unosID.isPresent()) {
            dialog2.setTitle("Unesite link web stranice");
            dialog2.setHeaderText("Sada unesite link do web stranice?");
            dialog2.setContentText("Link / adresa web stranice:");
            unosLink = dialog2.showAndWait();
        }

        try {
            java.sql.Statement st = conn.createStatement();
            st.execute("insert into stranice (idweba, linkweba) values ('" + unosID.get() + "','" + unosLink.get() + "')");

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Kreiranje precice");
            alert.setHeaderText("Uspjesno ste dodali novu precicu do web stranice!");
            alert.setContentText("\n    - ID:   '" + unosID.get() + "'\n\n    - LINK:   " + unosLink.get());
            alert.showAndWait();

        } catch (SQLException e) {
            //e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Doslo je do greske!");
            alert.setHeaderText(null);
            alert.setContentText("Greska! Doslo je do problema sa konekcijom na server databazu, provjerite internet!");
            alert.showAndWait();
        }
    }

    @FXML
    private void prikaziListu() throws ClassNotFoundException, SQLException {

        talista.clear();
        talista.appendText("- Lista trenutnih web stranica:");
        if (talista.disableProperty().get())//lista se ne vidi
        {
            talista.setDisable(false);
            talista.setOpacity(1);
            otvoriws.setDisable(true);
            otvoriws.setOpacity(0);

        } else {
            otvoriws.setDisable(false);
            otvoriws.setOpacity(1);
            talista.setDisable(true);
            talista.setOpacity(0);
        }

        stranice = FXCollections.observableArrayList();
        String textpr;
        try {
            java.sql.Statement st = conn.createStatement();

            st.executeQuery("select idweba, linkweba from stranice");
            ResultSet rs = st.getResultSet();

            talista.appendText("\n");
            while (rs.next()) {
                stranice.add(new Stranice(
                        rs.getString("idweba"),
                        rs.getString("linkweba")
                ));
                textpr = ("\n     # ID Weba: " + rs.getString("idweba") + " | Link weba: " + rs.getString("linkweba"));
                talista.appendText(textpr);
            }

        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    @FXML
    private void obrisiWeb() throws ClassNotFoundException, SQLException {

        TextInputDialog dialogObrisi = new TextInputDialog("otvori google");
        dialogObrisi.setTitle("Unesite ID web stranice");
        dialogObrisi.setHeaderText("Unesite precicu do web/internet stranice koju zelite ukloniti?");
        dialogObrisi.setContentText("Precica linka za brisanje:");

        unosID = dialogObrisi.showAndWait();

        if (unosID.isPresent()) {

            stranice = FXCollections.observableArrayList();
            String dtextid = null;
            try {
                java.sql.Statement st = conn.createStatement();
                st.executeQuery("select idweba from stranice");
                ResultSet rs = st.getResultSet();

                while (rs.next()) {
                    stranice.add(new Stranice(
                            rs.getString("idweba")
                    ));

                    dtextid = rs.getString("idweba");

                    if (dtextid.equals(unosID.get())) {
                        st.execute("delete from stranice where idweba = '" + unosID.get() + "'");
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Brisanje precice");
                        alert.setHeaderText("Uspjesno ste obrisali precicu do web stranice!");
                        alert.setContentText("\n    - Uklonili ste ID:   '" + unosID.get() +"'");
                        alert.showAndWait();
                    }
                }
            } catch (SQLException e) {
                //e.printStackTrace();
            }
        }
    }

    @FXML
    public void stisnuoEnter(KeyEvent event) throws ClassNotFoundException, SQLException, URISyntaxException, IOException {
        if (event.getCode() == KeyCode.ENTER) {
            otvoriWeb();
        }
    }

    @FXML
    private void exit() throws ClassNotFoundException, SQLException {
        Platform.exit();
    }

}
