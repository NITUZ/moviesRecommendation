import java.util.*;

public class Recommend {

    HashMap<Integer, Double> simUser2User; //key= userID , val= similarity
    HashMap<Integer, Double> MovieScore; //key-movieID, val-score
    HashMap<Integer, Calcs> calcs;


    User currentUser;

    public Recommend(User _currentUser) {
        currentUser = _currentUser;
        simUser2User = new HashMap<Integer, Double>();
        MovieScore = new HashMap<Integer, Double>();
        HashMap<Integer, Calcs> calcs = new HashMap<Integer, Calcs>();
    }
    //sort hashmap by value
    public LinkedHashMap<Integer, Double> sortHashMapByValues(
            HashMap<Integer, Double> passedMap) {
        List<Integer> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Double> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<Integer, Double> sortedMap =
                new LinkedHashMap<>();

        Iterator<Double> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Double val = valueIt.next();
            Iterator<Integer> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Integer key = keyIt.next();
                Double comp1 = passedMap.get(key);
                Double comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }
    public void calcSimUser2Users() {
        double currAVG = currentUser.avgRank;
        Iterator it = currentUser.MoviesRanks.entrySet().iterator();
        while (it.hasNext()) { //all movies curr rank
            Map.Entry pair = (Map.Entry) it.next(); // pair = key-movieID val-rankBycurr
            {
                Movie currMovie = ReadFromDB.movies.get(pair.getKey());
                for (User u : currMovie.users) {//for all users of a curr movie
                    double raForcurr = currentUser.MoviesRanks.get(pair.getKey());
                    double mone1 = raForcurr - currAVG;
                    double ruForCurr = u.MoviesRanks.get(pair.getKey());
                    double mone2 = ruForCurr - u.avgRank;
                    if (calcs.containsKey(u)) {
                        calcs.get(u).mone += (mone1 * mone2);
                        calcs.get(u).mehane1 += (mone1 * mone1);
                        calcs.get(u).mehane2 += (mone2 * mone2);
                    } else {
                        calcs.put(u.userID, new Calcs((mone1 * mone2), (mone1 * mone1), (mone2 * mone2)));
                    }
                }
            }

        }
        it = calcs.entrySet().iterator();
        while (it.hasNext()) { //all movies curr rank
            Map.Entry pair = (Map.Entry) it.next(); // pair = key-movieID val-rankBycurr
            {
                double Wij = ((Calcs) pair.getValue()).mone / (Math.sqrt(((Calcs) pair.getValue()).mehane1 * ((Calcs) pair.getValue()).mehane2));
                simUser2User.put(((Integer) pair.getKey()), Wij);
            }
            simUser2User=sortHashMapByValues(simUser2User);
        }
    }
}
