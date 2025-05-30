package fr.kazam.focus.commands;

import fr.kazam.focus.Main;
import fr.kazam.focus.utils.Lobby;
import fr.kazam.focus.utils.LobbyState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class JoinLobby implements CommandExecutor {

    Main plugin;
    public JoinLobby(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(args.length == 1){
                if(isInt(args[0])){
                    if(args[0].equalsIgnoreCase("-1")){
                        plugin.getLobbyPlayers().put(p,-1);
                    }
                    String oldLobbyId = String.valueOf(plugin.getLobbyPlayers().get(p));
                    for(Lobby b : plugin.getLobbies()){
                        if(b.getId().equals(args[0])){
                            if(b.getLobbyState().equals(LobbyState.WAITING) || b.getMaxPlayers()>b.getPlayers().size()){
                                if(!b.getPlayers().contains(p)){
                                    plugin.getLobbyPlayers().put(p,Integer.parseInt(args[0]));
                                    commandSender.sendMessage(ChatColor.GREEN +"Vous avez rejoins le Lobby " + b.getName());
                                    b.addPlayer(p);
                                    return false;
                                }else{
                                    commandSender.sendMessage(ChatColor.RED+"Vous êtes déjà dans le lobby " + b.getName());
                                }

                            }else{
                                commandSender.sendMessage(ChatColor.RED+"Impossible de rejoindre le lobby " + b.getName());
                                return false;
                            }
                        }
                        if(b.getId().equals(oldLobbyId)){
                            b.removePlayer(p);
                        }
                    }
                    commandSender.sendMessage(ChatColor.RED + "Aucun lobby n'a été trouvé");
                }else {
                    p.sendMessage(ChatColor.RED + "Veuillez spécifier les bons paramètres");
                }
            }else{
                commandSender.sendMessage(ChatColor.RED +"Un seul argument est attendu pour exécuter cette commande");
            }
        }else{
            commandSender.sendMessage(ChatColor.RED +"Cette commande doit être exécutée par un joueur");
        }
        return false;
    }


    private boolean isInt(String arg) {
        try{
            Integer.parseInt(arg);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

}
