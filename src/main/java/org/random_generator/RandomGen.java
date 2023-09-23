package org.random_generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SplittableRandom;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class RandomGen implements RandomGenerator {

  // Values that may be returned by nextNum()
  @Getter @Setter private int[] randomNums;

  // Probability of the occurrence of randomNums
  @Getter private float[] probabilities;

  public static final String UNEQUAL_LENGTH_OF_INPUTS_ERROR_MESSAGE = "The number of random numbers and probabilities should be the same";
  public static final String INVALID_SUM_OF_PROBABILITIES_ERROR_MESSAGE = "The sum of all probabilities must equal 1";

/**
 * Allocates a new {@code RandomGen} with the given parameters
 * @param randomNums
 * The list of numbers to be random selected from
 * @param probabilities
 * The probability of the corresponding number in {@code randomNums} being chosen
 *
 * @throws IllegalArgumentException
 * when {@code randomNums} and {@code probabilities} are not equal in length
 * or
 * when the sum of {@code probabilities} does not equal 1
 */
  RandomGen(int[] randomNums, float[] probabilities) {
    if (randomNums.length == probabilities.length) {
      setRandomNums(randomNums);
      setProbabilities(probabilities);
    } else {
      throw new IllegalArgumentException(UNEQUAL_LENGTH_OF_INPUTS_ERROR_MESSAGE);
    }

  }

   /**
   Returns one of the randomNums. When this method is called
   multiple times over a long period, it should return the
   numbers roughly with the initialized probabilities.
   */

  @Override
  public int nextNum() {
    // create a boolean for each number which is true if the number is selected
    List<Map<Integer, Boolean>> numberSelections = new ArrayList<>(getRandomNums().length);
    SplittableRandom random = new SplittableRandom();
    for (int i = 0; i < getRandomNums().length; i++) {
      boolean selected = random.nextFloat() < getProbabilities()[i]; // the probability this is true is equal to p[i]
      numberSelections.add(Map.of(getRandomNums()[i], selected));
    }
    // select one of the booleans that are true
    int chosenIndex = random.nextInt(numberSelections.size());
    // return value
    return getRandomNums()[chosenIndex];
  }

  public void setProbabilities(float[] probabilities) {
    float sum = 0;
    for (float e: probabilities) { sum += e; }
    if (sum != 1) {
      throw new IllegalArgumentException(INVALID_SUM_OF_PROBABILITIES_ERROR_MESSAGE);
    }
    this.probabilities = probabilities;
  }


}
