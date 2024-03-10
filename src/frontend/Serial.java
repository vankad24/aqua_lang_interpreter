package frontend;

/* id генератор */
public class Serial {
    static int serial;

    public static int getid() {
        serial++;
        return serial;
    }
}
