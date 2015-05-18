package model.command;

import java.util.HashMap;

import model.Player;
import shared.enums.PlayerID;
import shared.enums.SpellID;

public class CastOnPlayerCommand extends Command {
    SpellID id;
    Command spell;
    HashMap<PlayerID, Player> playerMap;

    public CastOnPlayerCommand(SpellID id, Command spell,
                               HashMap<PlayerID, Player> playerMap) {
        this.id = id;
        this.spell = spell;
        this.playerMap = playerMap;
    }

    @Override
    public void execute(Player sourcePlayer) {
        PlayerID target = sourcePlayer.getRepresentative().castOnPlayer(id);
        spell.execute(playerMap.get(target));

        setChanged();
        notifyObservers();
    }
}
