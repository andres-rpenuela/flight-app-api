package com.tokioschool.flightapp.store.configuration.ut;

import com.tokioschool.flightapp.store.configuration.StoreConfigurationProperties;
import com.tokioschool.flightapp.store.util.FileUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.Path;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StoreConfigurationTest {
/*
    @Autowired
    StoreConfigurationProperties storeConfigurationProperties;

    @Test
    @Order(1)
    void givenPathRelative_whenGetResourcePathFromRelativePath_returnOk() throws IOException {
        final Path path = storeConfigurationProperties.buildResourcePathFromRelativePathGivenNameResource();

        FileUtil.createWorkIfNotExists(path);

        Assertions.assertThat(path)
                .exists()
                .isDirectory();

        FileUtil.deleteWorkIfNotExists(path);
    }

    @Test
    @Order(2)
    void givenPathRelative_whenGetResourcePathFromAbsolute_returnOk() throws IOException {
        final Path path = storeConfigurationProperties.buildResourcePathFromAbsolutePath();

        FileUtil.createWorkIfNotExists(path);

        Assertions.assertThat(path)
                .exists()
                .isDirectory();

        FileUtil.deleteWorkIfNotExists(path);
    }

    @Test
    @Order(3)
    void givenResourceName_whenGetResourcePathFromRelativePath_returnOk() throws IOException {
        final Path path = storeConfigurationProperties.getdResourcePathFromRelativePathGivenNameResource("file.txt");

        FileUtil.createWorkIfNotExists(path);

        Assertions.assertThat(path)
                .exists()
                .isDirectory();

        FileUtil.deleteWorkIfNotExists(path);
    }
 */
}
