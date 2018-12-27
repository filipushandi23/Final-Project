/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import main.*;
import Jama.Matrix;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import model.RWFile;
import model.SVM;

/**
 *
 * @author Filipus
 */
public class GUI_ConfMatrix extends javax.swing.JFrame {

    /**
     * Creates new form GUI
     */
    private static String[] classes = {"pubescent bamboo", "chinese horse chestnut",
        "chinese redbud", "true indigo",
        "japanese maple", "nanmu", "castor aralia", "goldenrain tree", "chinese cinnamon",
        "anhui barberry", "big fruited holly", "japanese cheesewood",
        "wintersweet", "camphortree", "japan arrowwood", "sweet osmanthus",
        "deodar", "gingko", "crepe myrtle", "oleander", "yew plum pine",
        "japanese flowering cherry", "glossy privet", "chinese toon", "peach",
        "ford woodlotus", "trident maple", "beals barberry",
        "southern magnolia", "canadian poplar", "chinese tulip tree", "tangerine"};
    private SVM svm = new SVM();
    private int features = 0;

    public GUI_ConfMatrix() {
        initComponents();
        setTitle("Classification Using SVM Manual");
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public static int findMaxValue(double[] data) {
        double max = 0;
        int index = 0;

        for (int i = 0; i < data.length; i++) {
            if (data[i] > max) {
                max = data[i];
                index = i;
            }
        }

        //System.out.println("Data : " + data[index] + " at index -> " + index);
        return index;
    }

    public static double calculateAccuracy(int[][] confusionMatrix) {
        double accuracy = 0;
        double truePositive = 0;
        double totalData = 0;

        for (int i = 0; i < confusionMatrix.length; i++) {
            for (int j = 0; j < confusionMatrix.length; j++) {
                totalData += confusionMatrix[i][j];
                if (i == j) {
                    truePositive += confusionMatrix[i][j];
                }
            }
        }

        accuracy = truePositive / totalData;

        return accuracy;
    }

    public static int[][] createConfusionMatrix(double[][] data) {
        int[][] confusionMatrix = new int[classes.length][classes.length];
        int predictedIndex = 0;

        for (int i = 0; i < data.length; i++) {
            //System.out.println("index: " + (i / 5));
            predictedIndex = findMaxValue(data[i]);
            confusionMatrix[i / (data.length / classes.length)][predictedIndex]++;
        }

        return confusionMatrix;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        trainDataBtn = new javax.swing.JButton();
        testDataBtn = new javax.swing.JButton();
        sigmaValue = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        featuresList = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);

        trainDataBtn.setText("Train Data");
        trainDataBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trainDataBtnActionPerformed(evt);
            }
        });

        testDataBtn.setText("Test Data");
        testDataBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testDataBtnActionPerformed(evt);
            }
        });

        sigmaValue.setText("10");

        jLabel1.setText("Sigma");

        featuresList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "glcm", "morph", "color", "glcm+color","glcm+morph","color+morph","glcm+color+morph" }));

        jLabel3.setText("Features List");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(trainDataBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addComponent(testDataBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(featuresList, 0, 138, Short.MAX_VALUE)
                            .addComponent(sigmaValue))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(trainDataBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(testDataBtn)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sigmaValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(featuresList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(0, 98, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void trainDataBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trainDataBtnActionPerformed

        //parameter
        String feature = featuresList.getSelectedItem().toString();
        String path = "C:\\Users\\Filipus\\Documents\\NetBeansProjects\\KlasifikasiSVM\\feature-data\\" + feature + "\\" + feature + "-training.csv";
        double sigma = Double.parseDouble(sigmaValue.getText());

        switch (feature) {
            case "glcm": {
                features = 5;
                break;
            }
            case "morph": {
                features = 6;
                break;
            }
            case "color": {
                features = 9;
                break;
            }
            case "glcm+color": {
                features = 14;
                break;
            }
            case "glcm+morph": {
                features = 11;
                break;
            }
            case "color+morph": {
                features = 15;
                break;
            }
            case "glcm+color+morph": {
                features = 20;
                break;
            }
        }

        try {
            String[][] dataset = RWFile.getDataFromText2D(path, 961, (features + 1));
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
                    if (dataset[i + 1][features].equals(classes[index])) {
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
                String saveModel = "C:\\Users\\Filipus\\Documents\\NetBeansProjects\\KlasifikasiSVM\\models\\sigma-" + (int) sigma;
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
    }//GEN-LAST:event_trainDataBtnActionPerformed

    private void testDataBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testDataBtnActionPerformed
        //parameter
        String feature = featuresList.getSelectedItem().toString();
        String path = "C:\\Users\\Filipus\\Documents\\NetBeansProjects\\KlasifikasiSVM\\feature-data\\" + feature + "\\" + feature + "-training.csv";
        double sigma = Double.parseDouble(sigmaValue.getText());

        switch (feature) {
            case "glcm": {
                features = 5;
                break;
            }
            case "morph": {
                features = 6;
                break;
            }
            case "color": {
                features = 9;
                break;
            }
            case "glcm+color": {
                features = 14;
                break;
            }
            case "glcm+morph": {
                features = 11;
                break;
            }
            case "color+morph": {
                features = 15;
                break;
            }
            case "glcm+color+morph": {
                features = 20;
                break;
            }
        }

        try {
            String[][] dataset = RWFile.getDataFromText2D(path, 961, (features + 1));
            double[][] data = new double[960][features];

            for (int i = 1; i < dataset.length; i++) {
                for (int j = 0; j < dataset[0].length - 1; j++) {
                    data[i - 1][j] = Double.parseDouble(dataset[i][j]);
                }
            }

            double[][] classificationResult = new double[320][32];

            for (int index = 0; index < classes.length; index++) {
                double[] classList = new double[961];
                for (int i = 0; i < classList.length - 1; i++) {
                    if (dataset[i + 1][features].equals(classes[index])) {
                        classList[i] = 1;
                    } else {
                        classList[i] = -1;
                    }
                }
                classList[960] = 0;

                //get model (alpha and bias)
                String modelPath = "C:\\Users\\Filipus\\Documents\\NetBeansProjects\\KlasifikasiSVM\\models\\sigma-" + (int) sigma + "\\" + feature + "\\model-" + classes[index] + ".txt";

                double[] solutions = RWFile.getDataFromText(modelPath, 961);

//                    //get testing data, create RBF matrix test
                String pathTest = "C:\\Users\\Filipus\\Documents\\NetBeansProjects\\KlasifikasiSVM\\feature-data\\" + feature + "\\" + feature + "-testing.csv";
                String[][] datasetTest = RWFile.getDataFromText2D(pathTest, 321, (features + 1));
                double[][] dataTest = new double[320][features];

                for (int i = 1; i < datasetTest.length; i++) {
                    for (int j = 0; j < datasetTest[0].length - 1; j++) {
                        dataTest[i - 1][j] = Double.parseDouble(datasetTest[i][j]);
                    }
                }

                for (int jIndex = 0; jIndex < 320; jIndex++) {
                    double[] rbfMatrixTest = svm.createRBFTestMatrix(data, sigma, dataTest[jIndex]);
                    //classify
                    double result = svm.classify(solutions, rbfMatrixTest, classList);
                    //System.out.println("Result for data-"+(jIndex+1)+" = "+result);
                    classificationResult[jIndex][index] = result;
                }
            }

//            for (int i = 0; i < classificationResult.length; i++) {
//                for (int j = 0; j < classificationResult[0].length; j++) {
//                    System.out.print(classificationResult[i][j]+",");
//                }
//                System.out.println("");
//            }
            int[][] confusionMatrix = createConfusionMatrix(classificationResult);
            textArea.append("\nConfusion Matrix\n");
            System.out.println("Confusion Matrix");
            for (int i = 0; i < confusionMatrix.length; i++) {
                for (int j = 0; j < confusionMatrix.length; j++) {
                    System.out.print(confusionMatrix[i][j] + " ");
                    textArea.append(confusionMatrix[i][j] + " ");
                }
                System.out.println("" + classes[i]);
                textArea.append(classes[i] + "\n");
            }
            textArea.append("\nAccuracy : " + calculateAccuracy(confusionMatrix) * 100 + "%\n");
            System.out.println("\nAccuracy : " + calculateAccuracy(confusionMatrix) * 100 + "%");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_testDataBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI_ConfMatrix.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_ConfMatrix.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_ConfMatrix.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_ConfMatrix.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI_ConfMatrix().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> featuresList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField sigmaValue;
    private javax.swing.JButton testDataBtn;
    private javax.swing.JTextArea textArea;
    private javax.swing.JButton trainDataBtn;
    // End of variables declaration//GEN-END:variables
}
