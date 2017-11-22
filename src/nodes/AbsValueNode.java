package nodes;
import java.lang.Math;

public class AbsValueNode extends UnaryOperatorNode{

    public AbsValueNode(PIPCalcNode child){
        super(child, Precedence.MULT_DIVIDE, "|");
    }

    public int evaluate(){
        return Math.abs(child.evaluate());
    }
}
