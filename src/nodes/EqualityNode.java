package nodes;

public class EqualityNode extends BooleanOperatorNode {

    public EqualityNode(PIPCalcNode left, PIPCalcNode right){
        super(left,right,"==");
    }

    public int evaluate(){
        if (leftChild.evaluate() == rightChild.evaluate()){
            return 1;
        }
        else{
            return 0;
        }
    }
}
