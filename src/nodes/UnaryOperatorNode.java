package nodes;

public abstract class UnaryOperatorNode implements PIPCalcNode {

    protected PIPCalcNode child;

    protected String operator;

    protected Precedence precedence;

    public UnaryOperatorNode(PIPCalcNode child,
                             Precedence precedence,
                             String operator){
        this.child = child;
        this.operator = operator;
        this.precedence = precedence;
    }

    public void setChild(PIPCalcNode child){
        this.child = child;
    }

    public String toPrefixString(){
        return operator + child.toPrefixString();
    }

    public String toInfixString(){
        return operator + child.toInfixString();
    }

    public String toPostfixString(){
        return child.toPostfixString() + operator;
    }

    public int getPrecedence(){
        return precedence.getPrecedence();
    }

    public boolean isOperation(){
        return true;
    }

    public PIPCalcNode.NodeType getNodeType(){
        return NodeType.UnaryOperation;
    }
}
