import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class ClientLog {
    protected String log = "productNum, amount"+ "\n";

    public ClientLog() {
    }

    public void log(int productNum, int amount) {// сохранение покупок пользователя
        int[] n = new int[]{productNum};
        int[] a = new int[]{amount};
        log += Arrays.toString(n) + "," + Arrays.toString(a) + "\n";
    }


    public void exportAsCSV(File txtFile) {
        if (txtFile.exists()) {
            String log2 = log;
        }

        try (FileWriter writer = new FileWriter(txtFile, true)) {
            writer.write(log);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





}
