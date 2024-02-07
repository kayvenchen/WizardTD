package WizardTD;

import processing.core.PApplet;

/**
 * The Mana class represents a mana pool for a wizard in a game.
 * It provides methods to manage and display the wizard's mana.
 * 

 */
public class Mana {
    private float currentMana;
    private float manaCap;
    private float manaGainPerSecond;
    
    /**
     * Initializes a new Mana instance with the specified initial properties.
     *
     * @param initialMana           The initial amount of mana.
     * @param initialManaCap        The maximum mana capacity.
     * @param initialManaGainPerSecond The rate of mana gain per second.
     */
    public Mana(float initialMana, float initialManaCap, float initialManaGainPerSecond) {
        currentMana = initialMana;
        manaCap = initialManaCap;
        manaGainPerSecond = initialManaGainPerSecond;
    }
  
    /**
     * Uses the mana pool spell, increasing mana capacity and gain rate.
     *
     * @param spellInitialCost     The initial cost of the spell.
     * @param costIncreasePerUse   The increase in cost per use.
     * @param capMultiplier        The multiplier for increasing mana capacity.
     * @param manaGainedMultiplier The multiplier for increasing mana gain.
     */
    public void useManaPoolSpell(int spellInitialCost, float capMultiplier, float manaGainedMultiplier) {
        if (currentMana >= spellInitialCost) {
            currentMana -= spellInitialCost;
            manaCap *= capMultiplier;
            manaGainPerSecond *= manaGainedMultiplier;
        }
    }
    
    /**
     * Increases mana over time based on the mana gain per second.
     */
    public void increaseManaOverTime() {
            currentMana += manaGainPerSecond / App.FPS;
            currentMana = App.min(currentMana, manaCap);
    }
    
    /**
     * decrease mana by a specified amount
     * 
     * @param mana the amount to decrease by
     */
    public void decreaseMana(int mana) {
        currentMana -= mana;
    }

    /**
     * Retrieves the current amount of mana.
     *
     * @return The current amount of mana.
     */
    public float getCurrentMana() {
        return currentMana;
    }

    /**
     * Retrieves the maximum mana capacity.
     * 
     * @return The maximum mana capacity.
     */
    public float getManaCap() {
        return manaCap;
    }

    /**
     * Retrieves the rate of mana gain per second.
     *
     * @return The rate of mana gain per second.
     */
    public float getManaGainPerSecond() {
        return manaGainPerSecond;
    }

    /**
     * Sets the current amount of mana to the specified value.
     *
     * @param currentMana The new current mana value to set.
     */
    public void setCurrentMana(float currentMana) {
        this.currentMana = currentMana;
    }

    /**
     * Displays the wizard's mana bar and information on the screen.
     *
     * @param app The PApplet object for drawing and rendering.
     */
    public void displayManaBar(PApplet app) {
        app.text("MANA: ", 350, 32);

        // Draw the background for the mana bar
        app.fill(255);
        app.stroke(0);
        app.rect(430, 10, 300, 26);

        app.fill(94, 203, 204);
        float manaBarWidth = PApplet.map(currentMana, 0, manaCap, 0, 300);
        app.rect(430, 10, manaBarWidth, 26);
        
        // Draw the wizard's mana information inside the mana bar
        app.fill(0);
        app.text( (int)currentMana + " / " +  (int)manaCap, 500, 32);
    }
}