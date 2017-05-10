/**
 * Created by laroux0b on 27/04/2017.
 */

public class Hottest100 {
    public int year;
    public int number;
    public String song;
    public String artist;
    public String length;
    public String country;

    public Hottest100(int year, int number, String song, String artist, String length, String country) {
        this.year = year;
        this.number = number;
        this.song = song;
        this.artist = artist;
        this.length = length;
        this.country = country;
    }

    @Override
    public String toString() {
        return year + "," + number + "," + song + "," + artist + "," + length + "," + country;
    }

    public int getYear(){
        return year;
    }

    public int getNumber() {
        return number;
    }

    public String getSong(){
        return song;
    }

    public String getArtist(){
        return artist;
    }

    public String getLength(){
        return length;
    }

    public String getCountry() {
        return country;
    }

}