package runtime;

import frontend.Tree;

public class FunctionType {
    boolean is_build_in;
    Tree params;
    Tree block;

    public FunctionType() {
        is_build_in = true;
    }

    public FunctionType(Tree params, Tree block) {
        is_build_in = false;
        this.params = params;
        this.block = block;
    }
}
