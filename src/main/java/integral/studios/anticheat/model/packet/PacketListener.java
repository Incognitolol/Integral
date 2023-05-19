package integral.studios.anticheat.model.packet;

import integral.studios.anticheat.model.PlayerData;
import integral.studios.anticheat.service.CheckService;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import integral.studios.anticheat.model.packet.nms.PacketLookup;

@Getter
@RequiredArgsConstructor
public class PacketListener extends ChannelDuplexHandler {

    private final PlayerData playerData;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object nmsPacket) throws Exception {
        super.channelRead(ctx, nmsPacket);

        IPacket packet = PacketLookup.readNmsPacket(nmsPacket);

        if (packet == null)
            return;

        CheckService.EXECUTOR.submit(() -> this.playerData.getCheckManager().handleIncoming(packet));
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object nmsPacket, ChannelPromise promise) throws Exception {
        IPacket packet = PacketLookup.readNmsPacket(nmsPacket);

        if (packet == null) {
            super.write(ctx, nmsPacket, promise);
            return;
        }

        CheckService.EXECUTOR.submit(() -> this.playerData.getCheckManager().handleOutgoing(packet));

        super.write(ctx, nmsPacket, promise);
    }
}
