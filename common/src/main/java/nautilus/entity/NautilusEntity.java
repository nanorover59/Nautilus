package nautilus.entity;

import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class NautilusEntity extends WaterAnimal
{
	public NautilusEntity(EntityType<NautilusEntity> entityType, Level world)
	{
		super(entityType, world);
		this.moveControl = new NautilusMoveControl(this);
	}
	
	@Override
	protected void registerGoals()
	{
		this.goalSelector.addGoal(0, new PanicGoal(this, 2.0));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<Player>(this, Player.class, 16.0f, 1.6, 1.4, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(2, new SwimToRandomPlaceGoal(this));
	}
	
	public static AttributeSupplier.Builder createNautilusAttributes()
	{
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 24.0);
	}
	
	public int getArmsTickOffset()
	{
        return this.getId() * 3;
    }
	
	@Override
    protected PathNavigation createNavigation(Level world)
	{
        return new WaterBoundPathNavigation(this, world);
    }

    @Override
    public void travel(Vec3 movementInput)
    {
        if(this.isInWater())
        {
            this.moveRelative(0.01f, movementInput);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
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
            if(this.operation == MoveControl.Operation.MOVE_TO && !this.nautilus.getNavigation().isDone()) {
                float f = (float) (this.speedModifier * this.nautilus.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.nautilus.setSpeed(Mth.lerp(0.125f, this.nautilus.getSpeed(), f));
                double d = this.wantedX - this.nautilus.getX();
                double e = this.wantedY - this.nautilus.getY();
                double g = this.wantedZ - this.nautilus.getZ();

                if (e != 0.0) {
                    double h = Math.sqrt(d * d + e * e + g * g);
                    this.nautilus.setDeltaMovement(this.nautilus.getDeltaMovement().add(0.0, (double) this.nautilus.getSpeed() * (e / h) * 0.025, 0.0));
                }

                if (d != 0.0 || g != 0.0) {
                    float i = (float) (Mth.atan2(g, d) * 180.0f / (float) Math.PI) - 90.0F;
                    this.nautilus.setYRot(this.rotlerp(this.nautilus.getYRot(), i, 15.0f));
                    this.nautilus.yBodyRot = this.nautilus.getYRot();
                }
            }
            else
                this.nautilus.setSpeed(0.0f);
        }
    }

    static class SwimToRandomPlaceGoal extends RandomSwimmingGoal
    {
        public SwimToRandomPlaceGoal(NautilusEntity nautilus)
        {
            super(nautilus, 1.0, 20);
        }
        
        @Override
        @Nullable
        protected Vec3 getPosition()
        {
            Level level = this.mob.level();
            Vec3 find = BehaviorUtils.getRandomSwimmablePos(this.mob, 16, 16);

            if(!level.getFluidState(this.mob.blockPosition().above()).is(FluidTags.WATER)) {
                int offset = -this.mob.getRandom().nextIntBetweenInclusive(1, 6);
                find = find.add(0.0, offset, 0.0);
            }

            return find;
        }
    }
}