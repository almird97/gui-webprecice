/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Almir
 */
public class Stranice {

    public SimpleStringProperty idweba = new SimpleStringProperty();
    public SimpleStringProperty linkweba = new SimpleStringProperty();

    public Stranice() {
    }

    public Stranice(String idweba) {

        this.idweba.set(idweba);
    }

    public Stranice(String idweba, String linkweba) {

        this.idweba.set(idweba);
        this.linkweba.set(linkweba);
    }

    public String getID() {
        return idweba.get();
    }

    public void setID(String idweba) {
        this.idweba.set(idweba);
    }

    public StringProperty IDProperty() {
        return idweba;
    }

    public String getLink() {
        return linkweba.get();
    }

    public void setLink(String linkweba) {
        this.linkweba.set(linkweba);
    }

    public StringProperty linkProperty() {
        return linkweba;
    }

}
