package nautilus.entity;

import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.brain.task.TargetUtil;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class NautilusEntity extends WaterCreatureEntity
{
	public NautilusEntity(EntityType<NautilusEntity> entityType, World world)
	{
		super(entityType, world);
		this.moveControl = new NautilusMoveControl(this);
	}
	
	@Override
	protected void initGoals()
	{
		super.initGoals();
		this.goalSelector.add(0, new EscapeDangerGoal(this, 2.0));
        this.goalSelector.add(2, new FleeEntityGoal<PlayerEntity>(this, PlayerEntity.class, 32.0f, 1.6, 1.4, EntityPredicates.EXCEPT_SPECTATOR::test));
        this.goalSelector.add(4, new SwimToRandomPlaceGoal(this));
	}
	
	public static DefaultAttributeContainer.Builder createNautilusAttributes()
	{
		return MobEntity.createMobAttributes().add(EntityAttributes.MAX_HEALTH, 24.0);
	}
	
	public int getArmsTickOffset()
	{
        return this.getId() * 3;
    }
	
	@Override
    protected EntityNavigation createNavigation(World world)
	{
        return new SwimNavigation(this, world);
    }

    @Override
    public void travel(Vec3d movementInput)
    {
        if(this.isTouchingWater())
        {
            this.updateVelocity(0.01f, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.9));
        }
        else
            super.travel(movementInput);
    }
	
	static class NautilusMoveControl extends MoveControl
	{
		private final NautilusEntity nautilus;

		NautilusMoveControl(NautilusEntity owner)
		{
			super(owner);
			this.nautilus = owner;
		}

		@Override
		public void tick()
		{
			//if(this.nautilus.isSubmergedIn(FluidTags.WATER))
			//	this.nautilus.setVelocity(this.nautilus.getVelocity().add(0.0, 0.005, 0.0));

			if(this.state == MoveControl.State.MOVE_TO && !this.nautilus.getNavigation().isIdle())
			{
				float f = (float) (this.speed * this.nautilus.getAttributeValue(EntityAttributes.MOVEMENT_SPEED));
				this.nautilus.setMovementSpeed(MathHelper.lerp(0.125f, this.nautilus.getMovementSpeed(), f));
				double d = this.targetX - this.nautilus.getX();
				double e = this.targetY - this.nautilus.getY();
				double g = this.targetZ - this.nautilus.getZ();
				
				if(e != 0.0)
				{
					double h = Math.sqrt(d * d + e * e + g * g);
					this.nautilus.setVelocity(this.nautilus.getVelocity().add(0.0, this.nautilus.getMovementSpeed() * (e / h) * 0.025, 0.0));
				}
				
				if(Math.sqrt(d * d + g * g) > 0.5)
				{
					float i = (float) (MathHelper.atan2(g, d) * 180.0f / (float) Math.PI) - 90.0F;
					this.nautilus.setYaw(this.wrapDegrees(this.nautilus.getYaw(), i, 15.0f));
					this.nautilus.bodyYaw = this.nautilus.getYaw();
				}
			}
			else
				this.nautilus.setMovementSpeed(0.0f);
		}
	}
	
	static class SwimToRandomPlaceGoal extends SwimAroundGoal
	{
		public SwimToRandomPlaceGoal(NautilusEntity nautilus)
		{
			super(nautilus, 1.0, 20);
		}
		
		@Nullable
		@Override
		protected Vec3d getWanderTarget()
		{
			Vec3d find = TargetUtil.find(this.mob, 16, 16);
			
			if(!this.mob.getWorld().getFluidState(BlockPos.ofFloored(find).up()).isOf(Fluids.WATER))
				find = find.add(0.0, -this.mob.getRandom().nextBetween(1, 6), 0.0);
			
			return find;
		}
	}
}