package org.random_generator;

import java.util.ArrayList;
import java.util.Collections;
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
    @implNote if no number is selected on an initial function call then the function will be called again.
    This means a number will always be returned
   */

  @Override
  public int nextNum() throws Exception {
    // split the outcome space into regions for each number, the size of each region is p[i]
    // start from 0 to p[0] for randNum[0], then p[0] to p[0]+p[1] for randNum[1] ..., p(n) to 1
    SplittableRandom random = new SplittableRandom();
    float randomFloat = random.nextFloat();
    // initialise variables for sliding upper and lower bounds (between 0 and 1)
    float lowerBound = 0;
    float upperBound = 0;
    for (int i = 0; i < getRandomNums().length; i++) {
      upperBound = lowerBound + getProbabilities()[i];
      boolean selected = randomFloat < upperBound && randomFloat >= lowerBound; // the probability this is true is equal to p[i]
      if (selected) {
        return getRandomNums()[i];
      }
      lowerBound = upperBound;
    }
    throw new Exception("Error selecting a next num");
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
