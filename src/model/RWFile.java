/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author Filipus
 */
public class RWFile {
    public static String[][] getDataFromText2D(String path, int n, int features) throws FileNotFoundException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(path)));
//      Scanner sc = new Scanner(new BufferedReader(new FileReader("F:\\data.txt")));

        String[][] data = new String[n][features];
        while (sc.hasNextLine()) {
            for (int i = 0; i < data.length; i++) {
                String[] line = sc.nextLine().trim().split(",");
                for (int j = 0; j < line.length; j++) {
                    data[i][j] = line[j];
                }
            }
        }

        return data;
    }
    
    public static double[] getDataFromText(String path, int n) throws FileNotFoundException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(path)));
//      Scanner sc = new Scanner(new BufferedReader(new FileReader("F:\\data.txt")));

        double[] data = new double[n];
        while (sc.hasNextLine()) {
            for (int i = 0; i < data.length; i++) {
                String[] line = sc.nextLine().trim().split(",");
                for (int j = 0; j < line.length; j++) {
                    data[i] = Double.parseDouble(line[j]);
                }
            }
        }

        return data;
    }
}
