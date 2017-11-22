package nodes;

public class MultiplicationNode extends BinaryOperatorNode {

    public MultiplicationNode(PIPCalcNode left, PIPCalcNode right){
        super(left,right,Precedence.MULT_DIVIDE, "*");
    }

    public int evaluate(){
        return leftChild.evaluate() * rightChild.evaluate();
    }
}
