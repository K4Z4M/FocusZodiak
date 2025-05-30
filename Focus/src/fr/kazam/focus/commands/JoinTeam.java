package fr.kazam.focus.commands;

import fr.kazam.focus.Main;
import fr.kazam.focus.utils.Lobby;
import fr.kazam.focus.utils.ZodiacPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.awt.*;
import java.util.ArrayList;

public class JoinTeam implements CommandExecutor {

    Main plugin;
    private ArrayList<String> teams = new ArrayList<>();
    public JoinTeam(Main plugin){
        this.plugin = plugin;
        this.teams.add("white");
        this.teams.add("gray");
        this.teams.add("lightgray");
        this.teams.add("black");
        this.teams.add("red");
        this.teams.add("orange");
        this.teams.add("yellow");
        this.teams.add("lime");
        this.teams.add("green");
        this.teams.add("blue");
        this.teams.add("purple");
        this.teams.add("magenta");
        this.teams.add("cyan");
        this.teams.add("lightblue");
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            String lobbyId = String.valueOf(plugin.getLobbyPlayers().get(player));
            if(lobbyId.equals("-1")){
                commandSender.sendMessage(ChatColor.RED + "Il faut d'abord rejoindre un lobby");
            }else{
                if(args.length == 1){
                    if(teams.contains(args[0].toLowerCase())){
                        for(Lobby l : plugin.getLobbies()){
                            if(l.getId().equals(lobbyId)){
                                l.deletePlayerFromTeams(player);
                                switch(args[0].toLowerCase()){
                                    case "white":
                                        assignColoredTeam(player,ChatColor.WHITE,args[0].toLowerCase()+lobbyId,l);
                                        l.addWhitePlayer(player);
                                        break;
                                    case "gray":
                                        assignColoredTeam(player,ChatColor.DARK_GRAY,args[0].toLowerCase()+lobbyId,l);
                                        l.addGrayPlayer(player);
                                        break;
                                    case "lightgray":
                                        assignColoredTeam(player,ChatColor.GRAY,args[0].toLowerCase()+lobbyId,l);
                                        l.addLightGrayPlayer(player);
                                        break;
                                    case "black":
                                        assignColoredTeam(player,ChatColor.BLACK,args[0].toLowerCase()+lobbyId,l);
                                        l.addBlackPlayer(player);
                                        break;
                                    case "red":
                                        assignColoredTeam(player,ChatColor.RED,args[0].toLowerCase()+lobbyId,l);
                                        l.addRedPlayer(player);
                                        break;
                                    case "orange":
                                        assignColoredTeam(player,ChatColor.GOLD,args[0].toLowerCase()+lobbyId,l);
                                        l.addOrangePlayer(player);
                                        break;
                                    case "yellow":
                                        assignColoredTeam(player,ChatColor.YELLOW,args[0].toLowerCase()+lobbyId,l);
                                        l.addYellowPlayer(player);
                                        break;
                                    case "lime":
                                        assignColoredTeam(player,ChatColor.GREEN,args[0].toLowerCase()+lobbyId,l);
                                        l.addLimePlayer(player);
                                        break;
                                    case "green":
                                        assignColoredTeam(player,ChatColor.DARK_GREEN,args[0].toLowerCase()+lobbyId,l);
                                        l.addGreenPlayer(player);
                                        break;
                                    case "blue":
                                        assignColoredTeam(player,ChatColor.DARK_BLUE,args[0].toLowerCase()+lobbyId,l);
                                        l.addBluePlayer(player);
                                        break;
                                    case "purple":
                                        assignColoredTeam(player,ChatColor.DARK_PURPLE,args[0].toLowerCase()+lobbyId,l);
                                        l.addPurplePlayer(player);
                                        break;
                                    case "magenta":
                                        assignColoredTeam(player,ChatColor.LIGHT_PURPLE,args[0].toLowerCase()+lobbyId,l);
                                        l.addMagentaPlayer(player);
                                        break;
                                    case "cyan":
                                        assignColoredTeam(player,ChatColor.BLUE,args[0].toLowerCase()+lobbyId,l);
                                        l.addCyanPlayer(player);
                                        break;
                                    case "lightblue":
                                        assignColoredTeam(player,ChatColor.AQUA,args[0].toLowerCase()+lobbyId,l);
                                        l.addLightBluePlayer(player);
                                        break;

                                }
                            }
                        }
                    }else{
                        commandSender.sendMessage(ChatColor.RED +"Cette équipe n'existe pas");
                    }
                }else{
                    commandSender.sendMessage(ChatColor.RED +"Un seul argument est nécessaire pour exécuter cette commande");
                }

            }
        }else{
            commandSender.sendMessage(ChatColor.RED +"Cette commande doit être exécutée par un joueur");
        }
        return false;
    }

    public void assignColoredTeam(Player player, ChatColor color, String teamName, Lobby l) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        Team team = scoreboard.getTeam(teamName);
        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
        }


        // Set la couleur des pseudos
        team.setColor(color);

        // Forcer l'affichage des pseudos
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        player.sendMessage("Vous avez rejoins l'équipe "+color + teamName);
        // Ajoute le joueur à l'équipe
        team.addEntry(player.getName());
    }


}
