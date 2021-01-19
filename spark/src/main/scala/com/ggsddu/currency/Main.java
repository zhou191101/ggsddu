package com.ggsddu.currency;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while (in.hasNext()) {
            String line = in.next();
            StringBuffer sb = new StringBuffer();
            char current = line.charAt(0);
            if(line.length()>1){
                int count = 1;
                for (int i = 1; i < line.length(); i++) {
                    char next = line.charAt(i);
                    if (current == next) {
                        count++;
                    } else {
                        if (count > 1) {
                            sb.append(current);
                            sb.append(count);
                        } else {
                            sb.append(current);
                        }
                        current = next;
                        count = 1;
                    }
                }

                if (count > 1  ) {
                    sb.append(current);
                    sb.append(count);
                } else if(!Character.isDigit(sb.toString().charAt(sb.length()-1)) || sb.toString().charAt(sb.length()-2) != current){
                    sb.append(current);
                }

            }else {
                sb.append(current);
            }


            System.out.println(sb.toString());
        }
    }


}
