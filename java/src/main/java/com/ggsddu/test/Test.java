package com.ggsddu.test;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        System.out.println(new Test().longestPalindrome("aaa"));
        // System.out.println("aba".substring(1, 4));
    }

    public String longestPalindrome(String s) {
        if (s.length() == 1) {
            return s;
        }


        int start = 0;
        int end = 1;
        int position = 0;
        int r = position + 1;
        int l = position - 1;
        while (position < s.length() - 1) {
            while (r < s.length() && s.charAt(position) == s.charAt(r)) {
                if ((end - start) < (r + 1 - position)) {
                    start = position;
                    end = r + 1;
                }
                r++;
            }

            while (r < s.length() && l >= 0 && s.charAt(l) == s.charAt(r)) {
                if ((end - start) < (r + 1 - l)) {
                    end = r + 1;
                    start = l;
                }
                l--;
                r++;
            }

            position++;
            l = position - 1;
            r = position + 1;

        }
        return s.substring(start, end);
    }

    public String convert(String s, int numRows) {

        if (s.length() <= numRows || numRows == 1) {
            return s;
        }
        int num = 2 * numRows - 2;
        if (num == 0) {
            return s;
        }
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < numRows; i++) {
            int start = 0;
            while ((start - i) < s.length()) {
                if (start != 0 & i != (numRows - 1) & i != 0) {
                    sb.append(s.charAt(start - i));
                }
                if ((start + i) < s.length()) {
                    sb.append(s.charAt(start + i));
                }
                start += num;
            }
        }


        return sb.toString();
    }

    public int reverse(int x) {

        long r = x;
        boolean isNagetive = false;

        if (r < 0) {
            isNagetive = true;
            r = -r;
            System.out.println("aa");
        }
        System.out.println(r);
        if (r == 0) return 0;
        String str = String.valueOf(r);


        StringBuilder sb = new StringBuilder();
        int len = str.length();
        char current = str.charAt(len - 1);
        for (int i = len - 1; i >= 0; i--) {

            if (current != '0') {
                char c = str.charAt(i);
                sb.append(c);
            } else {
                char c = str.charAt(i);
                if (c != '0') {
                    sb.append(c);
                    current = '1';
                }

            }


        }

        long rs = Long.parseLong(sb.toString());

        if (isNagetive) {
            rs = -rs;
        }

        if (rs < Math.pow(-2, 31) || rs > Math.pow(2, 31) - 1) {
            return 0;
        }
        return (int) rs;
    }

    public int maxLength(List<String> arr) {
        int maxLength = 0;
        int c = 0;
        while (c < arr.size()) {
            String current = arr.get(c);
            maxLength = current.length();
            String newStr = current;
            for (int i = 1; i < arr.size(); i++) {
                String a = arr.get(i);
                for (Character aa : a.toCharArray()) {
                    if (current.contains(aa.toString())) {
                        break;
                    }
                }
                newStr = newStr.concat(a);

            }
            c++;
        }
        return maxLength;
    }

    public int lengthOfLongestSubstring(String s) {

        if (s.length() <= 1) {
            return s.length();
        }
        char[] chars = s.toCharArray();

        int maxLength = 1;

        int start = 0;
        HashMap<Character, Integer> hasSearched = new HashMap<>();

        for (int i = 0; i < chars.length; i++) {
            start = Math.max(start, hasSearched.getOrDefault(chars[i], 0));
            maxLength = Math.max(maxLength, i - start);
            hasSearched.put(chars[i], i);
        }
        return maxLength;
    }
}
