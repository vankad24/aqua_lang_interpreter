package ch5;

public class SymbolEntry {
    String name;
    int type;

    public SymbolEntry(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public SymbolEntry(String name, String type) {
        this.name = name;
        this.type = SymbolType.stringToType(type);
    }


}
