class Hottest100 {
    private final int year;
    private final int number;
    private final String song;
    private final String artist;
    private final String length;
    private final String country;

    Hottest100(int year, int number, String song, String artist, String length, String country) {
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

    int getYear() {
        return year;
    }

    int getNumber() {
        return number;
    }

    String getSong() {
        return song;
    }

    String getArtist() {
        return artist;
    }

    String getLength() {
        return length;
    }

    String getCountry() {
        return country;
    }
}