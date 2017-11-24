package processors;

import nodes.BinaryOperatorNode;
import nodes.PIPCalcNode;
import nodes.UnaryOperatorNode;
import java.util.ArrayList;
import java.util.Stack;

public class PIPCalcPostfixProcessor extends PIPCalcProcessor{

    @Override
    public void constructTree(ArrayList<String> tokens) {
        Stack<PIPCalcNode> operands = new Stack<>();
        PIPCalcNode operand1, operand2;
        BinaryOperatorNode binOp;
        UnaryOperatorNode unaOp;
        for (String token : tokens){
            PIPCalcNode curr = super.createPIPCalcNode(token);
            PIPCalcNode.NodeType cType = curr.getNodeType();
            if (curr.isOperation()){
                if (cType == PIPCalcNode.NodeType.UnaryOperation){
                    unaOp = (UnaryOperatorNode) curr;
                    operand1 = operands.pop();
                    unaOp.setChild(operand1);
                    operands.push(unaOp);
                }
                else{
                    binOp = (BinaryOperatorNode)curr;
                    operand2 = operands.pop();
                    operand1 = operands.pop();
                    binOp.setLeftChild(operand1);
                    binOp.setRightChild(operand2);
                    operands.push(binOp);
                }
            }
            else{
                operands.push(curr);
            }
        }
        super.tree = operands.pop();
    }
}
