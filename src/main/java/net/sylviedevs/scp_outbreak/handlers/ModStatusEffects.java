package net.sylviedevs.scp_outbreak.handlers;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sylviedevs.scp_outbreak.SCPOutbreak;

import java.util.ArrayList;
import java.util.List;

public class ModStatusEffects {
    public static List<StatusEffect> registeredStatus = new ArrayList<>();
    public static String[] statusIdentifiers = {
            "panacea"
    };

    private static StatusEffect registerStatus(String name, StatusEffect statusEffect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(SCPOutbreak.MOD_ID, name), statusEffect);
    }

    public static StatusEffect retrieveStatusFromIdentifier(String statusIdentifier) {
        for (int thisCounter = 0; thisCounter < statusIdentifiers.length; thisCounter++) {
            if (statusIdentifier.equals( statusIdentifiers[thisCounter] )) { return registeredStatus.get(thisCounter); }
        }

        return StatusEffects.BLINDNESS;
    }

    public static void registerModStatusEffects() throws ClassNotFoundException {
        SCPOutbreak.LOGGER.info("Registering Status Effects...");

        for (String thisStatus : statusIdentifiers) {
            try {
                Class<?> retrieveClass = Class.forName("net.sylviedevs.scp_outbreak.status." + thisStatus.toUpperCase() + "_Status");

                if (!StatusEffect.class.isAssignableFrom(retrieveClass)) {
                    throw new IllegalArgumentException("Class " + thisStatus + " is not a subtype of Item");
                }

                Class<? extends StatusEffect> typedClass = retrieveClass.asSubclass(StatusEffect.class);

                StatusEffect thisStatusClass = typedClass.getDeclaredConstructor().newInstance();
                StatusEffect thisRegisteredStatus = registerStatus(thisStatus, thisStatusClass);

                registeredStatus.add( thisRegisteredStatus );
            } catch (ClassNotFoundException e) {
                throw new ClassNotFoundException("Failed to create status: " + thisStatus, e);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create status: " + thisStatus, e);
            }
        }
    }
}
