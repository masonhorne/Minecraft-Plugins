package ranks.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckRankCommand extends RankCommand {

    public void run( CommandSender sender, String[] args) {
        if(args.length < 2){
            sender.sendMessage( ChatColor.YELLOW + "You didn't enter a permission" );
        }
        Player player = Bukkit.getServer().getPlayer( args[0] );
        if(player == null){
            sender.sendMessage( ChatColor.YELLOW + "Could not find player.");
            return;
        }
        if(player.hasPermission( args[1] )) {
            sender.sendMessage( ChatColor.YELLOW  + "Player " + player.getName() + " has permission " + args[1]);

        } else {
            sender.sendMessage( ChatColor.YELLOW  + "Player " + player.getName() + " does not have permission " + args[1]);
        }
    }

    public CheckRankCommand() {
        super("check", "<player> <perm>");
    }
}
