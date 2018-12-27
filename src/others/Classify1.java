/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package others;

import Jama.Matrix;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import model.RWFile;
import model.SVM;
import java.util.ArrayList;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author Filipus
 */
public class Classify1 extends JFrame {

    JComboBox speciesList;
    JComboBox featuresList;
    JButton classifyBtn;
    JTextField sigmaValue;
    JTextField totalFeatures;

    JLabel text1;
    JLabel text2;
    JLabel text3;
    JLabel text4;

    public Classify1() {
        init();
        classifyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SVM svm = new SVM();

                //coba load data
                String inputClass = speciesList.getSelectedItem().toString();

                //parameter
                String feature = featuresList.getSelectedItem().toString();
                String path = "C:\\Users\\Filipus\\Documents\\NetBeansProjects\\KlasifikasiSVM\\feature-data\\" + feature + "\\" + feature + "-training.csv";
                double sigma = Double.parseDouble(sigmaValue.getText());
                int features = Integer.parseInt(totalFeatures.getText());
                
                try {
                    String[][] dataset = RWFile.getDataFromText2D(path, 961, (features+1));
                    double[][] data = new double[960][features];

                    for (int i = 1; i < dataset.length; i++) {
                        for (int j = 0; j < dataset[0].length - 1; j++) {
                            data[i - 1][j] = Double.parseDouble(dataset[i][j]);
                        }
                    }

                    double[] classList = new double[961];
                    for (int i = 0; i < classList.length - 1; i++) {
                        if (dataset[i][5].equals(inputClass)) {
                            classList[i] = 1;
                        } else {
                            classList[i] = -1;
                        }
                    }
                    classList[960] = 0;

                    //get model (alpha and bias)
                    String modelPath = "C:\\Users\\Filipus\\Documents\\NetBeansProjects\\KlasifikasiSVM\\models\\sigma-"+(int)sigma+"\\" + feature + "\\model-" + inputClass + ".txt";
//                    String modelPath = "C:\\Users\\Filipus\\Documents\\NetBeansProjects\\KlasifikasiSVM\\models\\glcm\\model-pubescent bamboo.txt";
                    double[] solutions = RWFile.getDataFromText(modelPath, 961);

//                    //get testing data, create RBF matrix test
                    String pathTest = "C:\\Users\\Filipus\\Documents\\NetBeansProjects\\KlasifikasiSVM\\feature-data\\" + feature + "\\" + feature + "-testing.csv";
                    String[][] datasetTest = RWFile.getDataFromText2D(pathTest, 321, (features+1));
                    double[][] dataTest = new double[320][features];

                    for (int i = 1; i < datasetTest.length; i++) {
                        for (int j = 0; j < datasetTest[0].length - 1; j++) {
                            dataTest[i - 1][j] = Double.parseDouble(datasetTest[i][j]);
                        }
                    }
//                    for (int i = 0; i < dataTest.length; i++) {
//                        for (int j = 0; j < dataTest[0].length; j++) {
//                            System.out.print(dataTest[i][j] + "\t");
//                        }
//                        System.out.println("");
//                    }

                    int trueClassified = 0;
                    int falseClassified = 0;
                    
                    int start=0,end = 0;
                    switch(inputClass){
                        case "pubescent bamboo":{
                            start=0;
                            end=10;
                            break;
                        }
                        case "chinese horse chestnut":{
                            start=10;
                            end=20;
                            break;
                        }
                        case "chinese redbud":{
                            start=20;
                            end=30;
                            break;
                        }
                        case "true indigo":{
                            start=30;
                            end=40;
                            break;
                        }
                        case "japanese maple":{
                            start=40;
                            end=50;
                            break;
                        }
                        case "nanmu":{
                            start=50;
                            end=60;
                            break;
                        }
                        case "castor aralia":{
                            start=60;
                            end=70;
                            break;
                        }
                        case "goldenrain tree":{
                            start=70;
                            end=80;
                            break;
                        }
                        case "chinese cinnamon":{
                            start=80;
                            end=90;
                            break;
                        }
                        case "anhui barberry":{
                            start=90;
                            end=100;
                            break;
                        }
                        case "big fruited holly":{
                            start=100;
                            end=110;
                            break;
                        }
                        case "japanese cheesewood":{
                            start=110;
                            end=120;
                            break;
                        }
                        case "wintersweet":{
                            start=120;
                            end=130;
                            break;
                        }
                        case "camphortree":{
                            start=130;
                            end=140;
                            break;
                        }
                        case "japan arrowwood":{
                            start=140;
                            end=150;
                            break;
                        }
                        case "sweet osmanthus":{
                            start=150;
                            end=160;
                            break;
                        }
                        case "deodar":{
                            start=160;
                            end=170;
                            break;
                        }
                        case "gingko":{
                            start=170;
                            end=180;
                            break;
                        }
                        case "crepe myrtle":{
                            start=180;
                            end=190;
                            break;
                        }
                        case "oleander":{
                            start=190;
                            end=200;
                            break;
                        }
                        case "yew plum pine":{
                            start=200;
                            end=210;
                            break;
                        }
                        case "japanese flowering cherry":{
                            start=210;
                            end=220;
                            break;
                        }
                        case "glossy privet":{
                            start=220;
                            end=230;
                            break;
                        }
                        case "chinese toon":{
                            start=230;
                            end=240;
                            break;
                        }
                        case "peach":{
                            start=240;
                            end=250;
                            break;
                        }
                        case "ford woodlotus":{
                            start=250;
                            end=260;
                            break;
                        }
                        case "trident maple":{
                            start=260;
                            end=270;
                            break;
                        }
                        case "beals barberry":{
                            start=270;
                            end=280;
                            break;
                        }
                        case "southern magnolia":{
                            start=280;
                            end=290;
                            break;
                        }
                        case "canadian poplar":{
                            start=290;
                            end=300;
                            break;
                        }
                        case "chinese tulip tree":{
                            start=300;
                            end=310;
                            break;
                        }
                        case "tangerine":{
                            start=310;
                            end=320;
                            break;
                        }
                    }
                      
                    
                    for (int i = start; i < end; i++) {
                        double[] rbfMatrixTest = svm.createRBFTestMatrix(data, sigma, dataTest[i]);

                        //classify
                        double result = svm.classify(solutions, rbfMatrixTest, classList);
                        if (Math.signum(result) == 1) {
                            trueClassified++;
                        } else {
                            falseClassified++;
                        }
                        System.out.println("Result of classification: " + result);
                        System.out.println("====================================");
                    }
                    System.out.println("Total True Classified : " + trueClassified);
                    System.out.println("Total False Classified : " + falseClassified);
//
//                    JOptionPane.showMessageDialog(null, "Model Saved!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

    public void init() {
        String[] classes = {"pubescent bamboo", "chinese horse chestnut",
            "chinese redbud", "true indigo",
            "japanese maple", "nanmu", "castor aralia", "goldenrain tree", "chinese cinnamon",
            "anhui barberry", "big fruited holly", "japanese cheesewood",
            "wintersweet", "camphortree", "japan arrowwood", "sweet osmanthus",
            "deodar", "gingko", "crepe myrtle", "oleander", "yew plum pine",
            "japanese flowering cherry", "glossy privet", "chinese toon", "peach",
            "ford woodlotus", "trident maple", "beals barberry",
            "southern magnolia", "canadian poplar", "chinese tulip tree", "tangerine"};

        String[] features = {"glcm", "morph", "glcm+color", "glcm+color+morph", "color"};

        speciesList = new JComboBox(classes);
        featuresList = new JComboBox(features);
        classifyBtn = new JButton("Classify");
        text1 = new JLabel("Species List");
        text2 = new JLabel("Features List");
        text3 = new JLabel("Sigma Value");
        text4 = new JLabel("Features");
        sigmaValue = new JTextField("1");
        totalFeatures = new JTextField("1");

        text1.setBounds(50, 20, 100, 30);
        text2.setBounds(300, 20, 100, 30);
        text3.setBounds(150, 100, 100, 30);
        text4.setBounds(400,100,100,30);
        sigmaValue.setBounds(50, 100, 100, 30);
        totalFeatures.setBounds(300, 100, 100, 30);
        
        speciesList.setBounds(50, 50, 200, 30);
        featuresList.setBounds(300, 50, 150, 30);
        classifyBtn.setBounds(150, 200, 200, 30);

        add(speciesList);
        add(featuresList);
        add(classifyBtn);
        add(text1);
        add(text2);
        add(text3);
        add(text4);
        add(sigmaValue);
        add(totalFeatures);
        
        setTitle("Classify Model");
        setLayout(null);
        setSize(500, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setEnabled(true);
    }

    public static void main(String[] args) throws FileNotFoundException {
        new Classify1().show();
    }
}
