package org.random_generator;

import java.util.SplittableRandom;

public class Main {

  public static void main(String[] args) {

    SplittableRandom random = new SplittableRandom();
    boolean probability = random.nextFloat() < 0.3;

    System.out.println("Hello world!");
  }
}