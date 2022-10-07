import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        MachineTuring mt = new MachineTuring("src/machine-turing.txt", "1001");
        System.out.println(mt.getEtatCourant());
        mt.reconnaitreMot();
    }
}
