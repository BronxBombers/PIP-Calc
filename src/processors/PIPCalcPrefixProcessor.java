package processors;
import nodes.BinaryOperatorNode;
import nodes.PIPCalcNode;
import nodes.UnaryOperatorNode;

import java.util.ArrayList;
import java.util.Stack;

public class PIPCalcPrefixProcessor extends PIPCalcProcessor {

    public void constructTree(ArrayList<String> tokens){
        Stack<PIPCalcNode> operators = new Stack<>();
        Stack<PIPCalcNode> operands = new Stack<>();
        PIPCalcNode operand1, operator, operand;
        UnaryOperatorNode tempUnary;
        BinaryOperatorNode tempBinary;
        boolean pending_operand = false;
        for (String token : tokens){
            PIPCalcNode curr = super.createPIPCalcNode(token);
            PIPCalcNode.NodeType cType = curr.getNodeType();
            if (curr.isOperation()){
                operators.push(curr);
                pending_operand = (PIPCalcNode.NodeType.UnaryOperation == cType);
            }
            else{
                operand = curr;
                if (pending_operand) {
                    while (!operands.isEmpty()) {
                        operator = operators.pop();
                        PIPCalcNode.NodeType type = operator.getNodeType();
                        if (type == PIPCalcNode.NodeType.UnaryOperation) {
                            tempUnary = (UnaryOperatorNode) operator;
                            tempUnary.setChild(operand);
                            operand = tempUnary;
                        } else {
                            operand1 = operands.pop();
                            tempBinary = (BinaryOperatorNode) operator;
                            tempBinary.setLeftChild(operand1);
                            tempBinary.setRightChild(operand);
                            operand = tempBinary;
                        }
                    }
                }
                operands.push(operand);
                pending_operand = true;
            }
        }
        super.tree = operands.pop();
    }
}
