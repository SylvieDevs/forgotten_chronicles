package net.sylviedevs.scp_outbreak.item.food;

import net.sylviedevs.scp_outbreak.handlers.ModFoodComponents;
import net.sylviedevs.scp_outbreak.handlers.classes.SCPItem;

@SuppressWarnings("unused")
public class SCP_458_Item extends SCPItem {
    public SCP_458_Item(Settings settings) {
        super(settings
                .food(ModFoodComponents.PIZZA)
        );
    }
}
