//Daniel Lian DirectoryID: dlian
//Philip Huang DirectoryID: phuang16
//Derek Luc DirectoryID: dluc
//Mateo Rodriguez DirectoryID: mrodri29


package gui;


import java.text.NumberFormat;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Interest;
import model.InterestType;

public class InterestTableGUI extends Application {
	private TextField principalA, ratePL;
	private Slider slider;
	private TextArea display;

	public void start(Stage primaryStage) {
		int sceneWidth = 480, sceneHeight = 250;

		/* Setting pane properties */
		Pane pane = new Pane();
		Scene scene = new Scene(pane, sceneWidth, sceneHeight);
		primaryStage.setTitle("Interest Table Calculator");
		primaryStage.setScene(scene);

		/* Adding GUI elements to the pane */

		Label principal = new Label("Principal:");
		principal.relocate(60, 140);
		Label rateP = new Label("Rate(Percentage): ");
		rateP.relocate(235, 140);
		principalA = new TextField();
		principalA.setPrefWidth(120);
		principalA.relocate(110, 140);
		ratePL = new TextField();
		ratePL.setPrefWidth(65);
		ratePL.relocate(330, 140);
		pane.getChildren().addAll(principal, rateP, principalA, ratePL);

		Button simpleI = new Button("SimpleInterest");
		simpleI.relocate(50, 220);
		simpleI.setPrefWidth(100);
		Button compoundI = new Button("Compound Interest");
		compoundI.relocate(160, 220);
		compoundI.setPrefWidth(130);
		Button bI = new Button("BothInterests");
		bI.relocate(300, 220);
		bI.setPrefWidth(110);

		slider = new Slider(1, 25, 25);
		slider.relocate(180, 175);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(4);
		slider.setPrefWidth(190);

		display = new TextArea();
		display.setEditable(false);
		display.setWrapText(true);
		display.relocate(0, 0);
		display.setPrefHeight(130);

		/* Adding scroll pane to text area */
		ScrollPane scrollPane = new ScrollPane(display);
	
		pane.getChildren().addAll(simpleI, compoundI, bI, slider, scrollPane);

		Label numOfYears = new Label("Number of Years: ");
		numOfYears.relocate(80, 180);

		pane.getChildren().addAll(numOfYears);
		compoundI.setOnAction(new ButtonHandler());

		/* When simple interest is clicked */
		//Lambda
		simpleI.setOnAction(e -> {
			double pAmount = Double.parseDouble(principalA.getText());
			double rate = Double.parseDouble(ratePL.getText());
			double years = slider.getValue();
			Interest simpleInterest = new Interest();
			StringBuffer output = new StringBuffer(
					"Principal: " + NumberFormat.getCurrencyInstance().format(Double.parseDouble(principalA.getText())) + ", Rate:" + rate + "\n" + "Year, Simple Interest Amount" + "\n");
			for (int i = 1; i <= years; i++) {
				String formattedValue = NumberFormat.getCurrencyInstance().format(Double.parseDouble(String.format("%.2f",
						simpleInterest.calculateInterest(pAmount, rate, i, InterestType.SimpleInterest))));
				output.append(i + "-->" + formattedValue);
				output.append("\n");
			}
			String str = output.toString();
			display.setText(str);
		});
		
		/* When both interests is clicked */
		// Anonymous Class
		Interest compoundInterest = new Interest() {
			@Override
			public double calculateInterest(double principal, double interestRate, double numOfYears,
					InterestType type) {
				double interest = 0;
				if (type == InterestType.BothInterests) {
					interest = (principal * Math.pow((1 + interestRate / 100), numOfYears));
				}
				return interest;
			}
		};
		bI.setOnAction(e -> {
			StringBuffer output = new StringBuffer("Principal: " + NumberFormat.getCurrencyInstance().format(Double.parseDouble(principalA.getText())) + ", Rate:"
					+ Double.parseDouble(ratePL.getText()) + "\n" + "Year, Simple Interest Amount, Compound Interest Amount" + "\n");
			Interest simpleInterest = new Interest();
			for (int i = 1; i <= slider.getValue(); i++) {
				String formattedValue = NumberFormat.getCurrencyInstance().format(Double.parseDouble(String.format("%.2f",
						simpleInterest.calculateInterest(Double.parseDouble(principalA.getText()), Double.parseDouble(ratePL.getText()), i, InterestType.SimpleInterest))));
				output.append(i + "-->"
						+ formattedValue);
				String formattedValue2 = NumberFormat.getCurrencyInstance().format(Double.parseDouble(String.format("%.2f",
						compoundInterest.calculateInterest(Double.parseDouble(principalA.getText()), Double.parseDouble(ratePL.getText()), i, InterestType.BothInterests))));
				output.append( "-->" + formattedValue2);
				output.append("\n");
			}
			;
			String str = output.toString();
			display.setText(str);
		});

		/* Display the stage */
		primaryStage.show();

	}

	 /*When compound interest is clicked  */
	//Non-anonymous class

	private class ButtonHandler implements EventHandler<ActionEvent> {
		
		// Anonymous Class
		Interest compoundInterest = new Interest() {
			@Override
			public double calculateInterest(double principal, double interestRate, double numOfYears,
					InterestType type) {
				double interest = 0;
				if (type == InterestType.CompoundInterest) {
					interest = (principal * Math.pow((1 + interestRate / 100), numOfYears));
				}
				return interest;
			}
		};

		@Override
		public void handle(ActionEvent e) {
			double rate = Double.parseDouble(ratePL.getText());
			double years = slider.getValue();
			{
				StringBuffer output = new StringBuffer("Principal: " + NumberFormat.getCurrencyInstance().format(Double.parseDouble(principalA.getText())) + ", Rate:" + rate + "\n"
						+ "Year, Compound Interest Amount" + "\n");
				for (int i = 1; i <= years; i++) {
					String formattedValue = NumberFormat.getCurrencyInstance().format(Double.parseDouble(String.format("%.2f",
							compoundInterest.calculateInterest(Double.parseDouble(principalA.getText()),
									Double.parseDouble(ratePL.getText()), i, InterestType.CompoundInterest))));
					output.append(i + "-->" + formattedValue);
					output.append("\n");
				}
				String str = output.toString();
				display.setText(str);
			}
		}
	}
	

	

	public static void main(String[] args) {
		Application.launch(args);

	}
}



