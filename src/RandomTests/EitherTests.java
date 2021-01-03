package RandomTests;

import io.vavr.control.Either;

import java.util.Collections;

public class EitherTests {
    public static void main(String[] args){
    }

    private static Either<String, Integer> computeWithEither(int marks) {
        if (marks < 85) {
            return Either.left("Marks not acceptable");
        } else {
            return Either.right(marks);
        }
    }
}
