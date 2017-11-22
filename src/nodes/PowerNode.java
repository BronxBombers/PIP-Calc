package nodes;
import java.lang.Math;

public class PowerNode extends BinaryOperatorNode {
    public PowerNode(PIPCalcNode left, PIPCalcNode right){
        super(left,right, Precedence.POWER, "^");
    }

    public int evaluate(){
        return (int)Math.pow(leftChild.evaluate(), rightChild.evaluate());
    }
}
