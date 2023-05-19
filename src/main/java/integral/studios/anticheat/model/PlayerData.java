package integral.studios.anticheat.model;

import lombok.Getter;
import integral.studios.anticheat.Integral;
import integral.studios.anticheat.model.check.AbstractCheck;
import integral.studios.anticheat.service.CheckService;
import integral.studios.anticheat.model.processor.processors.PositionProcessor;
import org.atteo.classindex.ClassIndex;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Getter
public class PlayerData {

    private final Player player;

    private final long joinTime = System.currentTimeMillis();
    private final Map<Class<? extends AbstractCheck>, AbstractCheck> checks = new HashMap<>();

    private final PositionProcessor positionProcessor = new PositionProcessor();
    private final CheckService checkManager = new CheckService(this);

    public PlayerData(Player player) {
        this.player = player;

        for (Class<? extends AbstractCheck> c : ClassIndex.getSubclasses(AbstractCheck.class, Integral.class.getClassLoader())) {
            try {
                checks.put(c, c.getDeclaredConstructor(PlayerData.class).newInstance(this));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
}
