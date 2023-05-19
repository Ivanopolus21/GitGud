import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import yeremiva.gitgud.model.characters.Enemy;
import yeremiva.gitgud.model.characters.Player;

public class EnemyTest {
    private Player mockedPlayer;
    private Enemy enemy;

    @BeforeEach
    public void initialize() {
        enemy = new Enemy(12,23,34,45,50,10,100,10,1);

        mockedPlayer = Mockito.mock(Player.class);
        Mockito.when(mockedPlayer.getPlayerDamage()).thenReturn(15);
    }

    @Test
    public void inAttackTest() {
        int expected = 25;
        enemy.hurt(mockedPlayer.getPlayerDamage());

        // ASSERT
        Assertions.assertEquals(expected, enemy.getCurrentHealth());
    }

    @Test
    public void inAttackToDeathTest() {
        int expected = 0;

        enemy.hurt(mockedPlayer.getPlayerDamage());

        // ASSERT
        Assertions.assertEquals(expected, enemy.getCurrentHealth());
        Assertions.assertTrue(enemy.isAlive());
    }

}
