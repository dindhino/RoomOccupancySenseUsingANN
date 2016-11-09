/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import jxl.Workbook;
import jxl.Sheet;
import jxl.Cell;
import jxl.read.biff.BiffException;

/**
 *
 * @author dindhino
 */
public class JST2_Normal {

    public static final double rate = 0.5;
    public static final int MEpoch = 2665;
    public static double MSE = 0.0;

    public static final int nInput = 4;
    public static final int nHidden = 10;
    public static final int nOutput = 1;

    public static double input_hidden[][] = new double[nInput + 1][nHidden];

    public static double output_hidden[][] = new double[nHidden + 1][nOutput];
    public static double delta_bias[] = new double[nHidden + 1];
    public static double delta_bobot[][] = new double[nInput + 1][nHidden];

    public static double input[] = new double[nInput];
    public static double hidden[] = new double[nHidden];
    public static double target;
    public static double output;

    public static double error;
    public static double error_hidden[] = new double[nHidden];

    public static double iTraining[][];
    public static int oTraining[][];
    public static double iTesting[][];
    public static int oTesting[][];

    public static int banyakSample;

    public static void DataTraining() throws IOException, BiffException {
        Workbook w = Workbook.getWorkbook(new File("ocupancy_normal_training.xls"));
        Sheet s = w.getSheet(0);
        iTraining = new double[s.getRows()][s.getColumns() - 1];
        for (int baris = 0; baris < s.getRows(); baris++) {
            for (int kolom = 0; kolom < s.getColumns() - 1; kolom++) {
                Cell data = s.getCell(kolom, baris);
                iTraining[baris][kolom] = Double.parseDouble(data.getContents());
            }
        }
        banyakSample = iTraining.length;
    }

    public static void ocupancyTraining() throws IOException, BiffException {
        Workbook w = Workbook.getWorkbook(new File("ocupancy_normal_training.xls"));
        Sheet s = w.getSheet(0);
        oTraining = new int[s.getRows()][s.getColumns() - 4];
        for (int baris = 0; baris < s.getRows(); baris++) {
            Cell data = s.getCell(4, baris);
            oTraining[baris][0] = Integer.parseInt(data.getContents());
        }
    }

    public static void DataTesting() throws IOException, BiffException {
        Workbook w = Workbook.getWorkbook(new File("ocupancy_normal_testing.xls"));
        Sheet s = w.getSheet(0);
        iTesting = new double[s.getRows()][s.getColumns() - 1];
        for (int baris = 0; baris < s.getRows(); baris++) {
            for (int kolom = 0; kolom < s.getColumns() - 1; kolom++) {
                Cell data = s.getCell(kolom, baris);
                iTesting[baris][kolom] = Double.parseDouble(data.getContents());
            }
        }
        banyakSample = iTesting.length;
    }

    public static void ocupancyTesting() throws IOException, BiffException {
        Workbook w = Workbook.getWorkbook(new File("ocupancy_normal_testing.xls"));
        Sheet s = w.getSheet(0);
        oTesting = new int[s.getRows()][s.getColumns() - 4];
        for (int baris = 0; baris < s.getRows(); baris++) {
            Cell data = s.getCell(4, baris);
            oTesting[baris][0] = Integer.parseInt(data.getContents());
        }
    }

    public static void BobotRandom() {
        for (int i = 0; i <= nInput; i++) {
            for (int j = 0; j < nHidden; j++) {
                input_hidden[i][j] = 1 - (Math.random() * 2);
            }
        }

        for (int i = 0; i <= nHidden; i++) {
            for (int j = 0; j < nOutput; j++) {
                output_hidden[i][j] = 1 - (Math.random() * 2);
            }
        }
    }

    public static double sigmoid(double data) {
        return (1 / (1 + Math.exp(-data)));
    }

    public static void PropagasiMaju() {
        double sum = 0.0;

        for (int hdn = 0; hdn < nHidden; hdn++) {
            sum = 0.0;
            for (int in = 0; in < nInput; in++) {
                sum = sum + (input[in] * input_hidden[in][hdn]);
            }

            sum = sum + input_hidden[nInput][hdn];
            hidden[hdn] = sigmoid(sum);
        }

        for (int out = 0; out < nOutput; out++) {
            sum = 0.0;
            for (int hdn = 0; hdn < nHidden; hdn++) {
                sum = sum + (hidden[hdn] * output_hidden[hdn][out]);
            }
            sum = sum + output_hidden[nHidden][out];
            output = sigmoid(sum);
        }
    }

    public static void PropagasiMundur() {

        error = ((target - output) * output * (1 - output));

        for (int i = 0; i < nHidden; i++) {
            delta_bias[i] = error * hidden[i] * rate;
        }
        delta_bias[nHidden] = error * rate;

        for (int i = 0; i < nHidden; i++) {
            error_hidden[i] = error * output_hidden[i][0] * hidden[i] * (1 - hidden[i]);
        }

        for (int i = 0; i < nHidden; i++) {
            for (int j = 0; j < nInput; j++) {
                delta_bobot[j][i] = error_hidden[i] * hidden[j] * rate;
            }
            delta_bobot[nInput][i] = error_hidden[i] * rate;
        }

        for (int i = 0; i < nHidden; i++) {
            for (int j = 0; j < nInput; j++) {
                input_hidden[j][i] = input_hidden[j][i] + delta_bobot[j][i];
            }
            input_hidden[nInput][i] = input_hidden[nInput][i] + delta_bobot[nInput][i];
        }

        for (int i = 0; i < nOutput; i++) {
            for (int j = 0; j < nHidden; j++) {
                output_hidden[j][i] = output_hidden[j][i] + delta_bias[j];
            }
            output_hidden[nHidden][i] = output_hidden[nHidden][i] + delta_bias[nHidden];
        }
    }

    public static void Training() throws IOException, BiffException {
        DataTraining();
        ocupancyTraining();
        BobotRandom();

        for (int ep = 0; ep < MEpoch; ep++) {
            MSE = 0.0;
            for (int sample = 0; sample < banyakSample; sample++) {
                for (int inp = 0; inp < nInput; inp++) {
                    input[inp] = iTraining[sample][inp];
                }
                target = oTraining[sample][0];
                PropagasiMaju();
                PropagasiMundur();
                MSE = MSE + (Math.pow((target - output), 2));
            }
            System.out.println("MSE tiap epoch " + (ep + 1) + " : " + (MSE / banyakSample));
        }
    }

    public static void Testing() throws IOException, BiffException {
        DataTesting();
        ocupancyTesting();
        //BobotRandom();

        double jumlahbener = 0.0;
        double jumlahsalah = 0.0;

        for (int sample = 0; sample < banyakSample; sample++) {
            for (int inp = 0; inp < nInput; inp++) {
                input[inp] = iTesting[sample][inp];
            }
            target = oTesting[sample][0];
            PropagasiMaju();
            double cekout = Math.round(output);
            if (cekout == target) {
                jumlahbener = jumlahbener + 1;
            } else {
                jumlahsalah = jumlahsalah + 1;
            }
            System.out.println("Sample ke \t : " + (sample + 1));
            System.out.println("Output \t \t : " + cekout);
            System.out.println("Target \t \t : " + target);
            System.out.println();

        }

        System.out.println("Jumlah Benar : " + jumlahbener);
        System.out.println("Jumlah Salah : " + jumlahsalah);
        System.out.println("Total Sample : " + banyakSample);
        System.out.println("Akurasi : " + ((jumlahbener * 100) / banyakSample) + "%");
    }

    public static void main(String[] args) throws IOException, BiffException {
        Training();
        System.out.println("Hasil Testing : ");
        Testing();
    }

}
