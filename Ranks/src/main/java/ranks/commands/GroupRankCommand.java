package ranks.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import ranks.managers.PermissionsManager;

public class GroupRankCommand extends RankCommand {

    public GroupRankCommand () {
        super("group", "[<name>] [<add | remove | addpermission | removepermission | color> <user | perm | color>]");
    }

    @Override
    public void run ( CommandSender sender, String[] args ) {
        if(args.length == 0){
            sender.sendMessage( ChatColor.RED + "You didn't enter a group.");
            return;
        }
        String group = args[0];
        if(args.length < 2){
            // Handle permission information
            if(group.equalsIgnoreCase( "add" )) sender.sendMessage( ChatColor.YELLOW + "Please provide a group and player to add to the group." );
            else if(group.equalsIgnoreCase( "remove" )) sender.sendMessage( ChatColor.YELLOW + "Please provide a group and player to remove from the group." );
            else if(group.equalsIgnoreCase( "addpermission" )) sender.sendMessage( ChatColor.YELLOW + "Please provide a group and a permission to add to the group." );
            else if(group.equalsIgnoreCase( "removepermission" )) sender.sendMessage( ChatColor.YELLOW + "Please provide a group and a permission to remove from the group." );
            else if(group.equalsIgnoreCase( "color" )) sender.sendMessage(
                        ChatColor.YELLOW + "Available colors: " + ChatColor.BLACK + "&0 " + ChatColor.DARK_BLUE + "&1 " + ChatColor.DARK_GREEN + "&2 " + ChatColor.DARK_GREEN + "&3 " + ChatColor.DARK_AQUA + "&4 " + ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " + ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " + ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c " + ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e " + ChatColor.WHITE + "&f " + ChatColor.MAGIC + "&g " );
            else if(PermissionsManager.getInstance().getGroup( group ) != null) {
                // Display permissions for the group
                if ( PermissionsManager.getInstance().getGroup( group ).getPermissions().size() == 0 ) sender.sendMessage( ChatColor.YELLOW + "No permissions for " + group + "." );
                else {
                    for ( String permission : PermissionsManager.getInstance().getGroup( group ).getPermissions() ) {
                        sender.sendMessage( ChatColor.YELLOW + permission );
                    }

                }
            } else {
                // Create the group that was provided
                PermissionsManager.getInstance().createGroup(group);
                sender.sendMessage(ChatColor.YELLOW + "Created group.");
            }
        } else {
            // Add a player to a group
            if(args[1].equalsIgnoreCase("add")){
                PermissionsManager.getInstance().addGroup( args[2], group );
                sender.sendMessage(ChatColor.YELLOW + "Added player " + args[2] + " to group " + group + ".");
            }
            // Remove a player from a group
            if(args[1].equalsIgnoreCase("remove")){
                PermissionsManager.getInstance().removeGroup( args[2], group );
                sender.sendMessage(ChatColor.YELLOW + "Removed player " + args[2] + " from group " + group + ".");
            }
            // Remove a permission from a group
            if(args[1].equalsIgnoreCase("removepermission")){
                PermissionsManager.getInstance().getGroup( group ).removePermission( args[2] );
                sender.sendMessage(ChatColor.YELLOW + "Removed " + args[2] + " from group " + group + ".");
            }
            // Add a permission to a group
            if(args[1].equalsIgnoreCase("addpermission")){
                PermissionsManager.getInstance().getGroup( group ).addPermission( args[2] );
                sender.sendMessage(ChatColor.YELLOW + "Granted " + args[2] + " to group " + group + ".");
            }
            // Add a color for the group
            if(args[1].equalsIgnoreCase("color")){
                PermissionsManager.getInstance().getGroup( group ).setColor( args[2] );
                sender.sendMessage(ChatColor.YELLOW + "Assigned color " + args[2] + " to group " + group + ".");
            }
        }
    }
}
