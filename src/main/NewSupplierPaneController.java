package main;

import com.jfoenix.controls.JFXButton;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Supplier;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewSupplierPaneController implements Initializable {

    @FXML TextField supplierNameTxf;
    @FXML ComboBox categoryCmb;
    @FXML ComboBox provinceCmb;
    @FXML TextField addressTxf;
    @FXML TextField coOrdinatesTxf;
    private Supplier supplier;
    private String lastPane;
    private String category;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryCmb.getItems().clear();
        categoryCmb.getItems().addAll("Please Select Category", "Accommodation", "Golf", "Transport", "Activity");
        categoryCmb.getSelectionModel().select(0);
        provinceCmb.getItems().clear();
        provinceCmb.getItems().addAll("Please Select Province", "Western Cape", "Eastern Cape", "Northern Cape", "Gauteng", "Kwa-Zulu Natal", "North West", "Mpumalanga", "Limpopo", "Free-State", "South Africa", "World");
        provinceCmb.getSelectionModel().select(0);
        lastPane = "SuppliersPane";
    }

    public void initData(Supplier supplier, String lastPane, String category){
        this.supplier = supplier;
        this.lastPane = lastPane;
        this.category = category;
        supplierNameTxf.setText(supplier.getSupplierName());
        categoryCmb.getSelectionModel().select(supplier.getCategory());
        provinceCmb.getSelectionModel().select(supplier.getProvince());
        addressTxf.setText(supplier.getAddress());
    }

    public void addButtonClick(){
        if (!supplierNameTxf.getText().matches("")) {
            if (!categoryCmb.getSelectionModel().getSelectedItem().toString().matches("Please Select Category")) {
                if (!provinceCmb.getSelectionModel().getSelectedItem().toString().matches("Please Select Province")) {
                    if (!addressTxf.getText().matches("")) {
                        if (!addressTxf.getText().matches("")) {
                            if (supplier == null) {
                                supplier = new Supplier(-1, supplierNameTxf.getText(), categoryCmb.getSelectionModel().getSelectedItem().toString(), provinceCmb.getSelectionModel().getSelectedItem().toString(), addressTxf.getText(), null);
                            } else {
                                supplier = new Supplier(supplier.getSupplierNumber(), supplierNameTxf.getText(), categoryCmb.getSelectionModel().getSelectedItem().toString(), provinceCmb.getSelectionModel().getSelectedItem().toString(), addressTxf.getText(), supplier.getContactDetails());
                            }
                            Main.connectionHandler.outputQueue.add(supplier);
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource(lastPane + ".fxml"));
                            try {
                                Main.setStage(loader.load());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if(lastPane.matches("ViewSuppliersPane")){
                                ViewSuppliersPaneController vspc = loader.getController();
                                vspc.initData(supplier, category);
                            } else if (lastPane.matches("SuppliersPane")){
                                SuppliersPaneController spc = loader.getController();
                                spc.initData(category);
                            }
                        } else {
                            new CustomDialog().CustomDialog(Main.stage,"Co Ordinates not entered", "Enter Co Ordinates before adding supplier.", new JFXButton("Ok"));
                        }
                    } else {
                        new CustomDialog().CustomDialog(Main.stage,"Address not entered", "Enter Address before adding supplier.", new JFXButton("Ok"));
                    }
                } else {
                    new CustomDialog().CustomDialog(Main.stage,"Category not selected", "Select Category before adding supplier.", new JFXButton("Ok"));
                }
            } else {
                new CustomDialog().CustomDialog(Main.stage,"Province not selected", "Select Province before adding supplier.", new JFXButton("Ok"));
            }
        } else {
            new CustomDialog().CustomDialog(Main.stage,"Supplier Name not entered", "Enter Supplier Name before adding supplier.", new JFXButton("Ok"));
        }
    }

    public void backButtonClick(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SuppliersPane.fxml"));
        try {
            Main.setStage(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        SuppliersPaneController spc = loader.getController();
        spc.initData(category);
    }
}
