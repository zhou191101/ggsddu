package com.ggsddu.exceptions;

import java.io.*;

public class ExceptionTest {

    public static void main(String[] args) throws Exception{


        IOExcept(0);
    }


    public static void oneDevideZero(int i) throws ArithmeticException {
        try {
            System.out.println(i);
            System.out.println(1 / 0);
        } catch (ArithmeticException e) {
            //e.printStackTrace();
            if (i > 5) {
                throw e;
            }

            oneDevideZero(++i);

        }
    }

    public static void IOExcept(int i) throws IOException {

        try {
            System.out.println(i);
            BufferedReader as = new BufferedReader(new FileReader(new File("as")));
            as.read();
        }catch (IOException e){
            if(i>5){
                e.printStackTrace();
                throw e;
            }
            IOExcept(++i);
        }

    }
}

