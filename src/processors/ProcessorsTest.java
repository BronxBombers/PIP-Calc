package processors;

import java.util.ArrayList;
import java.util.Arrays;

public class ProcessorsTest {

    public static void main(String args[]){
        PIPCalcProcessor p = new PIPCalcPrefixProcessor();
        p.constructTree(new ArrayList<>(Arrays.asList("+", "|", "1", "2")));
        p.displayTree("prefix");
    }
}
