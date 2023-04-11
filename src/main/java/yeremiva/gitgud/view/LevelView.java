package yeremiva.gitgud.view;

//LEVEL
public class LevelView {

    private int[][] lvlData;

    public LevelView(int[][] lvlData){
        this.lvlData = lvlData;
    }

    public int getSpriteIndex(int x, int y){
        return lvlData[y][x];
    }
}
