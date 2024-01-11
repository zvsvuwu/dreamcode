package cc.dreamcode.vote.manager;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VoteManager {

    private @Getter @Setter boolean voteInProgress = false;
    private @Getter List<UUID> forVotes = new ArrayList<>();
    private @Getter List<UUID> againstVotes = new ArrayList<>();

    private @Getter List<UUID> playersWhoVoted = new ArrayList<>();
}
