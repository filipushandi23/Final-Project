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
import javax.swing.*;

/**
 *
 * @author Filipus
 */
public class ExtractModels extends JFrame {

    JComboBox speciesList;
    JComboBox featuresList;
    JButton saveModelBtn;
    JTextField sigmaValue;
    JTextField totalFeatures;
    JLabel text1;
    JLabel text2;
    JLabel text3;
    JLabel text4;
    String[] classes = {"pubescent bamboo", "chinese horse chestnut",
            "chinese redbud", "true indigo",
            "japanese maple", "nanmu", "castor aralia", "goldenrain tree", "chinese cinnamon",
            "anhui barberry", "big fruited holly", "japanese cheesewood",
            "wintersweet", "camphortree", "japan arrowwood", "sweet osmanthus",
            "deodar", "gingko", "crepe myrtle", "oleander", "yew plum pine",
            "japanese flowering cherry", "glossy privet", "chinese toon", "peach",
            "ford woodlotus", "trident maple", "beals barberry",
            "southern magnolia", "canadian poplar", "chinese tulip tree", "tangerine"};
    
    public ExtractModels() {
        init();
        saveModelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SVM svm = new SVM();

                //coba load data
                String inputClass = speciesList.getSelectedItem().toString();

                //parameter
                String feature = featuresList.getSelectedItem().toString();
                String path = "C:\\Users\\Filipus\\Documents\\NetBeansProjects\\KlasifikasiSVM\\feature-data\\"+feature+"\\"+feature+"-training.csv";
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
                    
                    //print dataset
//                    System.out.println("Features : "+feature+" terdiri dari "+features+" fitur");
//                    for (int i = 0; i < dataset.length; i++) {
//                        for (int j = 0; j < dataset[0].length; j++) {
//                            System.out.print(dataset[i][j]+"\t");
//                        }
//                        System.out.println("");
//                    }
                    
                    
                    for (int index = 0; index < classes.length; index++) {
                        double[] classList = new double[961];
                        for (int i = 0; i < classList.length - 1; i++) {
                            if (dataset[i+1][features].equals(classes[index])) {
                                classList[i] = 1;
                            } else {
                                classList[i] = -1;
                            }
                        }
                        classList[960] = 0;
                        
//                        //classlist
//                        System.out.println("Class : "+classes[index]);
//                        for (int i = 0; i < classList.length; i++) {
//                            System.out.println(i+" ->   "+classList[i]);
//                        }
//                        System.out.println("===========================");
                        
                        //create RBF Matrix
                        double[][] rbfMatrix = svm.createRBFMatrix(data, sigma);
                        double[][] linearEquation = svm.createLinearEquationMatrix(rbfMatrix, classList);

                        Matrix solutions = svm.getSolutions(linearEquation, classList);
                        //print solutions
                        for (int i = 0; i < linearEquation.length; i++) {
                            System.out.println("X - " + (i + 1) + " : " + solutions.get(i, 0));
                        }

                        System.out.println("Model for " + classes[index] + " with " + feature + " feature is saved!");
                        String saveModel = "C:\\Users\\Filipus\\Documents\\NetBeansProjects\\KlasifikasiSVM\\models\\sigma-"+(int) sigma;
                        StringBuilder builder = new StringBuilder();

                        for (int i = 0; i < linearEquation.length; i++) {
                            builder.append(solutions.get(i, 0));
                            builder.append(System.getProperty("line.separator"));
                        }

                        BufferedWriter writer = new BufferedWriter(new FileWriter(saveModel + "\\" + feature + "\\model-" + classes[index] + ".txt"));
                        writer.write(builder.toString());//save the string representation of the board
                        writer.close();
                    }
                    
//                    
                    JOptionPane.showMessageDialog(null, "Model Saved!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

    public void init() {
        

        String[] features = {"glcm", "morph", "glcm+color", "glcm+color+morph","color"};

        speciesList = new JComboBox(classes);
        featuresList = new JComboBox(features);
        saveModelBtn = new JButton("Save Models");
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
        saveModelBtn.setBounds(150, 200, 200, 30);

        add(speciesList);
        add(featuresList);
        add(saveModelBtn);
        add(text1);
        add(text2);
        add(text3);
        add(text4);
        add(sigmaValue);
        add(totalFeatures);
        
        setTitle("Extract Models");
        setLayout(null);
        setSize(500, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setEnabled(true);
    }

    public static void main(String[] args) throws FileNotFoundException {
        new ExtractModels().show();
    }
}
