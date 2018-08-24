package hu.frontrider.minecrafttestkit;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

public class RegistrationHelper implements TestInstancePostProcessor {

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {

        Class<?> testClass = testInstance.getClass();

        Field[] fields = testClass.getFields();
        Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(Register.class))
                .peek(field -> {
                    Register annotation = field.getAnnotation(Register.class);
                    Class value = annotation.value();
                    IForgeRegistry registry = GameRegistry.findRegistry(value);

                    try {
                        field.setAccessible(true);
                        Object entry = field.get(testClass);
                        registry.register((IForgeRegistryEntry) entry);

                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("failed to register entry", e);
                    }
                });
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Register {
        Class value();
    }

}
