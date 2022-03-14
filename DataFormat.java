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

    public static void csv(String path) {
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String dest = path.substring(0, path.lastIndexOf("\\"));
            FileWriter fw = new FileWriter(dest + "\\data.csv");
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
    }

    public static void json(String path) {
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String dest = path.substring(0, path.lastIndexOf("\\"));
            FileWriter fw = new FileWriter(dest + "\\data.json");
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

    public static void xml(String path) {
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String dest = path.substring(0, path.lastIndexOf("\\"));
            FileWriter fw = new FileWriter(dest + "\\data.xml");
            BufferedWriter bw = new BufferedWriter(fw);

            String[] properties = br.readLine().split("\\t"); //store top row
            String tab = "    ";
            String row;

            //begin structuring
            add(bw, "<data>", 0);

            while ((row = br.readLine()) != null) {
                add(bw, "<entry>", 1);
                String[] vals = row.split("\\t");
                for (int i = 0; i < vals.length; i++) {
                    String rmSpace = properties[i].replace(" ", "");
                    properties[i] = rmSpace;
                    add(bw, "<" + properties[i] + ">" + vals[i] + "</" + properties[i] + ">", 2);
                }
                add(bw, "</entry>", 1);

            }

            add(bw, "</data>", 0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        while (true) {
            Scanner in = new Scanner(System.in); //scanner for user input

            System.out.println("Convert file into CSV 'c', JSON 'j', XML 'x': "); //ask user
            String format = in.nextLine(); //users preferred format
            System.out.println("Path to the data file to be converted:"); //ask user
            String path = in.nextLine();

            if (format.equals("c")) csv(path);
            else if (format.equals("j")) json(path);
            else if (format.equals("x")) xml(path);
            else {
                System.out.println("Invalid format input");
                continue;
            }

            System.out.println("If no error, file converted to " + format);
            System.out.println("Do you want to perform another conversion? (y/n)");
            String c = in.nextLine();
            if (c.equals("n")) break;
            else if (c.equals("y")) continue;
            else {
                System.out.println("Invalid input, ending session");
                break;
            }
        }
    }

}
