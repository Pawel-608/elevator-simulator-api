package rainer.pawel.elevator.system.domain.util;

import rainer.pawel.elevator.system.domain.exception.ElevatorSystemException;
import java.util.function.Supplier;

import lombok.NoArgsConstructor;


import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class EnsureUtils {

    public static void ensureThat(boolean condition, Supplier<? extends ElevatorSystemException> exceptionSupplier) {
        if (!condition) {
            throw exceptionSupplier.get();
        }
    }
}
