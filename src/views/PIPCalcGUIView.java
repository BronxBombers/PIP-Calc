package views;

import controllers.PIPCalcController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import processors.PIPCalcInfixProcessor;
import processors.PIPCalcPostfixProcessor;
import processors.PIPCalcPrefixProcessor;
import processors.PIPCalcProcessor;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class PIPCalcGUIView extends Application implements Observer {

    private PIPCalcController controller;

    private BorderPane mainPane;

    private String invalid = "Invalid Entry";

    private ToggleGroup group;

    private String preConverted;

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
                "Clear","//",">",">=","Space","Backspace"
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
                            phraseTtrim = phraseTtrim.replaceAll("[()]","");
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
                        colSpan = 2;
                        btn.addEventHandler(MouseEvent.MOUSE_CLICKED,e-> {
                            Text box = (Text) this.mainPane.getTop();
                            if (box.getText().equals(this.invalid)){
                                box.setText("0");
                            }
                            else {
                                try {
                                    box.setText(box.getText().substring(0, box.getText().length() - 1));
                                } catch (IndexOutOfBoundsException indx) {
                                    box.setText(String.valueOf(box.getText().charAt(0)));
                                }
                                if (box.getText().equals("")) {
                                    box.setText("0");
                                }
                            }
                        });
                        break;
                    case "Space":
                        colSpan = 2;
                        btn.addEventHandler(MouseEvent.MOUSE_CLICKED,e->{
                            Text box = (Text) this.mainPane.getTop();
                            if (box.getText().equals(this.invalid)){
                                box.setText("0");
                            }
                            else{
                                String pretxt1 = box.getText();
                                box.setText(pretxt1 + " ");
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
                                    box.setText(buttonTxt);
                                }
                                else {
                                    box.setText(PreText + buttonTxt);
                                }
                            }
                        });
                }

                btn.setPrefHeight(100);
                btn.setPrefWidth(100);
                numPad.add(btn,j,i,colSpan,rowSpan);
                if (colSpan == 2) {
                    btn.setPrefWidth(200);
                }
                j++;
                if (colSpan == 2){
                    j++;
                }
                if (j == 6){
                    i++;
                    j = 0;
                }
        }
        String[] notationSelection = new String[]{"Infix","Postfix", "Prefix"};
        ToggleGroup group = new ToggleGroup();
        this.group = group;
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
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton r = (RadioButton)newValue;
                controller.changeModel(getTypeFromString(r.getText()));
            }
        });
        String[] notationButtons = new String[]{"To Infix","To Postfix","To Prefix"};
        i = 5;
        for (String text : notationButtons){
            Button b = new Button(text);
            b.setPrefWidth(300);
            b.setPrefHeight(100);
            b.addEventHandler(MouseEvent.MOUSE_CLICKED,e->{
                Text box = (Text) mainPane.getTop();
                try {
                    this.controller.convert(box.getText(), b.getText().substring(3, b.getText().length()).toLowerCase().trim());
                    this.preConverted = box.getText();
                }catch(Exception dfaf){
                    try {
                        this.controller.convert(this.preConverted, b.getText().substring(3, b.getText().length()).toLowerCase().trim());
                    }catch(NullPointerException afdf){
                        box.setText(this.invalid);
                    }
                }
            });
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
        Text box = (Text)mainPane.getTop();
        try{
            int result = Integer.parseInt(arg.toString());
            box.setText(Integer.toString(result));
        }catch(Exception e){
            box.setText(arg.toString());
        }
    }

    private PIPCalcProcessor getTypeFromString(String type){
        PIPCalcProcessor processor;
        if(type.equals("infix")){
            processor = new PIPCalcInfixProcessor();
        }
        else if(type.equals("prefix")){
            processor = new PIPCalcPrefixProcessor();
        }
        else{
            processor = new PIPCalcPostfixProcessor();
        }

        processor.addObserver(this);
        return processor;
    }

    public static void main(String[] args){
        Application.launch();
    }
}
