package net.sylviedevs.scp_outbreak.item.food;

import net.sylviedevs.scp_outbreak.handlers.ModFoodComponents;
import net.sylviedevs.scp_outbreak.handlers.classes.SCPItem;

public class SCP_458_Item extends SCPItem {
    public SCP_458_Item(Settings settings) {
        super(new Settings()
                .food(ModFoodComponents.PIZZA)
        );
    }
}
