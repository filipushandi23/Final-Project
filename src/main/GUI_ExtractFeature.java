/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import model.ColorMoments;
import model.GLCM;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.MorphFeatures;
import model.Point;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import static org.opencv.imgproc.Imgproc.THRESH_OTSU;

/**
 *
 * @author Filipus
 */
public class GUI_ExtractFeature extends javax.swing.JFrame {

    /**
     * Creates new form ImageProcessing
     */
    JLabel image;
    public static BufferedImage imageProcessed = null;
    public MorphFeatures morph = new MorphFeatures();
    private int xFrom = 0;
    private int yFrom = 0;
    private int xTo = 0;
    private int yTo = 0;
    private double physicalLength = 0;
    private double physicalWidth = 0;

    private JLabel img;
    static String currentDirectory;
    static String savedDirectory;

    private int counter = 0;
    private String imgName = "";

    public GUI_ExtractFeature() {
        initComponents();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        setResizable(false);
        setTitle("Tugas Akhir");
        setSize(1300, 750);
        setLocationRelativeTo(null);

        JFileChooser c = new JFileChooser();

        image = new JLabel();

        JButton openButton = new JButton("Open Image");
        JButton saveButton = new JButton("Save Image");

        JButton btnGetLength = new JButton("Get Length Value");
        JButton btnGetWidth = new JButton("Get Width Value");
        JButton extractMorphButton = new JButton("Extract Morph Features");
        JButton extractGLCMButton = new JButton("Extract GLCM Features");
        JButton extractColorButton = new JButton("Extract Color Features");
        JButton classifyGLCM = new JButton("Classify GLCM");
        JButton classifyColor = new JButton("Classify Color");

        String[] items = {"pubescent bamboo", "chinese horse chestnut",
            "chinese redbud", "true indigo",
            "japanese maple", "nanmu", "castor aralia", "goldenrain tree", "chinese cinnamon",
            "anhui barberry", "big fruited holly", "japanese cheesewood",
            "wintersweet", "camphortree", "japan arrowwood", "sweet osmanthus",
            "deodar", "gingko", "crepe myrtle", "oleander", "yew plum pine",
            "japanese flowering cherry", "glossy privet", "chinese toon", "peach",
            "ford woodlotus", "trident maple", "beals barberry",
            "southern magnolia", "canadian poplar", "chinese tulip tree", "tangerine"};

        JComboBox options = new JComboBox(items);
        options.setBounds(50, 150, 220, 30);

        JTextField inputField = new JTextField();
        inputField.setBounds(50, 200, 220, 30);

        openButton.setBounds(50, 50, 220, 30);
        saveButton.setBounds(50, 100, 220, 30);

        btnGetLength.setBounds(50, 260, 220, 30);
        btnGetWidth.setBounds(50, 300, 220, 30);

        extractMorphButton.setBounds(50, 400, 220, 30);
        extractMorphButton.setEnabled(false);

        extractGLCMButton.setBounds(50, 440, 220, 30);
        extractGLCMButton.setEnabled(true);

        extractColorButton.setBounds(50, 480, 220, 30);
        extractColorButton.setEnabled(true);

        classifyColor.setBounds(50, 500, 220, 30);
        classifyColor.setEnabled(false);
        classifyGLCM.setBounds(50, 540, 220, 30);
        classifyGLCM.setEnabled(false);

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rVal = c.showOpenDialog(GUI_ExtractFeature.this);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    currentDirectory = c.getCurrentDirectory().toString() + "\\" + c.getSelectedFile().getName();
                    imgName = c.getSelectedFile().getName();
                    System.out.println("Dir : " + currentDirectory);
                    extractMorphButton.setEnabled(true);
                    extractGLCMButton.setEnabled(true);
                    classifyGLCM.setEnabled(true);
                    classifyColor.setEnabled(true);
                    imageProcessed = normal();
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rVal = c.showSaveDialog(GUI_ExtractFeature.this);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    savedDirectory = c.getCurrentDirectory().toString() + "\\" + c.getSelectedFile().getName();
                    try {
                        ImageIO.write(imageProcessed, "png", new File(savedDirectory));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });

        btnGetLength.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                physicalLength = calculateDistance(xFrom, yFrom, xTo, yTo);
                System.out.println("Physical length : " + physicalLength);
                JOptionPane.showMessageDialog(null, "Panjang fisik didapatkan!");
            }
        });

        btnGetWidth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                physicalWidth = calculateDistance(xFrom, yFrom, xTo, yTo);
                System.out.println("Physical Width: " + physicalWidth);
                JOptionPane.showMessageDialog(null, "Lebar fisik didapatkan!");
            }
        });

        extractMorphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Click go: "+calculateDistance(xFrom, yFrom, xTo, yTo));
                String filename = "F:\\Dataset Text\\morphological-features-extracted-TESSTT.csv";
                //System.out.println("CUrr: "+currentDirectory);
//                Mat color = Imgcodecs.imread(currentDirectory);
                Mat color = morph.bufferedImageToMat(imageProcessed);
                Mat gray = new Mat();
                Mat binary = new Mat();
                Mat edge = new Mat();
                Imgproc.cvtColor(color, gray, COLOR_BGR2GRAY);

                Imgproc.GaussianBlur(gray, gray, new Size(15, 15), 0);

                //binarization
                Imgproc.threshold(gray, binary, 50, 250, THRESH_OTSU);
                //System.out.println("Threshold otsu " + THRESH_OTSU);

                //edge detection
                Imgproc.Canny(binary, edge, 200, 230);
                //Imgproc.dilate(binary, binary, kernel);

                BufferedImage edgeImage = morph.convertMatToBufferedImage(edge);
                BufferedImage binaryImage = morph.convertMatToBufferedImage(binary);
                ArrayList<Point> coordinates = new ArrayList();
                for (int i = 0; i < edgeImage.getWidth(); i++) {
                    for (int j = 0; j < edgeImage.getHeight(); j++) {
                        Color c = new Color(edgeImage.getRGB(i, j));
                        //253 warna putih di image binary, 0 hitam
                        if (c.getRed() == 255) {
                            coordinates.add(new Point(i, j));
                        }
                    }
                }

                Point init = new Point(xFrom, yFrom);
//                System.out.println(morph.findMaxDistance(init, coordinates));

                //fitur geometri
                double diameter = morph.findMaxDistance(init, coordinates);
                int perimeter = morph.calculatePerimeter(edgeImage);
                int area = morph.calculateArea(binaryImage);

                //fitur morfologi
                double aspectRatio = physicalLength / physicalWidth;
                double formFactor = area / Math.pow(perimeter, 2);
                double narrowFactor = diameter / physicalLength;
                double rectangularity = (physicalLength * physicalWidth) / area;
                double perimeterRatioOfDiameter = perimeter / diameter;
                double perimeterRatioOfLengthAndWidth = perimeter / (physicalLength + physicalWidth);

                try (FileWriter fw = new FileWriter(filename, true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        PrintWriter out = new PrintWriter(bw)) {
                    out.println(aspectRatio + "," + formFactor + "," + narrowFactor + ","
                            + rectangularity + "," + perimeterRatioOfDiameter + ","
                            + perimeterRatioOfLengthAndWidth
                            + "," + options.getSelectedItem().toString() + "," + imgName);
                } catch (IOException ex) {
                    //exception handling left as an exercise for the reader
                }
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
                counter++;
                System.out.println(counter + " Data berhasil disimpan!");
            }
        });

        extractGLCMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
//                File file = new File("E:\\KULIAH ITHB\\Semester 7\\Tugas akhir Semester 7\\Flavia Training Dataset\\JPG");
//                File file = new File("E:\\KULIAH ITHB\\Semester 7\\Tugas akhir Semester 7\\Data Testing\\JPG");
                File file = new File("E:\\KULIAH ITHB\\Semester 7\\Tugas akhir Semester 7\\Data Test Flavia 3");
                File[] listOfFiles = file.listFiles();

//        System.out.println("Length: "+listOfFiles.length);
                StringBuilder builder = new StringBuilder();
                try {
                    for (int i = 0; i < listOfFiles.length; i++) {
                        builder.append(extractGLCMFeatures(listOfFiles[i].getAbsolutePath()));
                        builder.append(System.getProperty("line.separator"));
                    }
                    BufferedWriter writer = new BufferedWriter(new FileWriter("F:\\Dataset Text\\glcm-extracted.csv"));
                    writer.write(builder.toString());//save the string representation of the board
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
            }
        });

        extractColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                File file = new File("E:\\KULIAH ITHB\\Semester 7\\Tugas akhir Semester 7\\Flavia Training Dataset");
//                File file = new File("E:\\KULIAH ITHB\\Semester 7\\Tugas akhir Semester 7\\Data Testing");
                File file = new File("E:\\KULIAH ITHB\\Semester 7\\Tugas akhir Semester 7\\Data Test Flavia 2");
                File[] listOfFiles = file.listFiles();

                StringBuilder builder = new StringBuilder();
                try {
                    for (int i = 0; i < listOfFiles.length; i++) {
                        builder.append(extractColorFeatures(listOfFiles[i].getAbsolutePath()));
                        builder.append(System.getProperty("line.separator"));
                    }

                    BufferedWriter writer = new BufferedWriter(new FileWriter("F:\\Dataset Text\\color-training-2.csv"));
                    writer.write(builder.toString());//save the string representation of the board
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
            }
        });
        add(image);
        add(openButton);
//        add(saveButton);
        add(options);
//        add(inputField);
        add(extractMorphButton);
        add(extractGLCMButton);
        add(extractColorButton);
        add(btnGetLength);
        add(btnGetWidth);
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseMotionHandler);
    }
    
    public static BufferedImage convertMatToBufferedImage(Mat input){
        BufferedImage out;
        byte[] data = new byte[input.width() * input.height() * (int)input.elemSize()];
        int type;
        input.get(0, 0, data);

        if(input.channels() == 1)
            type = BufferedImage.TYPE_BYTE_GRAY;
        else
            type = BufferedImage.TYPE_3BYTE_BGR;

        out = new BufferedImage(input.width(), input.height(), type);

        out.getRaster().setDataElements(0, 0, input.width(), input.height(), data);
        return out;
    }
    
    public static String extractGLCMFeatures(String path) {
        Mat color = Imgcodecs.imread(path);
        Mat gray = new Mat();
        Imgproc.cvtColor(color, gray, COLOR_BGR2GRAY);

        BufferedImage grayImage = convertMatToBufferedImage(gray);
        GLCM glcm = new GLCM();

        double[][] glcmMatrix = glcm.getNormalisedGLCMMatrix(glcm.getGLCMMatrix(grayImage));

//        System.out.println("\nFeature Calculation for Image "+path+"\n");
//        System.out.println("Angular second moment = " + glcm.angularSecondMoment(glcmMatrix));
//        System.out.println("Contrast = " + glcm.contrast(glcmMatrix));
//        System.out.println("Variance = " + glcm.variance(glcmMatrix));
//        System.out.println("Entropy = " + glcm.entropy(glcmMatrix));
//        System.out.println("Homogenity = " + glcm.homogenity(glcmMatrix));
//        System.out.println("Correlation = " + glcm.correlation(glcmMatrix));
        double asm = glcm.angularSecondMoment(glcmMatrix);
        double contrast = glcm.contrast(glcmMatrix);
//        double variance = glcm.variance(glcmMatrix);
        double entropy = glcm.entropy(glcmMatrix);
        double homogenity = glcm.homogenity(glcmMatrix);
        double correlation = glcm.correlation(glcmMatrix);

//        return asm + " " + contrast + " " + variance + " " + entropy + " " + homogenity + " " + correlation;
        return asm + "," + contrast + "," + entropy + "," + homogenity + "," + correlation;
    }

    public static String extractColorFeatures(String path) throws IOException {
        Mat color = Imgcodecs.imread(path);
        BufferedImage rgbImage = convertMatToBufferedImage(color);
//        BufferedImage rgbImage = ImageIO.read(new File(path));
        ColorMoments cm = new ColorMoments();
        double meanRed = cm.calculateMeanRed(rgbImage);
        double meanGreen = cm.calculateMeanGreen(rgbImage);
        double meanBlue = cm.calculateMeanBlue(rgbImage);

        double stdDevRed = cm.calculateStdDevRed(rgbImage, meanRed);
        double stdDevGreen = cm.calculateStdDevGreen(rgbImage, meanGreen);
        double stdDevBlue = cm.calculateStdDevBlue(rgbImage, meanBlue);

        double skewRed = cm.calculateSkewnessRed(rgbImage, meanRed);
        double skewGreen = cm.calculateSkewnessGreen(rgbImage, meanGreen);
        double skewBlue = cm.calculateSkewnessBlue(rgbImage, meanBlue);

        double kurtosisRed = cm.calculateKurtosisRed(rgbImage, meanRed);
        double kurtosisGreen = cm.calculateKurtosisGreen(rgbImage, meanGreen);
        double kurtosisBlue = cm.calculateKurtosisBlue(rgbImage, meanBlue);
        return meanRed + "," + meanGreen + "," + meanBlue
                + "," + stdDevRed + "," + stdDevGreen + "," + stdDevBlue + ","
                + skewRed + "," + skewGreen + "," + skewBlue;
//        return meanRed + " " + meanGreen + " " + meanBlue
//                + " " + stdDevRed + " " + stdDevGreen + " " + stdDevBlue + " "
//                + skewRed + " " + skewGreen + " " + skewBlue + " "
//                + kurtosisRed + " " + kurtosisGreen + " " + kurtosisBlue;
//        return meanRed + "," + meanGreen + "," + meanBlue + ","
//                + kurtosisRed + "," + kurtosisGreen + "," + kurtosisBlue;
    }

    public MouseListener mouseHandler = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            xFrom = xTo = e.getX();
            yFrom = yTo = e.getY();
            repaint();
            System.out.println("Titik 1: " + e.getPoint());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            xTo = e.getX();
            yTo = e.getY();
            repaint();
            System.out.println("Titik 2: " + e.getPoint());
            //System.out.println("Jarak Garis: "+calculateDistance(xFrom, yFrom, xTo, yTo));
        }
    };

    public MouseMotionAdapter mouseMotionHandler = new MouseMotionAdapter() {
        @Override
        public void mouseDragged(MouseEvent e) {
            xTo = e.getX();
            yTo = e.getY();
            repaint();
            //System.out.println("Titik 2: "+e.getPoint());
        }
    };

    public void paint(Graphics g) {
        super.paint(g);
        g.drawLine(xFrom, yFrom, xTo, yTo);
    }

    public double calculateDistance(double xFrom, double yFrom, double xTo, double yTo) {
        return Math.sqrt(Math.pow(xFrom - xTo, 2) + Math.pow(yFrom - yTo, 2));
    }

    public BufferedImage normal() {
        BufferedImage input = null;
        try {
            input = ImageIO.read(new File(currentDirectory));
            image.setIcon(new ImageIcon(resizeImage(input)));

            for (int i = 0; i < input.getHeight(); i++) {
                for (int j = 0; j < input.getWidth(); j++) {
                    Color tmp = new Color(input.getRGB(j, i));
                    //System.out.print("("+tmp.getRed()+","+tmp.getGreen()+","+tmp.getBlue()+")\t");
                    //System.out.print(i+","+j);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return input;
    }

    private Image resizeImage(BufferedImage img) {
        if (img.getWidth() > 970 && img.getHeight() > 600) {
            Image dimg = img.getScaledInstance(900, 675, Image.SCALE_SMOOTH);
            image.setBounds(300, 20, 900, 675);
            return dimg;
        } else {
            image.setBounds(300, 20, img.getWidth(), img.getHeight());
            return img;
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

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
            java.util.logging.Logger.getLogger(GUI_ExtractFeature.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_ExtractFeature.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_ExtractFeature.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_ExtractFeature.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI_ExtractFeature().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    // End of variables declaration                   
}
