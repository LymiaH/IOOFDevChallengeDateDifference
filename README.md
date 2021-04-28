# IOOFDevChallengeDateDifference

Order two dates chronologically and find the absolute difference between them.

## Running

Input from stdin:

```sh
# Manual
gradlew run
# From file
gradlew run < ./examples/sample.in
```

File as argument:

```sh
gradlew run --args="./examples/sample.in"
```

## Testing

```sh
gradlew test
```

## Building

```sh
gradlew build
```

Output will be in `./build/libs/IOOFDevChallengeDateDifference.jar`.

### Running the built JAR

Input from stdin:

```sh
# Manual
java -jar ./build/libs/IOOFDevChallengeDateDifference.jar
# From file
java -jar ./build/libs/IOOFDevChallengeDateDifference.jar < ./examples/sample.in
```

File as argument:

```sh
java -jar ./build/libs/IOOFDevChallengeDateDifference.jar ./examples/sample.in
```

## Relevant Files

- [Requirements and Assumptions](./notes.md)
- [Implementation](./src/main/kotlin/lymiah/ioof/datediff/Main.kt)
- [Test Implementations](./src/test/kotlin/lymiah/ioof/datediff)
