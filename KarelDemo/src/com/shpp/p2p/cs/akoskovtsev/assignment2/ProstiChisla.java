package com.shpp.p2p.cs.akoskovtsev.assignment2;

import com.shpp.cs.a.console.TextProgram;

public class ProstiChisla extends TextProgram {
    @Override
    public void run() {
        isPrime(6);
        int b = -1;
        while (b != 0) {
            println("vvedi chislo ");
            b = readInt();
            if(isPrime(b)){
                println("simple");
            } else println("NOT simple");
//            println(isPrime(b));
        }
    }

    private boolean isPrime(int n) {
        if (n <= 1) return true;
        for (int i = 2; i < n; i++) {
            if ((n % i) == 0) return false;
        }
        return true;
    }
}