package nodes;

public abstract class BooleanOperatorNode extends BinaryOperatorNode{

    public BooleanOperatorNode(PIPCalcNode left,
                               PIPCalcNode right,
                               String operator){
        super(left,right,Precedence.BOOLEAN, operator);
    }

    public int getPrecedence(){
        return Precedence.BOOLEAN.getPrecedence();
    }
}
