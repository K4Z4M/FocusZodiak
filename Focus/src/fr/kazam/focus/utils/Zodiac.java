package fr.kazam.focus.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Zodiac {

    private String name;
    private ItemStack flag;
    private List<String> description;
    private ItemStack itemLvl1;
    private ItemStack itemLvl2;
    private ItemStack itemLvl3;
    private ItemStack itemLvl4;
    private ItemStack itemLvl5;
    private ItemStack itemLvl6;
    private ItemStack itemLvl7;
    private List<String> descLvl2;
    private List<String> descLvl3;
    private List<String> descLvl4;
    private List<String> descLvl5;
    private List<String> descLvl6;
    private List<String> descLvl7;

    public Zodiac(){
        this.name = "";
        this.flag = null;
        this.description = new ArrayList<>();
        this.itemLvl1 = new ItemStack(Material.AIR);
        this.itemLvl2 = new ItemStack(Material.AIR);
        this.itemLvl3 = new ItemStack(Material.AIR);
        this.itemLvl4 = new ItemStack(Material.AIR);
        this.itemLvl5 = new ItemStack(Material.AIR);
        this.itemLvl6 = new ItemStack(Material.AIR);
        this.itemLvl7 = new ItemStack(Material.AIR);
        this.descLvl2 = new ArrayList<>();
        this.descLvl3 = new ArrayList<>();
        this.descLvl4 = new ArrayList<>();
        this.descLvl5 = new ArrayList<>();
        this.descLvl6 = new ArrayList<>();
        this.descLvl7 = new ArrayList<>();

    }


    public Zodiac(String name, ItemStack flag, List<String> description, ItemStack itemLvl1, ItemStack itemLvl2, ItemStack itemLvl3, ItemStack itemLvl4, ItemStack itemLvl5, ItemStack itemLvl6, ItemStack itemLvl7, List<String> descLvl2, List<String> descLvl3, List<String> descLvl4, List<String> descLvl5, List<String> descLvl6, List<String> descLvl7) {
        this.name = name;
        this.flag = flag;
        this.description = description;
        this.itemLvl1 = itemLvl1;
        this.itemLvl2 = itemLvl2;
        this.itemLvl3 = itemLvl3;
        this.itemLvl4 = itemLvl4;
        this.itemLvl5 = itemLvl5;
        this.itemLvl6 = itemLvl6;
        this.itemLvl7 = itemLvl7;
        this.descLvl2 = descLvl2;
        this.descLvl3 = descLvl3;
        this.descLvl4 = descLvl4;
        this.descLvl5 = descLvl5;
        this.descLvl6 = descLvl6;
        this.descLvl7 = descLvl7;
    }

    public String getName() {
        return name;
    }

    public ItemStack getFlag() {
        return flag;
    }

    public List<String> getDescription() {
        return description;
    }

    public ItemStack getItemLvl1() {
        return itemLvl1;
    }

    public ItemStack getItemLvl2() {
        return itemLvl2;
    }

    public ItemStack getItemLvl3() {
        return itemLvl3;
    }

    public ItemStack getItemLvl4() {
        return itemLvl4;
    }

    public ItemStack getItemLvl5() {
        return itemLvl5;
    }

    public ItemStack getItemLvl6() {
        return itemLvl6;
    }

    public ItemStack getItemLvl7() {
        return itemLvl7;
    }

    public List<String> getDescLvl2() {
        return descLvl2;
    }

    public List<String> getDescLvl3() {
        return descLvl3;
    }

    public List<String> getDescLvl4() {
        return descLvl4;
    }

    public List<String> getDescLvl5() {
        return descLvl5;
    }

    public List<String> getDescLvl6() {
        return descLvl6;
    }

    public List<String> getDescLvl7() {
        return descLvl7;
    }

    public ArrayList<Zodiac> getZodiac() {
        ArrayList<Zodiac> zodiacs = new ArrayList<Zodiac>();
        //******************************************LION***********************************************
        List<String> lionLore = Arrays.asList("",ChatColor.LIGHT_PURPLE+"Arme : Épée",ChatColor.RED+"DÉG:⭐⭐⭐⭐⭐",ChatColor.AQUA+ "UTI:⭐",ChatColor.GREEN+ "DEF:⭐", " ", ChatColor.WHITE+"Point fort : Constant");
        Zodiac lion = new Zodiac("Lion",createItem(Material.GOLDEN_SWORD,ChatColor.WHITE+"Lion"),lionLore,createItem(Material.WOODEN_SWORD,ChatColor.GOLD+"Croc du Lion"),
            createItem(Material.STONE_SWORD,ChatColor.GOLD+"Croc du Lion"),createItem(Material.IRON_SWORD,ChatColor.GOLD+"Croc du Lion"),
            createItem(Material.DIAMOND_SWORD,ChatColor.GOLD+"Croc du Lion"),createItem(Material.DIAMOND_SWORD,ChatColor.GOLD+"Croc du Lion"),
            createItem(Material.NETHERITE_SWORD,ChatColor.GOLD+"Croc du Lion"),createItem(Material.NETHERITE_SWORD,ChatColor.GOLD+"Croc du Lion"),
            Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"7",
                    ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en bois ->"+ChatColor.GOLD+" Épée en pierre",
                    ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Force I (4s) à chaque kill -> Force I (4s) à chaque kill",
                    ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.GOLD+"NOUVEAU! -> AA + 1 dégât (15s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"8",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en pierre ->"+ChatColor.GOLD+" Épée en fer",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Force I (4s) à chaque kill ->"+ ChatColor.GOLD+ " Force II (8s) à chaque kill",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"AA + 1 dégât (15s) -> AA + 1 dégât (15s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"8",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en fer ->"+ChatColor.GOLD+" Épée en diamant",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Force II (8s) à chaque kill -> Force II (8s) à chaque kill",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"AA + 1 dégât (15s) -> AA + 1 dégât (15s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"9",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en diamant -> Épée en diamant",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Force II (8s) à chaque kill ->"+ ChatColor.GOLD+ " Force III (12s) à chaque kill",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"AA + 1 dégât (15s) -> "+ChatColor.GOLD+" AA + 2 dégât (10s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"9",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en diamant ->"+ChatColor.GOLD+" Épée en netherite",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Force III (12s) à chaque kill -> Force III (12s) à chaque kill",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"AA + 2 dégât (10s) -> AA + 2 dégât (10s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"10",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en netherite -> Épée en netherite",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Force III (12s) à chaque kill ->"+ ChatColor.GOLD+ " Force IV (16s) à chaque kill",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"AA + 2 dégât (10s) -> "+ChatColor.GOLD+" AA + 3 dégât (5s)")
                );
        zodiacs.add(lion);
        //******************************************TAUREAU***********************************************
        List<String> taureauLore = Arrays.asList("",ChatColor.LIGHT_PURPLE+"Arme : Épée",ChatColor.RED+"DÉG:⭐⭐⭐⭐",ChatColor.AQUA+ "UTI:⭐⭐",ChatColor.GREEN+ "DEF:⭐"," ", ChatColor.WHITE+"Point fort : Fin");
        Zodiac taureau = new Zodiac("Taureau",createItem(Material.GOLDEN_BOOTS,ChatColor.WHITE+"Taureau"),taureauLore,createItem(Material.WOODEN_SWORD,ChatColor.GOLD+"Corne du Taureau"),
                createItem(Material.STONE_SWORD,ChatColor.GOLD+"Corne du Taureau"),createItem(Material.STONE_SWORD,ChatColor.GOLD+"Corne du Taureau"),
                createItem(Material.IRON_SWORD,ChatColor.GOLD+"Corne du Taureau"),createItem(Material.DIAMOND_SWORD,ChatColor.GOLD+"Corne du Taureau"),
                createItem(Material.DIAMOND_SWORD,ChatColor.GOLD+"Corne du Taureau"),createItem(Material.NETHERITE_SWORD,ChatColor.GOLD+"Corne du Taureau"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"7",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en bois ->"+ChatColor.GOLD+" Épée en pierre",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Force I avec 4 ennemis proches -> Force I avec 4 ennemis proches",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.GOLD+"NOUVEAU! -> Speed I 5s (20s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"8",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en pierre -> Épée en pierre",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Force I avec 4 ennemis proches ->"+ ChatColor.GOLD+ " Force I avec 3 ennemis proches",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Speed I 5s (20s) -> "+ChatColor.GOLD+" Speed I 5s (15s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"8",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en pierre ->"+ChatColor.GOLD+" Épée en fer",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Force I avec 3 ennemis proches -> Force I avec 3 ennemis proches",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Speed I 5s (20s) -> "+ChatColor.GOLD+" Speed I 7s (15s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"9",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en fer ->"+ChatColor.GOLD+" Épée en diamant",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Force I avec 3 ennemis proches ->"+ ChatColor.GOLD+ " Force I avec 2 ennemis proches",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Speed I 7s (15s) -> "+ChatColor.GOLD+" Speed I 10s (15s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"9",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en diamant -> Épée en diamant",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Force I avec 2 ennemis proches -> Force I avec 2 ennemis proches",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Speed I 10s (15s) -> "+ChatColor.GOLD+" Speed II 10s (15s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"10",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en diamant ->"+ChatColor.GOLD+" Épée en netherite",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Force I avec 2 ennemis proches ->"+ ChatColor.GOLD+ " Force I avec 1 ennemi proche",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Speed II 10s (15s) -> "+ChatColor.GOLD+" Speed II 10s (10s)")
        );
        zodiacs.add(taureau);
        //******************************************Vierge***********************************************
        List<String> viergeLore = Arrays.asList("",ChatColor.LIGHT_PURPLE+"Arme : Hache",ChatColor.RED+"DÉG:⭐⭐⭐⭐",ChatColor.AQUA+ "UTI:⭐⭐⭐",ChatColor.GREEN+ "DEF:"," ", ChatColor.WHITE+"Point fort : Début");
        Zodiac vierge = new Zodiac("Vierge",createItem(Material.GLASS,ChatColor.WHITE+"Vierge"),viergeLore,createItem(Material.STONE_AXE,ChatColor.GOLD+"Trancheuse Vierge"),
                createItem(Material.STONE_AXE,ChatColor.GOLD+"Trancheuse Vierge"),createItem(Material.STONE_AXE,ChatColor.GOLD+"Trancheuse Vierge"),
                createItem(Material.DIAMOND_AXE,ChatColor.GOLD+"Trancheuse Vierge"),createItem(Material.DIAMOND_AXE,ChatColor.GOLD+"Trancheuse Vierge"),
                createItem(Material.DIAMOND_AXE,ChatColor.GOLD+"Trancheuse Vierge"),createItem(Material.NETHERITE_AXE,ChatColor.GOLD+"Trancheuse Vierge"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"7",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Hache en pierre -> Hache en pierre",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Inflige 1 dégât dans le dos ->"+ ChatColor.GOLD+ " Inflige 2 dégâts dans le dos",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.GOLD+"NOUVEAU! -> Invisibilité 5s (30s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"8",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Hache en pierre -> Hache en pierre",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Inflige 2 dégât dans le dos ->"+ ChatColor.GOLD+ " Inflige 3 dégâts dans le dos",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Invisibilité 5s (30s) -> "+ChatColor.GOLD+" Invisibilité 10s (30s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"8",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Hache en pierre ->"+ChatColor.GOLD+" Hache en diamant",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Inflige 3 dégât dans le dos ->"+ ChatColor.GOLD+ " Inflige 4 dégâts dans le dos",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Invisibilité 10s (30s) -> "+ChatColor.GOLD+" Invisibilité 15s (30s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"9",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Hache en diamant -> Hache en diamant",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Inflige 4 dégât dans le dos -> Inflige 4 dégâts dans le dos",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Invisibilité 15s (30s) -> "+ChatColor.GOLD+" Invisibilité 15s (25s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"9",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Hache en diamant -> Hache en diamant",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Inflige 4 dégât dans le dos -> Inflige 4 dégâts dans le dos",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Invisibilité 15s (25s) -> "+ChatColor.GOLD+" Invisibilité 15s (20s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"10",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Hache en diamant ->"+ChatColor.GOLD+" Hache en netherite",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Inflige 4 dégât dans le dos ->"+ ChatColor.GOLD+ " Inflige 5 dégâts dans le dos",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Invisibilité 15s (20s) -> "+ChatColor.GOLD+" Invisibilité 15s (15s)")
        );
        zodiacs.add(vierge);
        //******************************************Balance***********************************************
        List<String> balanceLore = Arrays.asList("",ChatColor.LIGHT_PURPLE+"Arme : Épée",ChatColor.RED+"DÉG:⭐",ChatColor.AQUA+ "UTI:⭐⭐",ChatColor.GREEN+ "DEF:⭐⭐⭐⭐"," ", ChatColor.WHITE+"Point fort : Fin");
        Zodiac balance = new Zodiac("Balance",createItem(Material.TOTEM_OF_UNDYING,ChatColor.WHITE+"Balance"),balanceLore,createItem(Material.WOODEN_SWORD,ChatColor.GOLD+"Punition de la Balance"),
                createItem(Material.WOODEN_SWORD,ChatColor.GOLD+"Punition de la Balance"),createItem(Material.WOODEN_SWORD,ChatColor.GOLD+"Punition de la Balance"),
                createItem(Material.STONE_SWORD,ChatColor.GOLD+"Punition de la Balance"),createItem(Material.STONE_SWORD,ChatColor.GOLD+"Punition de la Balance"),
                createItem(Material.STONE_SWORD,ChatColor.GOLD+"Punition de la Balance"),createItem(Material.IRON_SWORD,ChatColor.GOLD+"Punition de la Balance"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"7",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en bois -> Épée en bois",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Aucun -> Aucun",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.GOLD+"NOUVEAU! -> Invocation"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"8",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en bois -> Épée en bois",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Aucun ->"+ ChatColor.GOLD+ " 1 Totem d'immunité",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Invocation -> "+ChatColor.GOLD+" + Puissante"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"8",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en bois ->"+ChatColor.GOLD+" Épée en pierre",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"1 Totem d'immunité -> 1 Totem d'immunité",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Invocation -> "+ChatColor.GOLD+" + Puissante"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"9",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en pierre -> Épée en pierre",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"1 Totem d'immunité -> 1 Totem d'immunité",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Invocation -> "+ChatColor.GOLD+" + Puissante"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"9",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en pierre -> Épée en pierre",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"1 Totem d'immunité -> 1 Totem d'immunité",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Invocation -> "+ChatColor.GOLD+" + Puissante"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"10",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en pierre ->"+ChatColor.GOLD+" Épée en fer",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"1 Totem d'immunité ->"+ ChatColor.GOLD+ " 2 Totems d'immunité",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Invocation -> "+ChatColor.GOLD+" + Puissante")
        );
        zodiacs.add(balance);
        //******************************************Scorpion***********************************************
        List<String> scorpionLore = Arrays.asList("",ChatColor.LIGHT_PURPLE+"Arme : Arc",ChatColor.RED+"DÉG:⭐⭐",ChatColor.AQUA+ "UTI:⭐⭐⭐⭐",ChatColor.GREEN+ "DEF:⭐"," ", ChatColor.WHITE+"Point fort : Constant");
        Zodiac scorpion = new Zodiac("Scorpion",createItem(Material.TIPPED_ARROW,ChatColor.WHITE+"Scorpion"),scorpionLore,createItem(Material.BOW,ChatColor.GOLD+"Dard du Scorpion"),
                createItem(Material.BOW,ChatColor.GOLD+"Dard du Scorpion"),createItem(Material.BOW,ChatColor.GOLD+"Dard du Scorpion"),
                createItem(Material.BOW,ChatColor.GOLD+"Dard du Scorpion"),createItem(Material.BOW,ChatColor.GOLD+"Dard du Scorpion"),
                createItem(Material.BOW,ChatColor.GOLD+"Dard du Scorpion"),createItem(Material.BOW,ChatColor.GOLD+"Dard du Scorpion"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"7",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Arc -> Arc",
                        ChatColor.AQUA+"Passif : "+ChatColor.GOLD+"NOUVEAU! -> Flèches POISON I pendant 1s",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.GOLD+"NOUVEAU! -> Wither I pendant 4s (15s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"8",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Arc -> Arc",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Flèches POISON I pendant 1s -> "+ChatColor.GOLD+"Flèches POISON I pendant 2s",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Wither I pendant 4s (15s) -> Wither I pendant 4s (15s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"8",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Arc -> Arc",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Flèches POISON I pendant 2s -> Flèches POISON I pendant 2s",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Wither I pendant 4s (15s) -> "+ChatColor.GOLD+" Wither I pendant 8s (15s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"9",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Arc -> Arc",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Flèches POISON I pendant 2s -> "+ChatColor.GOLD+"Flèches POISON I pendant 3s",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Wither I pendant 8s (15s) -> Wither I pendant 8s (15s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"9",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Arc -> Arc",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Flèches POISON I pendant 3s -> Flèches POISON I pendant 3s",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Wither I pendant 8s (15s) -> "+ChatColor.GOLD+" Wither II pendant 8s (15s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"10",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Arc -> Arc",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Flèches POISON I pendant 3s -> "+ChatColor.GOLD+"Flèches POISON II pendant 3s",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Wither II pendant 8s (15s) -> Wither II pendant 8s (15s)")
        );
        zodiacs.add(scorpion);
        //******************************************Sagitaire***********************************************
        List<String> sagitaireLore = Arrays.asList("",ChatColor.LIGHT_PURPLE+"Arme : Arc",ChatColor.RED+"DÉG:⭐⭐⭐⭐⭐",ChatColor.AQUA+ "UTI:⭐",ChatColor.GREEN+ "DEF:⭐"," ", ChatColor.WHITE+"Point fort : Fin");
        Zodiac sagitaire = new Zodiac("Sagitaire",createItem(Material.BOW,ChatColor.WHITE+"Sagitaire"),sagitaireLore,createItem(Material.BOW,ChatColor.GOLD+"Arc du Sagitaire"),
                createItem(Material.BOW,ChatColor.GOLD+"Arc du Sagitaire"),createItem(Material.BOW,ChatColor.GOLD+"Arc du Sagitaire"),
                createItem(Material.BOW,ChatColor.GOLD+"Arc du Sagitaire"),createItem(Material.BOW,ChatColor.GOLD+"Arc du Sagitaire"),
                createItem(Material.BOW,ChatColor.GOLD+"Arc du Sagitaire"),createItem(Material.BOW,ChatColor.GOLD+"Arc du Sagitaire"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"7",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Arc -> Arc",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Aucun -> Aucun",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.GOLD+"NOUVEAU! -> + Power II pendant 5s (30s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"8",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Arc -> Arc",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Aucun -> Aucun",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Power II pendant 5s (30s) -> "+ChatColor.GOLD+" Power II pendant 8s (26s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"8",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Arc -> Arc",
                        ChatColor.AQUA+"Passif : "+ ChatColor.GOLD+ "NOUVEAU! -> Flame I",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Power II pendant 8s (26s) -> "+ChatColor.GOLD+" Power II pendant 11s (22s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"9",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Arc -> Arc",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Flame I -> Flame I",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Power II pendant 11s (22s) -> "+ChatColor.GOLD+" Power II pendant 14s (18s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"9",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Arc -> Arc",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Flame I -> Flame I",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Power II pendant 14s (18s) -> "+ChatColor.GOLD+" Power II pendant 17s (14s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"10",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Arc -> Arc",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Flame I -> Flame I",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Power II pendant 17s (14s) -> "+ChatColor.GOLD+" Power II pendant 20s (10s)")
        );
        zodiacs.add(sagitaire);
        //******************************************Lapin***********************************************
        List<String> lapinLore = Arrays.asList("",ChatColor.LIGHT_PURPLE+"Arme : Masse",ChatColor.RED+"DÉG:⭐⭐⭐",ChatColor.AQUA+ "UTI:⭐⭐⭐",ChatColor.GREEN+ "DEF:⭐"," ", ChatColor.WHITE+"Point fort : Constant");
        Zodiac lapin = new Zodiac("Lapin",createItem(Material.RABBIT_FOOT,ChatColor.WHITE+"Lapin"),lapinLore,createItem(Material.MACE,ChatColor.GOLD+"Coup du Lapin"),
                createItem(Material.MACE,ChatColor.GOLD+"Coup du Lapin"),createItem(Material.MACE,ChatColor.GOLD+"Coup du Lapin"),
                createItem(Material.MACE,ChatColor.GOLD+"Coup du Lapin"),createItem(Material.MACE,ChatColor.GOLD+"Coup du Lapin"),
                createItem(Material.MACE,ChatColor.GOLD+"Coup du Lapin"),createItem(Material.MACE,ChatColor.GOLD+"Coup du Lapin"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"7",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Masse -> Masse",
                        ChatColor.AQUA+"Passif : "+ ChatColor.GOLD+ " NOUVEAU! -> Saut double (0.75)",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.GOLD+"NOUVEAU! -> Propulsion 1 bloc (20s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"8",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Masse -> Masse",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Saut double (0.75) ->"+ ChatColor.GOLD+ " Saut double (1)",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Propulsion 1 bloc (20s) -> "+ChatColor.GOLD+" Propulsion 1.5 bloc (18s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"8",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Masse -> Masse",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Saut double (1) ->"+ ChatColor.GOLD+ " Saut double (1.25)",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Propulsion 1.5 bloc (18s) -> "+ChatColor.GOLD+" Propulsion 2 bloc (16s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"9",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Masse -> Masse",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Saut double (1.25) ->"+ ChatColor.GOLD+ " Saut double (1.5)",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Propulsion 2 bloc (16s) -> "+ChatColor.GOLD+" Propulsion 2.5 bloc (14s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"9",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Masse -> Masse",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Saut double (1.5) ->"+ ChatColor.GOLD+ " Saut double (1.75)",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Propulsion 2.5 bloc (14s) -> "+ChatColor.GOLD+" Propulsion 3 bloc (12s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"10",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Masse -> Masse",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Saut double (1.75) ->"+ ChatColor.GOLD+ " Saut double (2)",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Propulsion 3 bloc (12s) -> "+ChatColor.GOLD+" Propulsion 3.5 bloc (10s)")
        );
        zodiacs.add(lapin);
        //******************************************Coq***********************************************
        List<String> coqLore = Arrays.asList("",ChatColor.LIGHT_PURPLE+"Arme : Masse",ChatColor.RED+"DÉG:⭐⭐⭐",ChatColor.AQUA+ "UTI:⭐⭐",ChatColor.GREEN+ "DEF:⭐⭐"," ", ChatColor.WHITE+"Point fort : Fin");
        Zodiac coq = new Zodiac("Coq",createItem(Material.FEATHER,ChatColor.WHITE+"Coq"),coqLore,createItem(Material.MACE,ChatColor.GOLD+"Serre du Coq"),
                createItem(Material.MACE,ChatColor.GOLD+"Serre du Coq"),createItem(Material.MACE,ChatColor.GOLD+"Serre du Coq"),
                createItem(Material.MACE,ChatColor.GOLD+"Serre du Coq"),createItem(Material.MACE,ChatColor.GOLD+"Serre du Coq"),
                createItem(Material.MACE,ChatColor.GOLD+"Serre du Coq"),createItem(Material.MACE,ChatColor.GOLD+"Serre du Coq"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"7",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Masse -> Masse",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Aucun -> Aucun",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.GOLD+"NOUVEAU! -> Lévitation (2s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"8",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Masse -> Masse",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Aucun -> Aucun",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Lévitation (2s) -> "+ChatColor.GOLD+" Lévitation (4s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"8",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Masse -> Masse",
                        ChatColor.AQUA+"Passif : "+ ChatColor.GOLD+ " NOUVEAU! -> Wind Burst",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Lévitation (4s) -> "+ChatColor.GOLD+" Lévitation (8s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"9",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Masse -> Masse",
                        ChatColor.RED+"Passif : "+ChatColor.WHITE+"Wind Burst -> Wind Burst",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Lévitation (8s) -> "+ChatColor.GOLD+" Lévitation (16s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"9",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Masse -> Masse",
                        ChatColor.RED+"Passif : "+ChatColor.WHITE+"Wind Burst -> Wind Burst",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Lévitation (16s) -> "+ChatColor.GOLD+" Lévitation (32s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"10",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Masse -> Masse",
                        ChatColor.RED+"Passif : "+ChatColor.WHITE+"Wind Burst -> Wind Burst",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Lévitation (32s) -> "+ChatColor.GOLD+" Lévitation (64s)")
        );
        zodiacs.add(coq);
        //******************************************Cheval***********************************************
        List<String> chevalLore = Arrays.asList("",ChatColor.LIGHT_PURPLE+"Arme : Épée",ChatColor.RED+"DÉG:⭐⭐⭐",ChatColor.AQUA+ "UTI:⭐",ChatColor.GREEN+ "DEF:⭐⭐⭐"," ", ChatColor.WHITE+"Point fort : Début");
        Zodiac cheval = new Zodiac("Cheval",createItem(Material.GOLDEN_HORSE_ARMOR,ChatColor.WHITE+"Cheval"),chevalLore,createItem(Material.WOODEN_SWORD,ChatColor.GOLD+"Sabot du cheval"),
                createItem(Material.STONE_SWORD,ChatColor.GOLD+"Sabot du cheval"),createItem(Material.IRON_SWORD,ChatColor.GOLD+"Sabot du cheval"),
                createItem(Material.IRON_SWORD,ChatColor.GOLD+"Sabot du cheval"),createItem(Material.DIAMOND_SWORD,ChatColor.GOLD+"Sabot du cheval"),
                createItem(Material.DIAMOND_SWORD,ChatColor.GOLD+"Sabot du cheval"),createItem(Material.NETHERITE_SWORD,ChatColor.GOLD+"Sabot du cheval"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"7",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en bois ->"+ChatColor.GOLD+" Épée en pierre",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Résistance au Recul 0.1 ->"+ ChatColor.GOLD+ " Résistance au Recul 0.25",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.GOLD+"NOUVEAU! -> Explosion (30s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"8",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en pierre ->"+ChatColor.GOLD+" Épée en fer",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Résistance au Recul 0.25 ->"+ ChatColor.GOLD+ " Résistance au Recul 0.4",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Explosion (30s) -> Explosion (30s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"8",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en fer -> Épée en fer",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Résistance au Recul 0.4 ->"+ ChatColor.GOLD+ " Résistance au Recul 0.55",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Explosion (30s) -> Explosion (30s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"9",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en fer"+ChatColor.GOLD+" Épée en diamant",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Résistance au Recul 0.55 ->"+ ChatColor.GOLD+ " Résistance au Recul 0.7",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Explosion (30s) -> "+ChatColor.GOLD+" Explosion plus puissante (25s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"9",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en diamant -> Épée en diamant",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Résistance au Recul 0.7 ->"+ ChatColor.GOLD+ " Résistance au Recul 0.85",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Explosion (25s) -> Explosion (25s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"10",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en diamant ->"+ChatColor.GOLD+" Épée en netherite",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Résistance au Recul 0.85 ->"+ ChatColor.GOLD+ " Résistance au Recul 1",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Explosion (25s) -> "+ChatColor.GOLD+" Explosion plus puissante (20s)")
        );
        zodiacs.add(cheval);
        //******************************************Singe***********************************************
        List<String> singeLore = Arrays.asList("",ChatColor.LIGHT_PURPLE+"Arme : Épée & Arc",ChatColor.RED+"DÉG:⭐⭐⭐⭐⭐",ChatColor.AQUA+ "UTI:⭐",ChatColor.GREEN+ "DEF:⭐"," ", ChatColor.WHITE+"Point fort : Constant");
        Zodiac singe = new Zodiac("Singe",createItem(Material.VINE,ChatColor.WHITE+"Singe"),singeLore,createItem(Material.WOODEN_SWORD,ChatColor.GOLD+"Malice du Singe"),
                createItem(Material.WOODEN_SWORD,ChatColor.GOLD+"Malice du Singe"),createItem(Material.STONE_SWORD,ChatColor.GOLD+"Malice du Singe"),
                createItem(Material.IRON_SWORD,ChatColor.GOLD+"Malice du Singe"),createItem(Material.IRON_SWORD,ChatColor.GOLD+"Malice du Singe"),
                createItem(Material.IRON_SWORD,ChatColor.GOLD+"Malice du Singe"),createItem(Material.DIAMOND_SWORD,ChatColor.GOLD+"Malice du Singe"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"7",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en bois ->"+ChatColor.GOLD+" Épée en bois & Arc",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"+0.5❤ à la même cible -> +0.5❤ à la même cible",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.GOLD+"NOUVEAU! -> Changement d'arme (11s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"8",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en bois & Arc ->"+ChatColor.GOLD+" Épée en pierre & Arc",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"+0.5❤ à la même cible -> +0.5❤ à la même cible",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Changement d'arme (11s) -> "+ChatColor.GOLD+" Changement d'arme (9s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"8",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en pierre & Arc ->"+ChatColor.GOLD+" Épée en fer & Arc",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"+0.5❤ à la même cible ->"+ ChatColor.GOLD+ " +1❤ à la même cible",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Changement d'arme (9s) -> "+ChatColor.GOLD+" Changement d'arme (7s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"9",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en fer & Arc -> Épée en fer & Arc",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"+1❤ à la même cible -> +1❤ à la même cible",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Changement d'arme (7s) -> "+ChatColor.GOLD+" Changement d'arme (5s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"9",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en fer & Arc -> Épée en fer & Arc",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"+1❤ à la même cible -> +1❤ à la même cible",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Changement d'arme (5s) -> "+ChatColor.GOLD+" Changement d'arme (3s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"10",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en fer & Arc ->"+ChatColor.GOLD+" Épée en diamant & Arc",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"+1❤ à la même cible ->"+ ChatColor.GOLD+ " +1.5❤ à la même cible",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Changement d'arme (3s) -> "+ChatColor.GOLD+" Changement d'arme (1s)")
        );
        zodiacs.add(singe);
        //******************************************Chien***********************************************
        List<String> chienLore = Arrays.asList("",ChatColor.LIGHT_PURPLE+"Arme : Épée",ChatColor.RED+"DÉG:⭐⭐⭐⭐",ChatColor.AQUA+ "UTI:⭐⭐",ChatColor.GREEN+ "DEF:⭐"," ", ChatColor.WHITE+"Point fort : Constant");
        Zodiac chien = new Zodiac("Chien",createItem(Material.BONE,ChatColor.WHITE+"Chien"),chienLore,createItem(Material.WOODEN_SWORD,ChatColor.GOLD+"Canines du Chien"),
                createItem(Material.STONE_SWORD,ChatColor.GOLD+"Canines du Chien"),createItem(Material.IRON_SWORD,ChatColor.GOLD+"Canines du Chien"),
                createItem(Material.IRON_SWORD,ChatColor.GOLD+"Canines du Chien"),createItem(Material.DIAMOND_SWORD,ChatColor.GOLD+"Canines du Chien"),
                createItem(Material.NETHERITE_SWORD,ChatColor.GOLD+"Canines du Chien"),createItem(Material.NETHERITE_SWORD,ChatColor.GOLD+"Canines du Chien"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"7",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en bois ->"+ChatColor.GOLD+" Épée en pierre",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Force I si -1❤ -> Force I si -1❤",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.GOLD+"NOUVEAU! -> Chienvocation (45s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"8",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en pierre ->"+ChatColor.GOLD+" Épée en fer",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Force I si -1❤ ->"+ ChatColor.GOLD+ " Force I si -2❤",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Chienvocation (45s) -> "+ChatColor.GOLD+" Chienvocation (37s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"8",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en fer -> Épée en fer",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Force I si -2❤ -> Force I si -2❤",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Chienvocation (37s) -> "+ChatColor.GOLD+" Chienvocation (30s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"9",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en fer ->"+ChatColor.GOLD+" Épée en diamant",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Force I si -2❤ ->"+ ChatColor.GOLD+ " Force I si -3❤",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Chienvocation (30s) -> "+ChatColor.GOLD+" Chienvocation (22s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"9",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en diamant ->"+ChatColor.GOLD+" Épée en netherite",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Force I si -3❤ -> Force I si -3❤",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Chienvocation (22s) -> "+ChatColor.GOLD+" Chienvocation (15s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"10",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Épée en netherite -> Épée en netherite",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Force I si -3❤ ->"+ ChatColor.GOLD+ " Force I si -4❤",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.GOLD+"NOUVEAU! -> Les chiens gagnent une armure")
        );

        zodiacs.add(chien);
        //******************************************Cochon***********************************************
        List<String> cochonLore = Arrays.asList("",ChatColor.LIGHT_PURPLE+"Arme : Arbalette",ChatColor.RED+"DÉG:⭐",ChatColor.AQUA+ "UTI:⭐⭐⭐⭐⭐",ChatColor.GREEN+ "DEF:⭐"," ", ChatColor.WHITE+"Point fort : Constant");
        Zodiac cochon = new Zodiac("Cochon",createItem(Material.PIGLIN_HEAD,ChatColor.WHITE+"Cochon"),cochonLore,createItem(Material.CROSSBOW,ChatColor.GOLD+"Groin du Cochon"),
                createItem(Material.CROSSBOW,ChatColor.GOLD+"Groin du Cochon"),createItem(Material.CROSSBOW,ChatColor.GOLD+"Groin du Cochon"),
                createItem(Material.CROSSBOW,ChatColor.GOLD+"Groin du Cochon"),createItem(Material.CROSSBOW,ChatColor.GOLD+"Groin du Cochon"),
                createItem(Material.CROSSBOW,ChatColor.GOLD+"Groin du Cochon"),createItem(Material.CROSSBOW,ChatColor.GOLD+"Groin du Cochon"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"7",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Arbalette -> Arbalette",
                        ChatColor.AQUA+"Passif : "+ChatColor.GOLD+"NOUVEAU! -> Recul I",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.GOLD+"NOUVEAU! -> Téléportation (45s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"8",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Arbalette -> Arbalette",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Recul I -> Recul I",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Téléportation (45s) -> "+ChatColor.GOLD+" Téléportation (39s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"8",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Arbalette -> Arbalette",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Recul I -> Recul I",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Téléportation (39s) -> "+ChatColor.GOLD+" Téléportation (33s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"9",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Arbalette -> Arbalette",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Recul I -> Recul I",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Téléportation (33s) -> "+ChatColor.GOLD+" Téléportation (27s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"9",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Arbalette -> Arbalette",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Recul I"+ ChatColor.GOLD+ " Recul II",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Téléportation (27s) -> "+ChatColor.GOLD+" Téléportation (21s)"),
                Arrays.asList(ChatColor.WHITE+"Coût : "+ChatColor.GOLD+"10",
                        ChatColor.RED+"Arme : "+ChatColor.WHITE+"Arbalette -> Arbalette",
                        ChatColor.AQUA+"Passif : "+ChatColor.WHITE+"Recul II -> Recul II",
                        ChatColor.LIGHT_PURPLE+"Actif : "+ChatColor.WHITE+"Téléportation (21s) -> "+ChatColor.GOLD+" Téléportation (15s)")
        );
        zodiacs.add(cochon);
        return zodiacs;
    }

    public ItemStack createItem(Material m, String name){
        ItemStack item = new ItemStack(m);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }



}
