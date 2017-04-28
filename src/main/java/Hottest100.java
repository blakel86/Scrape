/**
 * Created by laroux0b on 27/04/2017.
 */
public class Hottest100 {
//    public ArrayList<Hottest100> hottest100;
//    public int number;
//    public String song;
//    public String artist;
//    public double length;
    public String number;
    public String song;
    public String artist;
    public String length;
//
//    public ArrayList<Hottest100[]> Hottest100(){
//        hottest100 =  new ArrayList<Hottest100[]>();
//        return hottest100;
//    }
    public Hottest100(String number, String song, String artist, String length){
//    public Hottest100(int number, String song, String artist, double length){
        this.number = number;
        this.song = song;
        this.artist = artist;
        this.length = length;
    }

    @Override
    public String toString() {
        return number + "," + song + "," + artist + "," + length;
    }
}