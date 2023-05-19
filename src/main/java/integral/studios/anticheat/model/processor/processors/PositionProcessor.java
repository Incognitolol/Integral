package integral.studios.anticheat.model.processor.processors;

import integral.studios.anticheat.model.PlayerData;
import integral.studios.anticheat.model.packet.IPacket;
import integral.studios.anticheat.model.packet.impl.v_1_8_8.inbound.PacketInFlying;
import integral.studios.anticheat.model.processor.IProcessor;
import integral.studios.anticheat.util.math.MathUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionProcessor implements IProcessor {

    // ------------------------------------------------

    //Position
    private double x, y, z, lastX, lastY, lastZ, deltaX, deltaY, deltaZ, deltaXZ, lastDeltaX, lastDeltaY, lastDeltaZ, lastDeltaXZ;

    // ------------------------------------------------

    //Rotation
    private float yaw, pitch, lastYaw, lastPitch, deltaYaw, deltaPitch, lastDeltaYaw, lastDeltaPitch;
    private boolean positionUpdate, rotationUpdate;

    // ------------------------------------------------

    PlayerData data;

    @Override
    public void update(IPacket packet) {

        if (!(packet instanceof PacketInFlying))
            return;

        PacketInFlying flying = (PacketInFlying) packet;

        this.positionUpdate = flying.isPos();
        this.rotationUpdate = flying.isRot();

        if (this.positionUpdate) {
            this.lastX = this.x;
            this.lastY = this.y;
            this.lastZ = this.z;

            this.x = flying.getX();
            this.y = flying.getY();
            this.z = flying.getZ();

            this.lastDeltaX = this.deltaX;
            this.lastDeltaY = this.deltaY;
            this.lastDeltaZ = this.deltaZ;

            this.deltaX = this.x - this.lastX;
            this.deltaY = this.y - this.lastY;
            this.deltaZ = this.z - this.lastZ;

            this.deltaXZ = MathUtil.hypot(deltaX, deltaZ);
            this.lastDeltaXZ = this.deltaXZ;
        }

        if (this.rotationUpdate) {
            this.lastYaw = this.yaw;
            this.lastPitch = this.pitch;

            this.yaw = flying.getYaw();
            this.pitch = flying.getPitch();

            this.lastDeltaYaw = deltaYaw;
            this.lastDeltaPitch = deltaPitch;

            this.deltaYaw = Math.abs(yaw - lastYaw);
            this.deltaPitch = Math.abs(pitch - lastPitch);
        }
    }
}
