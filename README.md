# wonder_mouth

Calculates Mr Phi's coworker reactions when hearing numbers from 1..n

## Usage

Executable jar file can be downloaded from Tags.

    $ java -jar wonder_mouth-0.1.0-SNAPSHOT-standalone.jar

## Options

Enter 'n' when you are asked for.

## Examples

Sample run:

    $ java -jar wonder_mouth-0.1.0-SNAPSHOT-standalone.jar
    Enter number:
    125
    OOOOOOOoOoOOooOoooOoooooOoooooooOOoooooo

### Limitation

It can calculate output for an upper limit input of around 150. I'v checked it with default java runtime memory. 

If you provide numbers greater than 150, it possibly gives 'StackOverflowError'
