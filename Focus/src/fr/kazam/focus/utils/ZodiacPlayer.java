package fr.kazam.focus.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class ZodiacPlayer {

    private Player player;
    private Zodiac zodiac;
    int zodiacLevel;
    int armorMaterialLevel;
    int protectionLevel;
    int fireProtectionLevel;
    int arrowProtectionLevel;
    int thornsLevel;
    int damageLevel;
    int quickChargeLevel;
    int multishotLevel;
    int piercingLevel;
    int densityLevel;
    int breachLevel;
    private int victoryPoints;
    private int nbKill;//TODO prime?
    private TeamColor teamColor;
    private Player singeVictim;
    private int singeHit;
    private int balanceTotal;
    private int activeTimer;
    private int[] levelTimer;
    private boolean activeIsUsed;
    private float coqTimer;



    public ZodiacPlayer(Player player) {
        this.player = player;
        this.zodiac = null;
        this.zodiacLevel = 1;
        this.armorMaterialLevel = 0;
        this.protectionLevel = 0;
        this.fireProtectionLevel = 0;
        this.arrowProtectionLevel = 0;
        this.thornsLevel = 0;
        this.damageLevel = 0;
        this.quickChargeLevel = 0;
        this.multishotLevel = 0;
        this.piercingLevel = 0;
        this.densityLevel = 0;
        this.breachLevel = 0;
        this.victoryPoints = 0;
        this.nbKill = 0;
        this.teamColor = TeamColor.WHITE;
        this.singeVictim = player;
        this.singeHit = 0;
        this.activeTimer = -1;
        this.levelTimer = new int[8];
        this.activeIsUsed = false;
        this.balanceTotal = 0;
        this.coqTimer=0;
    }


    public float getCoqTimer() {
        return coqTimer;
    }

    public void setCoqTimer(float coqTimer) {
        this.coqTimer = coqTimer;
    }

    public int getBalanceTotal() {
        return balanceTotal;
    }

    public void addBalanceTotal(int balanceTotal) {
        this.balanceTotal += balanceTotal;
    }

    public void setBalanceTotal(int balanceTotal) {
        this.balanceTotal = balanceTotal;
    }

    public Player getSingeVictim() {
        return singeVictim;
    }

    public int getSingeHit() {
        return singeHit;
    }

    public void setSingeHit(int singeHit) {
        this.singeHit = singeHit;
    }

    public void setSingeVictim(Player singeVictim) {
        this.singeVictim = singeVictim;
    }

    public Player getPlayer() {
        return player;
    }

    public Zodiac getZodiac() {
        return zodiac;
    }

    public int getActiveTimer() {
        return activeTimer;
    }

    public void setActiveTimer(int activeTimer) {
        this.activeTimer = activeTimer;
    }

    public int[] getLevelTimer() {
        return levelTimer;
    }

    public void setLevelTimer(int[] levelTimer) {
        this.levelTimer = levelTimer;
    }

    public boolean isActiveIsUsed() {
        return activeIsUsed;
    }

    public void setActiveIsUsed(boolean activeIsUsed) {
        this.activeIsUsed = activeIsUsed;
    }

    public int getZodiacLevel() {
        return zodiacLevel;
    }

    public int getArmorMaterialLevel() {
        return armorMaterialLevel;
    }

    public int getProtectionLevel() {
        return protectionLevel;
    }

    public int getFireProtectionLevel() {
        return fireProtectionLevel;
    }

    public int getArrowProtectionLevel() {
        return arrowProtectionLevel;
    }

    public int getThornsLevel() {
        return thornsLevel;
    }

    public int getDamageLevel() {
        return damageLevel;
    }

    public int getQuickChargeLevel() {
        return quickChargeLevel;
    }

    public int getMultishotLevel() {
        return multishotLevel;
    }

    public int getPiercingLevel() {
        return piercingLevel;
    }

    public int getDensityLevel() {
        return densityLevel;
    }

    public int getBreachLevel() {
        return breachLevel;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public int getNbKill() {
        return nbKill;
    }

    public TeamColor getTeamColor() {
        return teamColor;
    }

    public void setTeamColor(TeamColor teamColor) {
        this.teamColor = teamColor;
    }

    public void setZodiac(Zodiac zodiac) {
        this.zodiac = zodiac;
        switch(zodiac.getName()){
            case "Lion":
                this.levelTimer = new int[]{-5, -5, 15, 15, 15, 10, 10, 5};
                break;
            case "Taureau":
                this.levelTimer = new int[]{-5, -5, 20, 15, 15, 15, 15, 10};
                break;
            case "Vierge":
                this.levelTimer = new int[]{-5, -5, 30, 30, 30, 25, 20, 15};
                break;
            case "Balance":
                this.levelTimer = new int[]{-5, -5, 60, 54, 48, 42, 36, 30};
                break;
            case "Scorpion":
                this.levelTimer = new int[]{-5, -5, 15, 15, 15, 15, 15, 15};
                break;
            case "Sagitaire":
                this.levelTimer = new int[]{-5, -5, 30, 26, 22, 18, 14, 10};
                break;
            case "Lapin":
                this.levelTimer = new int[]{-5, -5, 20, 18, 16, 14, 12, 10};
                break;
            case "Cheval":
                this.levelTimer = new int[]{-5, -5, 30, 30, 30, 25, 25, 20};
                break;
            case "Singe":
                this.levelTimer = new int[]{-5, -5, 11, 9, 7, 5, 3, 1};
                break;
            case "Coq":
                this.levelTimer = new int[]{-5, -5, 2, 4, 8, 16, 32, 64};
                break;
            case "Chien":
                this.levelTimer = new int[]{-5, -5, 45, 37, 30, 22, 15, 15};
                break;
            case "Cochon":
                this.levelTimer = new int[]{-5, -5, 45, 39, 33, 27, 21, 15};
                break;
        }
    }


    public void addPoint(int i) {
        this.victoryPoints+=i;
    }

    public void addKill(int i){
        this.nbKill+=i;
    }

    public void resetZodiac(){
        this.zodiac = null;
        this.zodiacLevel = 1;
        this.armorMaterialLevel = 0;
        this.protectionLevel = 0;
        this.fireProtectionLevel = 0;
        this.arrowProtectionLevel = 0;
        this.thornsLevel = 0;
        this.damageLevel = 0;
        this.quickChargeLevel = 0;
        this.multishotLevel = 0;
        this.piercingLevel = 0;
        this.densityLevel = 0;
        this.breachLevel = 0;
        this.victoryPoints = 0;
        this.nbKill = 0;
        this.singeVictim = player;
        this.singeHit = 0;
        this.activeTimer = -1;
        this.levelTimer = new int[8];
        this.activeIsUsed = false;
        this.balanceTotal = 0;
        this.coqTimer=0;
    }

    public void addArmorLevel(int i) {
        this.armorMaterialLevel+=i;
    }

    public void addProtectionLevel(int i) {
        this.protectionLevel+=i;
    }
    public void addFireProtectionLevel(int i) {
        this.fireProtectionLevel+=i;
    }
    public void addArrowProtectionLevel(int i) {
        this.arrowProtectionLevel+=i;
    }
    public void addThornsLevel(int i) {
        this.thornsLevel+=i;
    }
    public void addDamageLevel(int i) {
        this.damageLevel+=i;
    }
    public void addQuickChargeLevel(int i) {
        this.quickChargeLevel+=i;
    }
    public void addMultishotLevel(int i) {
        this.multishotLevel+=i;
    }
    public void addPiercingLevel(int i) {
        this.piercingLevel+=i;
    }
    public void addDensityLevel(int i) {
        this.densityLevel+=i;
    }
    public void addBreachLevel(int i) {
        this.breachLevel+=i;
    }
    public void addZodiacLevel(int i) {
        this.zodiacLevel+=i;
    }
}
