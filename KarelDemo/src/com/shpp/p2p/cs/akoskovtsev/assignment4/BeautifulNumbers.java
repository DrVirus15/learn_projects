package com.shpp.p2p.cs.akoskovtsev.assignment4;

import com.shpp.cs.a.console.TextProgram;

import java.util.ArrayList;
import java.util.Arrays;

public class BeautifulNumbers extends TextProgram {
    @Override
    public void run() {
//        String str = readLine("enter a number: ");
//        print(addCommasToNumericString(str));
        ArrayList list = new ArrayList(Arrays.asList("a", "b", "c", "d"));
        for (int i = 0; i < list.size(); i++) {
            print(list.get(i) + " ");
            list.remove(0);
        }
        println("");
        System.out.println(list);
    }

    private String addCommasToNumericString(String digits) {
        String res = "";
        int count = 0;
        for (int i = digits.length() - 1; i >= 0; i--) {

            if (count % 3 == 0 && i != digits.length()-1 ) {
                res = ',' + res;
            }
            count++;
            res = digits.charAt(i) + res;
        }
        return  res;
    }
}
