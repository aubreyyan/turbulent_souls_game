package org.example.dto;

import org.example.enums.WeaponType;

import static org.example.util.ResourcePathUtility.ADVANCED_MAGIC_PATH;
import static org.example.util.ResourcePathUtility.ADVANCED_MAGIC_PLAYER_ATTACK_LEFT;
import static org.example.util.ResourcePathUtility.ADVANCED_MAGIC_PLAYER_ATTACK_RIGHT;
import static org.example.util.ResourcePathUtility.ALL_MAGIC_PLAYER_LEFT;
import static org.example.util.ResourcePathUtility.ALL_MAGIC_PLAYER_RIGHT;

public class AdvancedMagic extends Weapon {
    public AdvancedMagic() {
        this.name = "Advanced Magic";
        this.type = WeaponType.MAGIC;
        this.attack = 12;
        this.range = 5.0;
        this.quantity = 1;
        this.imagePath = ADVANCED_MAGIC_PATH;
        this.description = "Unpredictable and powerful.";
        this.price = 300;
        this.animationLeft = ALL_MAGIC_PLAYER_LEFT;
        this.animationRight = ALL_MAGIC_PLAYER_RIGHT;
        this.attackAnimationLeft = ADVANCED_MAGIC_PLAYER_ATTACK_LEFT;
        this.attackAnimationRight = ADVANCED_MAGIC_PLAYER_ATTACK_RIGHT;
    }
}
