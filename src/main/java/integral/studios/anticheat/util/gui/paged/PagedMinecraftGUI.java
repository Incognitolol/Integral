package integral.studios.anticheat.util.gui.paged;

import integral.studios.anticheat.util.gui.button.Button;
import lombok.Getter;
import lombok.Setter;
import integral.studios.anticheat.util.gui.MinecraftGUI;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public abstract class PagedMinecraftGUI extends MinecraftGUI {

    @Setter private int page = 1;
    protected int buttonsPerPage = 21;

    public PagedMinecraftGUI(String title, Player player) {
        super(title, 45, player);
    }

    @Override
    protected void tick(Player player, long lastTickTime) {
        int i = page == 1 ? 0 : page * buttonsPerPage;

        buttons.putAll(getTopGlobalButtons(player));
        buttons.putAll(getBottomGlobalButtons(player));

        List<Button> pagedButtons = new ArrayList<>(100);
        pagedButtons.addAll((getPagedButtons(player)));

        int index = 10;
        for (int get = i; get <= get + buttonsPerPage; get++) {
            if (get >= getPagedButtons(player).size()) break;
            if (getPagedButtons(player).get(get) == null) break;;

            buttons.put(index++, getPagedButtons(player).get(get));

            if (index == 16 || index == 25) index += 3;
        }

        buttons.put(18, new PagedButton(-1, this));
        buttons.put(26, new PagedButton(1, this));
    }

    public int getPages(Player player) {
        return (int) Math.ceil(getPagedButtons(player).size() - 1 / (double) buttonsPerPage);
    }

    public abstract List<Button> getPagedButtons(Player player);
    public Map<Integer, Button> getTopGlobalButtons(Player player){ return new HashMap<>(); }
    public Map<Integer, Button> getBottomGlobalButtons(Player player){ return new HashMap<>(); }

}
