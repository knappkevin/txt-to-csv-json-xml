import java.io.*;
import java.util.Scanner;

public class DataFormat {

    public static void add(BufferedWriter bw, String s, int tabs) {
        try {
            for (int i = 0; i < tabs; i++) bw.write("    ");
            bw.write(s);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void csv() {
        try {
            FileReader fr = new FileReader("src/data.txt");
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter("src/data.csv");
            BufferedWriter bw = new BufferedWriter(fw);

            String line;
            while ((line = br.readLine()) != null) {
                //String[] vals = line.split("\\t");
                String newLine = "";
                for (String val : line.split("\\t")) {
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
//            Scanner sc = new Scanner(new File("src/test.txt"));
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

    public static void json() {
        try {
            FileReader fr = new FileReader("src/data.txt");
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter("src/data.json");
            BufferedWriter bw = new BufferedWriter(fw);

            String[] properties = br.readLine().split("\\t"); //store top row
            String row;
            String tab = "    ";
            int tabs = 2;

            //begin structuring
            add(bw, "{", 0);
            add(bw, "\"data\":[", 1);

            //for first row of data
            if ((row = br.readLine()) != null) {
                add(bw, "{", 2);

                String[] vals = row.split("\\t");
                for (int i = 0; i < vals.length; i++) {
                    String line = "\"" + properties[i] + "\":\"" + vals[i] + "\",";
                    if (i == vals.length - 1) line = line.substring(0, line.length() - 1); //remove , for last
                    add(bw, line, 3);
                }

                bw.write(tab + tab + "}");
            }

            //for remainder of the rows
            while ((row = br.readLine()) != null) {
                bw.write(",");
                bw.newLine();
                add(bw, "{", 2);

                String[] vals = row.split("\\t");
                for (int i = 0; i < vals.length; i++) {
                    String line = "\"" + properties[i] + "\":\"" + vals[i] + "\",";
                    if (i == vals.length - 1) line = line.substring(0, line.length() - 1); //remove , for last
                    add(bw, line, 3);
                }

                bw.write(tab + tab + "}");
            }
            bw.newLine();

            //finish structuring
            add(bw, "]", 1);
            add(bw, "}", 0);


            bw.close();
            fw.close();
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        Scanner in = new Scanner(System.in); //scanner for user input

        System.out.println("Convert file into CSV 'c', JSON 'j', XML 'x': "); //ask user
        String format = in.nextLine(); //users preferred format

        if (format.equals("c")) csv();
        if (format.equals("j")) json();

    }

}
