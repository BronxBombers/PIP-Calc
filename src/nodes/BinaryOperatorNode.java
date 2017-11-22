package nodes;

public abstract class BinaryOperatorNode implements PIPCalcNode {

    protected PIPCalcNode leftChild;

    protected PIPCalcNode rightChild;

    protected Precedence precedence;

    protected String operator;

    public BinaryOperatorNode(PIPCalcNode leftChild,
                              PIPCalcNode rightChild,
                              Precedence precedence,
                              String operator){
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.precedence = precedence;
        this.operator = operator;
    }

    public void setLeftChild(PIPCalcNode leftChild){
        this.leftChild = leftChild;
    }

    public void setRightChild(PIPCalcNode rightChild){
        this.rightChild = rightChild;
    }

    public String toPrefixString(){
        return operator + leftChild.toPrefixString() + rightChild.toPrefixString();
    }

    public String toInfixString(){
        return leftChild.toInfixString() + operator + rightChild.toInfixString();
    }

    public String toPostfixString(){
        return leftChild.toPostfixString() + rightChild.toPostfixString() + operator;
    }

    public int getPrecedence(){
        return precedence.getPrecedence();
    }

    public boolean isOperation(){
        return true;
    }

    public PIPCalcNode.NodeType getNodeType(){
        return NodeType.BinaryOperation;
    }
}
