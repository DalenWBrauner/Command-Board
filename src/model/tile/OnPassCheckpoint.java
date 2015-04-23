package model.tile;

public class OnPassCheckpoint implements OnPassStrat {

    @Override
    public void onPass() {
        System.out.println("You passed a checkpoint!");
    }
}
