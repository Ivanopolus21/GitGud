import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import yeremiva.gitgud.controller.EnemyController;
import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.controller.GameProcessController;
import yeremiva.gitgud.model.characters.Enemy;
import yeremiva.gitgud.model.characters.Player;
import yeremiva.gitgud.model.characters.Skeleton;

import static org.mockito.Mockito.*;

public class PlayerTest {
    private Player player;
    private Enemy mockedEnemy;

    @BeforeEach
    public void initialize() {
        player = new Player(200,200,32,32,100, 100, 2f, 10, new GameProcessController(new GameController()));

        mockedEnemy = Mockito.mock(Enemy.class);
        Mockito.when(mockedEnemy.getEnemyDamage()).thenReturn(10);
    }

    @Test
    public void inAttackTest() {
        int expected = 90;
        player.changeHealth(- mockedEnemy.getEnemyDamage());

        // ASSERT
        Assertions.assertEquals(expected, player.getCurrentHealth());
    }

    @Test
    public void inAttackToDeathTest() {
        int expected = 0;

        player.changeHealth(- mockedEnemy.getEnemyDamage() *11);

        // ASSERT
        Assertions.assertEquals(expected, player.getCurrentHealth());
        Assertions.assertTrue(player.isDead());
    }

}
