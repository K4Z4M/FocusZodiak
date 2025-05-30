package fr.kazam.focus.commands;

import fr.kazam.focus.Main;
import fr.kazam.focus.task.GameCycle;
import fr.kazam.focus.utils.Arena;
import fr.kazam.focus.utils.Lobby;
import fr.kazam.focus.utils.LobbyState;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StartGame implements CommandExecutor {

    private Main plugin;

    public StartGame(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        List<Arena> arenas = new ArrayList<>();
        if(args.length == 3){
            if(isInt(args[0]) && isInt(args[1]) && isInt(args[2])){
                int numberOfPoints = Integer.parseInt(args[1]);
                int gameTime = Integer.parseInt(args[2]);

                if(gameTime<20){
                    gameTime=20;
                }

                if(numberOfPoints<1){
                    numberOfPoints=1;
                }


                for(Lobby b : plugin.getLobbies()){
                    if(b.getId().equals(args[0]) && b.getLobbyState().equals(LobbyState.WAITING) && b.getPlayers().size()>1){//TODO vérifier que seul un joueur de son lobby puisse démarer son lobby??????
                        if(b.everyoneInATeam()){
                            for(Arena a : plugin.getArenas()){
                                if(a.getMaxPlayers()>=b.getPlayers().size()){
                                    arenas.add(a);
                                }
                            }
                            if(!arenas.isEmpty()){
                                b.setLobbyState(LobbyState.SELECTING);
                                b.resetZodiacs();
                                GameCycle gc = new GameCycle(plugin,b,arenas,numberOfPoints,gameTime);
                                gc.runTaskTimer(plugin,0,20);
                            }else{
                                commandSender.sendMessage(ChatColor.RED +"Aucune arène existante trouvée");
                            }

                        }

                    }else{
                        commandSender.sendMessage(ChatColor.RED +"Votre lobby n'est pas prêt à démarrer "+b.getLobbyState().toString()+" " + b.getPlayers().size());
                    }
                }
            }else{
                commandSender.sendMessage(ChatColor.RED +"Tous les arguments doivent être des entiers");
            }
        }else {
            commandSender.sendMessage(ChatColor.RED +"Trois arguments sont nécessaires pour exécuter cette commande");
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
