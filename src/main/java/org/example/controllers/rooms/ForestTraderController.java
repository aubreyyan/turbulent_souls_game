package org.example.controllers.rooms;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.controllers.GameScreenController;
import org.example.dao.Monster;
import org.example.services.AppService;
import org.example.services.DirectionService;
import org.example.services.HealthService;
import org.example.services.MonsterService;
import org.example.services.PlayerService;
import org.example.services.RoomDirectionService;
import org.example.services.SaveService;
import org.example.services.TraderService;
import org.example.util.ScheduleUtility;
import org.example.util.TraderInventoryUtility;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import static org.example.enums.RoomType.FOREST_TRADER;
import static org.example.enums.Direction.LEFT;
import static org.example.enums.MonsterType.SLIME;
import static org.example.util.ResourcePathUtility.SLIME_ATTACK_LEFT_PATH;
import static org.example.util.ResourcePathUtility.SLIME_DEATH_LEFT_PATH;
import static org.example.util.ResourcePathUtility.SLIME_LEFT_PATH;

public class ForestTraderController extends GameScreenController implements Initializable {

    private Timeline slime1AttackSchedule;
    private Timeline slime1ResetSchedule;
    private Timeline resetPlayerHueSchedule;

    @FXML
    private ImageView slime1;
    private final String slime1Key = "forestTslime1";
    private final int slime1HealthCapacity = 10;

    @FXML
    private ProgressBar slime1HealthBar;

    @FXML
    private ImageView trader;

    public ForestTraderController(AppService appService,
                                  PlayerService playerService,
                                  DirectionService directionService,
                                  RoomDirectionService roomDirectionService,
                                  HealthService healthService,
                                  SaveService saveService, Scene scene) {
        super(
                appService,
                playerService,
                directionService,
                roomDirectionService,
                healthService,
                saveService,
                scene
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.monsterService = new MonsterService();
        this.playerService.setMonsterService(this.monsterService);
        this.traderService = new TraderService(
                TraderInventoryUtility.getTraderInventoryForRoomType(FOREST_TRADER));
        this.initGameScreenController(this.monsterService, this.trader);
        this.resetPlayerHueSchedule = ScheduleUtility.generatePlayerResetSchedule(0.5,
                this.playerService);
        this.playerService.registerTimeline(this.resetPlayerHueSchedule);
        this.slime1ResetSchedule = ScheduleUtility.generateMonsterResetSchedule(
                1.0,
                this.monsterService,
                this.slime1Key,
                this.slime1,
                SLIME_LEFT_PATH,
                null
        );
        if (!this.appService.getMonstersKilled().contains(this.slime1Key)) {
            this.setupSlime1();
            this.playerService.registerTimeline(this.slime1AttackSchedule);
        }
    }

    private void setupSlime1() {
        this.slime1.setTranslateX(1500);
        this.slime1.setTranslateY(400);
        this.slime1.setVisible(true);
        this.slime1HealthBar.setVisible(true);
        this.slime1HealthBar.setTranslateX(1570);
        this.slime1HealthBar.setTranslateY(400);
        this.monsterService.addMonster(
                this.slime1Key,
                new Monster()
                        .setHealth(this.slime1HealthCapacity)
                        .setHealthCapacity(this.slime1HealthCapacity)
                        .setRange(3.0)
                        .setAttack(5)
                        .setAccuracy(0.5)
                        .setKillReward(100)
                        .setMonsterType(SLIME)
                        .setImageView(this.slime1)
                        .setHealthBar(this.slime1HealthBar)
                        .setOrientation(LEFT)
                        .setDeathAnimationLeft(
                                new Image(Paths.get(SLIME_DEATH_LEFT_PATH).toUri().toString()))
                        .setKey(this.slime1Key));
        this.slime1AttackSchedule = ScheduleUtility.generateMonsterAttackSchedule(
                1.0,
                this.appService,
                this.slime1Key,
                this.playerService,
                this.monsterService,
                this.healthService,
                this.resetPlayerHueSchedule,
                this.slime1ResetSchedule,
                Timeline.INDEFINITE,
                SLIME_ATTACK_LEFT_PATH,
                null
        );
        this.slime1AttackSchedule.play();
    }
}
