package org.random_generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
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
    target = new RandomGen();
    Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> target.setProbabilities(invalidProbabilities));
  }

  @ParameterizedTest
  @MethodSource("getParameters")
  void nextNum(int[] randomNums, float[] probabilities, int count) {
    target = new RandomGen(randomNums, probabilities);
    Map<Integer, Integer> result = generateNumbers(target, count);
    for (Integer k: result.keySet().stream().sorted().collect(Collectors.toList())) {
      System.out.println(k + ": " + result.get(k));
    }
    List<Integer> expectedOutcomes = new ArrayList<>(randomNums.length);
    for (int i = 0; i < randomNums.length; i++) {
      int expectedOutcome = (int) (probabilities[i] * count);
      expectedOutcomes.add(expectedOutcome);
      System.out.println("The expected count for "+ randomNums[i] + " is "+ expectedOutcome);

      int actualOutcome = result.get(randomNums[i]);
      int difference = Math.round(((float)(actualOutcome - expectedOutcome)/(float) expectedOutcome)*100);

      //System.out.println("difference in %: " + difference);
      System.out.println(String.format("difference: %+d%%", difference));
      Assertions.assertTrue(Math.abs(difference) < 10);
    }
  }

  private static Stream<Arguments> getParameters() {
    return Stream.of(
        Arguments.of(new int[]{3, 5, 4}, new float[]{0.1f, 0.5f, 0.4f}, 500)
    );
  }

  /**
   * Function to call {@code nextNum} {@code count} number of times
   * @param generator an instance of randomGen with numbers and probabilities
   * @param count the number of outcomes to be generated
   * @return
   */
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