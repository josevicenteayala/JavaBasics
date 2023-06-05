package com.epam.metadata;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import java.lang.reflect.AnnotatedElement;
import java.util.stream.Stream;

public class MetaAnnotationProcessor implements TestTemplateInvocationContextProvider {
    @Override
    public boolean supportsTestTemplate(ExtensionContext extensionContext) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext extensionContext) {
        return extensionContext.getTestMethod()
                .filter(method -> method.isAnnotationPresent(UnitTest.class))
                .map(this::toTestTemplateInvocationContext)
                .stream();
    }

    private TestTemplateInvocationContext toTestTemplateInvocationContext(AnnotatedElement annotatedElement) {
        return new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return annotatedElement.toString();
            }
        };
    }
}
