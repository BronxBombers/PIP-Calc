package nodes;

public class DivisionNode extends BinaryOperatorNode{

    public DivisionNode(PIPCalcNode left, PIPCalcNode right){
        super(left,right,Precedence.MULT_DIVIDE, "//");
    }

    public int evaluate(){
        return leftChild.evaluate() / rightChild.evaluate();
    }
}
