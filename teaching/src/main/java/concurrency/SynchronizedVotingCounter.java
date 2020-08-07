package concurrency;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

enum Location {
    BLR,
    MUM,
    DEL,
    HYD
}
enum Party {
    BJP,
    LOL,
    CONG
}
public class SynchronizedVotingCounter {
    private static final int TOTAL_VOTERS = 20;
    static List<Voter> generateVoters() {
        List<Voter> voters = new ArrayList<>();
        Random rand = new Random();
        for(int i = 0; i < TOTAL_VOTERS; i++) {
            voters.add(new Voter(Party.values()[rand.nextInt(3)], Integer.toString(i), Location.values()[rand.nextInt(4)]));
        }
        return voters;
    }
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(Location.values().length);
        Map<Location, LocalVotingCounter> localCounters = new HashMap<>();
        for(Location city : Location.values()) {
            localCounters.put(city, new LocalVotingCounter(city));
        }
        List<Voter> voters = generateVoters();
        Map<Location, Queue<Voter>> locationVoterQueue = new HashMap<>();
        for(Voter voter: voters) {
            System.out.printf("%s\n", voter.party);
            if(!locationVoterQueue.containsKey(voter.location)) {
                locationVoterQueue.put(voter.location, new LinkedList<>());
            }
            locationVoterQueue.get(voter.location).add(voter);
        }
        GlobalVotingCounter globalVotingCounter = new GlobalVotingCounter();
        for(Map.Entry<Location, Queue<Voter>> entry: locationVoterQueue.entrySet()) {
            executorService.submit(() -> {
                while(!entry.getValue().isEmpty()) {
                    localCounters.get(entry.getKey()).addVote(Objects.requireNonNull(entry.getValue().peek()).party);
                    entry.getValue().poll();
                }
                globalVotingCounter.reportLocalVoting(localCounters.get(entry.getKey()).votes);
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        for(Map.Entry<Party, Long> votes: globalVotingCounter.getResult().entrySet()) {
            System.out.printf("Party: [%s], \tTotalVotes:[%d]\n", votes.getKey(), votes.getValue());
        }

    }
}

class Voter {
    final Party party;
    final String personId;
    final Location location;
    Voter(Party party, String personId, Location location) {
        this.party = party;
        this.personId = personId;
        this.location = location;
    }
}
class GlobalVotingCounter {
    Map<Party, Long> votes = new HashMap<>();
    public synchronized void reportLocalVoting( Map<Party, Long> localVotes) {
        for(Map.Entry<Party, Long> entry: localVotes.entrySet()) {
            votes.putIfAbsent(entry.getKey(), 0L);
            votes.put(entry.getKey(), votes.get(entry.getKey())+entry.getValue());
        }
    }
    public Map<Party, Long> getResult() {
        return votes;
    }
}
class LocalVotingCounter {
    final Location location;
    Map<Party, Long> votes = new HashMap<>();
    LocalVotingCounter(Location location) {
        this.location = location;
    }
    public void addVote(Party party) {
        votes.putIfAbsent(party, 0L);
        votes.put(party, votes.get(party)+1);
    }
}