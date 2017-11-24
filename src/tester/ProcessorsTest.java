package tester;

import processors.PIPCalcPrefixProcessor;
import processors.PIPCalcProcessor;

import java.util.ArrayList;
import java.util.Arrays;

public class ProcessorsTest {

    public static void main(String args[]){
        PIPCalcProcessor p = new PIPCalcPrefixProcessor();
        p.constructTree(new ArrayList<>(Arrays.asList("+", "_", "-1","+","8","9")));
        p.displayTree("prefix");
        p.evaluateTree();
    }
}
