package io.qameta.atlas.github.rules;

import org.junit.rules.ExternalResource;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.nio.file.Paths;

import static java.nio.file.Files.exists;

public class UnZipResource extends ExternalResource {

    private static final String PATH_TO_ZIP_APPS = "src/test/resources/mobile-applications.zip";
    private static final String IOS = "src/test/resources/mobile-applications/Wikipedia.app";
    private static final String ANDROID = "src/test/resources/mobile-applications/org.wikipedia.apk";

    @Override
    protected void before() {
        if (exists(Paths.get(IOS)) && exists(Paths.get(ANDROID))) {
            return;
        }
        ZipUtil.unpack(new File(PATH_TO_ZIP_APPS), new File("src/test/resources/"));
    }
}
