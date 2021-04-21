package ie.tudublin;

public class Note
{
    private char note;
    private int duration;

    public Note(char note, int duration) {

        this.note = note;
        this.duration = duration;

    }

    public int getDuration() {
        return duration;
    }

    public char getNote() {
        return note;
    }

    public String toString() {

        return note+ " " +duration;  

    }



}