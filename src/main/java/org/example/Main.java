package org.example;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String sifra = "abcd";

        while (true){
            System.out.println("Password please:");
            String password = sc.nextLine();

            if (password.equals(sifra)) {
                System.out.println("Congrats!");
                break;
            }
        }


    }
}

