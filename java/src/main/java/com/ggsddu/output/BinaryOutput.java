package com.ggsddu.output;

import java.io.*;

public class BinaryOutput {

    public static void main(String[] args) throws IOException {
        String fileName = "test.txt";
        write(fileName);
    }

    private static void write(String fileName) throws IOException {
        final DataOutputStream outputStream = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(fileName)));


        outputStream.writeBytes(toBinary("JAVA周"));

        outputStream.close();
    }


    private static String toBinary(String str) {
        final char[] chars = str.toCharArray();
        StringBuilder result = new StringBuilder();
        for (char aChar : chars) {
            result.append(Integer.toBinaryString(aChar));
        }
        return result.toString();
    }
}
