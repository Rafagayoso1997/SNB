package file_management;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadFiles {


    public ReadFiles(File fileToRead){

    }

    public static List<String> readMutations() {
        List<String>mutations = new ArrayList<>();
        File file = null;
        java.io.FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            file = new File("src/files/Mutaciones.txt");
            fr = new java.io.FileReader(file);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String line;
            while ((line = br.readLine()) != null)
                mutations.add(line);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return mutations;
    }
}
