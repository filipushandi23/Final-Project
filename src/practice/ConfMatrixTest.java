/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

/**
 *
 * @author Filipus
 */
public class ConfMatrixTest {
    //misal ada 3 kelas : anggrek, mawar, melati

    static String[] kelas = {"anggrek", "mawar", "melati"};

    public static void main(String[] args) {

        //hasil pengujian dengan svm
        //1 kelas dibandingkan dengan dirinya sendiri dan dibandingkan dengan kelas lainnya
        //data pengujian misal ada 15 data, 5 untuk masing-masing kelas
        /*
            misal data ke 1 - 5 adalah data pengujian anggrek
            misal data ke 6 - 10 adalah data pengujian mawar
            misal data ke 11 - 15 adalah data pengujian melati
        
         */

        double[][] result = {
            {2.14245, 0.52451, -0.12523},//data 1 diuji untuk 3 kelas (anggrek, mawar, melati)
            {1.661235, 0.991235, 0.053261},//data 2 diuji untuk 3 kelas (anggrek, mawar, melati)
            {2.0124, -0.9124, -0.012523},//data 3 diuji untk 3 kelas (anggrek, mawar, melati)
            {0.528294, 0.2224, -0.2423},//data 4 diuji untuk 3 kelas (anggrek, mawar, melati)
            {0.42525, 0.98242, -1.5232},//data 5 diuji untuk 3 kelas (anggrek, mawar, melati)

            {-0.2423, 1.55123, 0.112},//data 6 diuji untuk 3 kelas (anggrek, mawar, melati)
            {-2.524, 0.5523, -0.9923},//data 7 diuji untuk 3 kelas (anggrek, mawar, melati)
            {-1.2523, 2.1123, -0.1245},//data 8 diuji untuk 3 kelas (anggrek, mawar, melati)
            {-0.16634, 1.223, -1.5023},//data 9 diuji untuk 3 kelas (anggrek, mawar, melati)
            {-1.7293, 2.235, -0.2927},//data 10 diuji untuk 3 kelas (anggrek, mawar, melati)

            {-2.2423, 0.902524, 0.1235},//data 11 diuji untuk 3 kelas (anggrek, mawar, melati)
            {-2.1251, -0.25239, 1.5123},//data 12 diuji untuk 3 kelas (anggrek, mawar, melati)
            {-2.5123, -0.55123, 2.5152},//data 13 diuji untuk 3 kelas (anggrek, mawar, melati)
            {-1.5523, -0.124, 1.5232},//data 14 diuji untuk 3 kelas (anggrek, mawar, melati)
            {-1.8791, 0.24235, 2.23523},//data 15 diuji untuk 3 kelas (anggrek, mawar, melati)
        };

        System.out.println("CONFUSION MATRIX");
        double[][] confusionMatrix = createConfusionMatrix(result);
        System.out.println("\n1\t2\t3");
        for (int i = 0; i < confusionMatrix.length; i++) {
            for (int j = 0; j < confusionMatrix.length; j++) {
                System.out.print(confusionMatrix[i][j] + "\t");
            }
            System.out.println("");
        }
        
        System.out.println("\nAccuracy : "+calculateAccuracy(confusionMatrix)*100 +"%");
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

    public static double calculateAccuracy(double[][] confusionMatrix){
        double accuracy = 0;
        double truePositive = 0;
        double totalData = 0;
        
        for (int i = 0; i < confusionMatrix.length; i++) {
            for (int j = 0; j < confusionMatrix.length; j++) {
                totalData += confusionMatrix[i][j];
                if(i==j){
                    truePositive += confusionMatrix[i][j];
                }
            }
        }
        
        accuracy = truePositive/totalData;
        
        return accuracy;
    }
    public static double[][] createConfusionMatrix(double[][] data) {
        double[][] confusionMatrix = new double[3][3];
        int predictedIndex = 0;

        for (int i = 0; i < data.length; i++) {
            //System.out.println("index: " + (i / 5));
            predictedIndex = findMaxValue(data[i]);
            confusionMatrix[i / (data.length /kelas.length)][predictedIndex]++;
        }

        return confusionMatrix;
    }
}
