package net.sylviedevs.scp_outbreak.utils;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class ModParticleUtils {
    public static void summonPoofParticles(World world, double x, double y, double z) {
        for(int i = 0; i < 20; ++i) {
            double d = world.random.nextGaussian() * 0.02;
            double e = world.random.nextGaussian() * 0.02;
            double f = world.random.nextGaussian() * 0.02;

            world.addParticle(ParticleTypes.POOF, x - d * (double)10.0F, y - e * (double)10.0F, z - f * (double)10.0F, d, e, f);
        }
    }
}