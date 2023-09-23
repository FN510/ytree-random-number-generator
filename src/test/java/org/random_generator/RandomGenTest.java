package org.random_generator;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


class RandomGenTest {

  RandomGen target;


  @Test
  void shouldSetProbabilitiesGivenSumIsOne() {
    float[] validProbabilities = {0.1f, 0.2f, 0.3f, 0.05f, 0.35f};
    target = new RandomGen();
    target.setProbabilities(validProbabilities);
    Assertions.assertArrayEquals(validProbabilities, target.getProbabilities());

  }

  @Test
  void shouldThrowErrorGivenSumOfProbabilitiesIsNotOne() {
    float[] invalidProbabilities = {1f, 0.2f, 0.3f, 0.05f, 0.35f}; // sum is over 1
    target = new RandomGen();
    Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> target.setProbabilities(invalidProbabilities));
  }

  @Test
  void shouldThrowErrorGivenSumOfUnequalListLengths() {
    float[] invalidProbabilities = {0.1f, 0.2f, 0.3f, 0.05f, 0.35f};
    int[] randomNums
    target = new RandomGen();
    Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> target.setProbabilities(invalidProbabilities));
  }

  @ParameterizedTest
  @MethodSource
  void nextNum(int[] randomNums, float) {
    target = new RandomGen(new int[]{3, 5}, new float[]{0.5f, 0.5f});
    Map<Integer, Integer> result = generateNumbers(target, 100);
    for (Integer k: result.keySet()) {
      System.out.println(k + ": " + result.get(k));
    }
    Assertions.assertTrue()
  }

  public Map<Integer, Integer> generateNumbers(RandomGen generator, int count) {

    if (generator.getRandomNums() == null || generator.getProbabilities() == null) {
      return Map.of();
    }

    Map<Integer, Integer> numCounts = new HashMap<>(count);
    while (count > 0) {
      int nextNum = generator.nextNum();
      if (numCounts.containsKey(nextNum)) {
        numCounts.put(nextNum, numCounts.get(nextNum) + 1);
      } else {
        numCounts.put(nextNum, 1);
      }
      count--;
    }
    return numCounts;
  }
}