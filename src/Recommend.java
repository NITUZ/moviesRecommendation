import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Recommend {

    HashMap<Integer,Double> simUser2User; //key= userID , val= similarity
    HashMap<Integer,Double> MovieScore; //key-movieID, val-score
    HashMap<User,Calcs> calcs;

    User currentUser;

    public Recommend(User _currentUser ) {
        currentUser=_currentUser;
        simUser2User = new   HashMap<Integer,Double> ();
        MovieScore = new  HashMap<Integer,Double>();
        HashMap<User,Calcs> calcs= new  HashMap<User,Calcs>();

    }

    public double calcSimUser2User(){
       double currAVG=currentUser.avgRank;
        Iterator it = currentUser.MoviesRanks.entrySet().iterator();
        double mone1;
        while (it.hasNext()) { //all movies curr rank
            Map.Entry pair = (Map.Entry) it.next(); // pair = key-movieID val-rankBycurr
                  {

            }
            Double rA=(Double)pair.getValue(); //rank curr movie by curr user
             mone1=rA-currAVG;
             if(calcs.containsKey())




        }
    }


}
