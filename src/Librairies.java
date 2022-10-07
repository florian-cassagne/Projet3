import java.util.Arrays;

public class Librairies {
  public static <T> void afficherContenuTableau(T[] tableau){
    for (T t : tableau) {
      System.out.println(t);
    }
  }
}
