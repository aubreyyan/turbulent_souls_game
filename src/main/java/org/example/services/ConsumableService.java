package org.example.services;

import org.example.dao.Item;
import org.example.dto.consumables.Potion;
import org.example.dao.Weapon;
import org.example.util.SFXUtility;

import java.util.Map;

import static org.example.services.PlayerService.DEFAULT_MOVE_SIZE;
import static org.example.util.ScheduleUtility.generateSpeedPotionSchedule;
import static org.example.util.ScheduleUtility.generateStrengthPotionSchedule;

public class ConsumableService {

    private HealthService healthService;
    private PlayerService playerService;
    private AppService appService;

    public ConsumableService(HealthService healthService, PlayerService playerService,
                             AppService appService) {
        this.healthService = healthService;
        this.playerService = playerService;
        this.appService = appService;
    }

    public void consumePotion(Potion potion) {
        switch (potion.getType()) {
        case HEALTH:
            this.healthService.applyHealthModifier(potion.getStatValue());
            break;
        case STRENGTH:
            Map<String, Item> weaponInventory =
                    this.appService.getPlayerState().getWeaponInventory();
            for (String key : weaponInventory.keySet()) {
                Weapon weapon = (Weapon) weaponInventory.get(key);
                weapon.setAttack(weapon.getAttack() + potion.getStatValue());
                weaponInventory.put(key, weapon);
            }
            this.appService.getPlayerState().setWeaponInventory(weaponInventory);
            generateStrengthPotionSchedule(appService, potion).play();
            break;
        case SPEED:
            if (playerService.getMoveSize() == DEFAULT_MOVE_SIZE) {
                this.playerService.setMoveSize(playerService.getMoveSize()
                        + playerService.getMoveSize() * potion.getStatValue() / 100);
                generateSpeedPotionSchedule(appService, playerService).play();
            }
            break;
        default:
            break;
        }
        SFXUtility.USE_CONSUMABLE.play();
    }

    public HealthService getHealthService() {
        return healthService;
    }

    public ConsumableService setHealthService(HealthService healthService) {
        this.healthService = healthService;
        return this;
    }

    public PlayerService getPlayerService() {
        return playerService;
    }

    public ConsumableService setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
        return this;
    }

    public AppService getAppService() {
        return appService;
    }

    public ConsumableService setAppService(AppService appService) {
        this.appService = appService;
        return this;
    }
}
