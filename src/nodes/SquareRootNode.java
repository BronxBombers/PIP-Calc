package nodes;
import java.lang.Math;

public class SquareRootNode extends UnaryOperatorNode{

    public SquareRootNode(PIPCalcNode child){
        super(child,Precedence.POWER, "@");
    }

    public int evaluate(){
        return (int)Math.sqrt(child.evaluate());
    }
}
