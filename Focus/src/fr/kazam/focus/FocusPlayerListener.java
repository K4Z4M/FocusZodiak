package fr.kazam.focus;

import fr.kazam.focus.utils.*;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;
import org.bukkit.util.Vector;

import java.awt.event.FocusListener;
import java.util.*;

public class FocusPlayerListener implements Listener {
    private Main plugin;

    public FocusPlayerListener(Main plugin) {this.plugin = plugin;}


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("Bienvenue sur le mode FOCUS [remasterisé par KaZaM]");
        if(!plugin.getPlayers().contains(player)) {
            plugin.getPlayers().add(player);
        }
        plugin.getLobbyPlayers().put(player,-1);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String id = String.valueOf(plugin.getLobbyPlayers().get(player));
        for(Lobby l : plugin.getLobbies()){
            if(l.getId().equals(id)){
                l.removePlayer(player);
            }
        }
        if(plugin.getPlayers().contains(player)) {
            plugin.getPlayers().remove(player);
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if(event.getEntity() instanceof Player){
            Player killed = (Player) event.getEntity();
            killed.setGameMode(GameMode.SPECTATOR);
            if(event.getEntity().getKiller() != killed && event.getEntity().getKiller() != null && event.getEntity().getKiller() instanceof Player) {
                Player killer = (Player) event.getEntity().getKiller();
                if(plugin.getLobbyPlayers().containsKey(killed) && plugin.getLobbyPlayers().get(killed)!=-1){
                    int id = plugin.getLobbyPlayers().get(killed);
                    for(Lobby l : plugin.getLobbies()){
                        if(l.getId().equals(String.valueOf(id))){
                            if(l.getLobbyState()== LobbyState.PLAYING){
                                TeamColor a = null;
                                TeamColor b = null;
                                ZodiacPlayer killerZP = null;
                                ZodiacPlayer killedZP = null;
                                for(ZodiacPlayer zp : l.getZodiacPlayers()){
                                    if(zp.getPlayer().equals(killed)){
                                        a = zp.getTeamColor();
                                        killedZP = zp;
                                        killed.getWorld().spawnParticle(Particle.SCULK_SOUL, killed.getLocation(), 500, 0.5, 1, 0.5, 0.06);
                                        killed.getWorld().spawnParticle(Particle.SONIC_BOOM, killed.getLocation(), 5, 0.5, 1, 0.5, 0.06);
                                        killed.getWorld().playSound(killed.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1.0f, 1.0f);
                                    }
                                    if(zp.getPlayer().equals(killer)){
                                        b = zp.getTeamColor();
                                        killerZP = zp;
                                        killed.getWorld().spawnParticle(Particle.CLOUD, killed.getLocation(), (30*killerZP.getVictoryPoints()), 0.5, 0.5, 0.5, 0.02);
                                    }
                                }
                                if(a!=b){//TODO : primes
                                    killerZP.addKill(1);
                                    killerZP.addPoint(1);
                                    if(killerZP.getZodiac().getName().equals("Lion")){//TODO ajouter effet
                                        if(killerZP.getZodiacLevel()==1 || killerZP.getZodiacLevel()==2){
                                            killerZP.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH,100,0,false,false));
                                        }
                                        if(killerZP.getZodiacLevel()==3 || killerZP.getZodiacLevel()==4){
                                            killerZP.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH,180,1,false,false));
                                        }
                                        if(killerZP.getZodiacLevel()==5 || killerZP.getZodiacLevel()==6){
                                            killerZP.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH,260,2,false,false));
                                        }
                                        if(killerZP.getZodiacLevel()==7){
                                            killerZP.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH,340,3,false,false));
                                        }
                                    }
                                }else{
                                    killerZP.addPoint(-1);
                                    killerZP.addKill(1);
                                }
                                setupScoreboard(l);
                            }
                        }
                    }
                }
            }
            EntityDamageEvent damageEvent = killed.getLastDamageCause();
            if (damageEvent instanceof EntityDamageByEntityEvent damageByEntityEvent) {
                Entity damager = damageByEntityEvent.getDamager();
                if (damager instanceof Zombie zombie && zombie.hasMetadata("owner")) {
                    String uuidStr = zombie.getMetadata("owner").get(0).asString();
                    UUID ownerUUID = UUID.fromString(uuidStr);
                    Player owner = Bukkit.getPlayer(ownerUUID);
                    if (owner != null) {
                        if(plugin.getLobbyPlayers().containsKey(killed) && plugin.getLobbyPlayers().get(killed)!=-1) {
                            int id = plugin.getLobbyPlayers().get(killed);
                            for (Lobby l : plugin.getLobbies()) {
                                if (l.getId().equals(String.valueOf(id))) {
                                    if (l.getLobbyState() == LobbyState.PLAYING) {
                                        TeamColor a = null;
                                        TeamColor b = null;
                                        ZodiacPlayer killerZP = null;
                                        for (ZodiacPlayer zp : l.getZodiacPlayers()) {
                                            if (zp.getPlayer().equals(killed)) {
                                                a = zp.getTeamColor();
                                                killed.getWorld().spawnParticle(Particle.SCULK_SOUL, killed.getLocation(), 500, 0.5, 1, 0.5, 0.06);
                                                killed.getWorld().spawnParticle(Particle.SONIC_BOOM, killed.getLocation(), 5, 0.5, 1, 0.5, 0.06);
                                                killed.getWorld().playSound(killed.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1.0f, 1.0f);
                                            }
                                            if (zp.getPlayer().equals(owner)) {
                                                b = zp.getTeamColor();
                                                killerZP = zp;
                                                killed.getWorld().spawnParticle(Particle.CLOUD, killed.getLocation(), (30 * killerZP.getVictoryPoints()), 0.5, 0.5, 0.5, 0.02);
                                            }
                                        }
                                        if (a != b) {//TODO : primes
                                            killerZP.addKill(1);
                                            killerZP.addPoint(1);
                                            setupScoreboard(l);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player attacker = (Player) event.getDamager();
            int id = plugin.getLobbyPlayers().get(attacker);
            for(Lobby l : plugin.getLobbies()) {
                if (l.getId().equals(String.valueOf(id))) {
                    if (l.getLobbyState() == LobbyState.PLAYING) {
                        for (ZodiacPlayer zp : l.getZodiacPlayers()) {
                            if (zp.getPlayer().equals(attacker)) {
                                if(zp.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.MACE)){
                                    event.setDamage(event.getDamage()/1.8);
                                }
                                if (zp.getZodiac().getName().equals("Vierge")) {
                                    if(event.getEntity() instanceof Player){
                                        Player target = (Player) event.getEntity();
                                        Vector attackerDirection = attacker.getLocation().getDirection().normalize();
                                        Vector targetDirection = target.getLocation().getDirection().normalize();
                                        double dot = attackerDirection.dot(targetDirection);
                                        if (dot >0.8) {
                                            if (zp.getZodiacLevel() < 5) {
                                                event.setDamage(event.getDamage() + zp.getZodiacLevel()); //TODO ajouter effet de crit en plus
                                                zp.getPlayer().getWorld().spawnParticle(Particle.CRIT, target.getLocation().add(0,1,0), 50*zp.getZodiacLevel(), 0.5, 0.5, 0.5, 0.06);

                                            }
                                            if (zp.getZodiacLevel() == 5 || zp.getZodiacLevel() == 6) {
                                                event.setDamage(event.getDamage() + 4); //TODO ajouter effet de crit en plus
                                                zp.getPlayer().getWorld().spawnParticle(Particle.CRIT, target.getLocation().add(0,1,0), 50*zp.getZodiacLevel(), 0.5, 0.5, 0.5, 0.06);
                                            }
                                            if (zp.getZodiacLevel() == 7) {
                                                event.setDamage(event.getDamage() + 5); //TODO ajouter effet de crit en plus
                                                zp.getPlayer().getWorld().spawnParticle(Particle.CRIT, target.getLocation().add(0,1,0), 50*zp.getZodiacLevel(), 0.5, 0.5, 0.5, 0.06);
                                            }

                                        }
                                    }

                                }
                                if(zp.getZodiac().getName().equals("Singe")){
                                    if(event.getEntity() instanceof Player){
                                        Player target = (Player) event.getEntity();
                                        if(!zp.getSingeVictim().equals(target)){
                                            zp.setSingeHit(0);
                                            zp.setSingeVictim(target);
                                        }else{
                                            if(zp.getSingeHit()<5){
                                                zp.setSingeHit(zp.getSingeHit()+1);
                                            }
                                        }
                                        float multiplierLevel = 0.2f;
                                        if(zp.getZodiacLevel()>3){
                                            multiplierLevel = 0.4f;
                                        }
                                        if(zp.getZodiacLevel()==7){
                                            multiplierLevel = 0.6f;
                                        }
                                        event.setDamage(event.getDamage() + (multiplierLevel*zp.getSingeHit())); //TODO ajouter effet de crit en plus

                                    }
                                }
                                if(zp.getZodiac().getName().equals("Lion") && zp.isActiveIsUsed() && zp.getZodiacLevel()>1){
                                    int damage = 1;
                                    if(zp.getZodiacLevel()>4){
                                        damage = 2;
                                    }
                                    if(zp.getZodiacLevel()==7){
                                        damage = 3;
                                    }
                                    zp.setActiveIsUsed(false);
                                    zp.setActiveTimer(zp.getLevelTimer()[zp.getZodiacLevel()]);
                                    event.setDamage(event.getDamage() + damage);
                                }
                            }
                            if(event.getEntity() instanceof Player target && zp.getPlayer().equals(target) && zp.getZodiac().getName().equals("Balance") && zp.getZodiacLevel()>1){
                                zp.addBalanceTotal((int) event.getDamage()*2);
                                zp.setBalanceTotal(Math.min(200,zp.getBalanceTotal()));
                                zp.getPlayer().setExp((float) zp.getBalanceTotal() /200);
                            }
                        }
                    }
                }
            }
        }
        if (event.getDamager() instanceof Arrow) {
            Arrow a = (Arrow) event.getDamager();
            if(a.getShooter() instanceof Player){
                Player attacker = (Player) a.getShooter();
                int id = plugin.getLobbyPlayers().get(attacker);
                for(Lobby l : plugin.getLobbies()) {
                    if (l.getId().equals(String.valueOf(id))) {
                        if (l.getLobbyState() == LobbyState.PLAYING) {
                            for (ZodiacPlayer zp : l.getZodiacPlayers()) {
                                if (zp.getPlayer().equals(attacker)) {
                                    event.setDamage(event.getDamage()/2);//TODO
                                    if(zp.getZodiac().getName().equals("Scorpion")){
                                        if(event.getDamager() instanceof Arrow arrow && event.getEntity() instanceof LivingEntity cible){
                                            List<PotionEffect> effets = new ArrayList<>();
                                            if(zp.getZodiacLevel()==2){
                                                effets.add(new PotionEffect(PotionEffectType.POISON, 40, 0, false,false));
                                            }
                                            if(zp.getZodiacLevel()==3 || zp.getZodiacLevel()==4){
                                                effets.add(new PotionEffect(PotionEffectType.POISON, 60, 0, false,false));
                                            }
                                            if(zp.getZodiacLevel()==5 || zp.getZodiacLevel()==6){
                                                effets.add(new PotionEffect(PotionEffectType.POISON, 80, 0, false,false));
                                            }
                                            if(zp.getZodiacLevel()==7){
                                                effets.add(new PotionEffect(PotionEffectType.POISON, 80, 1, false,false));
                                            }
                                            if(zp.isActiveIsUsed()){
                                                if(zp.getZodiacLevel()==2 || zp.getZodiacLevel()==3){
                                                    effets.add(new PotionEffect(PotionEffectType.WITHER, 80, 0, false,false));
                                                }
                                                if(zp.getZodiacLevel()==4 || zp.getZodiacLevel()==5){
                                                    effets.add(new PotionEffect(PotionEffectType.WITHER, 160, 0, false,false));
                                                }
                                                if(zp.getZodiacLevel()==6 || zp.getZodiacLevel()==7){
                                                    effets.add(new PotionEffect(PotionEffectType.WITHER, 160, 1, false,false));
                                                }
                                                zp.setActiveIsUsed(false);
                                                zp.setActiveTimer(zp.getLevelTimer()[zp.getZodiacLevel()]);
                                            }
                                            cible.addPotionEffects(effets);
                                        }
                                    }
                                    if(zp.getZodiac().getName().equals("Cochon")){
                                        if(event.getDamager() instanceof Arrow arrow && event.getEntity() instanceof LivingEntity cible){
                                            Vector direction = cible.getLocation().toVector().subtract(attacker.getLocation().toVector()).normalize();
                                            if (direction.lengthSquared() > 0) {
                                                direction.normalize();
                                                if (zp.getZodiacLevel() > 1 && zp.getZodiacLevel() < 6) {
                                                    cible.setVelocity(direction.multiply(1.15));
                                                }
                                                if (zp.getZodiacLevel() > 5) {
                                                    cible.setVelocity(direction.multiply(1.65));
                                                }
                                            }

                                        }
                                    }
                                    if(zp.getZodiac().getName().equals("Cochon") || zp.getZodiac().getName().equals("Chèvre")){
                                        if(event.getDamager() instanceof Arrow arrow && event.getEntity() instanceof LivingEntity cible){
                                            event.setDamage(event.getDamage()+((double) zp.getDamageLevel() /2));
                                        }
                                    }
                                    if(zp.getZodiac().getName().equals("Singe")){
                                        if(event.getDamager() instanceof Arrow arrow && event.getEntity() instanceof Player cible){
                                            if(!zp.getSingeVictim().equals(cible)){
                                                zp.setSingeHit(0);
                                                zp.setSingeVictim(cible);
                                            }else{
                                                if(zp.getSingeHit()<5){
                                                    zp.setSingeHit(zp.getSingeHit()+1);
                                                }
                                            }
                                            float multiplierLevel = 0.2f;
                                            if(zp.getZodiacLevel()>3){
                                                multiplierLevel = 0.4f;
                                            }
                                            if(zp.getZodiacLevel()==7){
                                                multiplierLevel = 0.6f;
                                            }
                                            event.setDamage(event.getDamage() + (multiplierLevel*zp.getSingeHit()));

                                        }
                                    }
                                }
                                if(event.getEntity() instanceof Player target && zp.getPlayer().equals(target) && zp.getZodiac().getName().equals("Balance") && zp.getZodiacLevel()>1){
                                    zp.addBalanceTotal((int) event.getDamage()*2);
                                    zp.setBalanceTotal(Math.min(200,zp.getBalanceTotal()));
                                    zp.getPlayer().setExp((float) zp.getBalanceTotal() /200);
                                }
                            }
                        }
                    }
                }
            }


        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if(!p.isOnGround()){//si chute importante pas le droit de voler
            if(p.getVelocity().getY() <-0.5) {
                p.setAllowFlight(false);
            }
        }else{
            if(e.getFrom().getY()-e.getTo().getY()<0.1){
                p.setAllowFlight(true);
            }
        }

    }


    @EventHandler
    public void onDoubleJump(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) return;
        player.setFlying(false);
        player.setAllowFlight(false);
        event.setCancelled(true);
        int id = plugin.getLobbyPlayers().get(player);
        for(Lobby l : plugin.getLobbies()) {
            if (l.getId().equals(String.valueOf(id))) {
                if (l.getLobbyState() == LobbyState.PLAYING) {
                    for (ZodiacPlayer zp : l.getZodiacPlayers()) {
                        if (zp.getPlayer().equals(player)) {
                            if (zp.getZodiac().getName().equals("Lapin") && zp.getZodiacLevel() > 1) {
                                Vector jump = player.getLocation().getDirection().multiply(0.25).setY(0.05+(0.1*zp.getZodiacLevel()));
                                player.setVelocity(jump);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event) {
        ItemStack dropped = event.getItemDrop().getItemStack();

        if (dropped.getType() != Material.OMINOUS_TRIAL_KEY){
            event.setCancelled(true);
        }

    }





    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow arrow)) return;
        if (!(arrow.getShooter() instanceof Player)) return;
        Player player = (Player) arrow.getShooter();
        player.getInventory().addItem(createItemAndAmount(Material.ARROW,1,"Flèche"));

        int id = plugin.getLobbyPlayers().get(player);
        for(Lobby l : plugin.getLobbies()) {
            if (l.getId().equals(String.valueOf(id))) {
                if (l.getLobbyState() == LobbyState.PLAYING) {
                    for (ZodiacPlayer zp : l.getZodiacPlayers()) {
                        if (zp.getPlayer().equals(player)) {
                            if (zp.getZodiac().getName().equals("Scorpion") && zp.isActiveIsUsed() && !(event.getHitEntity() instanceof LivingEntity)) {
                                zp.setActiveIsUsed(false);
                                zp.setActiveTimer(zp.getLevelTimer()[zp.getZodiacLevel()]);
                            }
                            if(zp.getZodiac().getName().equals("Cochon") && zp.isActiveIsUsed()){
                                Location teleportLocation = arrow.getLocation().clone().add(0, 0.5, 0);
                                player.teleport(teleportLocation);
                                zp.setActiveIsUsed(false);
                                zp.setActiveTimer(zp.getLevelTimer()[zp.getZodiacLevel()]);
                            }
                        }
                    }
                }
            }
        }

    }





    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getEntityType() == EntityType.ENDERMITE
                && event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.ENDER_PEARL) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerAnimate(PlayerAnimationEvent e) {
        Player player = e.getPlayer();
        int id = plugin.getLobbyPlayers().get(player);
        for(Lobby l : plugin.getLobbies()) {
            if (l.getId().equals(String.valueOf(id))) {
                if (l.getLobbyState() == LobbyState.PLAYING) {
                    for (ZodiacPlayer zp : l.getZodiacPlayers()) {
                        if (zp.getPlayer().equals(player)) {
                            if(e.getAnimationType().equals(PlayerAnimationType.ARM_SWING)){
                                if(zp.getZodiac().getName().equals("Lion") && zp.isActiveIsUsed()){
                                    zp.setActiveIsUsed(false);
                                    zp.setActiveTimer(zp.getLevelTimer()[zp.getZodiacLevel()]);
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Zodiac dummy = new Zodiac();
        int lobbyId = plugin.getLobbyPlayers().get(player);
        Lobby lobby = null;
        for(Lobby l : plugin.getLobbies()){
            if(l.getId().equals(String.valueOf(lobbyId))){
                lobby = l;
            }
        }
        if(lobby==null){
            return;
        }
        ZodiacPlayer zp = null;
        for(ZodiacPlayer z : lobby.getZodiacPlayers()){
            if(z.getPlayer().equals(player)){
                zp = z;
            }
        }
        ItemStack item = event.getItem();
        Action action = event.getAction();
        Block block = event.getClickedBlock();
        if(item!=null && item.getType()== Material.NETHER_STAR && item.getItemMeta()!=null){
            if(item.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Étoile du Zodiac") && (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) && lobby.getLobbyState()==LobbyState.SELECTING) {
                zp.getPlayer().getWorld().playSound(zp.getPlayer().getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.0f, 1.0f);
                Inventory inv = Bukkit.createInventory(player,54,ChatColor.LIGHT_PURPLE+"Sélection du Zodiac");
                ArrayList<Zodiac> zodiacs = dummy.getZodiac();
                ItemStack lion = createItemStack(zodiacs.get(0));
                ItemStack taureau = createItemStack(zodiacs.get(1));
                ItemStack vierge = createItemStack(zodiacs.get(2));
                ItemStack balance = createItemStack(zodiacs.get(3));
                ItemStack scorpion = createItemStack(zodiacs.get(4));
                ItemStack sagitaire = createItemStack(zodiacs.get(5));
                ItemStack lapin = createItemStack(zodiacs.get(6));
                ItemStack coq = createItemStack(zodiacs.get(7));
                ItemStack cheval = createItemStack(zodiacs.get(8));
                ItemStack singe = createItemStack(zodiacs.get(9));
                ItemStack chien = createItemStack(zodiacs.get(10));
                ItemStack cochon = createItemStack(zodiacs.get(11));
                inv.setItem(10, createItem(Material.BARRIER,"Bélier (WIP)", Arrays.asList("Le bélier arrivera prochainement dans le jeu")));
                inv.setItem(11,taureau);
                inv.setItem(12, createItem(Material.BARRIER,"Gémeaux (WIP)", Arrays.asList("Le gémeaux arrivera prochainement dans le jeu")));

                inv.setItem(19, createItem(Material.BARRIER,"Cancer (WIP)", Arrays.asList("Le cancer arrivera prochainement dans le jeu")));
                inv.setItem(20,lion);
                inv.setItem(21,vierge);

                inv.setItem(28,balance);
                inv.setItem(29,scorpion);
                inv.setItem(30,sagitaire);

                inv.setItem(37, createItem(Material.BARRIER,"Capricorne (WIP)", Arrays.asList("Le capricorne arrivera prochainement dans le jeu")));
                inv.setItem(38, createItem(Material.BARRIER,"Verseau (WIP)", Arrays.asList("Le verseau arrivera prochainement dans le jeu")));
                inv.setItem(39, createItem(Material.BARRIER,"Poisson (WIP)", Arrays.asList("Le poisson arrivera prochainement dans le jeu")));

                inv.setItem(14,lapin);
                inv.setItem(15, createItem(Material.BARRIER,"Tigre (WIP)", Arrays.asList("Le tigre arrivera prochainement dans le jeu")));
                inv.setItem(16, createItem(Material.BARRIER,"Buffle (WIP)", Arrays.asList("Le buffle arrivera prochainement dans le jeu")));

                inv.setItem(23, createItem(Material.BARRIER,"Rat (WIP)", Arrays.asList("Le rat arrivera prochainement dans le jeu")));
                inv.setItem(24,cochon);
                inv.setItem(25,chien);

                inv.setItem(32,coq);
                inv.setItem(33,singe);
                inv.setItem(34, createItem(Material.BARRIER,"Chèvre (WIP)", Arrays.asList("La chèvre arrivera prochainement dans le jeu")));

                inv.setItem(41,cheval);
                inv.setItem(42, createItem(Material.BARRIER,"Serpent (WIP)", Arrays.asList("Le serpent arrivera prochainement dans le jeu")));
                inv.setItem(43, createItem(Material.BARRIER,"Dragon (WIP)", Arrays.asList("Le dragon arrivera prochainement dans le jeu")));


                player.openInventory(inv);
            }
        }
        if(item!=null && item.getType()== Material.OMINOUS_TRIAL_KEY && item.getItemMeta()!=null){
            if(item.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE+ "Point de Gloire") && action.equals(Action.RIGHT_CLICK_BLOCK) && block.getType().equals(Material.AMETHYST_CLUSTER) && lobby.getLobbyState()==LobbyState.SELECTING){
                player.getWorld().playSound(block.getLocation(), Sound.BLOCK_AMETHYST_CLUSTER_STEP, 1.0f, 1.0f);
                player.openInventory(getInventory(zp, player));
            }
        }
        if(block != null && block.getType().equals(Material.DECORATED_POT) && lobby.getLobbyState()==LobbyState.PLAYING){
            event.setCancelled(true); // Empêche l'ajout d'objets dans le pot
            player.getInventory().addItem(plugin.giveGloryPoints(1));
            player.getWorld().playSound(block.getLocation(), Sound.BLOCK_DECORATED_POT_BREAK, 1.0f, 1.0f);
            player.getWorld().spawnParticle(Particle.END_ROD, block.getLocation().add(0,1,0), 20, 0.4, 0.8, 0.4, 0.02);
            block.setType(Material.AIR);
        }
        boolean isRightClick = action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;
        boolean isLeftClick = action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK;
        if(item != null && lobby.getLobbyState()==LobbyState.PLAYING && zp.getZodiac().getName().equals("Coq") && item.getType().equals(Material.MACE) && isRightClick && zp.getZodiacLevel()>1 && zp.getCoqTimer()>0){
            zp.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,10,2,false,false));
            zp.setCoqTimer(zp.getCoqTimer()-0.2f);
            zp.getPlayer().setExp((Math.max(0f, ( zp.getCoqTimer() / (float) zp.getLevelTimer()[zp.getZodiacLevel()]))));
            player.getWorld().spawnParticle(Particle.SMALL_GUST, player.getLocation().add(0,-0.5,0), 20, 0.2, 0.2, 0.2, 1);

            if(zp.getCoqTimer()<0){
                zp.setCoqTimer(0);
            }

        }
        List<Material> weapons = Arrays.asList(Material.WOODEN_SWORD,Material.STONE_SWORD, Material.IRON_SWORD, Material.DIAMOND_SWORD, Material.NETHERITE_SWORD,Material.WOODEN_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.DIAMOND_AXE, Material.NETHERITE_AXE, Material.TRIDENT, Material.MACE);
        if(item!=null && lobby.getLobbyState()==LobbyState.PLAYING && (item.getType().equals(Material.BOW) || item.getType().equals(Material.CROSSBOW)) && zp.getActiveTimer()==0 && isLeftClick && zp.getZodiacLevel()!=1) {
            int plus = 0;
            switch(zp.getZodiac().getName()) {
                case "Scorpion":
                    zp.setActiveIsUsed(true);
                    break;
                case "Sagitaire":
                    switch(zp.getZodiacLevel()){
                        case 2:
                            plus = 5;
                            break;
                        case 3:
                            plus = 8;
                            break;
                        case 4:
                            plus = 11;
                            break;
                        case 5:
                            plus = 14;
                            break;
                        case 6:
                            plus = 17;
                            break;
                        case 7:
                            plus = 20;
                            break;
                    }
                    ItemStack arcSag = zp.getPlayer().getInventory().getItemInMainHand();
                    ItemMeta meta = arcSag.getItemMeta();
                    if (meta != null) {
                        int level = arcSag.getEnchantmentLevel(Enchantment.POWER);
                        arcSag.removeEnchantment(Enchantment.POWER);
                        arcSag.addUnsafeEnchantment(Enchantment.POWER, level + 2);
                    }
                    zp.setActiveTimer(zp.getLevelTimer()[zp.getZodiacLevel()]+plus);
                    zp.setActiveIsUsed(true);
                    break;
                case "Singe":
                    zp.getPlayer().getInventory().setItemInMainHand(switchItem(item.getType(),zp));
                    zp.setActiveTimer(zp.getLevelTimer()[zp.getZodiacLevel()]);
                    break;
                case "Cochon":
                    zp.setActiveIsUsed(true);
                    break;
            }
            player.getWorld().spawnParticle(Particle.WAX_ON, player.getLocation().add(0,1,0), 250, 0, 0, 0, 50);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 1.0f, 1.0f);

        }
        if(item!=null && lobby.getLobbyState()==LobbyState.PLAYING && weapons.contains(item.getType()) && zp.getZodiac().getName().equals("Balance") && zp.getBalanceTotal()>29 && isRightClick && zp.getZodiacLevel()!=1){
            spawnZombie(zp);
            player.getWorld().spawnParticle(Particle.WAX_ON, player.getLocation().add(0,1,0), 250, 0, 0, 0, 50);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 1.0f, 1.0f);
            zp.setBalanceTotal(0);
            zp.getPlayer().setExp(0);
        }

        if(item!=null && lobby.getLobbyState()==LobbyState.PLAYING && weapons.contains(item.getType()) && zp.getActiveTimer()==0 && isRightClick && zp.getZodiacLevel()!=1){
            int plus = 0;
            switch(zp.getZodiac().getName()){
                case "Lion":
                    zp.setActiveIsUsed(true);
                    break;
                case "Taureau":
                    switch(zp.getZodiacLevel()){
                        case 2,3:
                            zp.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 0, false, false));
                            plus = 5;
                            break;
                        case 4:
                            zp.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 140, 0, false, false));
                            plus = 7;
                            break;
                        case 5:
                            zp.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 0, false, false));
                            plus = 10;
                            break;
                        case 6,7:
                            zp.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1, false, false));
                            plus = 10;
                            break;
                    }
                    zp.setActiveTimer(zp.getLevelTimer()[zp.getZodiacLevel()]+plus);
                    break;
                case "Vierge":
                    switch(zp.getZodiacLevel()){
                        case 2:
                            zp.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 0, false, false));
                            plus = 5;
                            break;
                        case 3:
                            zp.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 0, false, false));
                            plus = 10;
                            break;
                        case 4,5,6,7:
                            zp.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 300, 0, false, false));
                            plus = 15;
                            break;
                    }
                    lobby.setHelmetVierge(zp.getPlayer(), zp.getPlayer().getInventory().getHelmet());
                    lobby.setChestplateVierge(zp.getPlayer(), zp.getPlayer().getInventory().getChestplate());
                    lobby.setLeggingsVierge(zp.getPlayer(), zp.getPlayer().getInventory().getLeggings());
                    lobby.setBootsVierge(zp.getPlayer(), zp.getPlayer().getInventory().getBoots());
                    zp.getPlayer().getInventory().setArmorContents(new ItemStack[4]);
                    zp.setActiveTimer(zp.getLevelTimer()[zp.getZodiacLevel()]+plus);
                    zp.setActiveIsUsed(true);
                    break;
                case "Lapin":
                    float y = 0.3f;
                    switch(zp.getZodiacLevel()){
                        case 3:
                            y = 0.44f;
                            break;
                        case 4:
                            y = 0.55f;
                            break;
                        case 5:
                            y = 0.65f;
                            break;
                        case 6:
                            y = 0.75f;
                            break;
                        case 7:
                            y = 0.85f;
                            break;
                    }
                    Vector velocity = player.getVelocity();
                    velocity.setY(y);
                    player.setVelocity(velocity);
                    zp.setActiveTimer(zp.getLevelTimer()[zp.getZodiacLevel()]);
                    break;
                case "Cheval":
                    Location explosionCenter = player.getLocation();
                    float rayon = 3f;
                    int damageInt = 6;
                    if(zp.getZodiacLevel()>4){
                        rayon = 4f;
                        damageInt = 10;
                    }
                    if(zp.getZodiacLevel()==7){
                        rayon = 5f;
                        damageInt = 14;
                    }
                    player.getWorld().spawnParticle(Particle.LARGE_SMOKE, explosionCenter, 300, 0, 0, 0, 1);
                    player.getWorld().playSound(explosionCenter, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);

                    for (Entity entity : player.getWorld().getNearbyEntities(explosionCenter, rayon, rayon, rayon)) {
                        if (entity instanceof LivingEntity cible && !cible.equals(player)) {
                            double distance = cible.getLocation().distance(explosionCenter);
                            double damage = Math.max(0, damageInt - (2 * distance));
                            cible.damage(damage, player);
                            Vector direction = cible.getLocation().toVector().subtract(explosionCenter.toVector()).normalize();
                            direction.multiply(rayon/2);
                            direction.setY(0.5);
                            cible.setVelocity(direction);
                        }
                    }
                    explosionCenter.getWorld().createExplosion(explosionCenter, 0F, false, false);
                    zp.setActiveTimer(zp.getLevelTimer()[zp.getZodiacLevel()]);
                    break;
                case "Singe":
                    zp.getPlayer().getInventory().setItemInMainHand(switchItem(item.getType(),zp));
                    zp.setActiveTimer(zp.getLevelTimer()[zp.getZodiacLevel()]);
                    break;
                case "Chien":
                    Wolf wolf = (Wolf) player.getWorld().spawnEntity(player.getLocation(), EntityType.WOLF);
                    wolf.setTamed(true);
                    wolf.setOwner(player);
                    wolf.setHealth(11+zp.getZodiacLevel()*2);
                    wolf.setCustomName("Chien de "+player.getName());
                    wolf.setCustomNameVisible(true);
                    if (wolf.getEquipment() != null && zp.getZodiacLevel()==7) {
                        ItemStack wa = new ItemStack(Material.WOLF_ARMOR);
                        wa.setDurability((short) (wa.getType().getMaxDurability() - 8));
                        wolf.getEquipment().setItem(EquipmentSlot.BODY, wa);
                    }
                    wolf.setCollarColor(getDyeColor(zp.getTeamColor()));
                    zp.setActiveTimer(zp.getLevelTimer()[zp.getZodiacLevel()]);
                    break;
            }
            player.getWorld().spawnParticle(Particle.WAX_ON, player.getLocation().add(0,1,0), 250, 0, 0, 0, 50);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 1.0f, 1.0f);

        }

    }

    private ItemStack createItemStack(Zodiac zodiac) {
        ItemStack i = new ItemStack(zodiac.getFlag());
        ItemMeta meta = i.getItemMeta();
        meta.setLore(zodiac.getDescription());
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        i.setItemMeta(meta);
        return i;
    }

    @EventHandler
    public void onZombieDeath(EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.ZOMBIE) {
            event.getDrops().clear();
            event.setDroppedExp(0);
        }
    }


    @EventHandler
    public void onZombieTarget(EntityTargetEvent event) {
        if (event.getEntity() instanceof Zombie zombie && event.getTarget() instanceof Player player) {
            if (zombie.hasMetadata("owner") && zombie.getMetadata("owner").get(0).asString().equals(player.getUniqueId().toString())) {
                event.setCancelled(true);
            }
        }
    }

    private void spawnZombie(ZodiacPlayer zp) {
        List<Integer> parts = splitIntoSixParts(zp.getBalanceTotal());
        float health = (float) (parts.get(0)*0.7);//0.75 [0.75 - 45]
        health+=(zp.getZodiacLevel());
        health = Math.max(health,16);
        float damage = (float) (parts.get(1)*0.2);//0.25 [0.25-15]
        damage+=((float) zp.getZodiacLevel() /2);
        float speed = (float) (parts.get(2)*0.01);//0.01 [0.01, 0.60]
        speed = Math.min(Math.max(speed, 0.2f), 0.45f);
        float knockBackR = (float) (parts.get(3)*0.015);//0.015 [0.015-0.9]
        knockBackR+=((float) zp.getZodiacLevel() /20);
        float protectionLevel = (float) (parts.get(4)*0.1);//0.1 [0.1-6]
        protectionLevel+=((float) zp.getZodiacLevel() /10);
        float armorType = (float) (parts.get(5)*0.1);//0.1 [0.6-6]
        armorType+=((float) zp.getZodiacLevel() /10);
        Zombie zombie = (Zombie) zp.getPlayer().getWorld().spawnEntity(zp.getPlayer().getLocation(), EntityType.ZOMBIE);
        zombie.setCustomName("Bourreau de "+zp.getPlayer().getName());
        zombie.setCustomNameVisible(true);
        zombie.setAge(1);
        zombie.setMetadata("owner", new FixedMetadataValue(plugin, zp.getPlayer().getUniqueId().toString()));
        zombie.setAI(true);
        zombie.getAttribute(Attribute.MAX_HEALTH).setBaseValue(health);
        zombie.setHealth(health);
        zombie.getAttribute(Attribute.ATTACK_DAMAGE).setBaseValue(damage);
        zombie.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(speed);
        zombie.getAttribute(Attribute.FOLLOW_RANGE).setBaseValue(30);
        zombie.getAttribute(Attribute.KNOCKBACK_RESISTANCE).setBaseValue(knockBackR);
        ItemStack zombieHelmet = new ItemStack(Material.LEATHER_HELMET);
        ItemStack zombieChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack zombieLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack zombieBoots = new ItemStack(Material.LEATHER_BOOTS);
        if(armorType>1){
            zombieHelmet = new ItemStack(Material.CHAINMAIL_HELMET);
            zombieChestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
            zombieLeggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
            zombieBoots = new ItemStack(Material.CHAINMAIL_BOOTS);
        }
        if(armorType>2){
            zombieHelmet = new ItemStack(Material.IRON_HELMET);
            zombieChestplate = new ItemStack(Material.IRON_CHESTPLATE);
            zombieLeggings = new ItemStack(Material.IRON_LEGGINGS);
            zombieBoots = new ItemStack(Material.IRON_BOOTS);
        }
        if(armorType>3){
            zombieHelmet = new ItemStack(Material.DIAMOND_HELMET);
            zombieChestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
            zombieLeggings = new ItemStack(Material.DIAMOND_LEGGINGS);
            zombieBoots = new ItemStack(Material.DIAMOND_BOOTS);
        }
        if(armorType>4){
            zombieHelmet = new ItemStack(Material.NETHERITE_HELMET);
            zombieChestplate = new ItemStack(Material.NETHERITE_CHESTPLATE);
            zombieLeggings = new ItemStack(Material.NETHERITE_LEGGINGS);
            zombieBoots = new ItemStack(Material.NETHERITE_BOOTS);
        }
        if(protectionLevel>=1){
            zombieHelmet.addUnsafeEnchantment(Enchantment.PROJECTILE_PROTECTION,Math.round(protectionLevel));
            zombieChestplate.addUnsafeEnchantment(Enchantment.PROTECTION,Math.round(protectionLevel));
            zombieLeggings.addUnsafeEnchantment(Enchantment.PROTECTION,Math.round(protectionLevel));
            zombieBoots.addUnsafeEnchantment(Enchantment.FIRE_PROTECTION,Math.round(protectionLevel));
        }
        zombie.getEquipment().setHelmet(zombieHelmet);
        zombie.getEquipment().setChestplate(zombieChestplate);
        zombie.getEquipment().setLeggings(zombieLeggings);
        zombie.getEquipment().setBoots(zombieBoots);
    }

    public static List<Integer> splitIntoSixParts(int total) {
        Random random = new Random();
        List<Integer> parts = new ArrayList<>();

        int remaining = total;

        for (int i = 0; i < 6; i++) {
            // Nombre de parts restantes après celle-ci
            int partsLeft = 6 - i;

            // Calcul des bornes : il faut laisser au moins 1 pour chaque part restante
            int minPart = Math.max(1, remaining - (partsLeft - 1) * 60);
            int maxPart = Math.min(60, remaining - (partsLeft - 1) * 1);

            int part = random.nextInt(maxPart - minPart + 1) + minPart;
            parts.add(part);
            remaining -= part;
        }

        return parts;
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

    private ItemStack switchItem(Material type, ZodiacPlayer zp) {
        ItemStack item = new ItemStack(Material.WOODEN_SWORD);
        if(type.equals(Material.BOW)){
            switch(zp.getZodiacLevel()){
                case 3:
                    item = new ItemStack(Material.STONE_SWORD);
                    break;
                case 4,5,6:
                    item = new ItemStack(Material.IRON_SWORD);
                    break;
                case 7:
                    item = new ItemStack(Material.DIAMOND_SWORD);
                    break;
            }
            item.addUnsafeEnchantment(Enchantment.SHARPNESS, zp.getDamageLevel());

        }else{
            item = new ItemStack(Material.BOW);
            item.addUnsafeEnchantment(Enchantment.POWER, zp.getDamageLevel());
        }
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD+"Malice du Singe");
        item.setItemMeta(meta);
        return item;
    }


    private Inventory getInventory(ZodiacPlayer zp, Player player) {
        Inventory inv = Bukkit.createInventory(player, 27, ChatColor.LIGHT_PURPLE+"Observatoire");
        ItemStack armor = switch (zp.getArmorMaterialLevel()) {
            case 0 ->
                    createItem(Material.CHAINMAIL_CHESTPLATE, ChatColor.LIGHT_PURPLE + "Améliorer l'armure", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "7", ChatColor.WHITE + "Matériau : Cuir -> " + ChatColor.GOLD + "Maille",
                            ChatColor.WHITE + "points d'armure : 7 -> " + ChatColor.GOLD + "11", ChatColor.WHITE + "Solidité d'armure : 0 -> 0", ChatColor.WHITE + "Résistance au recul : 0 -> 0"));
            case 1 ->
                    createItem(Material.IRON_CHESTPLATE, ChatColor.LIGHT_PURPLE + "Améliorer l'armure", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "8", ChatColor.WHITE + "Matériau : Maille -> " + ChatColor.GOLD + "Fer",
                            ChatColor.WHITE + "points d'armure : 11 -> " + ChatColor.GOLD + "13", ChatColor.WHITE + "Solidité d'armure : 0 -> 0", ChatColor.WHITE + "Résistance au recul : 0 -> 0"));
            case 2 ->
                    createItem(Material.DIAMOND_CHESTPLATE, ChatColor.LIGHT_PURPLE + "Améliorer l'armure", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "8", ChatColor.WHITE + "Matériau : Fer -> " + ChatColor.GOLD + "Diamant",
                            ChatColor.WHITE + "points d'armure : 13 -> " + ChatColor.GOLD + "16", ChatColor.WHITE + "Solidité d'armure : 0 -> " + ChatColor.GOLD + "8", ChatColor.WHITE + "Résistance au recul : 0 -> 0"));
            case 3 ->
                    createItem(Material.NETHERITE_CHESTPLATE, ChatColor.LIGHT_PURPLE + "Améliorer l'armure", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "9", ChatColor.WHITE + "Matériau : Diamant -> " + ChatColor.GOLD + "Netherite",
                            ChatColor.WHITE + "points d'armure : 16 -> 16", ChatColor.WHITE + "Solidité d'armure : 8 -> " + ChatColor.GOLD + "12", ChatColor.WHITE + "Résistance au recul : 0 -> " + ChatColor.GOLD + "4"));
            default ->
                    createItem(Material.BARRIER, ChatColor.RED + "Armure au niveau maximum", Arrays.asList("Vous ne pouvez plus améliorer cet objet"));
        };
        ItemStack protection = switch (zp.getProtectionLevel()) {
            case 0 ->
                    createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Protection I", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "7", ChatColor.WHITE + "Aucun enchantement -> " + ChatColor.GOLD + "Protection I"));
            case 1 ->
                    createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Protection II", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "8", ChatColor.WHITE + "Protection I -> " + ChatColor.GOLD + "Protection II"));
            case 2 ->
                    createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Protection III", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "8", ChatColor.WHITE + "Protection II -> " + ChatColor.GOLD + "Protection III"));
            case 3 ->
                    createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Protection IV", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "9", ChatColor.WHITE + "Protection III -> " + ChatColor.GOLD + "Protection IV"));
            default ->
                    createItem(Material.BARRIER, ChatColor.RED + "Enchantement au niveau maximum", Arrays.asList("Vous ne pouvez plus améliorer cet enchantement"));
        };
        ItemStack fireProtection = switch (zp.getFireProtectionLevel()) {
            case 0 ->
                    createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Protection Feu I", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "3", ChatColor.WHITE + "Aucun enchantement -> " + ChatColor.GOLD + "Protection Feu I"));
            case 1 ->
                    createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Protection Feu II", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "4", ChatColor.WHITE + "Protection Feu I -> " + ChatColor.GOLD + "Protection Feu II"));
            case 2 ->
                    createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Protection Feu III", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "4", ChatColor.WHITE + "Protection Feu II -> " + ChatColor.GOLD + "Protection Feu III"));
            case 3 ->
                    createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Protection Feu IV", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "5", ChatColor.WHITE + "Protection Feu III -> " + ChatColor.GOLD + "Protection Feu IV"));
            default ->
                    createItem(Material.BARRIER, ChatColor.RED + "Enchantement au niveau maximum", Arrays.asList("Vous ne pouvez plus améliorer cet enchantement"));
        };
        ItemStack projProtection = switch (zp.getArrowProtectionLevel()) {
            case 0 ->
                    createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Protection Projectiles I", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "3", ChatColor.WHITE + "Aucun enchantement -> " + ChatColor.GOLD + "Protection Projectiles I"));
            case 1 ->
                    createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Protection Projectiles II", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "4", ChatColor.WHITE + "Protection Projectiles I -> " + ChatColor.GOLD + "Protection Projectiles II"));
            case 2 ->
                    createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Protection Projectiles III", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "4", ChatColor.WHITE + "Protection Projectiles II -> " + ChatColor.GOLD + "Protection Projectiles III"));
            case 3 ->
                    createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Protection Projectiles IV", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "5", ChatColor.WHITE + "Protection Projectiles III -> " + ChatColor.GOLD + "Protection Projectiles IV"));
            default ->
                    createItem(Material.BARRIER, ChatColor.RED + "Enchantement au niveau maximum", Arrays.asList("Vous ne pouvez plus améliorer cet enchantement"));
        };
        ItemStack thorns = switch (zp.getThornsLevel()) {
            case 0 ->
                    createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Épines I", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "5", ChatColor.WHITE + "Aucun enchantement -> " + ChatColor.GOLD + "Épines I"));
            case 1 ->
                    createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Épines II", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "6", ChatColor.WHITE + "Épines I -> " + ChatColor.GOLD + "Épines II"));
            case 2 ->
                    createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Épines III", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "7", ChatColor.WHITE + "Épines II -> " + ChatColor.GOLD + "Épines III"));
            default ->
                    createItem(Material.BARRIER, ChatColor.RED + "Enchantement au niveau maximum", Arrays.asList("Vous ne pouvez plus améliorer cet enchantement"));
        };
        ItemStack zodiacItem = switch (zp.getZodiacLevel()) {
            case 1 ->
                    createItem(zp.getZodiac().getFlag().getType(), ChatColor.LIGHT_PURPLE + "Améliorer votre zodiac", zp.getZodiac().getDescLvl2());
            case 2 ->
                    createItem(zp.getZodiac().getFlag().getType(), ChatColor.LIGHT_PURPLE + "Améliorer votre zodiac", zp.getZodiac().getDescLvl3());
            case 3 ->
                    createItem(zp.getZodiac().getFlag().getType(), ChatColor.LIGHT_PURPLE + "Améliorer votre zodiac", zp.getZodiac().getDescLvl4());
            case 4 ->
                    createItem(zp.getZodiac().getFlag().getType(), ChatColor.LIGHT_PURPLE + "Améliorer votre zodiac", zp.getZodiac().getDescLvl5());
            case 5 ->
                    createItem(zp.getZodiac().getFlag().getType(), ChatColor.LIGHT_PURPLE + "Améliorer votre zodiac", zp.getZodiac().getDescLvl6());
            case 6 ->
                    createItem(zp.getZodiac().getFlag().getType(), ChatColor.LIGHT_PURPLE + "Améliorer votre zodiac", zp.getZodiac().getDescLvl7());
            default ->
                    createItem(Material.BARRIER, ChatColor.RED + "Zodiac au niveau maximum", Arrays.asList("Vous ne pouvez plus améliorer votre zodiac"));
        };
        ItemStack damage = switch (zp.getDamageLevel()) {
            case 0 ->
                    createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Dégâts I", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "7", ChatColor.WHITE + "Aucun enchantement -> " + ChatColor.GOLD + "Dégâts I"));
            case 1 ->
                    createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Dégâts II", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "8", ChatColor.WHITE + "Dégâts I -> " + ChatColor.GOLD + "Dégâts II"));
            case 2 ->
                    createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Dégâts III", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "9", ChatColor.WHITE + "Dégâts II -> " + ChatColor.GOLD + "Dégâts III"));
            case 3 ->
                    createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Dégâts IV", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "10", ChatColor.WHITE + "Dégâts III -> " + ChatColor.GOLD + "Dégâts IV"));
            case 4 ->
                    createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Dégâts V", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "11", ChatColor.WHITE + "Dégâts IV -> " + ChatColor.GOLD + "Dégâts V"));
            default ->
                    createItem(Material.BARRIER, ChatColor.RED + "Enchantement au niveau maximum", Arrays.asList("Vous ne pouvez plus améliorer cet enchantement"));
        };
        if(zp.getZodiac().getName().equals("Dragon") || zp.getZodiac().getName().equals("Coq") ||zp.getZodiac().getName().equals("Lapin")){
            ItemStack breach = switch (zp.getBreachLevel()) {
                case 0 ->
                        createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Brèche I", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "9", ChatColor.WHITE + "Aucun enchantement -> " + ChatColor.GOLD + "Brèche I"));
                case 1 ->
                        createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Brèche II", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "10", ChatColor.WHITE + "Brèche I -> " + ChatColor.GOLD + "Brèche II"));
                case 2 ->
                        createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Brèche III", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "10", ChatColor.WHITE + "Brèche II -> " + ChatColor.GOLD + "Brèche III"));
                case 3 ->
                        createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Brèche IV", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "11", ChatColor.WHITE + "Brèche III -> " + ChatColor.GOLD + "Brèche IV"));
                default ->
                        createItem(Material.BARRIER, ChatColor.RED + "Enchantement au niveau maximum", Arrays.asList("Vous ne pouvez plus améliorer cet enchantement"));
            };
            inv.setItem(20,breach);
        }
        if(zp.getZodiac().getName().equals("Capricorne") || zp.getZodiac().getName().equals("Cochon")){
            ItemStack piercing = switch (zp.getPiercingLevel()) {
                case 0 ->
                        createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Perforation I", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "4", ChatColor.WHITE + "Aucun enchantement -> " + ChatColor.GOLD + "Perforation I"));
                case 1 ->
                        createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Perforation II", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "5", ChatColor.WHITE + "Perforation I -> " + ChatColor.GOLD + "Perforation II"));
                case 2 ->
                        createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Perforation III", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "5", ChatColor.WHITE + "Perforation II -> " + ChatColor.GOLD + "Perforation III"));
                case 3 ->
                        createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Perforation IV", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "6", ChatColor.WHITE + "Perforation III -> " + ChatColor.GOLD + "Perforation IV"));
                default ->
                        createItem(Material.BARRIER, ChatColor.RED + "Enchantement au niveau maximum", Arrays.asList("Vous ne pouvez plus améliorer cet enchantement"));
            };
            ItemStack multiShot = switch (zp.getMultishotLevel()) {
                case 0 ->
                        createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Tir multiple I", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "10", ChatColor.WHITE + "Aucun enchantement -> " + ChatColor.GOLD + "Tir multiple I"));
                default ->
                        createItem(Material.BARRIER, ChatColor.RED + "Enchantement au niveau maximum", Arrays.asList("Vous ne pouvez plus améliorer cet enchantement"));
            };
            ItemStack quickCharge = switch (zp.getQuickChargeLevel()) {
                case 0 ->
                        createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Charge rapide I", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "4", ChatColor.WHITE + "Aucun enchantement -> " + ChatColor.GOLD + "Charge rapide I"));
                case 1 ->
                        createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Charge rapide II", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "5", ChatColor.WHITE + "Charge rapide I -> " + ChatColor.GOLD + "Charge rapide II"));
                case 2 ->
                        createItem(Material.ENCHANTED_BOOK, ChatColor.LIGHT_PURPLE + "Charge rapide III", Arrays.asList(ChatColor.WHITE + "Coût : " + ChatColor.GOLD + "6", ChatColor.WHITE + "Charge rapide II -> " + ChatColor.GOLD + "Charge rapide III"));
                default ->
                        createItem(Material.BARRIER, ChatColor.RED + "Enchantement au niveau maximum", Arrays.asList("Vous ne pouvez plus améliorer cet enchantement"));
            };
            inv.setItem(21,quickCharge);
            inv.setItem(22,multiShot);
            inv.setItem(20,piercing);
        }
        int breadAmount = countMaterial(player, Material.BREAD);
        int goldenCarrot = countMaterial(player, Material.GOLDEN_CARROT);
        int goldenApple = countMaterial(player, Material.GOLDEN_APPLE);
        int notchApple = countMaterial(player, Material.ENCHANTED_GOLDEN_APPLE);
        int enderPearlAmount = countMaterial(player, Material.ENDER_PEARL);
        ItemStack barrier = createItem(Material.BARRIER, ChatColor.RED + "Nombre maximum atteint", Arrays.asList("Vous ne pouvez plus acheter cet objet pour le moment"));
        if(breadAmount<9){
            ItemStack bread = createItem(Material.BREAD, ChatColor.LIGHT_PURPLE + "Pain (x2)", Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"1",ChatColor.RED+String.valueOf(breadAmount)+"/10"));
            inv.setItem(6,bread);
        }else{
            inv.setItem(6,barrier);
        }
        if(goldenCarrot<4){
            ItemStack g = createItem(Material.GOLDEN_CARROT, ChatColor.LIGHT_PURPLE + "Carotte dorée", Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"1",ChatColor.RED+String.valueOf(goldenCarrot)+"/4"));
            inv.setItem(7,g);
        }else{
            inv.setItem(7,barrier);
        }
        if(goldenApple<2){
            ItemStack apple = createItem(Material.GOLDEN_APPLE, ChatColor.LIGHT_PURPLE + "Pomme dorée", Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"4",ChatColor.RED+String.valueOf(goldenApple)+"/2"));
            inv.setItem(15,apple);
        }else{
            inv.setItem(15,barrier);
        }
        if(notchApple<1){
            ItemStack notch = createItem(Material.ENCHANTED_GOLDEN_APPLE, ChatColor.LIGHT_PURPLE + "Pomme dorée enchantée", Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"10",ChatColor.RED+String.valueOf(notchApple)+"/1"));
            inv.setItem(16,notch);
        }else{
            inv.setItem(16,barrier);
        }
        if(enderPearlAmount<3){
            ItemStack ender = createItem(Material.ENDER_PEARL, ChatColor.LIGHT_PURPLE + "Perle de l'End", Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"2",ChatColor.RED+String.valueOf(enderPearlAmount)+"/3"));
            inv.setItem(8,ender);
        }else{
            inv.setItem(8,barrier);
        }
        int shieldDuration = countShield(player);
        ItemStack shield = createItem(Material.SHIELD, ChatColor.LIGHT_PURPLE + "Duration du bouclier +10", Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"1",ChatColor.RED+String.valueOf(336-shieldDuration)+"/336"));
        if(zp.getZodiac().getName().equals("Balance")){
            ItemStack antiShield = createItem(Material.BARRIER, ChatColor.RED + "Achat impossible", Arrays.asList("Votre zodiac ne peut pas bénéficier du bouclier"));
            inv.setItem(17,antiShield);
        }else{
            inv.setItem(17,shield);
        }
        inv.setItem(19,damage);
        inv.setItem(18,zodiacItem);
        inv.setItem(4,thorns);
        inv.setItem(3,projProtection);
        inv.setItem(2, fireProtection);
        inv.setItem(1,protection);
        inv.setItem(0, armor);
        return inv;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Zodiac dummy = new Zodiac();
        int lobbyId = plugin.getLobbyPlayers().get(player);
        Lobby lobby = null;
        for(Lobby l : plugin.getLobbies()){
            if(l.getId().equals(String.valueOf(lobbyId))){
                lobby = l;
            }
        }
        if(lobby == null){
            return;
        }
        ArrayList<Zodiac> zodiacs = dummy.getZodiac();
        Inventory inv = event.getInventory();
        ItemStack item = event.getCurrentItem();
        if(item != null && inv.contains(Material.GOLDEN_BOOTS) && inv.contains(Material.GOLDEN_SWORD) && inv.contains(Material.GLASS) && lobby.getLobbyState()==LobbyState.SELECTING){
            switch(item.getType()){
                case GOLDEN_SWORD:
                    lobby.setZodiacFor(player, zodiacs.get(0));
                    break;
                case GOLDEN_BOOTS:
                    lobby.setZodiacFor(player, zodiacs.get(1));
                    break;
                case GLASS:
                    lobby.setZodiacFor(player, zodiacs.get(2));
                    break;
                case TOTEM_OF_UNDYING:
                    lobby.setZodiacFor(player, zodiacs.get(3));
                    break;
                case TIPPED_ARROW:
                    lobby.setZodiacFor(player, zodiacs.get(4));
                    break;
                case BOW:
                    lobby.setZodiacFor(player, zodiacs.get(5));
                    break;
                case RABBIT_FOOT:
                    lobby.setZodiacFor(player, zodiacs.get(6));
                    break;
                case FEATHER:
                    lobby.setZodiacFor(player, zodiacs.get(7));
                    break;
                case GOLDEN_HORSE_ARMOR:
                    lobby.setZodiacFor(player, zodiacs.get(8));
                    break;
                case VINE:
                    lobby.setZodiacFor(player, zodiacs.get(9));
                    break;
                case BONE:
                    lobby.setZodiacFor(player, zodiacs.get(10));
                    break;
                case PIGLIN_HEAD:
                    lobby.setZodiacFor(player, zodiacs.get(11));
                    break;
            }
            player.closeInventory();
            player.getInventory().clear();
            player.updateInventory();
        }
        if(item != null && event.getView().getTitle().equals(ChatColor.LIGHT_PURPLE+"Observatoire") && lobby.getLobbyState()==LobbyState.SELECTING && event.getClickedInventory() == event.getView().getTopInventory()){
            event.setCancelled(true);
            if(item.getType()==Material.BARRIER){
                return;
            }
            ZodiacPlayer zp = null;
            for(ZodiacPlayer z : lobby.getZodiacPlayers()){
                if(z.getPlayer().equals(player)){
                    zp = z;
                }
            }
            int amount = 0;
            for(ItemStack i : player.getInventory().getContents()){
                if(i!=null){
                    if(i.getType() == Material.OMINOUS_TRIAL_KEY){
                        amount += i.getAmount();
                    }
                }

            }
            boolean bought = false;
            switch(event.getSlot()){
                case 0:
                    switch(zp.getArmorMaterialLevel()){
                        case 0:
                            if(amount > 6){
                                zp.addArmorLevel(1);
                                removeGloryPoints(zp.getPlayer(),7);
                                bought = true;
                            }
                            break;
                        case 1, 2:
                            if(amount > 7){
                                zp.addArmorLevel(1);
                                removeGloryPoints(zp.getPlayer(),8);
                                bought = true;

                            }
                            break;
                        case 3:
                            if(amount > 8){
                                zp.addArmorLevel(1);
                                removeGloryPoints(zp.getPlayer(),9);
                                bought = true;

                            }
                            break;
                    }
                    break;
                case 1:
                    switch(zp.getProtectionLevel()){
                        case 0:
                            if(amount > 6){
                                zp.addProtectionLevel(1);
                                removeGloryPoints(zp.getPlayer(),7);
                                bought = true;

                            }
                            break;
                        case 1, 2:
                            if(amount > 7){
                                zp.addProtectionLevel(1);
                                removeGloryPoints(zp.getPlayer(),8);
                                bought = true;

                            }
                            break;
                        case 3:
                            if(amount > 9){
                                zp.addProtectionLevel(1);
                                removeGloryPoints(zp.getPlayer(),9);
                                bought = true;

                            }
                            break;
                    }
                    break;
                case 2:
                    switch(zp.getFireProtectionLevel()){
                        case 0:
                            if(amount > 2){
                                zp.addFireProtectionLevel(1);
                                removeGloryPoints(zp.getPlayer(),3);
                                bought = true;

                            }
                            break;
                        case 1, 2:
                            if(amount > 3){
                                zp.addFireProtectionLevel(1);
                                removeGloryPoints(zp.getPlayer(),4);
                                bought = true;

                            }
                            break;
                        case 3:
                            if(amount > 4){
                                zp.addFireProtectionLevel(1);
                                removeGloryPoints(zp.getPlayer(),5);
                                bought = true;

                            }
                            break;
                    }
                    break;
                case 3:
                    switch(zp.getArrowProtectionLevel()){
                        case 0:
                            if(amount > 2){
                                zp.addArrowProtectionLevel(1);
                                removeGloryPoints(zp.getPlayer(),3);
                                bought = true;

                            }
                            break;
                        case 1, 2:
                            if(amount > 3){
                                zp.addArrowProtectionLevel(1);
                                removeGloryPoints(zp.getPlayer(),4);
                                bought = true;

                            }
                            break;
                        case 3:
                            if(amount > 4){
                                zp.addArrowProtectionLevel(1);
                                removeGloryPoints(zp.getPlayer(),5);
                                bought = true;

                            }
                            break;
                    }
                    break;
                case 4:
                    switch(zp.getThornsLevel()){
                        case 0:
                            if(amount > 4){
                                zp.addThornsLevel(1);
                                removeGloryPoints(zp.getPlayer(),5);
                                bought = true;

                            }
                            break;
                        case 1:
                            if(amount > 5){
                                zp.addThornsLevel(1);
                                removeGloryPoints(zp.getPlayer(),6);
                                bought = true;

                            }
                            break;
                        case 2:
                            if(amount > 6){
                                zp.addThornsLevel(1);
                                removeGloryPoints(zp.getPlayer(),7);
                                bought = true;

                            }
                            break;
                    }
                    break;
                case 6:
                    if(amount > 0){
                        removeGloryPoints(zp.getPlayer(),1);
                        player.getInventory().addItem(createItem(Material.BREAD,ChatColor.LIGHT_PURPLE+"Pain", List.of()));
                        bought = true;

                    }
                    break;
                case 7:
                    if(amount > 0){
                        removeGloryPoints(zp.getPlayer(),1);
                        player.getInventory().addItem(createItem(Material.GOLDEN_CARROT,ChatColor.LIGHT_PURPLE+"Carotte dorée", List.of()));
                        bought = true;

                    }
                    break;
                case 8:
                    if(amount > 1){
                        removeGloryPoints(zp.getPlayer(),2);
                        player.getInventory().addItem(createItem(Material.ENDER_PEARL,ChatColor.LIGHT_PURPLE+"Perle de l'End", List.of()));
                        bought = true;

                    }
                    break;
                case 15:
                    if(amount > 3){
                        removeGloryPoints(zp.getPlayer(),4);
                        player.getInventory().addItem(createItem(Material.GOLDEN_APPLE,ChatColor.LIGHT_PURPLE+"Pomme dorée", List.of()));
                        bought = true;

                    }
                    break;
                case 16:
                    if(amount > 9){
                        removeGloryPoints(zp.getPlayer(),10);
                        player.getInventory().addItem(createItem(Material.ENCHANTED_GOLDEN_APPLE,ChatColor.LIGHT_PURPLE+"Pomme dorée enchantée", List.of()));
                        bought = true;

                    }
                    break;
                case 17:
                    if(amount > 0){
                        removeGloryPoints(zp.getPlayer(),1);
                        bought = true;
                        int shieldPlace = -1;
                        for(int i = 0;i<player.getInventory().getSize();i++){
                            if(player.getInventory().getItem(i)!=null){
                                if(player.getInventory().getItem(i).getType() == Material.SHIELD){
                                    shieldPlace = i;
                                }
                            }

                        }
                        if(shieldPlace == -1){
                            ItemStack shield = new ItemStack(Material.SHIELD);
                            ItemMeta itemMeta = shield.getItemMeta();
                            itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Shield");
                            shield.setItemMeta(itemMeta);
                            shield.setDurability((short) 326);
                            player.getInventory().addItem(shield);
                        }else{
                            ItemStack shield = player.getInventory().getItem(shieldPlace);
                            if(shield.getDurability() < 11){
                                shield.setDurability((short) 1);
                            }else{
                                shield.setDurability((short) (shield.getDurability()- 10));
                            }
                            player.getInventory().setItem(shieldPlace, shield);
                        }
                    }
                    break;
                case 18:
                    switch(zp.getZodiacLevel()){
                        case 1:
                            if(amount > 6){
                                zp.addZodiacLevel(1);
                                removeGloryPoints(zp.getPlayer(),7);
                                bought = true;

                            }
                            break;
                        case 2, 3:
                            if(amount > 7){
                                zp.addZodiacLevel(1);
                                removeGloryPoints(zp.getPlayer(),8);
                                bought = true;

                            }
                            break;
                        case 4, 5:
                            if(amount > 8){
                                zp.addZodiacLevel(1);
                                removeGloryPoints(zp.getPlayer(),9);
                                bought = true;

                            }
                            break;
                        case 6:
                            if(amount > 9){
                                zp.addZodiacLevel(1);
                                removeGloryPoints(zp.getPlayer(),10);
                                bought = true;

                            }
                            break;
                    }
                    break;
                case 19:
                    switch(zp.getDamageLevel()){
                        case 0:
                            if(amount > 6){
                                zp.addDamageLevel(1);
                                removeGloryPoints(zp.getPlayer(),7);
                                bought = true;

                            }
                            break;
                        case 1:
                            if(amount > 7){
                                zp.addDamageLevel(1);
                                removeGloryPoints(zp.getPlayer(),8);
                                bought = true;

                            }
                            break;
                        case 2:
                            if(amount > 8){
                                zp.addDamageLevel(1);
                                removeGloryPoints(zp.getPlayer(),9);
                                bought = true;

                            }
                            break;
                        case 3:
                            if(amount > 9){
                                zp.addDamageLevel(1);
                                removeGloryPoints(zp.getPlayer(),10);
                                bought = true;

                            }
                            break;
                        case 4:
                            if(amount > 10){
                                zp.addDamageLevel(1);
                                removeGloryPoints(zp.getPlayer(),11);
                                bought = true;

                            }
                            break;
                    }
                    break;
                case 20:
                    if(zp.getZodiac().getName().equals("Dragon") || zp.getZodiac().getName().equals("Coq") || zp.getZodiac().getName().equals("Lapin")){
                        switch(zp.getBreachLevel()){
                            case 0:
                                if(amount > 8){
                                    zp.addBreachLevel(1);
                                    removeGloryPoints(zp.getPlayer(),9);
                                    bought = true;

                                }
                                break;
                            case 1, 2:
                                if(amount > 9){
                                    zp.addBreachLevel(1);
                                    removeGloryPoints(zp.getPlayer(),10);
                                    bought = true;

                                }
                                break;
                            case 3:
                                if(amount > 10){
                                    zp.addBreachLevel(1);
                                    removeGloryPoints(zp.getPlayer(),11);
                                    bought = true;

                                }
                                break;
                        }
                        break;
                    }
                    if(zp.getZodiac().getName().equals("Cochon") || zp.getZodiac().getName().equals("Capricorne")){
                        switch(zp.getPiercingLevel()){
                            case 0:
                                if(amount > 3){
                                    zp.addPiercingLevel(1);
                                    removeGloryPoints(zp.getPlayer(),4);
                                    bought = true;

                                }
                                break;
                            case 1, 2:
                                if(amount > 4){
                                    zp.addPiercingLevel(1);
                                    removeGloryPoints(zp.getPlayer(),5);
                                    bought = true;

                                }
                                break;
                            case 3:
                                if(amount > 5){
                                    zp.addPiercingLevel(1);
                                    removeGloryPoints(zp.getPlayer(),6);
                                    bought = true;

                                }
                                break;
                        }
                        break;
                    }
                    break;
                case 21:
                    if(zp.getZodiac().getName().equals("Cochon") || zp.getZodiac().getName().equals("Capricorne")){
                        switch(zp.getQuickChargeLevel()){
                            case 0:
                                if(amount > 3){
                                    zp.addQuickChargeLevel(1);
                                    removeGloryPoints(zp.getPlayer(),4);
                                    bought = true;

                                }
                                break;
                            case 1:
                                if(amount > 4){
                                    zp.addQuickChargeLevel(1);
                                    removeGloryPoints(zp.getPlayer(),5);
                                    bought = true;

                                }
                                break;
                            case 2:
                                if(amount > 5){
                                    zp.addQuickChargeLevel(1);
                                    removeGloryPoints(zp.getPlayer(),6);
                                    bought = true;

                                }
                                break;
                        }
                        break;
                    }
                    break;
                case 22:
                    if(zp.getZodiac().getName().equals("Cochon") || zp.getZodiac().getName().equals("Capricorne")){
                        if (zp.getMultishotLevel() == 0) {
                            if (amount > 9) {
                                zp.addMultishotLevel(1);
                                removeGloryPoints(zp.getPlayer(),10);
                                bought = true;

                            }
                        }
                        break;
                    }
                    break;

            }
            event.getInventory().setContents(getInventory(zp, player).getContents());
            player.updateInventory();
            if(bought){
                player.getWorld().spawnParticle(Particle.TRIAL_SPAWNER_DETECTION_OMINOUS, player.getLocation().add(0,1,0), 80, 0.5, 0.5, 0.5, 0.01);
                player.getWorld().playSound(zp.getPlayer().getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0f, 1.0f);
            }

        }

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


    public ItemStack createItem(Material m, String name, List<String> lore){
        ItemStack i = new ItemStack(m);
        ItemMeta meta = i.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(name);
        if(!lore.isEmpty()){
            meta.setLore(lore);
        }
        i.setItemMeta(meta);
        if(m.equals(Material.BREAD)){
            i.setAmount(2);
        }
        return i;
    }

    public int countMaterial(Player player, Material m) {
        int count = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == m) {
                count += item.getAmount();
            }
        }
        return count;
    }

    public int countShield(Player player){
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == Material.SHIELD) {
                return item.getDurability();
            }
        }
        return 336;
    }

    public void removeGloryPoints(Player player, int amount) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null
                    && item.getType() == Material.OMINOUS_TRIAL_KEY
                    && item.hasItemMeta()
                    && ChatColor.stripColor(item.getItemMeta().getDisplayName()).equals("Point de Gloire")) {

                int remove = Math.min(item.getAmount(), amount);
                item.setAmount(item.getAmount() - remove);
                amount -= remove;

                if (amount <= 0) {
                    break;
                }
            }
        }
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


}
