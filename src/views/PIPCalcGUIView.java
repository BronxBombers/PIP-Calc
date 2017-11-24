package views;

import controllers.PIPCalcController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import processors.PIPCalcInfixProcessor;
import processors.PIPCalcProcessor;
import java.util.Observable;
import java.util.Observer;

public class PIPCalcGUIView extends Application implements Observer {

    private PIPCalcController controller;

    private BorderPane mainPane;

    private String invalid = "Invalid Entry";

    @Override
    public void init() throws Exception {
        super.init();
        PIPCalcProcessor processor = new PIPCalcInfixProcessor();
        processor.addObserver(this);
        this.controller = new PIPCalcController(processor);
        this.mainPane = new BorderPane();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Text textBox = new Text("0");
        textBox.setFont(new Font("Arial",72));
        textBox.setWrappingWidth(600);
        textBox.setTextAlignment(TextAlignment.RIGHT);
        mainPane.setTop(textBox);
        GridPane numPad = new GridPane();
        String[] buttonText = new String[]{
                "@","^","1","2","3","+","|","_","4","5","6","-",
                "<","<=","7","8","9","*","!=","==","Enter","0",
                "Clear","//",">",">=","Backspace"
        };
            int i,j,colSpan,rowSpan;
            i = 0;
            j = 0;
            for (String text : buttonText){
                colSpan = 1;
                rowSpan = 1;
                Button btn = new Button(text);
                switch(text){
                    case "Enter":
                        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
                            Text phrase = (Text)this.mainPane.getTop();
                            String phraseT = phrase.getText();
                            String phraseTtrim = phraseT.trim();
                            try {
                                this.controller.process(phraseTtrim);
                            }catch(Exception exception){
                                exception.printStackTrace();
                                phrase.setText(this.invalid);
                            }
                        });
                        break;
                    case "Clear":
                        btn.addEventHandler(MouseEvent.MOUSE_CLICKED,e->{
                            Text box = (Text)this.mainPane.getTop();
                            box.setText("0");
                        });
                        break;
                    case "Backspace":
                        colSpan = 4;
                        btn.addEventHandler(MouseEvent.MOUSE_CLICKED,e-> {
                            Text box = (Text) this.mainPane.getTop();
                            if (box.getText().equals(this.invalid)){
                                box.setText("0");
                            }
                            else {
                                try {
                                    if (box.getText().charAt(box.getText().length() - 1) == ' ') {
                                        String c = Character.toString(box.getText().charAt(box.getText().length() - 2));
                                        if (c.matches("")) {
                                            box.setText(box.getText().substring(0, box.getText().length() - 4));
                                        } else {
                                            box.setText(box.getText().substring(0, box.getText().length() - 3));
                                        }
                                    } else {
                                        box.setText(box.getText().substring(0, box.getText().length() - 1));
                                    }
                                } catch (IndexOutOfBoundsException indx) {
                                }
                                if (box.getText().equals("")) {
                                    box.setText("0");
                                }
                            }
                        });
                        break;
                    default:
                        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
                            Text box = (Text)this.mainPane.getTop();
                            String PreText = box.getText();
                            String buttonTxt = btn.getText();
                            if (PreText.equals(this.invalid)){
                                box.setText("0");
                                PreText = "";
                            }
                            try{
                                int num = Integer.parseInt(buttonTxt);
                                if (PreText.equals("0")){
                                    box.setText(Integer.toString(num));
                                }
                                else {
                                    box.setText(PreText + num);
                                }
                            }catch(Exception ex) {
                                if (PreText.equals("0")){
                                    box.setText(buttonTxt + " ");
                                }
                                else {
                                    if (PreText.charAt(PreText.length()-1) == ' '){
                                        box.setText(PreText + buttonTxt + " ");
                                    }
                                    else {
                                        box.setText(PreText + " " + buttonTxt + " ");
                                    }
                                }
                            }
                        });
                }
                if (colSpan != 1){
                    btn.setPrefHeight(100);
                    btn.setPrefWidth(400);
                    numPad.add(btn,j,i,colSpan,rowSpan);
                }
                else {
                    btn.setPrefHeight(100);
                    btn.setPrefWidth(100);
                    numPad.add(btn, j, i);
                }
                j++;
                if (j == 6 || colSpan != 1){
                    i++;
                    j = 0;
                }
        }
        String[] notationSelection = new String[]{"Infix","Postfix", "Prefix"};
        ToggleGroup group = new ToggleGroup();
        i = 5;
        for (String text : notationSelection){
            RadioButton rb = new RadioButton(text);
            rb.setFont(new Font("Arial", 42));
            rb.setToggleGroup(group);
            if (text.equals("Infix")){
                rb.setSelected(true);
            }
            numPad.add(rb,0,i,3,1);
            i++;
        }

        String[] notationButtons = new String[]{"To Infix","To Postfix","To Prefix"};
        i = 5;
        for (String text : notationButtons){
            Button b = new Button(text);
            b.setPrefWidth(300);
            b.setPrefHeight(100);
            numPad.add(b,3,i,3,1);
            i++;
        }
        mainPane.setCenter(numPad);
        Scene scene = new Scene(mainPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("PIP Calc");
        primaryStage.show();
    }


    @Override
    public void update(Observable o, Object arg) {
        try{
            int result = Integer.parseInt(arg.toString());
            Text box = (Text)mainPane.getTop();
            box.setText(Integer.toString(result));
        }catch(Exception e){
            return;
        }
    }

    public static void main(String[] args){
        Application.launch();
    }
}
