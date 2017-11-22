package nodes;

public class AdditionNode extends BinaryOperatorNode{

    public AdditionNode(PIPCalcNode left, PIPCalcNode right){
        super(left,right, Precedence.ADD_SUBTRACT, "+");
    }

    public int evaluate(){
        return leftChild.evaluate() + rightChild.evaluate();
    }
}
