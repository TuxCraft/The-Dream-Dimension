package com.github.ubiquitousspice.dreamdimension.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityFireworkOverlayFX;
import net.minecraft.client.particle.EntityFireworkSparkFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityFirework extends EntityFX {
	private int field_92042_ax;
	private final EffectRenderer field_92040_ay;
	boolean field_92041_a;
	private int effects;
	private int type;
	private boolean flicker;
	private int[] colors2;
	private int[] colors;
	private boolean trail;
	
	public EntityFirework(World par1World, double par2, double par4,
			double par6, double par8, double par10, double par12,
			EffectRenderer par14EffectRenderer, int maxAge, int effects, boolean flicker, int type, int[] colors, int[] colors2, boolean trail) {
		super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
		this.motionX = par8;
		this.motionY = par10;
		this.motionZ = par12;
		this.field_92040_ay = par14EffectRenderer;
		this.particleMaxAge = 8;
		this.particleMaxAge = maxAge;
		this.type = type;
		this.effects = effects;
		this.flicker = flicker;
		this.colors = colors;
		this.colors2 = colors2;
		this.trail = trail;
		this.setInvisible(true);
		
		for (int i = 0; i < effects; ++i) {

			if (flicker) {
				this.field_92041_a = true;
				this.particleMaxAge += 15;
				break;
			}
		}
	}

	public void renderParticle(Tessellator par1Tessellator, float par2,
			float par3, float par4, float par5, float par6, float par7) {
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		boolean flag;

		if (this.field_92042_ax == 0) {
			flag = this.func_92037_i();
			boolean flag1 = false;

			if (effects >= 3) {
				flag1 = true;
			} else {
				for (int i = 0; i < effects; ++i) {

					if (this.type == 1) {
						flag1 = true;
						break;
					}
				}
			}

			String s = "fireworks." + (flag1 ? "largeBlast" : "blast")
					+ (flag ? "_far" : "");
			this.worldObj.playSound(this.posX, this.posY, this.posZ, s, 20.0F,
					0.95F + this.rand.nextFloat() * 0.1F, true);
		}

		if (this.field_92042_ax % 2 == 0
				&& this.field_92042_ax / 2 < this.effects) {
			int j = this.field_92042_ax / 2;
			
			byte b0 = (byte) this.type;
			boolean flag2 = this.trail;
			boolean flag3 = this.flicker;
			int[] aint = this.colors;
			int[] aint1 = this.colors2;

			if (b0 == 1) {
				this.func_92035_a(0.5D, 4, aint, aint1, flag2, flag3);
			} else if (b0 == 2) {
				this.func_92038_a(0.5D, new double[][] { { 0.0D, 1.0D },
						{ 0.3455D, 0.309D }, { 0.9511D, 0.309D },
						{ 0.3795918367346939D, -0.12653061224489795D },
						{ 0.6122448979591837D, -0.8040816326530612D },
						{ 0.0D, -0.35918367346938773D } }, aint, aint1, flag2,
						flag3, false);
			} else if (b0 == 3) {
				this.func_92038_a(0.5D, new double[][] { { 0.0D, 0.2D },
						{ 0.2D, 0.2D }, { 0.2D, 0.6D }, { 0.6D, 0.6D },
						{ 0.6D, 0.2D }, { 0.2D, 0.2D }, { 0.2D, 0.0D },
						{ 0.4D, 0.0D }, { 0.4D, -0.6D }, { 0.2D, -0.6D },
						{ 0.2D, -0.4D }, { 0.0D, -0.4D } }, aint, aint1, flag2,
						flag3, true);
			} else if (b0 == 4) {
				this.func_92036_a(aint, aint1, flag2, flag3);
			} else {
				this.func_92035_a(0.25D, 2, aint, aint1, flag2, flag3);
			}

			int k = aint[0];
			float f = (float) ((k & 16711680) >> 16) / 255.0F;
			float f1 = (float) ((k & 65280) >> 8) / 255.0F;
			float f2 = (float) ((k & 255) >> 0) / 255.0F;
			EntityFireworkOverlay entityfireworkoverlayfx = new EntityFireworkOverlay(
					this.worldObj, this.posX, this.posY, this.posZ);
			entityfireworkoverlayfx.setRBGColorF(f, f1, f2);
			this.field_92040_ay.addEffect(entityfireworkoverlayfx);
		}

		++this.field_92042_ax;

		if (this.field_92042_ax > this.particleMaxAge) {
			if (this.field_92041_a) {
				flag = this.func_92037_i();
				String s1 = "fireworks." + (flag ? "twinkle_far" : "twinkle");
				this.worldObj.playSound(this.posX, this.posY, this.posZ, s1,
						20.0F, 0.9F + this.rand.nextFloat() * 0.15F, true);
			}

			this.setDead();
		}
	}

	private boolean func_92037_i() {
		Minecraft minecraft = Minecraft.getMinecraft();
		return minecraft == null
				|| minecraft.renderViewEntity == null
				|| minecraft.renderViewEntity.getDistanceSq(this.posX,
						this.posY, this.posZ) >= 256.0D;
	}

	private void func_92034_a(double par1, double par3, double par5,
			double par7, double par9, double par11, int[] par13ArrayOfInteger,
			int[] par14ArrayOfInteger, boolean par15, boolean par16) {
		EntityFireworkSparkFX entityfireworksparkfx = new EntityFireworkSparkFX(
				this.worldObj, par1, par3, par5, par7, par9, par11,
				this.field_92040_ay);
		entityfireworksparkfx.func_92045_e(par15);
		entityfireworksparkfx.func_92043_f(par16);
		int i = this.rand.nextInt(par13ArrayOfInteger.length);
		entityfireworksparkfx.func_92044_a(par13ArrayOfInteger[i]);

		if (par14ArrayOfInteger != null && par14ArrayOfInteger.length > 0) {
			entityfireworksparkfx.func_92046_g(par14ArrayOfInteger[this.rand
					.nextInt(par14ArrayOfInteger.length)]);
		}

		this.field_92040_ay.addEffect(entityfireworksparkfx);
	}

	private void func_92035_a(double par1, int par3, int[] par4ArrayOfInteger,
			int[] par5ArrayOfInteger, boolean par6, boolean par7) {
		double d1 = this.posX;
		double d2 = this.posY;
		double d3 = this.posZ;

		for (int j = -par3; j <= par3; ++j) {
			for (int k = -par3; k <= par3; ++k) {
				for (int l = -par3; l <= par3; ++l) {
					double d4 = (double) k
							+ (this.rand.nextDouble() - this.rand.nextDouble())
							* 0.5D;
					double d5 = (double) j
							+ (this.rand.nextDouble() - this.rand.nextDouble())
							* 0.5D;
					double d6 = (double) l
							+ (this.rand.nextDouble() - this.rand.nextDouble())
							* 0.5D;
					double d7 = (double) MathHelper.sqrt_double(d4 * d4 + d5
							* d5 + d6 * d6)
							/ par1 + this.rand.nextGaussian() * 0.05D;
					this.func_92034_a(d1, d2, d3, d4 / d7, d5 / d7, d6 / d7,
							par4ArrayOfInteger, par5ArrayOfInteger, par6, par7);

					if (j != -par3 && j != par3 && k != -par3 && k != par3) {
						l += par3 * 2 - 1;
					}
				}
			}
		}
	}

	private void func_92038_a(double par1, double[][] par3ArrayOfDouble,
			int[] par4ArrayOfInteger, int[] par5ArrayOfInteger, boolean par6,
			boolean par7, boolean par8) {
		double d1 = par3ArrayOfDouble[0][0];
		double d2 = par3ArrayOfDouble[0][1];
		this.func_92034_a(this.posX, this.posY, this.posZ, d1 * par1,
				d2 * par1, 0.0D, par4ArrayOfInteger, par5ArrayOfInteger, par6,
				par7);
		float f = this.rand.nextFloat() * (float) Math.PI;
		double d3 = par8 ? 0.034D : 0.34D;

		for (int i = 0; i < 3; ++i) {
			double d4 = (double) f + (double) ((float) i * (float) Math.PI)
					* d3;
			double d5 = d1;
			double d6 = d2;

			for (int j = 1; j < par3ArrayOfDouble.length; ++j) {
				double d7 = par3ArrayOfDouble[j][0];
				double d8 = par3ArrayOfDouble[j][1];

				for (double d9 = 0.25D; d9 <= 1.0D; d9 += 0.25D) {
					double d10 = (d5 + (d7 - d5) * d9) * par1;
					double d11 = (d6 + (d8 - d6) * d9) * par1;
					double d12 = d10 * Math.sin(d4);
					d10 *= Math.cos(d4);

					for (double d13 = -1.0D; d13 <= 1.0D; d13 += 2.0D) {
						this.func_92034_a(this.posX, this.posY, this.posZ, d10
								* d13, d11, d12 * d13, par4ArrayOfInteger,
								par5ArrayOfInteger, par6, par7);
					}
				}

				d5 = d7;
				d6 = d8;
			}
		}
	}

	private void func_92036_a(int[] par1ArrayOfInteger,
			int[] par2ArrayOfInteger, boolean par3, boolean par4) {
		double d0 = this.rand.nextGaussian() * 0.05D;
		double d1 = this.rand.nextGaussian() * 0.05D;

		for (int i = 0; i < 70; ++i) {
			double d2 = this.motionX * 0.5D + this.rand.nextGaussian() * 0.15D
					+ d0;
			double d3 = this.motionZ * 0.5D + this.rand.nextGaussian() * 0.15D
					+ d1;
			double d4 = this.motionY * 0.5D + this.rand.nextDouble() * 0.5D;
			this.func_92034_a(this.posX, this.posY, this.posZ, d2, d4, d3,
					par1ArrayOfInteger, par2ArrayOfInteger, par3, par4);
		}
	}

	public int getFXLayer() {
		return 0;
	}
}