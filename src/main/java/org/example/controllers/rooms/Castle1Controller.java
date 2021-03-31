package org.example.controllers.rooms;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.controllers.GameScreenController;
import org.example.dto.Monster;
import org.example.services.AppService;
import org.example.services.DirectionService;
import org.example.services.HealthService;
import org.example.services.MonsterService;
import org.example.services.PlayerService;
import org.example.services.RoomDirectionService;
import org.example.util.ScheduleUtility;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import static org.example.enums.Direction.LEFT;
import static org.example.enums.MonsterType.WHITE_DRAGON;

public class Castle1Controller extends GameScreenController implements Initializable {

    private MonsterService monsterService;

    private Timeline whiteDragon1AttackSchedule;
    private Timeline whiteDragon1ResetSchedule;
    private Timeline resetPlayerSchedule;

    @FXML
    private ImageView whitedragon1;
    private final String whitedragon1Key = "castle1whitedragon1";
    private final int whitedragon1HealthCapacity = 10;

    @FXML
    private ProgressBar whitedragon1HealthBar;

    public Castle1Controller(AppService appService,
                             PlayerService playerService,
                             DirectionService directionService,
                             RoomDirectionService roomDirectionService,
                             HealthService healthService, Scene scene) {
        super(
                appService,
                playerService,
                directionService,
                roomDirectionService,
                healthService, scene
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.monsterService = new MonsterService();
        this.playerService.setMonsterService(this.monsterService);
        this.initGameScreenController(this.monsterService);
        this.resetPlayerSchedule = ScheduleUtility.generatePlayerResetSchedule(0.5,
                this.playerService);
        this.playerService.registerTimeline(this.resetPlayerSchedule);
        this.whiteDragon1ResetSchedule = ScheduleUtility.generateMonsterResetSchedule(
                0.5,
                this.monsterService,
                this.whitedragon1Key,
                this.whitedragon1,
                "src/main/resources/static/images/monsters/gifs/white_dragon_left.gif",
                "src/main/resources/static/images/monsters/gifs/white_dragon_right.gif"
        );
        if(!this.appService.getMonstersKilled().contains(this.whitedragon1Key)) {
            this.setupWhitedragon1();
            this.playerService.registerTimeline(this.whiteDragon1AttackSchedule);
        }
    }

    private void setupWhitedragon1() {
        this.whitedragon1.setTranslateX(1000);
        this.whitedragon1.setTranslateY(400);
        this.whitedragon1.setVisible(true);
        this.whitedragon1HealthBar.setTranslateX(970);
        this.whitedragon1HealthBar.setTranslateY(360);
        this.whitedragon1HealthBar.setVisible(true);
        this.monsterService.addMonster(
                this.whitedragon1Key,
                new Monster()
                        .setHealth(this.whitedragon1HealthCapacity)
                        .setHealthCapacity(this.whitedragon1HealthCapacity)
                        .setRange(5.0)
                        .setAttack(2)
                        .setAccuracy(0.5)
                        .setMonsterType(WHITE_DRAGON)
                        .setImageView(this.whitedragon1)
                        .setHealthBar(this.whitedragon1HealthBar)
                        .setOrientation(LEFT)
                        .setDeathAnimationLeft(new Image(Paths.get("src/main/resources/static/images/monsters/gifs/white_dragon_death_left.gif").toUri().toString()))
                        .setDeathAnimationRight(new Image(Paths.get("src/main/resources/static/images/monsters/gifs/white_dragon_death_right.gif").toUri().toString())));
        this.whiteDragon1AttackSchedule = ScheduleUtility.generateMonsterAttackSchedule(
                1.0,
                this.appService,
                this.whitedragon1Key,
                this.playerService,
                this.monsterService,
                this.healthService,
                this.resetPlayerSchedule,
                this.whiteDragon1ResetSchedule,
                Timeline.INDEFINITE,
                "src/main/resources/static/images/monsters/gifs/white_dragon_attack_left.gif",
                null
        );
        this.whiteDragon1AttackSchedule.play();
    }
}
