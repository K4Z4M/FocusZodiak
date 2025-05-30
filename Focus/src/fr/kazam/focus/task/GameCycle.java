package fr.kazam.focus.task;

import fr.kazam.focus.Main;
import fr.kazam.focus.utils.*;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.DecoratedPot;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.*;

public class GameCycle extends BukkitRunnable {
    int timer;
    int gameTime;
    Main plugin;
    Lobby lobby;
    List<Arena> arenas;
    int numberOfPoints;
    boolean selectingZodiacPhase;
    int r = 0;
    ArrayList<Material> potterys;
    int roundNumber;

    public GameCycle(Main plugin, Lobby b, List<Arena> a, int nop, int gt) {
        this.timer=30;
        this.gameTime = gt;
        this.plugin = plugin;
        this.lobby = b;
        this.arenas = a;
        this.selectingZodiacPhase=true;
        this.numberOfPoints = nop;
        this.potterys = getPotterys();
        this.roundNumber = 0;
    }



    @Override
    public void run() {
        for(Player p : lobby.getPlayers()) {
            p.setLevel(timer);
        }
        if(selectingZodiacPhase) {
            if(timer==30){
                Bukkit.getWorld("world").setGameRule(GameRule.KEEP_INVENTORY, true);
                Bukkit.getWorld("world").setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);

                ItemStack netherStar = new ItemStack(Material.NETHER_STAR);
                ItemMeta meta = netherStar.getItemMeta();
                meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Étoile du Zodiac");
                meta.setLore(Arrays.asList("Utilisez l'étoile pour choisir votre classe"));
                netherStar.setItemMeta(meta);
                r = (int) (Math.random()*arenas.size());
                Collections.shuffle(arenas.get(r).getShopLocations());
                Collections.shuffle(arenas.get(r).getSpawnLocations());

                for(int i = 0;i<lobby.getPlayers().size();i++) {
                    Player p = lobby.getPlayers().get(i);
                    AttributeInstance attr = p.getAttribute(Attribute.KNOCKBACK_RESISTANCE);
                    if(attr!=null){
                        attr.setBaseValue(0);
                    }
                    lobby.getZodiacPlayers().add(new ZodiacPlayer(p));
                    p.teleport(arenas.get(r).getShopLocations().get(i));
                    p.setBedSpawnLocation(p.getLocation(), true);
                    p.sendTitle(ChatColor.LIGHT_PURPLE + "Bienvenu, "+p.getDisplayName(), ChatColor.MAGIC+p.getDisplayName(), 20, 60, 20);
                    p.getInventory().clear();
                    clearPotionEffect(p);
                    p.setHealth(20);
                    p.getInventory().addItem(netherStar);
                    p.setGameMode(GameMode.ADVENTURE);
                    addShopEffect(p);

                }
                assignTeamColor();
                setupScoreboard(lobby);
            }
            if(timer<1){
                selectingZodiacPhase = false;
                for (World world : Bukkit.getWorlds()) {
                    for (Entity entity : world.getEntities()) {
                        if (entity instanceof LivingEntity e) {
                            if(e instanceof Wolf || e instanceof Zombie){
                                e.setHealth(0);
                            }
                        }
                    }
                }
                lobby.setLobbyState(LobbyState.PLAYING);
                for(Location l : arenas.get(r).getGloryPointsLocations().keySet()){
                    Collections.shuffle(potterys);
                    if(l!=null){
                        Bukkit.getWorld("world").getBlockAt(l).setType(Material.AIR);
                    }
                    int chance = (int) (Math.random()*100);
                    if(arenas.get(r).getGloryPointsLocations().get(l)>=chance){
                        Block block = Bukkit.getWorld("world").getBlockAt(l);
                        block.setType(Material.DECORATED_POT);
                        BlockState state = block.getState();
                        if (state instanceof DecoratedPot pot) {
                            pot.setSherd(DecoratedPot.Side.FRONT, potterys.get(0));
                            pot.setSherd(DecoratedPot.Side.BACK, potterys.get(1));
                            pot.setSherd(DecoratedPot.Side.LEFT, potterys.get(2));
                            pot.setSherd(DecoratedPot.Side.RIGHT, potterys.get(3));

                            pot.update(true);
                        }
                    }
                }
                for(int i = 0;i<lobby.getPlayers().size();i++) {
                    Player p = lobby.getPlayers().get(i);
                    p.setGameMode(GameMode.ADVENTURE);
                    p.teleport(arenas.get(r).getSpawnLocations().get(i));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,20,10,false,false));
                    p.setFoodLevel(18);
                    if(arenas.get(r).isHasNightVision()){
                        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,1200,1,false,false));
                    }
                    p.getWorld().spawnParticle(Particle.SCULK_SOUL, p.getLocation(), 500, 0.5, 1, 0.5, 0.06);
                    p.getWorld().spawnParticle(Particle.SONIC_BOOM, p.getLocation(), 500, 0.5, 1, 0.5, 0.06);
                    p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1.0f, 1.0f);
                    arenas.get(r).useArena();
                    Bukkit.getWorld("world").setTime(gameTime);
                    if(!isNotIn(p)){
                        Zodiac zodiac = new Zodiac();
                        ArrayList<Zodiac> zodiacs = zodiac.getZodiac();
                        for(ZodiacPlayer z : lobby.getZodiacPlayers()){
                            if(z.getPlayer().equals(p)){
                                int random = (int) (Math.random()*zodiacs.size());
                                z.setZodiac(zodiacs.get(random));
                            }
                        }
                    }
                    giveAll(p, lobby.getZodiacPlayers(),5);
                }
                timer=gameTime;
            }
        }else{
            if(lobby.getLobbyState() == LobbyState.PLAYING){
                if(getWinner()!=null){
                    TeamColor winner = getWinner();
                    lobby.setLobbyState(LobbyState.WAITING);
                    for(ZodiacPlayer p : lobby.getZodiacPlayers()){
                        p.getPlayer().sendMessage("L'"+getStringFrom(winner)+" remporte la partie");
                        p.resetZodiac();
                    }
                    this.cancel();
                    return;

                }
                if(lobby.getPlayers().size()<2){
                    Bukkit.getConsoleSender().sendMessage("Il n'y a plus assez de joueur pour continuer la partie");
                    lobby.setLobbyState(LobbyState.WAITING);
                    for(ZodiacPlayer p : lobby.getZodiacPlayers()){
                        p.resetZodiac();
                    }
                    this.cancel();
                    return;
                }
                int countAlive = 0;
                for(ZodiacPlayer p : lobby.getZodiacPlayers()){
                    p.getPlayer().setLevel(timer);
                    if(p.getPlayer().getGameMode() == GameMode.ADVENTURE){
                        countAlive++;
                    }
                    if(!p.getZodiac().getName().equals("Balance") && !p.getZodiac().getName().equals("Coq")){
                        int current = p.getLevelTimer()[p.getZodiacLevel()];
                        p.getPlayer().setExp((Math.max(0f, (current - p.getActiveTimer()) / (float) current)));
                        if(p.getActiveTimer()>0){
                            p.setActiveTimer(p.getActiveTimer()-1);
                        }
                    }


                    if(p.getZodiac().getName().equals("Sagitaire") && p.isActiveIsUsed() && p.getActiveTimer() == p.getLevelTimer()[p.getZodiacLevel()]){
                        p.setActiveIsUsed(false);
                        int place = getPlaceOfWeapon(p.getPlayer());
                        ItemStack arcSag = p.getPlayer().getInventory().getItem(place);
                        ItemMeta meta = arcSag.getItemMeta();
                        if (meta != null) {
                            int level = arcSag.getEnchantmentLevel(Enchantment.POWER)-2;
                            arcSag.removeEnchantment(Enchantment.POWER);
                            if(level>0){
                                arcSag.addUnsafeEnchantment(Enchantment.POWER, level);
                            }
                        }
                    }

                    if(p.getZodiac().getName().equals("Vierge") && p.isActiveIsUsed() && p.getActiveTimer() == p.getLevelTimer()[p.getZodiacLevel()]){
                        p.setActiveIsUsed(false);
                        p.getPlayer().getInventory().setHelmet(lobby.getHelmetVierge().get(p.getPlayer()));
                        p.getPlayer().getInventory().setChestplate(lobby.getChestplateVierge().get(p.getPlayer()));
                        p.getPlayer().getInventory().setLeggings(lobby.getLeggingsVierge().get(p.getPlayer()));
                        p.getPlayer().getInventory().setBoots(lobby.getBootsVierge().get(p.getPlayer()));
                        p.getPlayer().getWorld().spawnParticle(Particle.DUST_PLUME, p.getPlayer().getLocation().add(0,1,0), 500, 0.4, 0.8, 0.4, 0.02);
                    }


                    if(p.getZodiac().getName().equals("Chien")){
                        switch(p.getZodiacLevel()){
                            case 1,2:
                                if(p.getPlayer().getHealth()<2){
                                    p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH,40,0,false,false));
                                    p.getPlayer().getWorld().spawnParticle(Particle.ANGRY_VILLAGER, p.getPlayer().getLocation().add(0,2,0), 5, 0.6, 0.5, 0.6, 2);

                                }
                                break;
                            case 3,4:
                                if(p.getPlayer().getHealth()<4){
                                    p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH,40,0,false,false));
                                    p.getPlayer().getWorld().spawnParticle(Particle.ANGRY_VILLAGER, p.getPlayer().getLocation().add(0,2,0), 5, 0.6, 0.5, 0.6, 2);
                                }
                                break;
                            case 5,6:
                                if(p.getPlayer().getHealth()<6){
                                    p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH,40,0,false,false));
                                    p.getPlayer().getWorld().spawnParticle(Particle.ANGRY_VILLAGER, p.getPlayer().getLocation().add(0,2,0), 5, 0.6, 0.5, 0.6, 2);
                                }
                                break;
                            case 7:
                                if(p.getPlayer().getHealth()<8){
                                    p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH,40,0,false,false));
                                    p.getPlayer().getWorld().spawnParticle(Particle.ANGRY_VILLAGER, p.getPlayer().getLocation().add(0,2,0), 5, 0.6, 0.5, 0.6, 2);
                                }
                                break;

                        }
                    }


                    if(p.getZodiac().getName().equals("Taureau")){
                        List<Entity> nearby = p.getPlayer().getNearbyEntities(5, 5, 5);
                        int enemyCount = 0;
                        for (Entity e : nearby) {
                            if (e instanceof Player) {
                                Player other = (Player) e;
                                for(ZodiacPlayer z : lobby.getZodiacPlayers()){
                                    if(z.getPlayer().equals(other)){
                                        if(z.getTeamColor()!=p.getTeamColor()){
                                            enemyCount++;
                                        }
                                    }
                                }
                            }
                        }
                        if((p.getZodiacLevel()==1 || p.getZodiacLevel() ==2) && enemyCount>3){//TODO ajouter effet
                            p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH,80,0,false,false));
                            p.getPlayer().getWorld().spawnParticle(Particle.ANGRY_VILLAGER, p.getPlayer().getLocation().add(0,2,0), 5, 0.6, 0.5, 0.6, 2);
                        }
                        if((p.getZodiacLevel()==3 || p.getZodiacLevel() ==4) && enemyCount>2){
                            p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH,80,0,false,false));
                            p.getPlayer().getWorld().spawnParticle(Particle.ANGRY_VILLAGER, p.getPlayer().getLocation().add(0,2,0), 5, 0.6, 0.5, 0.6, 2);
                        }
                        if((p.getZodiacLevel()==5|| p.getZodiacLevel() ==6) && enemyCount>1){
                            p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH,80,0,false,false));
                            p.getPlayer().getWorld().spawnParticle(Particle.ANGRY_VILLAGER, p.getPlayer().getLocation().add(0,2,0), 5, 0.6, 0.5, 0.6, 2);
                        }
                        if((p.getZodiacLevel()==7) && enemyCount>0){
                            p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH,80,0,false,false));
                            p.getPlayer().getWorld().spawnParticle(Particle.ANGRY_VILLAGER, p.getPlayer().getLocation().add(0,2,0), 5, 0.6, 0.5, 0.6, 2);
                        }

                    }
                }
                if(timer<1 || countAlive <2){
                    roundNumber++;
                    r = (int) (Math.random()*arenas.size());
                    Collections.shuffle(arenas.get(r).getShopLocations());
                    Collections.shuffle(arenas.get(r).getSpawnLocations());
                    for(int i = 0;i<lobby.getPlayers().size();i++) {
                        Player p = lobby.getPlayers().get(i);
                        p.getInventory().addItem(plugin.giveGloryPoints(1+(roundNumber/2)));
                        if(p.getGameMode() == GameMode.ADVENTURE){
                            p.getInventory().addItem(plugin.giveGloryPoints(1));
                        }
                        p.teleport(arenas.get(r).getShopLocations().get(i));
                        p.setBedSpawnLocation(p.getLocation(), true);
                        p.getActivePotionEffects().clear();
                        if(arenas.get(r).isHasNightVision()){
                            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,300,1,false,false));
                        }
                        p.sendTitle(ChatColor.LIGHT_PURPLE + arenas.get(r).getName(), ChatColor.MAGIC+p.getDisplayName(), 5, 30, 5);
                    }
                    timer=15;
                    lobby.setLobbyState(LobbyState.SELECTING);

                }
            }else{
                for(Player p : lobby.getPlayers()){
                    p.setLevel(timer);
                    p.setHealth(20);
                    p.setGameMode(GameMode.ADVENTURE);
                    if(timer>13){
                        addShopEffect(p);
                    }
                }
                if(timer<1){
                    lobby.setLobbyState(LobbyState.PLAYING);
                    endOfShop();
                }
            }


        }

        timer--;

    }

    private void addShopEffect(Player p) {
        p.getWorld().spawnParticle(Particle.CLOUD, p.getLocation(), 250, 0.5, 1, 0.5, 0.4);
        p.getWorld().spawnParticle(Particle.REVERSE_PORTAL, p.getLocation(), 500, 0.5, 1, 0.5, 1);
        p.getWorld().spawnParticle(Particle.ENCHANT, p.getLocation().add(0,1,0), 500, 0.4, 0.8, 0.4, 0.01);
        p.getWorld().spawnParticle(Particle.WITCH, p.getLocation(), 300, 0.5, 0.5, 0.5, 1);
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1.0f, 1.0f);
    }

    public void setupScoreboard(Lobby l) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("points", "dummy", ChatColor.LIGHT_PURPLE + "Points de Victoire");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);



        for (ArrayList<Player> team : l.getTeams()) {//TO TEST
            TeamColor t = TeamColor.WHITE;
            if(!team.isEmpty()){
                int scoreNum = 0;
                for(ZodiacPlayer z : l.getZodiacPlayers()){
                    if(team.contains(z.getPlayer())){
                        t = z.getTeamColor();
                        scoreNum +=z.getVictoryPoints();
                    }
                }
                Score score = objective.getScore(getStringFrom(t));
                score.setScore(scoreNum);
            }
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(board);
        }
    }

    public TeamColor getWinner(){
        TeamColor winner = null;
        for (ArrayList<Player> team : lobby.getTeams()) {//TO TEST
            if(!team.isEmpty()){
                int scoreNum = 0;
                TeamColor c = TeamColor.WHITE;
                for(ZodiacPlayer z : lobby.getZodiacPlayers()){
                    if(team.contains(z.getPlayer())){
                        c = z.getTeamColor();
                        scoreNum +=z.getVictoryPoints();
                    }
                }
                if(scoreNum >=numberOfPoints){
                    winner = c;
                }
            }
        }

        return winner;
    }

    private String getStringFrom(TeamColor t) {
        String color = "Équipe Blanche";
        switch (t) {
            case BLACK:
                color = "Équipe Noire";
                break;
            case BLUE:
                color = "Équipe Bleue";
                break;
            case LIGHT_BLUE:
                color = "Équipe Bleue Claire";
                break;
            case CYAN:
                color = "Équipe Cyan";
                break;
            case GRAY:
                color = "Équipe Grise";
                break;
            case ORANGE:
                color = "Équipe Orange";
                break;
            case LIME:
                color = "Équipe Verte Claire";
                break;
            case RED:
                color = "Équipe Rouge";
                break;
            case YELLOW:
                color = "Équipe Jaune";
                break;
            case GREEN:
                color = "Équipe Verte";
                break;
            case LIGHT_GRAY:
                color = "Équipe Grise Claire";
                break;
            case MAGENTA:
                color = "Équipe Magenta";
                break;
            case PURPLE:
                color = "Équipe Violette";
                break;
        }
        return color;

    }


    private void assignTeamColor() {
        for(ZodiacPlayer z : lobby.getZodiacPlayers()){
            if(lobby.getLightGrayPlayers().contains(z.getPlayer())){
                z.setTeamColor(TeamColor.LIGHT_GRAY);
            }
            if(lobby.getGrayPlayers().contains(z.getPlayer())){
                z.setTeamColor(TeamColor.GRAY);
            }
            if(lobby.getBlackPlayers().contains(z.getPlayer())){
                z.setTeamColor(TeamColor.BLACK);
            }
            if(lobby.getRedPlayers().contains(z.getPlayer())){
                z.setTeamColor(TeamColor.RED);
            }
            if(lobby.getOrangePlayers().contains(z.getPlayer())){
                z.setTeamColor(TeamColor.ORANGE);
            }
            if(lobby.getYellowPlayers().contains(z.getPlayer())){
                z.setTeamColor(TeamColor.YELLOW);
            }
            if(lobby.getGreenPlayers().contains(z.getPlayer())){
                z.setTeamColor(TeamColor.GREEN);
            }
            if(lobby.getLimePlayers().contains(z.getPlayer())){
                z.setTeamColor(TeamColor.LIME);
            }
            if(lobby.getCyanPlayers().contains(z.getPlayer())){
                z.setTeamColor(TeamColor.CYAN);
            }
            if(lobby.getBluePlayers().contains(z.getPlayer())){
                z.setTeamColor(TeamColor.BLUE);
            }
            if(lobby.getPurplePlayers().contains(z.getPlayer())){
                z.setTeamColor(TeamColor.PURPLE);
            }
            if(lobby.getMagentaPlayers().contains(z.getPlayer())){
                z.setTeamColor(TeamColor.MAGENTA);
            }
            if(lobby.getLightBluePlayers().contains(z.getPlayer())){
                z.setTeamColor(TeamColor.LIGHT_BLUE);
            }

        }
    }

    private void clearPotionEffect(Player p) {
        for (PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }
    }

    private boolean isNotIn(Player p) {
        for(ZodiacPlayer zodiacPlayer : lobby.getZodiacPlayers()) {
            if(zodiacPlayer.getPlayer().equals(p) && zodiacPlayer.getZodiac() != null){
                return true;
            }
        }
        return false;
    }

    private void endOfShop(){
        lobby.setLobbyState(LobbyState.PLAYING);
        for(Location l : arenas.get(r).getGloryPointsLocations().keySet()){
            Collections.shuffle(potterys);
            if(l!=null){
                Bukkit.getWorld("world").getBlockAt(l).setType(Material.AIR);
            }            int chance = (int) (Math.random()*100);
            if(arenas.get(r).getGloryPointsLocations().get(l)>=chance && l!=null){
                Block block = Bukkit.getWorld("world").getBlockAt(l);
                block.setType(Material.DECORATED_POT);
                BlockState state = block.getState();
                if (state instanceof DecoratedPot pot) {
                    pot.setSherd(DecoratedPot.Side.FRONT, potterys.get(0));
                    pot.setSherd(DecoratedPot.Side.BACK, potterys.get(1));
                    pot.setSherd(DecoratedPot.Side.LEFT, potterys.get(2));
                    pot.setSherd(DecoratedPot.Side.RIGHT, potterys.get(3));

                    pot.update(true);
                }

            }
        }
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof LivingEntity e) {
                    if(e instanceof Wolf || e instanceof Zombie){
                        e.setHealth(0);
                    }
                }
            }
        }
        for(int i = 0;i<lobby.getPlayers().size();i++) {
            Player p = lobby.getPlayers().get(i);
            p.closeInventory();
            p.teleport(arenas.get(r).getSpawnLocations().get(i));
            p.getWorld().spawnParticle(Particle.SCULK_SOUL, p.getLocation(), 500, 0.5, 1, 0.5, 0.06);
            p.getWorld().spawnParticle(Particle.SONIC_BOOM, p.getLocation(), 500, 0.5, 1, 0.5, 0.06);
            p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1.0f, 1.0f);
            p.setArrowsInBody(0);
            clearPotionEffect(p);
            int amount = 0;
            for(ItemStack is : p.getInventory().getContents()){
                if(is!=null){
                    if(is.getType().equals(Material.OMINOUS_TRIAL_KEY)){
                        amount+=is.getAmount();
                    }
                }

            }
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 10, false, false));
            p.setFoodLevel(15);
            if (arenas.get(r).isHasNightVision()) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1200, 1, false, false));
            }
            arenas.get(r).useArena();
            Bukkit.getWorld("world").setTime(gameTime);
            giveAll(p, lobby.getZodiacPlayers(), amount);
        }
        timer=gameTime;
    }




    private void giveAll(Player p, ArrayList<ZodiacPlayer> zodiacPlayers, int amount) {
        ZodiacPlayer zp = null;
        ItemStack gp = plugin.giveGloryPoints(amount);
        int placeOfWeapon = getPlaceOfWeapon(p);
        int[] placeOfEnder = getPlaceOf(Material.ENDER_PEARL, p);
        int[] placeOfBread = getPlaceOf(Material.BREAD, p);
        int[] placeOfGoldenCarrot = getPlaceOf(Material.GOLDEN_CARROT, p);
        int[] placeOfGoldenApple = getPlaceOf(Material.GOLDEN_APPLE, p);
        int[] placeOfNothApple = getPlaceOf(Material.ENCHANTED_GOLDEN_APPLE,p);
        int[] placeOfTotem = getPlaceOf(Material.TOTEM_OF_UNDYING, p);
        boolean hasArrow = false;
        for(ZodiacPlayer zodiac : zodiacPlayers) {
            if (zodiac.getPlayer().equals(p)) {
                zp = zodiac;
                zp.getPlayer().getWorld().spawnParticle(Particle.FLAME, p.getLocation(), (25 * zp.getVictoryPoints()), 0.5, 0.5, 0.5, 0.5);
                ItemStack boots = createItem(Material.LEATHER_BOOTS, "Bottes", List.of());
                ItemStack leggings = createItem(Material.LEATHER_LEGGINGS, "Jambière", List.of());
                ItemStack chestplate = createItem(Material.LEATHER_CHESTPLATE, "Tunique", List.of());
                ItemStack helmet = createItem(Material.LEATHER_HELMET, "Casque", List.of());
                switch (zp.getArmorMaterialLevel()) {
                    case 1:
                        boots.setType(Material.LEATHER_BOOTS);
                        leggings.setType(Material.CHAINMAIL_LEGGINGS);
                        chestplate.setType(Material.CHAINMAIL_CHESTPLATE);
                        helmet.setType(Material.LEATHER_HELMET);
                        break;
                    case 2:
                        boots.setType(Material.LEATHER_BOOTS);
                        leggings.setType(Material.IRON_LEGGINGS);
                        chestplate.setType(Material.IRON_CHESTPLATE);
                        helmet.setType(Material.LEATHER_HELMET);
                        break;
                    case 3:
                        boots.setType(Material.LEATHER_BOOTS);
                        leggings.setType(Material.DIAMOND_LEGGINGS);
                        chestplate.setType(Material.DIAMOND_CHESTPLATE);
                        helmet.setType(Material.LEATHER_HELMET);
                        break;
                    case 4:
                        boots.setType(Material.LEATHER_BOOTS);
                        leggings.setType(Material.NETHERITE_LEGGINGS);
                        chestplate.setType(Material.NETHERITE_CHESTPLATE);
                        helmet.setType(Material.LEATHER_HELMET);
                        break;

                }
                if (zp.getZodiac().getName().equals("Dragon")) {
                    chestplate.setType(Material.ELYTRA);
                }
                if (zp.getProtectionLevel() > 0) {
                    chestplate.addEnchantment(Enchantment.PROTECTION, zp.getProtectionLevel());
                    leggings.addEnchantment(Enchantment.PROTECTION, zp.getProtectionLevel());
                }
                if (zp.getArrowProtectionLevel() > 0) {
                    boots.addEnchantment(Enchantment.PROJECTILE_PROTECTION, zp.getArrowProtectionLevel());
                    helmet.addEnchantment(Enchantment.PROJECTILE_PROTECTION, zp.getArrowProtectionLevel());
                }
                if (zp.getThornsLevel() > 0) {
                    chestplate.addEnchantment(Enchantment.THORNS, zp.getThornsLevel());
                }
                if (zp.getFireProtectionLevel() > 0) {
                    leggings.addEnchantment(Enchantment.FIRE_PROTECTION, zp.getFireProtectionLevel());
                    boots.addEnchantment(Enchantment.FIRE_PROTECTION, zp.getFireProtectionLevel());
                }
                boots = setColors(boots, zp.getTeamColor());
                helmet = setColors(helmet, zp.getTeamColor());

                ItemStack weapon = null;
                switch (zp.getZodiacLevel()) {
                    case 1:
                        weapon = zp.getZodiac().getItemLvl1();
                        break;
                    case 2:
                        weapon = zp.getZodiac().getItemLvl2();
                        break;
                    case 3:
                        weapon = zp.getZodiac().getItemLvl3();
                        break;
                    case 4:
                        weapon = zp.getZodiac().getItemLvl4();
                        break;
                    case 5:
                        weapon = zp.getZodiac().getItemLvl5();
                        break;
                    case 6:
                        weapon = zp.getZodiac().getItemLvl6();
                        break;
                    case 7:
                        weapon = zp.getZodiac().getItemLvl7();
                        break;
                }
                switch (weapon.getType()) {
                    case CROSSBOW:
                        if (zp.getDamageLevel() > 0) {
                            weapon.addUnsafeEnchantment(Enchantment.POWER, zp.getDamageLevel());
                        }
                        if (zp.getQuickChargeLevel() > 0) {
                            weapon.addEnchantment(Enchantment.QUICK_CHARGE, zp.getQuickChargeLevel());
                        }
                        if (zp.getPiercingLevel() > 0) {
                            weapon.addEnchantment(Enchantment.PIERCING, zp.getPiercingLevel());
                        }
                        if (zp.getMultishotLevel() > 0) {
                            weapon.addEnchantment(Enchantment.MULTISHOT, 1);
                        }
                        if (zp.getZodiac().getName().equals("Cochon")) {
                            if (zp.getZodiacLevel() > 1) {
                                weapon.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
                            }
                            if (zp.getZodiacLevel() > 5) {
                                weapon.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
                            }
                        }
                        hasArrow = true;
                        break;
                    case MACE:
                        if (zp.getDamageLevel() > 0) {
                            weapon.addEnchantment(Enchantment.DENSITY, zp.getDamageLevel());
                        }
                        if (zp.getBreachLevel() > 0) {
                            weapon.addEnchantment(Enchantment.BREACH, zp.getBreachLevel());
                        }
                        if (zp.getZodiac().getName().equals("Coq")) {
                            if (zp.getZodiacLevel() > 3) {
                                weapon.addUnsafeEnchantment(Enchantment.WIND_BURST, 1);
                            }
                        }
                        break;
                    case BOW:
                        if (zp.getDamageLevel() > 0) {
                            weapon.addEnchantment(Enchantment.POWER, zp.getDamageLevel());
                        }
                        if (zp.getZodiac().getName().equals("Sagitaire")) {
                            if (zp.getZodiacLevel() > 3) {
                                weapon.addUnsafeEnchantment(Enchantment.FLAME, 1);
                            }
                        }
                        hasArrow = true;
                        break;
                    case TRIDENT:
                        break;
                    default:
                        if (zp.getDamageLevel() > 0) {
                            weapon.addEnchantment(Enchantment.SHARPNESS, zp.getDamageLevel());
                        }

                        break;
                }
                int[] shieldlife = countShield(p);
                p.getInventory().clear();
                if (shieldlife[0] > 0) {
                    ItemStack shield = new ItemStack(Material.SHIELD);
                    shield.setDurability((short) (shieldlife[1]));
                    shield = createShield(shield, zp.getTeamColor());
                    p.getInventory().setItem(shieldlife[0], shield);
                }
                if (placeOfBread[1] > 0) {
                    ItemStack bread = createItemAndAmount(Material.BREAD, placeOfBread[1], "Pain");
                    p.getInventory().setItem(placeOfBread[0], bread);
                }
                if (placeOfGoldenCarrot[1] > 0) {
                    ItemStack goldenCarrot = createItemAndAmount(Material.GOLDEN_CARROT, placeOfGoldenCarrot[1], "Carotte dorée");
                    p.getInventory().setItem(placeOfGoldenCarrot[0], goldenCarrot);
                }
                if (placeOfGoldenApple[1] > 0) {
                    ItemStack goldenApple = createItemAndAmount(Material.GOLDEN_APPLE, placeOfGoldenApple[1], "Pomme dorée");
                    p.getInventory().setItem(placeOfGoldenApple[0], goldenApple);
                }
                if (placeOfNothApple[1] > 0) {
                    ItemStack notchApple = createItemAndAmount(Material.ENCHANTED_GOLDEN_APPLE, placeOfNothApple[1], "Pomme dorée enchantée");
                    p.getInventory().setItem(placeOfNothApple[0], notchApple);
                }
                if (placeOfEnder[1] > 0) {
                    ItemStack enderPearl = createItemAndAmount(Material.ENDER_PEARL, placeOfEnder[1], "Perle de l'End");
                    p.getInventory().setItem(placeOfEnder[0], enderPearl);
                }

                if (zp.getZodiac().getName().equals("Cheval")) {
                    AttributeInstance attr = zp.getPlayer().getAttribute(Attribute.KNOCKBACK_RESISTANCE);
                    if (attr != null) {
                        switch (zp.getZodiacLevel()) {
                            case 1:
                                attr.setBaseValue(0.1);
                                break;
                            case 2:
                                attr.setBaseValue(0.25);
                                break;
                            case 3:
                                attr.setBaseValue(0.4);
                                break;
                            case 4:
                                attr.setBaseValue(0.55);
                                break;
                            case 5:
                                attr.setBaseValue(0.7);
                                break;
                            case 6:
                                attr.setBaseValue(0.85);
                                break;
                            case 7:
                                attr.setBaseValue(1);
                                break;

                        }
                    }
                }
                zp.setCoqTimer(zp.getLevelTimer()[zp.getZodiacLevel()]);
                zp.setActiveTimer(zp.getLevelTimer()[zp.getZodiacLevel()]);
                zp.setActiveIsUsed(false);
                if(!zp.getZodiac().getName().equals("Coq")) {
                    p.setExp(0);
                }else{
                    p.setExp(0.99f);
                }
                zp.setBalanceTotal(0);
                p.getInventory().setHelmet(helmet);
                p.getInventory().setChestplate(chestplate);
                p.getInventory().setLeggings(leggings);
                p.getInventory().setBoots(boots);
                if(placeOfWeapon>-1){
                    p.getInventory().setItem(placeOfWeapon, weapon);
                }else{
                    p.getInventory().addItem(weapon);
                }
                if(zp.getZodiac().getName().equals("Singe")){
                    hasArrow=true;
                }
                if(hasArrow){
                    zp.getPlayer().getInventory().addItem(createItemAndAmount(Material.ARROW,10,"Flèche"));
                }
                if (zp.getZodiac().getName().equals("Balance")) {
                    if (zp.getZodiacLevel() > 2) {
                        ItemStack totem = createItemAndAmount(Material.TOTEM_OF_UNDYING, 1, "Bénédiction du Zodiac");
                        if (zp.getZodiacLevel() == 7) {
                            totem = createItemAndAmount(Material.TOTEM_OF_UNDYING, 2, "Bénédiction du Zodiac");
                        }

                        PlayerInventory inv = p.getInventory();
                        ItemStack oldOff = inv.getItemInOffHand();

                        inv.setItemInOffHand(totem);
                        if (oldOff != null && oldOff.getType() != Material.AIR) {
                            int empty = inv.firstEmpty();
                            if (empty != -1) {
                                inv.setItem(empty, oldOff);
                            } else {
                                p.getWorld().dropItemNaturally(p.getLocation(), oldOff);
                            }
                        }
                    }
                }
                zp.setSingeVictim(zp.getPlayer());
                zp.setSingeHit(0);
                p.getInventory().addItem(gp);

            }
        }

    }

    private ItemStack createShield(ItemStack shield, TeamColor teamColor) {
        ItemMeta meta = shield.getItemMeta();

        BlockStateMeta blockMeta = (BlockStateMeta) meta;
        Banner banner = (Banner) blockMeta.getBlockState();

        DyeColor dyeColor = getDyeColor(teamColor);
        if (dyeColor == null) return null;

        banner.setBaseColor(dyeColor);
        banner.getPatterns().clear();
        banner.addPattern(new Pattern(DyeColor.BLACK, getRandomPattern())); // Optionnel

        banner.update();
        blockMeta.setBlockState(banner);
        shield.setItemMeta(blockMeta);
        return shield;
    }

    private PatternType getRandomPattern() {
        PatternType[] patterns = PatternType.values();
        int index = new Random().nextInt(patterns.length);
        return patterns[index];
    }

    private DyeColor getDyeColor(TeamColor teamColor) {
        switch (teamColor) {
            case WHITE:
                return DyeColor.WHITE;
            case LIGHT_GRAY:
                return DyeColor.LIGHT_GRAY;
            case GRAY:
                return DyeColor.GRAY;
            case BLACK:
                return DyeColor.BLACK;
            case RED:
                return DyeColor.RED;
            case ORANGE:
                return DyeColor.ORANGE;
            case YELLOW:
                return DyeColor.YELLOW;
            case LIME:
                return DyeColor.LIME;
            case GREEN:
                return DyeColor.GREEN;
            case CYAN:
                return DyeColor.CYAN;
            case LIGHT_BLUE:
                return DyeColor.LIGHT_BLUE;
            case BLUE:
                return DyeColor.BLUE;
            case PURPLE:
                return DyeColor.PURPLE;
            case MAGENTA:
                return DyeColor.MAGENTA;
            default:
                return null;
        }
    }

    private ItemStack setColors(ItemStack boots, TeamColor teamColor) {

        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();

        Color color;

        switch (teamColor) {
            case WHITE: color = Color.WHITE; break;
            case LIGHT_GRAY: color = Color.SILVER; break;
            case GRAY: color = Color.GRAY; break;
            case BLACK: color = Color.BLACK; break;
            case RED: color = Color.RED; break;
            case ORANGE: color = Color.ORANGE; break;
            case YELLOW: color = Color.YELLOW; break;
            case LIME: color = Color.LIME; break;
            case GREEN: color = Color.GREEN; break;
            case CYAN: color = Color.TEAL; break;
            case LIGHT_BLUE: color = Color.AQUA; break;
            case BLUE: color = Color.BLUE; break;
            case PURPLE: color = Color.PURPLE; break;
            case MAGENTA: color = Color.FUCHSIA; break;
            default: color = Color.WHITE; break; // fallback
        }

        meta.setColor(color);
        boots.setItemMeta(meta);
        return boots;
    }


    private ItemStack createItemAndAmount(Material material, int number, String name) {
        ItemStack i = new ItemStack(material);
        ItemMeta meta = i.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + name);
        i.setItemMeta(meta);
        i.setAmount(number);
        return i;
    }

    private int[] getPlaceOf(Material material, Player p){
        int[] result = {-1,0};
        for(int i = 0; i<p.getInventory().getContents().length; i++){
            ItemStack is = p.getInventory().getItem(i);
            if(is!=null){
                if(is.getType().equals(material)){
                        result[0] = i;
                        result[1] += is.getAmount();
                }
            }

        }
        return result;
    }

    private ArrayList<Material> getPotterys() {
        ArrayList<Material> potterys = new ArrayList<>();
        potterys.add(Material.HEART_POTTERY_SHERD);
        potterys.add(Material.SHELTER_POTTERY_SHERD);
        potterys.add(Material.ANGLER_POTTERY_SHERD);
        potterys.add(Material.SHEAF_POTTERY_SHERD);
        potterys.add(Material.ARCHER_POTTERY_SHERD);
        potterys.add(Material.BLADE_POTTERY_SHERD);
        potterys.add(Material.ARMS_UP_POTTERY_SHERD);
        potterys.add(Material.BREWER_POTTERY_SHERD);
        potterys.add(Material.BURN_POTTERY_SHERD);
        potterys.add(Material.EXPLORER_POTTERY_SHERD);
        potterys.add(Material.DANGER_POTTERY_SHERD);
        potterys.add(Material.FLOW_POTTERY_SHERD);
        potterys.add(Material.FRIEND_POTTERY_SHERD);
        potterys.add(Material.HOWL_POTTERY_SHERD);
        potterys.add(Material.SNORT_POTTERY_SHERD);
        potterys.add(Material.SKULL_POTTERY_SHERD);
        potterys.add(Material.SCRAPE_POTTERY_SHERD);
        potterys.add(Material.GUSTER_POTTERY_SHERD);
        potterys.add(Material.HEARTBREAK_POTTERY_SHERD);
        potterys.add(Material.MINER_POTTERY_SHERD);
        potterys.add(Material.MOURNER_POTTERY_SHERD);
        potterys.add(Material.PLENTY_POTTERY_SHERD);
        potterys.add(Material.PRIZE_POTTERY_SHERD);




        return potterys;
    }

    private int[] countShield(Player player){
        int[] toReturn = {-1,-1};
        for (int i = 0; i < player.getInventory().getContents().length; i++) {
            if (player.getInventory().getItem(i) != null && player.getInventory().getItem(i).getType() == Material.SHIELD) {
                toReturn[0] = i;
                toReturn[1] = player.getInventory().getItem(i).getDurability();
                break;
            }
        }
        return toReturn;
    }

    private int getPlaceOfWeapon(Player p) {
        for(int i = 0; i<p.getInventory().getContents().length; i++){
            ItemStack is = p.getInventory().getItem(i);
            if(is!=null){
                if(is.getType().equals(Material.BOW) || is.getType().equals(Material.CROSSBOW) || is.getType().equals(Material.MACE) || is.getType().equals(Material.NETHERITE_SWORD)
                        || is.getType().equals(Material.DIAMOND_SWORD) || is.getType().equals(Material.IRON_SWORD) ||  is.getType().equals(Material.STONE_SWORD) ||  is.getType().equals(Material.WOODEN_SWORD)
                        || is.getType().equals(Material.NETHERITE_AXE) || is.getType().equals(Material.DIAMOND_AXE) || is.getType().equals(Material.IRON_AXE) || is.getType().equals(Material.STONE_AXE) || is.getType().equals(Material.WOODEN_AXE)){
                    return i;
                }
            }

        }
        return -1;
    }

    public ItemStack createItem(Material m, String name, List<String> lore){
        ItemStack i = new ItemStack(m);
        ItemMeta meta = i.getItemMeta();
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(name);
        if(!lore.isEmpty()){
            meta.setLore(lore);
        }
        i.setItemMeta(meta);
        return i;
    }

}
