package net.sylviedevs.forgotten_chronicles.client.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.sylviedevs.forgotten_chronicles.ForgottenChronicles;
import net.sylviedevs.forgotten_chronicles.entities.Hero_Entity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Hero_Renderer extends GeoEntityRenderer<Hero_Entity> {
    public Hero_Renderer(EntityRendererFactory.Context context) {
        super(context, new Hero_Model());
    }

    @Override
    public Identifier getTextureLocation(Hero_Entity animatable) {
        return new Identifier(ForgottenChronicles.MOD_ID, "textures/entity/hero.png");
    }

    @Override
    public void render(Hero_Entity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        var player = MinecraftClient.getInstance().player;

        if (player != null) {
            Vec3d entityPos = entity.getLerpedPos(partialTick);
            Vec3d playerPos = player.getLerpedPos(partialTick);

            double dx = playerPos.x - entityPos.x;
            double dy = playerPos.y - (entityPos.y + entity.getStandingEyeHeight());
            double dz = playerPos.z - entityPos.z;

            double yaw = Math.toDegrees(Math.atan2(dz, dx)) - 90;

            double distance = Math.sqrt(dx * dx + dz * dz);
            double pitch = -Math.toDegrees(Math.atan2(dy, distance));

            poseStack.push();
            poseStack.translate(0, entity.getStandingEyeHeight(), 0);
            poseStack.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_Y.rotationDegrees((float) (yaw - entity.getYaw())));
            poseStack.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_X.rotationDegrees((float) pitch));
            poseStack.translate(0, -entity.getStandingEyeHeight(), 0);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);

        if (player != null) {
            poseStack.pop();
        }
    }
}
