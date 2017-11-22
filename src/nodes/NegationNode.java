package nodes;

public class NegationNode extends UnaryOperatorNode {

    public NegationNode(PIPCalcNode child){
        super(child,Precedence.MULT_DIVIDE, "_");
    }

    public int evaluate(){
        return -child.evaluate();
    }
}
