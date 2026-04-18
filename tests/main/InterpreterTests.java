package main;

import frontend.j0;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class InterpreterTests {

    void test_success(String filename, Object var_value){
        var file_path = "files/"+filename;
        int exit_code;
        try {
            j0.init(file_path);
            exit_code = j0.par.yyparse();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        var scope = j0.global_scope;

        var var_val = scope.getVar("res").value;

        assertEquals(0, exit_code);
        assertEquals(var_value, var_val);
    }

    void test_fail(String filename, String error_msg){
        var file_path = "files/"+filename;
        Exception e = assertThrows(RuntimeException.class, () -> {
            j0.init(file_path);
            j0.par.yyparse();
        });
        System.out.println(e.getMessage());
        assertTrue(e.getMessage().contains(error_msg));
    }


    @Test
    void test_if_success() {
        test_success("if_success", 8);
    }

    @Test
    void test_if_fail_init() {
        test_fail("if_fail_init", "might not have been initialized");
    }

    @Test
    void test_while_success() {
        test_success("while_success", 3);
    }

    @Test
    void test_while_fail() {
        test_fail("while_fail", "name 'j' is not defined");
    }

    @Test
    void test_for_success() {
        test_success("for_success", 6);
    }

    @Test
    void test_for_fail() {
        test_fail("for_fail", "name 'sum' is not defined");
    }

    @Test
    void test_func_success() {
        test_success("func_success", 8);
    }

    @Test
    void test_func_recursion_success() {
        test_success("func_recursion_success", 24);
    }

    @Test
    void test_func_fail() {
        test_fail("func_fail", "name 'b' is not defined");
    }

    @Test
    void test_intarray_success() {
        test_success("intarray_success", 3);
    }

    @Test
    void test_intarray_fail() {
        test_fail("intarray_fail", "Index 3 out of bounds for length 2");
    }
}