package integral.studios.anticheat.model.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import integral.studios.anticheat.Integral;
import integral.studios.anticheat.model.check.AbstractCheck;
import integral.studios.anticheat.util.chat.CC;
import org.bukkit.entity.Player;

@CommandAlias("integral")
public class IntegralCommands extends BaseCommand {

    @Default
    @HelpCommand
    @Syntax("[page]")
    public void help(CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("alerts")
    public void alerts(Player player) {
        boolean alerts = Integral.get(AbstractCheck.class).toggleAlerts(player);
        player.sendMessage(CC.GOLD + CC.DOT + CC.YELLOW + " Successfully " + (alerts ? CC.GREEN + "enabled" : CC.RED + "disabled") + CC.YELLOW + " Integral alerts.");
    }
}
