package nodes;

public class GreaterThanNode extends BooleanOperatorNode {

    public GreaterThanNode(PIPCalcNode left, PIPCalcNode right){
        super(left,right,">");
    }

    public int evaluate(){
        if (leftChild.evaluate() > rightChild.evaluate()){
            return 1;
        }
        else{
            return 0;
        }
    }
}
