package rainer.pawel.elevator.system;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;


import static com.tngtech.archunit.library.Architectures.onionArchitecture;

public class HexagonalArchitectureTest {

    public static final String BASE_PACKAGE = "rainer.pawel.elevator.system";

    private final JavaClasses classes = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
            .importPackages(BASE_PACKAGE);

    @Test
    void testHexagonalArchitecture() {
        onionArchitecture()
                .domainModels("..domain..")
                .domainServices("..domain..")
                .applicationServices("..application..")
                .adapter("rest", "..infrastructure..")
                .check(classes);
    }
}
