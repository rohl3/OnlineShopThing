package com.classes;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class FormArticolo extends JFrame{
    private ArrayList <Articolo> list = new ArrayList<>();
    private ArrayList <Articolo> orderList = new ArrayList<>();
    private Articolo articolo;
    private JPanel Form;
    private JTabbedPane tabbedPane1;
    private JList productList;
    private JTextField articoloTxtField;
    private JLabel descrizioneArticoloLabel;
    private JLabel quantitaLabel;
    private JSpinner quantitaSpinner;
    private JLabel prezzoLabel;
    private JTextField prezzoTxtField;
    private JButton evadiButton;
    private JTextField articoloTxt;
    private JLabel Articolo;
    private JLabel articoloLabel;
    private JLabel quantityLabel;
    private JSpinner quantitySpinner;
    private JLabel priceLabel;
    private JTextField priceTxt;
    private JButton addBtn;
    private JTextField uniqueTxtCode;
    private JLabel codeLabel;
    private JButton pdfButton;
    private JLabel image;
    private JLabel productImageLabel;
    private DefaultListModel modelList;
    Document pdfDoc = new Document();

    public FormArticolo() {
        setContentPane(Form);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
        modelList = new DefaultListModel();
        productList.setModel(modelList);
        ImageIcon imageIcon = new ImageIcon("dd.jpg");
        image.setIcon(imageIcon);
        uniqueTxtCode.setEnabled(false);

        addButton();
        listElementSelector();
        evadiButton();
        pdfOpener();
    }

    public void addButton(){
        addBtn.addActionListener(e -> {
            try {

                articolo = new Articolo(articoloTxt.getText(),Integer.parseInt(String.valueOf(quantitySpinner.getValue())),Double.parseDouble(priceTxt.getText()));
                articolo.setUniqueCode(randomTxtGenerator());
                list.add(articolo);

                //Text File
                FileWriter writer = new FileWriter ("Product_data.txt",true);
                writer.write(articolo.toString());
                writer.write(System.lineSeparator());
                writer.write(System.lineSeparator());
                writer.close();

                //JList
                jListFiller();

            } catch (Exception exception) {
                JOptionPane.showMessageDialog(rootPane, exception);
            }
        });
    }

    public void listElementSelector(){
        productList.addListSelectionListener(e -> {
            int JlistIndex = productList.getSelectedIndex();
            if(JlistIndex >= 0){
                Articolo art = list.get(JlistIndex);
                articoloTxtField.setText(art.getProductName());
                quantitaSpinner.setValue(art.getQuantity());
                /*double cost = art.costMultiplier(Integer.parseInt(String.valueOf(art.getCost())));*/
                prezzoTxtField.setText(String.valueOf(art.getCost()));
                uniqueTxtCode.setText(art.getUniqueCode());
                ImageIcon imageIcon = new ImageIcon(art.getProductName()+".jpg");
                imageIcon.getImage().getScaledInstance(10,10,10);
                productImageLabel.setIcon(imageIcon);
            }
        });
    }

    public void jListFiller(){
        modelList.removeAllElements();
        for(Articolo art : list){
            modelList.addElement(art.getProductName());
        }
    }

    public void evadiButton(){
        evadiButton.addActionListener(e -> {
            int JlistIndex = productList.getSelectedIndex();
            if(JlistIndex >= 0){
                Articolo art = list.get(JlistIndex);
                orderList.add(art);
            }
            JOptionPane.showMessageDialog(rootPane, "Grazie");
        });
    }

    //Generates unique codes for every product
    public String randomTxtGenerator(){
        String upperCase = "QWERTYUIOPASDFGHJKLZXCVBNM";
        String lowerCase = "qwertyuiopasdfghjklzxcvbnm";
        String numbers= "123456789";
        String letters=upperCase+lowerCase+numbers;

        int codeLength = 10;
        char [] uniqueCode = new char[codeLength];
        Random rand = new Random();

        for(int i = 0; i<codeLength; i++){
            uniqueCode[i] = letters.charAt(rand.nextInt(letters.length()));
        }
        return new String(uniqueCode);
        /*uniqueTxtCode.setText(new String(uniqueCode));*/
    }

    //Creates PDF File
    public void pdfFiller(){
        try {
            /*Image image = Image.getInstance("C:\\Users\\acer\\IdeaProjects\\Gestione Magazzino\\dd.png");*/
            PdfWriter pdfWriter = PdfWriter.getInstance(pdfDoc,new FileOutputStream("C:\\Users\\acer\\IdeaProjects\\Gestione Magazzino\\Data.pdf"));
            pdfWriter.open();
            pdfDoc.open();
            /*pdfDoc.add(image);*/
            pdfDoc.open();
            pdfDoc.add(new Paragraph(String.valueOf(orderList)));
            pdfDoc.close();
            pdfWriter.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(rootPane,e);
        } catch (DocumentException e) {
            JOptionPane.showMessageDialog(rootPane,e);
        }
    }

    //Opens a PDF File
    public void pdfOpener(){
        pdfButton.addActionListener(e -> {
            pdfFiller();
            if((new File("C:\\Users\\acer\\IdeaProjects\\Gestione Magazzino\\Data.pdf").exists())){
                try {
                    Process open = Runtime
                            .getRuntime()
                            .exec("rundll32 url.dll,FileProtocolHandler C:\\Users\\acer\\IdeaProjects\\Gestione Magazzino\\Data.pdf");
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(rootPane,ioException);
                }
            }
        });
    }

}
