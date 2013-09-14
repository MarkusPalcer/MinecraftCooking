package mods.cooking.machines.tiles;

import buildcraft.api.core.Position;
import buildcraft.api.core.SafeTimeTracker;
import buildcraft.api.gates.IAction;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.core.TileBuildCraft;
import mods.cooking.fluids.Fluids;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class MilkerTile extends TileEntity implements IPowerReceptor {
	
	private PowerHandler powerProvider;
	
	private int storedMilk = 0;
	
	private SafeTimeTracker timer = null;
	
	public static final int MAX_ENERGY = 15000;
	
	public AxisAlignedBB searchArea;
	
	public MilkerTile() {
		powerProvider = new PowerHandler(this, PowerHandler.Type.MACHINE);
		powerProvider.configure(1, 100, 100, MAX_ENERGY);
		powerProvider.configurePowerPerdition(2, 1);
		timer = new SafeTimeTracker();
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		
		// Use up 10 energy (the same amount as the pump uses)
		if (this.powerProvider.useEnergy(10, 10, true) != 10) {
			return;
		}
		
		// We can only milk once so often
		if (timer.markTimeIfDelay(worldObj, 1000))
		{
			int cowsToMilk = worldObj.getEntitiesWithinAABB(EntityCow.class, this.getRenderBoundingBox().expand(5, 5, 2)).size();
			int milkers = Math.max(worldObj.getEntitiesWithinAABB(this.getClass(), this.getRenderBoundingBox().expand(10, 10, 4)).size(), 1);

			this.storedMilk = FluidContainerRegistry.BUCKET_VOLUME * cowsToMilk / milkers;
		}
		
		// Pump out the milk
		if (this.storedMilk > 0) {
			int amountToMove = Math.min(this.storedMilk, FluidContainerRegistry.BUCKET_VOLUME);
			
			for (int i = 0; i < 6; ++i) {
				Position p = new Position(xCoord, yCoord, zCoord, ForgeDirection.values()[i]);
				p.moveForwards(1);

				TileEntity tile = worldObj.getBlockTileEntity((int) p.x, (int) p.y, (int) p.z);
				
				if (tile instanceof IFluidHandler) {
					FluidStack liquid = new FluidStack(Fluids.Milk, amountToMove);
					int moved = ((IFluidHandler) tile).fill(p.orientation.getOpposite(), liquid, true);
					this.storedMilk -= moved;
					amountToMove -= moved;
					
					if (amountToMove <= 0) {
						break;
					}
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.storedMilk = nbt.getInteger("storedMilk");
		this.powerProvider.readFromNBT(nbt, "power");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("storedMilk", this.storedMilk);
		this.powerProvider.readFromNBT(nbt, "power");
	}
	
	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side) {
		return this.powerProvider.getPowerReceiver();
	}

	@Override
	public void doWork(PowerHandler workProvider) { }

	@Override
	public World getWorld() {
		return this.worldObj;
	}

	public int powerRequest(ForgeDirection from) {
		PowerHandler p = this.powerProvider;
		float needed = p.getMaxEnergyStored() - p.getEnergyStored();
		return (int) Math.ceil(Math.min(p.getMaxEnergyReceived(), needed));
	}
}
