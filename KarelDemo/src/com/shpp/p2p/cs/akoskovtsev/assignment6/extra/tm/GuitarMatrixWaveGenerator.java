package com.shpp.p2p.cs.akoskovtsev.assignment6.extra.tm;

import acm.graphics.GMath;

public class GuitarMatrixWaveGenerator {

   private static final double[] FREQUENCIES = new double[]{41.20D, 55.00D, 73.42D, 97.99D, 65.41D};

   private static int periodOf(double frequency) {
      return GMath.round(44100.0D / frequency);
   }

   // Метод для генерації хвилі, що імітує гітарний тон з гармоніками
   private static double[] guitarLikeWave(double frequency) {
      int period = periodOf(frequency);
      double[] result = new double[period];

      for (int i = 0; i < period; ++i) {
         double t = 6.283185307179586D * (double)i / (double)period;

         // Основна частота (фундаментальна)
         double fundamental = Math.sin(t);

         // Додаткові гармоніки для насиченого тембру
         double secondHarmonic = Math.sin(2.0 * t) * 0.4;
         double thirdHarmonic = Math.sin(3.0 * t) * 0.15;
         double fourthHarmonic = Math.sin(4.0 * t) * 0.05;

         // Комбінуємо всі хвилі
         result[i] = fundamental + secondHarmonic + thirdHarmonic + fourthHarmonic;
      }
      return result;
   }

   public static double[][] getSamples() {
      double[][] result = new double[ToneMatrixConstants.size()][];

      for (int note = 0; note < result.length; ++note) {
         int noteIndex = note % FREQUENCIES.length;
         double[] wave = guitarLikeWave(FREQUENCIES[noteIndex] * Math.pow(2.0D, (double)(1 + note / FREQUENCIES.length)));
         double[] generated = new double[ToneMatrixConstants.sampleSize()];

         for (int attenuationFactor = 0; attenuationFactor < generated.length; ++attenuationFactor) {
            generated[attenuationFactor] = wave[attenuationFactor % wave.length];
         }

         // Швидке наростання (Attack)
         double attackDuration = (double)generated.length / 100; // Дуже швидке наростання
         for (int i = 0; i < (int)attackDuration; ++i) {
            generated[i] *= (double)i / attackDuration;
         }

         // Повільне експоненціальне згасання (Decay)
         double decayFactor = 0.99995; // Дуже повільне згасання
         for (int i = 0; i < generated.length; ++i) {
            generated[i] *= Math.pow(decayFactor, i);
         }

         result[note] = generated;
      }

      return result;
   }
}
