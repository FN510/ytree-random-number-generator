# ytree-random-number-generator

Random number generator

## RandomGen class

### Fields
This class has two fields:
- `int[] randomNums` - a list of numbers that can be chosen
- `float[] probabilities` - `probabilities[i]` is the associated probability of `randomNums[i]` being chosen

### Methods
`nextNum(): int` - Returns one of the `randomNums`. When this method is called multiple times over a long period, it should return the numbers roughly with the initialized probabilities.
