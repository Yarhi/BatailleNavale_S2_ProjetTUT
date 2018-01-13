import java.io.Serializable;

public class Score implements Serializable{
    private int score;

    private int score1;
    private int score2;
    private int score3;

    public Score(int score,int score1,int score2,int score3) {
        this.score = score;

        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
    }
    public String toString(){
        return "Meilleurs scores :"
                + "\n1 -" + this.score1 + "-"
                + "\n\n2 -" + this.score2 + "-"
                + "\n\n3 -" + this.score3 + "-";
    }
}