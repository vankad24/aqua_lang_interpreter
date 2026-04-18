package runtime;

import frontend.Tree;

import java.util.ArrayList;

public class FunctionType {
    boolean is_build_in;
    ArrayList<Tree> params;
    Tree block;
    int return_type;

    public FunctionType(int return_type) {
        is_build_in = true;
        this.return_type = return_type;
    }

    public FunctionType(ArrayList<Tree> params, Tree block, int return_type) {
        is_build_in = false;
        this.params = params;
        this.block = block;
        this.return_type = return_type;
    }
}
