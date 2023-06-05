package com.epam.ld.module2.testing.modes;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.junit.jupiter.api.extension.*;

public class TemporaryDirectoryExtension implements ParameterResolver, AfterAllCallback {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(Path.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(ExtensionContext.Namespace.GLOBAL).getOrComputeIfAbsent("root", key -> createTemporaryDirectory());
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        Path root = extensionContext.getStore(ExtensionContext.Namespace.GLOBAL).get("root", Path.class);
        if(root != null){
            deleteRecursively(root);
        }

    }

    private void deleteRecursively(Path root) throws IOException {
        Files.walkFileTree(root, new SimpleFileVisitor<>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("Deleting file " + file);
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("Deleting directory " + dir);
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }

    private Path createTemporaryDirectory() {
        try {
            return Files.createTempDirectory("junit");
        } catch (IOException e) {
            throw new ParameterResolutionException(e.getMessage());
        }
    }
}
