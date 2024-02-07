package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.MouseEvent;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class App extends PApplet {

    public static final int CELLSIZE = 32;
    public static final int SIDEBAR = 120;
    public static final int TOPBAR = 40;
    public static final int BOARD_WIDTH = 20;

    public static int WIDTH = CELLSIZE*BOARD_WIDTH+SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH*CELLSIZE+TOPBAR;
    
    public static final int FPS = 60;

    public String configPath;
    public JSONObject config;

    public Random random = new Random();
	
    private PImage grassImage;
    private PImage path0Image;
    private PImage path1Image;
    private PImage path2Image;
    private PImage path3Image;
    private PImage shrubImage;
    private PImage wizardHouseImage;

    private Tile wizardHouse;
    private ArrayList<Tile> tiles;
    private Cell[][] grid = new Cell[20][20];
    private Cell hoveredCell = null;

    private ArrayList<Waypoint> waypoints;
    private ArrayList<SpawnPoint> spawnPoints;

    private PImage gremlinImage;


    PImage[] towerImages = new PImage[3];
    private ArrayList<Tower> towers;

    private Tower towerInitStats;
    
    private PImage fireballImage;
    private ArrayList<Fireball> fireballs;

    private boolean towerPlacementMode = false;
    private boolean upgradeRange = false;
    private boolean upgradeSpeed = false;
    private boolean upgradeDamage = false;

    private ArrayList<Wave> waves;
    private ArrayList<Monster> monsters;
    private ArrayList<Monster> monstersToSpawn;
    boolean allMonstersDead = false;

    private int currentWaveIndex;

    private Mana wizardMana;

    private int initialMana;
    private int initialManaCap;
    private int initialManaGain;

    private int manaPoolSpellInitialCost;
    private int manaPoolSpellCostIncreasePerUse;
    private float manaPoolSpellCapMultiplier;
    private float manaPoolSpellManaGainedMultiplier;

    private int manaPoolSpellCurrentCost;
    
    private double spawnTimer = 0;

    private int gameSpeedMultiplier = 1;
    private boolean isPaused = false;
    private boolean isFastfoward = false;

    private boolean lost = false;
    
    public App() {
        this.configPath = "config.json";

        this.tiles = new ArrayList<Tile>();

        this.waves = new ArrayList<Wave>();
        this.monsters = new ArrayList<Monster>();
        this.monstersToSpawn = new ArrayList<Monster>();
        this.waypoints = new ArrayList<Waypoint>();
        this.spawnPoints = new ArrayList<SpawnPoint>();

        this.towers = new ArrayList<Tower>();

        this.fireballs = new ArrayList<Fireball>();

    }

    /**
     * Initialise the setting of the window size.
     */
	@Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
     */
	@Override
    public void setup() {
        frameRate(FPS);

        config = loadJSONObject("config.json");


        // init grid
        for (int y = 0; y < grid.length; y++) {
            for(int x = 0; x < grid[0].length; x++) {
                grid[x][y] = new Cell(x, y);
            }
        }

        // map loading
        this.grassImage = loadImage("src/main/resources/WizardTD/grass.png");
        this.path0Image = loadImage("src/main/resources/WizardTD/path0.png");
        this.path1Image = loadImage("src/main/resources/WizardTD/path1.png");
        this.path2Image = loadImage("src/main/resources/WizardTD/path2.png");
        this.path3Image = loadImage("src/main/resources/WizardTD/path3.png");
        this.shrubImage = loadImage("src/main/resources/WizardTD/shrub.png");
        this.wizardHouseImage = loadImage("src/main/resources/WizardTD/wizard_house.png");

        String layout = config.getString("layout");
        loadMap(layout);

        

        // getting wave info from config
        this.gremlinImage = loadImage("src/main/resources/WizardTD/gremlin.png");
        
        
        // read config file for waves
        JSONArray configWaves = config.getJSONArray("waves");
        for (int i = 0; i < configWaves.size(); i++) {
            JSONObject configWave = configWaves.getJSONObject(i);
            int duration = configWave.getInt("duration");
            double preWavePause = configWave.getDouble("pre_wave_pause");
            JSONArray configMonsters = configWave.getJSONArray("monsters");
            // Clear the monsters list for each wave
            monsters.clear();

            // monsters
            for (int j = 0; j < configMonsters.size(); j++) {
                JSONObject configMonster = configMonsters.getJSONObject(j);
                String type = configMonster.getString("type");
                int hp = configMonster.getInt("hp");
                double speed = configMonster.getDouble("speed");
                double armour = configMonster.getDouble("armour");
                int mana_gained_on_kill = configMonster.getInt("mana_gained_on_kill");
                int quantity = configMonster.getInt("quantity");
                // get death frames
                
                if (type.equals("gremlin")) {
                    monsters.add(new Monster(gremlinImage, hp, speed, armour, mana_gained_on_kill, wizardHouse));
                    }
                
                waves.add(new Wave(preWavePause, duration, monsters, quantity));
            }

            // tower loading 
            towerImages[0] = loadImage("src/main/resources/WizardTD/tower0.png");
            towerImages[1] = loadImage("src/main/resources/WizardTD/tower1.png");
            towerImages[2] = loadImage("src/main/resources/WizardTD/tower2.png");
            
            int towerCost = config.getInt("tower_cost");
            int initialTowerRange = config.getInt("initial_tower_range");
            double initialTowerFiringSpeed = config.getDouble("initial_tower_firing_speed");
            int initialTowerDamage = config.getInt("initial_tower_damage");

            towerInitStats = new Tower(towerImages, towerCost, initialTowerRange, initialTowerFiringSpeed, initialTowerDamage);

            this.fireballImage = loadImage("src/main/resources/WizardTD/fireball.png");
            
            for (int m = 0; m < 100; m++) {
                Fireball fireball = new Fireball(fireballImage);
                fireballs.add(fireball);
            }

            initialMana = config.getInt("initial_mana");
            initialManaCap = config.getInt("initial_mana_cap");
            initialManaGain = config.getInt("initial_mana_gained_per_second");

            manaPoolSpellInitialCost = config.getInt("mana_pool_spell_initial_cost");
            manaPoolSpellCurrentCost = manaPoolSpellInitialCost;
            manaPoolSpellCostIncreasePerUse =  config.getInt("mana_pool_spell_cost_increase_per_use");
            manaPoolSpellCapMultiplier = config.getFloat("mana_pool_spell_cap_multiplier");
            manaPoolSpellManaGainedMultiplier = config.getFloat("mana_pool_spell_mana_gained_multiplier");

            wizardMana = new Mana(initialMana, initialManaCap, initialManaGain);
        }
        
        
    }

    /**
     * check location mouse
     */
    public void mouseCheck() {
        int gridX = (int) (mouseX / CELLSIZE);
        int gridY = (int) ((mouseY-TOPBAR) / CELLSIZE);

        if (gridX >= 0 && gridX < BOARD_WIDTH && gridY >= 0 && gridY < BOARD_WIDTH) {
            hoveredCell = grid[gridX][gridY];
            if (hoveredCell.getOccupant() != null) {
                noFill();
                strokeWeight(2);
                stroke(232, 219, 120);
                ellipse(hoveredCell.getX() * CELLSIZE + CELLSIZE/2, hoveredCell.getY() * CELLSIZE + TOPBAR + CELLSIZE/2, hoveredCell.getOccupant().getRange()*2, hoveredCell.getOccupant().getRange()*2);
                if (upgradeRange || upgradeSpeed || upgradeDamage) {
                    int rangeCost = upgradeRange ? hoveredCell.getOccupant().getUpgradeCost(hoveredCell.getOccupant().getRangeLevel()) : 0;
                    int speedCost = upgradeSpeed ? hoveredCell.getOccupant().getUpgradeCost(hoveredCell.getOccupant().getSpeedLevel()) : 0;
                    int damageCost = upgradeDamage ? hoveredCell.getOccupant().getUpgradeCost(hoveredCell.getOccupant().getDamageLevel()) : 0;
                    displayUpgradeCost(rangeCost, speedCost, damageCost);
                }
            }
        }
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
	@Override
    public void keyPressed(){
        if (key == 'f' || key == 'F') {
            gameSpeedMultiplier = (gameSpeedMultiplier == 1) ? 2 : 1;
            isFastfoward = !isFastfoward;
        } else if (key == 'p' || key == 'P') {
            isPaused = !isPaused;
        } else if (key == 't' || key == 'T') {
            towerPlacementMode = !towerPlacementMode; 
        } else if (key == '1') {
            upgradeRange = !upgradeRange;
        } else if (key == '2') {
            upgradeSpeed = !upgradeSpeed;
        } else if (key == '3') {
            upgradeDamage = !upgradeDamage;
        } else if (key == 'm' || key == 'M') {
            wizardMana.useManaPoolSpell(manaPoolSpellCurrentCost, manaPoolSpellCapMultiplier, manaPoolSpellManaGainedMultiplier);
            manaPoolSpellCurrentCost *= manaPoolSpellCostIncreasePerUse;
        } else if (key == 'r' || key == 'R') {
            resetGame();
            loop();
        }  else if (key == 's' || key == 'S') {
            startNextWave();
        }
    }

    public void resetGame() {
        if (lost) {
            currentWaveIndex = 0;
            wizardMana.setCurrentMana(initialMana);
            manaPoolSpellCurrentCost = manaPoolSpellInitialCost;
            monstersToSpawn.clear();
            lost = false;
            loop();
        }
    }

    public Fireball getAvailableFireball() {
        for (Fireball fireball : fireballs) {
            if (!fireball.isFiring()) {
                return fireball; // Return the first available fireball
            }
        }
        return null; // If all fireballs in the pool are in use, return null
    }

    /**
     * Receive key released signal from the keyboard.
     */
	@Override
    public void keyReleased(){

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // tower placement
        if (towerPlacementMode) {
            if (hoveredCell != null) {
                if (hoveredCell.getBuildable()) {
                    wizardMana.decreaseMana(100);
                    hoveredCell.buildTower(new Tower(towerInitStats, hoveredCell.getX(), hoveredCell.getY()), towers);
                }
            }
        // if not in tower placement mode we have to check if there is a tower at hovered square
        }
        Tower towerToUpgrade = hoveredCell.getOccupant();
        if (towerToUpgrade != null) {
            if (upgradeRange && wizardMana.getCurrentMana() >= towerToUpgrade.getUpgradeCost(towerToUpgrade.getRangeLevel())) {
                wizardMana.decreaseMana(towerToUpgrade.getUpgradeCost(towerToUpgrade.getRangeLevel()));
                hoveredCell.getOccupant().upgradeRange();
            } 
            if (upgradeSpeed && wizardMana.getCurrentMana() >= towerToUpgrade.getUpgradeCost(towerToUpgrade.getSpeedLevel())) {
                wizardMana.decreaseMana(towerToUpgrade.getUpgradeCost(towerToUpgrade.getSpeedLevel()));
                hoveredCell.getOccupant().upgradeSpeed();
            }
            if (upgradeDamage && wizardMana.getCurrentMana() >= towerToUpgrade.getUpgradeCost(towerToUpgrade.getDamageLevel())) {
                wizardMana.decreaseMana(towerToUpgrade.getUpgradeCost(towerToUpgrade.getDamageLevel()));
                hoveredCell.getOccupant().upgradeDamage();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /*@Override
    public void mouseDragged(MouseEvent e) {

    }*/

    /**
     * Draw all elements in the game by current frame.
     */
	@Override
    public void draw() {
        for (int i = 0; i < gameSpeedMultiplier; i++) {
            if (!isPaused) {
                spawnMonster();
                allMonstersDead = checkAllMonstersDead();

                if (allMonstersDead) {

                    startNextWave();
                }        

                for (Monster monster : monstersToSpawn) {
                    monster.tick();
                }

                for (Fireball fireball : fireballs) {
                    fireball.tick();
                }
                
                for (Tower tower : towers) {
                    for (Monster monster : monstersToSpawn) {
                        if (monster.isAlive()) {
                            long currentTime = System.currentTimeMillis();
                            if (currentTime - tower.getLastFireTime() >= tower.getFireInterval()) {
                                // monster offsets
                                float monsterXOffset = monster.getX()*CELLSIZE + 5 + monster.getWidth()/2;
                                float monsterYOffset = monster.getY()*CELLSIZE + TOPBAR + 5 + monster.getHeight()/2;
                                // tower offsets
                                int towerXOffset = tower.getX() * CELLSIZE + CELLSIZE/2;
                                int towerYOffset = tower.getY() * CELLSIZE + CELLSIZE/2 + TOPBAR;

                                float distance = dist(towerXOffset, towerYOffset, monsterXOffset, monsterYOffset);
                                double direction = Math.atan2(monsterYOffset - towerYOffset, monsterXOffset - towerXOffset);

                                if (distance <= tower.getRange()) {
                                    Fireball newFireball = getAvailableFireball();
                                    if (newFireball != null) {
                                        newFireball.setStartLocation(towerXOffset, towerYOffset, direction, tower.getDamage(), tower.getRange());
                                        tower.setLastFireTime(currentTime);
                                    }
                                } 
                            }
                        }
                    }
                }
            // increase mana over time
            wizardMana.increaseManaOverTime();    
            }     

            for (Tile tile : tiles) {
                    image(tile.getTile(), (tile.getX() * CELLSIZE), tile.getY() * CELLSIZE + TOPBAR); 
                }
                image(this.wizardHouse.getTile(), this.wizardHouse.getX() * 32 - 8, this.wizardHouse.getY() * CELLSIZE + TOPBAR - 8);
            
            for (Monster monster : monstersToSpawn) {
                monster.draw(this);
            }

            for (Tower tower : towers) {
                tower.draw(this);
            }
            
            for (Fireball fireball : fireballs) {
                fireball.draw(this);
            }
            
            // topbar and sidebar
            fill(129, 116, 79);
            noStroke();
            rect(0, 0, width, TOPBAR);
            rect(BOARD_WIDTH*CELLSIZE, TOPBAR, SIDEBAR, height - TOPBAR);

            fill(0);  // Fill color (black)
            textSize(24); // Text size
            text("Wave " + getCurrentWaveNumber(), 0, 32);
            
            // mana bar
            wizardMana.displayManaBar(this);
            // draw text to show how to use the functions

            fill(0);
            textSize(12);
            text("2x speed\nkey: f\nstatus: " + isFastfoward + 
                "\n\npause\nkey: p\nstatus: " + isPaused + 
                "\n\nbuild tower\nkey: t\ncost: 100\nstatus: " + towerPlacementMode +
                "\n\nupgrade range\nkey: 1\nstatus: " + upgradeRange + 
                "\n\nupgrade speed\nkey: 2\nstatus: " + upgradeSpeed +
                "\n\nupgrade damage\nkey: 3\nstatus: " + upgradeDamage +
                "\n\nmana pool spell\nkey: m\ncost: "+ manaPoolSpellCurrentCost,
                WIDTH - SIDEBAR, TOPBAR + 20);
                     
            mouseCheck();
            LoseCheck();
            winCheck();
        }
    }


    public void winCheck() {
        if (currentWaveIndex > waves.size()-1) {
            // You win
            background(0, 255, 0);
            textSize(32);
            text("YOU WIN", width/2 - 100, height/2);
            noLoop();
        }
    }

    public void LoseCheck() {
        for (Monster monster : monstersToSpawn) {
            if (monster.getIsBanished()) {
                int randomIndex = random.nextInt(spawnPoints.size());
                SpawnPoint randomSpawnPoint = spawnPoints.get(randomIndex);
                monster.setBanishedMonsterLocation(randomSpawnPoint.getX(), randomSpawnPoint.getY(), randomSpawnPoint.getDirection());
                if (monster.getHp() > wizardMana.getCurrentMana()) {
                    background(255, 0, 0);
                    textSize(32);
                    text("YOU LOST", WIDTH/2 - 100, HEIGHT/2);
                    text("Press 'r' to restart", WIDTH/2 - 150, HEIGHT/2 + 50);
                    lost = true;
                    noLoop();
                } else {
                    wizardMana.decreaseMana(monster.getHp());
                }
            }
        }
    }

    public int getCurrentWaveNumber() {
        return currentWaveIndex + 1;
    }

    public void spawnMonster() {
        this.spawnTimer--;
        if (this.spawnTimer < 0) {
            int randomIndex = random.nextInt(spawnPoints.size());
            SpawnPoint randomSpawnPoint = spawnPoints.get(randomIndex);
            if (monstersToSpawn.size() < getCurrentWave().getQuantity()) {
                Monster monsterType = getCurrentWave().getMonstersToSpawn().get(0);
                monstersToSpawn.add(new Monster(monsterType, randomSpawnPoint.getX(), randomSpawnPoint.getY(), randomSpawnPoint.getDirection(), waypoints, fireballs));
            }
            this.spawnTimer = 120;
        }
    }

    public boolean checkAllMonstersDead() {

        if (currentWaveIndex >= waves.size()) {
            // All waves are completed, no more monsters to check
            return true;
        }    

        // Loop through list of monsters and check if they are all dead
        if (monstersToSpawn.size() < getCurrentWave().getQuantity()) {
            return false;
        }
        for (Monster monster : monstersToSpawn) {
            if (monster.isAlive()) {
                return false; // If at least one monster is still alive, return false
            }
        }
        return true; // All monsters are dead
    }

    public Wave getCurrentWave() {
        if (currentWaveIndex >= 0 && currentWaveIndex < waves.size()) {
            return waves.get(currentWaveIndex);
        }
        return null;
    }

    public void startNextWave() {
        currentWaveIndex++;
        monstersToSpawn.clear();
    }
    
    public void displayUpgradeCost(int rangeCost, int speedCost, int damageCost) {
        // Display the upgrade costs at the bottom right of the screen
        fill(255);
        stroke(0);
        rect(WIDTH - SIDEBAR, HEIGHT - 100, 200, 100);
    
        fill(0); // Text color
        textSize(12);
    
        text("Range Cost: " + rangeCost, WIDTH - SIDEBAR + 5, HEIGHT - 80);
        text("Speed Cost: " + speedCost, WIDTH - SIDEBAR + 5, HEIGHT - 60);
        text("Damage Cost: " + damageCost, WIDTH - SIDEBAR+  5, HEIGHT - 40);
    
        int totalCost = rangeCost + speedCost + damageCost;
        text("Total Cost: " + totalCost, WIDTH - SIDEBAR + 5, HEIGHT - 20);
    }

    public static void main(String[] args) {
        PApplet.main("WizardTD.App");
    }

    /**
     * loads map by reading the txt files and placing tiles based of of the letter in the row
     * only works for files that have 20 lines
     * @param filename the path to the level file you want to load.
     */
    public void loadMap(String filename) {
        try (BufferedReader br = createReader(filename)) {
            HashSet<Character> pathChars = new HashSet<>(Arrays.asList('X', 'U', 'D', 'L', 'R'));
            HashSet<Character> waypointChars = new HashSet<>(Arrays.asList('U', 'D', 'L', 'R'));
            String[] lines = new String[20];
            int y = 0;
    
            // Read the map layout into an array of strings
            String line;
            while ((line = br.readLine()) != null) {
                lines[y] = line;
                y++;
            }
    
            // Process the map layout to determine the appropriate path types
            for (y = 0; y < lines.length; y++) {
                for (int x = 0; x < lines[y].length(); x++) {
                    char charAtXY = lines[y].charAt(x);

                    // get waypoints
                    if (waypointChars.contains(charAtXY)) {
                        waypoints.add(new Waypoint(charAtXY, x, y));
                    }

                    // get spawnpoints paths at the edges of the map
                    if (pathChars.contains(charAtXY) && x == 0) {
                        spawnPoints.add(new SpawnPoint(x, y, 0));
                    } else if (pathChars.contains(charAtXY) && x == 20) {
                        spawnPoints.add(new SpawnPoint(x, y, 180));
                    } else if (pathChars.contains(charAtXY) && y == 0) {
                        spawnPoints.add(new SpawnPoint(x, y, 90));
                    } else if (pathChars.contains(charAtXY) && y == 20) {
                        spawnPoints.add(new SpawnPoint(x, y, 270));
                    }

                    // tiles which do not need to be rotated
                    if (charAtXY == 'S') {
                        tiles.add(new Tile(x, y, shrubImage));
                    } else if (charAtXY == ' ') {
                        tiles.add(new Tile(x, y, grassImage));
                        grid[x][y].setBuildable(true);
                    } else if (charAtXY == 'W') {
                        tiles.add(new Tile(x, y, grassImage));
                        this.wizardHouse = new Tile(x, y, wizardHouseImage);
                    }
                    // check which path is appropriate and what it needs to be rotated by.
                    else if (pathChars.contains(charAtXY)) {
                        // check surrounding tiles
                        boolean isUp = (y > 0 && pathChars.contains(lines[y - 1].charAt(x)));
                        boolean isDown = (y < lines.length - 1 && pathChars.contains(lines[y + 1].charAt(x)));
                        boolean isLeft = (x > 0 && pathChars.contains(lines[y].charAt(x - 1)));
                        boolean isRight = (x < lines[y].length() - 1 && pathChars.contains(lines[y].charAt(x + 1)));

                        if (isUp && isDown && isLeft && isRight) {
                            tiles.add(new Tile(x, y, path3Image)); // default 4-way intersection
                        
                        }else if (isLeft && isRight && isDown) {
                            tiles.add(new Tile(x, y, path2Image)); // default 3-way intersection
                        
                        }else if (isLeft && isUp && isDown) {
                            tiles.add(new Tile(x, y, rotateImageByDegrees(path2Image, 90))); // 3-way rotate 90 deg
                        }else if (isLeft && isRight && isUp) {
                            tiles.add(new Tile(x, y, rotateImageByDegrees(path2Image, 180))); // 3-way rotate 180 deg
                        }else if (isRight && isUp && isDown) {
                            tiles.add(new Tile(x, y, rotateImageByDegrees(path2Image, 270))); // 3-way rotate 270 deg
                            
                        } else if (isLeft && isDown) { 
                            tiles.add(new Tile(x, y, path1Image)); // default corner
                        
                        } else if (isLeft && isUp) { 
                            tiles.add(new Tile(x, y, rotateImageByDegrees(path1Image, 90))); // corner rotate 90 deg
                        
                        } else if (isRight && isUp) { 
                            tiles.add(new Tile(x, y, rotateImageByDegrees(path1Image, 180))); // default corner 180 deg
                        
                        } else if (isRight && isDown) { 
                            tiles.add(new Tile(x, y, rotateImageByDegrees(path1Image, 270))); // default corner 270 deg
                        
                            
                        } else if (isUp || isDown) { 
                            tiles.add(new Tile(x, y, rotateImageByDegrees(path0Image, 90))); // vertical straight
                        }
                        
                        else {
                            tiles.add(new Tile(x, y, path0Image)); // horizontal straight
                        }
                        
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Source: https://stackoverflow.com/questions/37758061/rotate-a-buffered-image-in-java
     * @param pimg The image to be rotated
     * @param angle between 0 and 360 degrees
     * @return the new rotated image
     */
    public PImage rotateImageByDegrees(PImage pimg, double angle) {
        BufferedImage img = (BufferedImage) pimg.getNative();
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        PImage result = this.createImage(newWidth, newHeight, RGB);
        //BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        BufferedImage rotated = (BufferedImage) result.getNative();
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                result.set(i, j, rotated.getRGB(i, j));
            }
        }

        return result;
    }
}
