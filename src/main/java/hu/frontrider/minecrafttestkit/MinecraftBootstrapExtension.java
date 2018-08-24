package hu.frontrider.minecrafttestkit;

import net.minecraft.init.Bootstrap;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

public class MinecraftBootstrapExtension implements TestInstancePostProcessor {

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        Class<?> aClass = testInstance.getClass();

    }
}
