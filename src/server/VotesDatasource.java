package server;

import java.util.HashMap;
import java.util.Objects;

public class VotesDatasource {

    private HashMap<String, Boolean> votacao = new HashMap<>();
    private static VotesDatasource instance;

    private VotesDatasource() {

    }

    public static VotesDatasource getInstance() {
        if (instance == null) {
            instance = new VotesDatasource();
        }
        return instance;
    }

    public long getTotalVotosAFavor() {
        return votacao.values().stream().filter(i -> i).count();
    }

    public long getTotalVotosComtra() {
        return votacao.values().stream().filter(i -> !i).count();
    }

    public String getVotesData() {
        String result = "";
        for ( String key : votacao.keySet() ) {
            result.concat(key).concat(":").concat(String.valueOf(votacao.get(key))).concat(";");
        }
        return result;
    }


    // ParamExample voteData = "Victor;favor;Lucas;favor;Bruce;contra";
    public void processVote(String voteData) {
        if (voteData == null) {
            return;
        }
        String[] votes = voteData.split(";");

        for (int i = 0; i < votes.length; i += 2) {
            votacao.put(votes[i], Objects.equals(votes[i + 1], "favor"));
        }
    }

}
