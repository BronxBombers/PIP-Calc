package nodes;

public class GreaterThanEqualNode extends BooleanOperatorNode {

    public GreaterThanEqualNode(PIPCalcNode left, PIPCalcNode right){
        super(left,right,">=");
    }

    public int evaluate(){
        if (leftChild.evaluate() >= rightChild.evaluate()){
            return 1;
        }
        else{
            return 0;
        }
    }
}
