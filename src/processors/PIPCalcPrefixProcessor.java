package processors;
import nodes.BinaryOperatorNode;
import nodes.PIPCalcNode;
import nodes.UnaryOperatorNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class PIPCalcPrefixProcessor extends PIPCalcProcessor {

    /**
    public void constructTree(ArrayList<String> tokens) {
        Stack<PIPCalcNode> operators = new Stack<>();
        Stack<PIPCalcNode> operands = new Stack<>();
        PIPCalcNode operand1, operator, operand;
        UnaryOperatorNode tempUnary;
        BinaryOperatorNode tempBinary;
        boolean pending_operand = false;
        boolean pending_unary = false;
        for (String token : tokens) {
            PIPCalcNode curr = super.createPIPCalcNode(token);
            PIPCalcNode.NodeType cType = curr.getNodeType();
            if (curr.isOperation()) {
                operators.push(curr);
                pending_operand = false;
                if (PIPCalcNode.NodeType.UnaryOperation == cType){
                    pending_unary = true;
                }
            } else {
                operand = curr;
                if (pending_operand || pending_unary) {
                    while (!operands.isEmpty() || pending_unary) {
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
                        pending_unary = false;
                    }
                }
                operands.push(operand);
                pending_operand = true;
            }
        }
        super.tree = operands.pop();
    }
     **/

    @Override
    public void constructTree(ArrayList<String> tokens) {
        Collections.reverse(tokens);
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
