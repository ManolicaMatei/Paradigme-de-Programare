package org.example;
import java.io.*;
import java.util.*;
import org.graalvm.polyglot.*;

public class Main {
    public static void test(String[] args) throws Exception {
        try (Context context = Context.newBuilder().allowAllAccess(true).option("java.JavaHome", System.getProperty("java.home")).build()) {
            File pyth = new File("scripts/python.py");
            File jsc = new File("scripts/js.js");
            Source source_pyth = Source.newBuilder("python", pyth).build();
            Source source_jsc = Source.newBuilder("python", jsc).build();

            Value test = context.eval(source_pyth);

            int rez = test.asInt();
            System.out.println("am testat: " + rez);

        }
    }

    public static void problema1(String[] args) throws Exception {
        try (Context polyglot = Context.newBuilder().allowAllAccess(true).build()) {
            File pyth = new File("scripts/python.py");
            File jsc = new File("scripts/js.js");
            Source source_pyth = Source.newBuilder("python", pyth).build();
            polyglot.eval(source_pyth);

            Source source_jsc = Source.newBuilder("js", jsc).build();
            Value array = polyglot.eval(source_jsc);

            for (int i = 0; i < array.getArraySize(); i++) {
                String element = array.getArrayElement(i).asString();

                String cuv_mare = polyglot.eval("python", "'" + element + "'.upper()").asString();
                cuv_mare = remove_prim_ultim(cuv_mare);
                int sum_control = suma(polyglot, cuv_mare);

                polyglot.getBindings("python").getMember("adauga_la_grup").execute(cuv_mare, sum_control);

                System.out.println(cuv_mare + " -> " + sum_control);
            }
            polyglot.getBindings("python").getMember("afiseaza_duplicatele").execute();
        }
    }

    private static String litera_upper(String token) {
        try (Context polyglot = Context.newBuilder().allowAllAccess(true).build()) {
            Value result = polyglot.eval("python", "'" + token + "'.upper()");
            return result.asString();
        }
    }

    private static int suma(Context context, String sir) {
        String pythonCode = """
                sum = 0
                for ch in '%s':
                    val = ord(ch)
                    sum = sum + (val + 1)
                sum
                """.formatted(sir);
        Value result = context.eval("python", pythonCode);
        return result.asInt();
    }

    private static String remove_prim_ultim(String sir) {
        if (sir.length() > 2) {
            String procesat = sir.substring(1, sir.length() - 1);
            return procesat;
        }
        return sir;
    }

    public static void Problema2(String[] args) throws Exception {

        // configurari
        Scanner sc = new Scanner(System.in);

        System.out.print("Numele imaginii: ");
        String numeImg = sc.nextLine().trim();

        System.out.print("Calea unde se salveaza imaginea: ");
        String cale = sc.nextLine().trim(); //  .\..

        System.out.print("Culoarea punctelor (ex: blue, red, green): ");
        String culoare = sc.nextLine().trim();

        // rulare Python extern
        ProcessBuilder pb = new ProcessBuilder(
                "C:/Users/matei/AppData/Local/Programs/Python/Python314/python.exe",
                "scripts/regresie.py",
                numeImg,
                cale,
                culoare
        );

        // pornire proces
        Process proc = pb.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String caleAbsoluta = reader.readLine(); // aici citim calea din Python
        int exitCode = proc.waitFor();

        if (exitCode == 0 && caleAbsoluta != null) {
            System.out.println("\nImagine salvata cu succes la: " + caleAbsoluta);

            Runtime.getRuntime().exec("cmd /c start \"\" \"" + caleAbsoluta + "\""); // deschide automat imaginea
        }
        else {
            System.out.println("Eroare la rularea scriptului Python.");
        }
    }

    public static void Problema3(String[] args) throws Exception {

        // Construim procesul Python
        ProcessBuilder pb = new ProcessBuilder(
                "python",
                "scripts/moneda.py"
        );

        // Mostenim input de la tastatura si output in consola
        pb.inheritIO();

        // Pornim procesul
        Process proc = pb.start();
        // Asteptam finalizarea procesului
        int exitCode = proc.waitFor();

        if(exitCode != 0){
            System.out.println("Eroare la rularea scriptului Python.");
        }
    }
}

