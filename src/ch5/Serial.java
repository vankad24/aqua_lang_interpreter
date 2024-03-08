package ch5;

/* id генератор */
class Serial {
    static int serial;

    public static int getid() {
        serial++;
        return serial;
    }
}
