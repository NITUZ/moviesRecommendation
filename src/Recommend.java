import java.util.*;

public class Recommend {

    HashMap<Integer, Double> simUser2User; //key= userID , val= similarity
    HashMap<Integer, Double> MovieScore; //key-movieID, val-score
    HashMap<Integer, Calcs> calcs;
    HashMap<Integer, Double> MovieToOffer;
    User currentUser;

    public static void main(String[] args) {
        Movie m1 = new Movie(1, "1");
        m1.avgRank = 3;
        Movie m2 = new Movie(2, "2");
        m2.avgRank = 2;
        Movie m3 = new Movie(3, "3");
        m3.avgRank = 4;
        User u1 = new User(1, 3);
        User u2 = new User(2, 4);
        User u3 = new User(3, 2);
        m1.addUser(u1);
        m1.addUser(u2);
        m1.addUser(u3);
        m2.addUser(u1);
        m2.addUser(u3);
        m3.addUser(u2);
        u1.addMovie(m1.movieID, 2);
        u1.addMovie(m2.movieID, 4);
        u2.addMovie(m1.movieID, 4);
        u2.addMovie(m3.movieID, 4);
        u3.addMovie(m1.movieID, 1);
        u3.addMovie(m2.movieID, 3);
        Recommend r = new Recommend(u3);

        ReadFromDB.movies.put(1,m1);
        ReadFromDB.movies.put(2,m2);
        ReadFromDB.movies.put(3,m3);
        ReadFromDB.users.put(1,u1);
        ReadFromDB.users.put(2,u2);
        ReadFromDB.users.put(3,u3);


        r.calcSimUser2Users();
        r.findBestRankMovies();




    }


    public Recommend(User _currentUser) {
        currentUser = _currentUser;
        simUser2User = new HashMap<Integer, Double>();
        MovieScore = new HashMap<Integer, Double>();
        calcs = new HashMap<Integer, Calcs>();
        MovieToOffer = new HashMap<Integer, Double>();
    }

    //sort Similarity hashmap by value
    public void sortByValuesSim(HashMap map) {
        List list = new LinkedList(map.entrySet());
// Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                if (((double) ((Map.Entry) (o2)).getValue()) -
                        (((double) ((Map.Entry) (o1)).getValue())) > 0)
                    return 1;
                if (((double) ((Map.Entry) (o2)).getValue()) -
                        (((double) ((Map.Entry) (o1)).getValue())) < 0)
                    return -1;
                else return 0;
            }
        });
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        int i = 0;
        simUser2User = new HashMap<Integer, Double>();
        Iterator it = sortedHashMap.entrySet().iterator();
        while (it.hasNext()/*&& i<50*/) {
            Map.Entry pair = (Map.Entry) it.next();
            simUser2User.put((Integer) pair.getKey(), ((double) pair.getValue()));
            i++;
        }
    }

    //calc similarity between current user to neighbers
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
                        calcs.get(u.userID).mone += (mone1 * mone2);
                        calcs.get(u.userID).mehane1 += (mone1 * mone1);
                        calcs.get(u.userID).mehane2 += (mone2 * mone2);
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
                double Wij;
                if((((Calcs) pair.getValue()).mone)==0.0 || (Math.sqrt(((Calcs) pair.getValue()).mehane1 * ((Calcs) pair.getValue()).mehane2)==0 ))
                {
                    Wij=0;
                }else {
                    Wij = ((Calcs) pair.getValue()).mone / (Math.sqrt(((Calcs) pair.getValue()).mehane1 * ((Calcs) pair.getValue()).mehane2));
                }
                simUser2User.put(((Integer) pair.getKey()), Wij);
            }
            sortByValuesSim(simUser2User);
        }
    }

    public void sortByValuesScores(HashMap map) {
        List list = new LinkedList(map.entrySet());
// Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                if (((double) ((Map.Entry) (o2)).getValue()) -
                        (((double) ((Map.Entry) (o1)).getValue())) > 0)
                    return 1;
                if (((double) ((Map.Entry) (o2)).getValue()) -
                        (((double) ((Map.Entry) (o1)).getValue())) < 0)
                    return -1;
                else return 0;
            }
        });
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        int i = 0;
        MovieToOffer = new HashMap<Integer, Double>();
        Iterator it = sortedHashMap.entrySet().iterator();
        while (it.hasNext() && i < 10) {
            Map.Entry pair = (Map.Entry) it.next();
            MovieToOffer.put((Integer) pair.getKey(), ((double) pair.getValue()));
            i++;
        }
    }

    //predict which score current users give to all the movies he didn`t watch
    public void findBestRankMovies() {

        Iterator it = ReadFromDB.movies.entrySet().iterator();
        while (it.hasNext()) { //all movies
            Map.Entry pair = (Map.Entry) it.next(); // pair = key-movieID val-Movie
            Movie currMovie = ((Movie) pair.getValue());
            if (!currMovie.users.contains(currentUser)) {
                Double ScoreForItem = 0.0, calculate1 = 0.0, calculate2 = 0.0;
                for (User u : currMovie.users) {
                    if (simUser2User.containsKey(u.userID)) { //if user u is a neighber of current user
                        calculate1 += ((u.MoviesRanks.get(currMovie.movieID) - u.avgRank) * simUser2User.get(u.userID));
                        calculate2 += simUser2User.get(u.userID);
                    }
                }
                if(calculate1==0 || calculate2==0)
                    ScoreForItem=0.0;
                else {
                    ScoreForItem = currentUser.avgRank + (calculate1) / calculate2;
                }
                MovieToOffer.put(currMovie.movieID, ScoreForItem);
            }

        }
        sortByValuesScores(MovieToOffer);
        Iterator it3 = MovieToOffer.entrySet().iterator();
        while (it3.hasNext()) { //all movies
            Map.Entry pair = (Map.Entry) it3.next(); // pair = key-movieID val-Movie
            System.out.println(pair.getKey().toString() + "  " + pair.getValue().toString());

        }
    }
}