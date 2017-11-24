package processors;

import nodes.BinaryOperatorNode;
import nodes.PIPCalcNode;
import nodes.Precedence;
import nodes.UnaryOperatorNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class PIPCalcInfixProcessor extends PIPCalcProcessor {
    @Override
    public void constructTree(ArrayList<String> tokens) {
        Stack<PIPCalcNode> operators = new Stack<>();
        Queue<PIPCalcNode> outputQ = new LinkedList<>();
        Stack<PIPCalcNode> operands = new Stack<>();
        PIPCalcNode operand1, operand2;
        BinaryOperatorNode binOp;
        UnaryOperatorNode unaOp;
        for (String token : tokens) {
            PIPCalcNode curr = super.createPIPCalcNode(token);
            if (!curr.isOperation()){
                outputQ.add(curr);
            }
            else{
                int precedence = curr.getPrecedence();
                while(!operators.isEmpty() && precedence >= operators.peek().getPrecedence()){
                    outputQ.add(operators.pop());
                }
                operators.push(curr);
            }
        }
        while (!operators.isEmpty()){
            outputQ.add(operators.pop());
        }
        for (PIPCalcNode curr : outputQ){
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
