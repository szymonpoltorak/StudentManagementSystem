package razepl.dev.sms;

import razepl.dev.sms.exceptions.auth.NullArgumentException;

import java.util.Arrays;
import java.util.Objects;

public class ArgumentValidator {
    private ArgumentValidator() {
    }

    public static void throwIfNull(Object... objects) {
        if (!ArgumentValidator.areArgumentsNullSafe(objects)) {
            throw new NullArgumentException("Encountered null arguments in method!");
        }
    }

    private static boolean areArgumentsNullSafe(Object... objects) {
        return Arrays.stream(objects).noneMatch(Objects::isNull);
    }
}
