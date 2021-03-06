package main;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import models.ContactDetails;
import models.Supplier;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewSuppliersContactCardPaneController implements Initializable {

    @FXML Label contactNameLbl;
    @FXML Label positionLbl;
    @FXML Label contactNumberLbl;
    @FXML Hyperlink emailHL;
    @FXML Label dateAddedLbl;
    @FXML Button editBtn;
    @FXML Button removeBtn;
    private ContactDetails cd;
    private Supplier supplier;
    private String category;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initData(ContactDetails cd, Supplier supplier, String category){
        this.cd = cd;
        this.supplier = supplier;
        this.category = category;
        contactNameLbl.setText(cd.getPersonName());
        positionLbl.setText(cd.getPosition());
        contactNumberLbl.setText(cd.getNumber());
        emailHL.setText(cd.getEmail());
        editBtn.setTooltip(new Tooltip("Edit"));
        removeBtn.setTooltip(new Tooltip("Remove"));
    }

    public void editButtonClick(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("NewSupplierContactPane.fxml"));
        try {
            Main.setStage(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        NewSupplierContactPaneController nscpc = loader.getController();
        nscpc.initEditData(cd, supplier, category);
    }

    public void removeButtonClick(){
        //if (UserNotification.confirmationDialog(Main.stage, "Are you sure you want to remove " + cd.getPersonName() + "?", "Emails with this contact will still be in emails.")) {
            Main.connectionHandler.outputQueue.add("rcd:" + cd.getContactDetailsID());
        //}
    }

    public void emailHLClicked(){
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(emailHL.getText()), null);
        new CustomDialog().CustomDialog(Main.stage,"Copied", "Mail Address Copied to Clipboard.", new JFXButton("Ok"));
    }

}
