package nodes;

public class ConstantNode implements PIPCalcNode {

    private int value;

    public ConstantNode(int value){
        this.value = value;
    }

    public int evaluate(){
        return value;
    }

    public String toPrefixString(){
        return Integer.toString(value);
    }

    public String toInfixString(){
        return Integer.toString(value);
    }

    public String toPostfixString(){
        return Integer.toString(value);
    }

    public int getPrecedence(){
        return Precedence.CONSTANT.getPrecedence();
    }

    public boolean isOperation(){
        return false;
    }

    public PIPCalcNode.NodeType getNodeType(){
        return NodeType.Constant;
    }
}
