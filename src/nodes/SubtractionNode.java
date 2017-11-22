package nodes;

public class SubtractionNode extends BinaryOperatorNode{

    public  SubtractionNode(PIPCalcNode left, PIPCalcNode right){
        super(left,right,Precedence.ADD_SUBTRACT, "-");
    }

    public int evaluate(){
        return leftChild.evaluate() - rightChild.evaluate();
    }
}
