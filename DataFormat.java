import java.io.*;
import java.util.Scanner;

public class DataFormat {

    public static void csv() {
        try {
            FileReader fr = new FileReader("src/data.txt");
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter("src/data.csv");
            BufferedWriter bw = new BufferedWriter(fw);

            String line;
            while ((line = br.readLine()) != null) {
                String[] vals = line.split("\\t");
                String newLine = "";
                for (String val : vals) {
                    newLine = newLine + val + ",";
                }
                bw.write(newLine, 0, newLine.length() - 1);
                bw.newLine();
            }

            bw.close();
            fw.close();
            br.close();
            fr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            FileWriter fw = new FileWriter("src/data.csv");
//
//            Scanner sc = new Scanner(new File("src/data.txt"));
////            Scanner sc = new Scanner(new File("src/test.txt"));
//
//            sc.useDelimiter("\\t");
//
//            while (sc.hasNext()) {
//                fw.write(sc.next() + ",");
//            }
//
//            sc.close();
//            fw.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    public static void main(String[] args) {

        Scanner in = new Scanner(System.in); //scanner for user input

        System.out.println("Convert file into CSV 'c', JSON 'j', XML 'x': "); //ask user
        String format = in.nextLine(); //users preferred format

        if (format.equals("c")) csv();

    }

}
