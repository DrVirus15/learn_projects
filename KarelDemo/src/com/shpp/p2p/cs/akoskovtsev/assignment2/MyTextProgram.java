package com.shpp.p2p.cs.akoskovtsev.assignment2;

import com.shpp.cs.a.console.TextProgram;

public class MyTextProgram extends TextProgram {
    @Override
    public void run() {
        int chislo = readInt("Vvedite chislo");
        for (int i = 0; i < chislo; i++) {
               println(i % 15 == 0 ? "BUZZ"
                    : (i % 5 == 0) ? "BAZZ"
                    : (i % 3 == 0) ? "FIZZ"
                    : i);
        }
    }

}