package frontend;

public class Token {
    public int id;
    public int code; /* from Parser class */
    public String text;
    public int lineno;

    public Token(int code, String s, int l) {
        this.code = code;
        text = s;
        lineno = l;
        id = Serial.getid();
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", code=" + code +
                ", text='" + text + '\'' +
                ", lineno=" + lineno +
                '}';
    }
}
