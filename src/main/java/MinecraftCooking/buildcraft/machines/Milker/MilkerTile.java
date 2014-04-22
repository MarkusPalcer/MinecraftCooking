package MinecraftCooking.buildcraft.machines.Milker;

import MinecraftCooking.fluids.Fluids;
import MinecraftCooking.machines.Machines;
import buildcraft.api.core.Position;
import buildcraft.api.core.SafeTimeTracker;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

public class MilkerTile extends TileEntity implements IPowerReceptor {

	private PowerHandler powerProvider;

	private int storedMilk = 0;

    private int cowsToMilk = 0;

	private SafeTimeTracker timer = null;

    public AxisAlignedBB searchArea;

	public MilkerTile() {
		powerProvider = new PowerHandler(this, PowerHandler.Type.MACHINE);
		powerProvider.configure(1, 100, 100, Machines.MAX_ENERGY);
		powerProvider.configurePowerPerdition(0, 0);
		timer = new SafeTimeTracker();
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

        checkCows();


		// Use up 10 energy (the same amount as the pump uses)
		if (this.powerProvider.useEnergy(10, 10, true) == 10) {
			milkCow();
		}

        // Pump out the milk
		if (this.storedMilk > 0) {
			int amountToMove = Math.min(this.storedMilk, FluidContainerRegistry.BUCKET_VOLUME);

			for (int i = 0; i < 6; ++i) {
				Position p = new Position(xCoord, yCoord, zCoord, ForgeDirection.values()[i]);
				p.moveForwards(1);

				TileEntity tile = worldObj.getTileEntity((int) p.x, (int) p.y, (int) p.z);

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

    private void milkCow() {
        if (this.cowsToMilk > 0) {
            this.cowsToMilk--;
            this.storedMilk += FluidContainerRegistry.BUCKET_VOLUME;
        }
    }

    private void checkCows() {
        // We can only milk once so often
        if (timer.markTimeIfDelay(worldObj, 1000))
        {
            int cowsToMilk = worldObj.getEntitiesWithinAABB(EntityCow.class, this.getRenderBoundingBox().expand(5, 5, 2)).size();
            int milkers = Math.max(worldObj.getEntitiesWithinAABB(this.getClass(), this.getRenderBoundingBox().expand(10, 10, 4)).size(), 1);

            this.cowsToMilk = cowsToMilk / milkers;
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
		this.powerProvider.writeToNBT(nbt, "power");
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
		double needed = p.getMaxEnergyStored() - p.getEnergyStored();
		return (int) Math.ceil(Math.min(p.getMaxEnergyReceived(), needed));
	}
}
