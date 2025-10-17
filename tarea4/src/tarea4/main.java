package tarea4;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase principal que gestiona la entrada de datos de alumnos y los guarda en un fichero binario
 * @author David Ortiz
 */
public class main {

    /**
     * Metodo principal del programa Solicita los datos de varios alumnos y los escribe en un fichero
     * @param args Argumentos de línea de comandos (no usados)
     * @throws IOException Si ocurre un error de entrada o salida
     */
    public static void main(String[] args) throws IOException {
        DataOutputStream salida;
        FileInputStream entrada;
        ArrayList<alumno> lista = new ArrayList<>();
        int veces = 5;
        boolean generoComprovacion = false;
        char genero = 'm';
        Scanner sc = new Scanner(System.in);

        System.out.println("Pasame la ruta del fichero a usar:");
        //src/tarea4/salida.txt
        File fichero = new File(sc.nextLine());
        salida = new DataOutputStream(new FileOutputStream(fichero, true));
        entrada = new FileInputStream(fichero);

        for (int i = 0; i < veces; i++) {
            try {
                generoComprovacion = false;
                boolean fechaValida = false;
                Date fecha = null;
                System.out.println("Dime el NIA:");
                int nia = sc.nextInt();
                System.out.println("Dime el nombre:");
                String nombre = sc.next();
                System.out.println("Dime el apellido:");
                String apellido = sc.next();
                while (!generoComprovacion) {
                    System.out.println("Dime el genero (F o M):");
                    genero = (sc.next().toLowerCase()).charAt(0);
                    if (genero == 'f' || genero == 'm') {
                        generoComprovacion = true;
                    } else {
                        System.out.println("Error: introduce F o M");
                    }
                }
                while (!fechaValida) {
                    try {
                        System.out.println("Dime la fecha (formato yyyy-mm-dd):");
                        String dateS = sc.next();
                        String[] partes = dateS.split("-");
                        if (partes.length != 3) {
                            System.out.println("Formato incorrecto Usa yyyy-mm-dd");
                            continue;
                        }
                        int anio = Integer.parseInt(partes[0]);
                        int mes = Integer.parseInt(partes[1]);
                        int dia = Integer.parseInt(partes[2]);

                        fecha = new Date(anio - 1900, mes - 1, dia);
                        fechaValida = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Formato incorrecto Usa yyyy-mm-dd");
                    }
                }

                System.out.println("Dime el ciclo:");
                String ciclo = sc.next();
                System.out.println("Dime el curso:");
                String curso = sc.next();
                System.out.println("Dime el grupo:");
                String grupo = sc.next();

                alumno a = new alumno(nia, nombre, apellido, genero, fecha, ciclo, curso, grupo);
                lista.add(a);
                salida.writeInt(nia);
                salida.writeUTF(nombre);
                salida.writeUTF(apellido);
                salida.writeChar(genero);
                salida.writeInt(fecha.getYear() + 1900);
                salida.writeInt(fecha.getMonth() + 1);
                salida.writeInt(fecha.getDate());
                salida.writeUTF(ciclo);
                salida.writeUTF(curso);
                salida.writeUTF(grupo);
            } catch (InputMismatchException e) {
                System.out.println("Tipo de dato incorrecto");
                veces++;
                sc.nextLine();
            }
        }

        salida.close();
        entrada.close();
        sc.close();
    }
}
