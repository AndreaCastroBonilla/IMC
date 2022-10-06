package imc;

import javafx.application.Application;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IMC extends Application {

	private TextField altura, peso;
	private Label alturaLabel, pesoLabel, unidadA, unidadP, imcLabel, txtLabel, resLabel;

	private DoubleProperty p = new SimpleDoubleProperty();
	private DoubleProperty a = new SimpleDoubleProperty();
	private DoubleProperty result = new SimpleDoubleProperty();

	@Override
	public void start(Stage primaryStage) throws Exception {
		peso = new TextField();
		altura = new TextField();

		pesoLabel = new Label();
		pesoLabel.setText("Peso: ");
		alturaLabel = new Label();
		alturaLabel.setText("Altura: ");

		unidadA = new Label();
		unidadA.setText(" cm");
		unidadP = new Label();
		unidadP.setText(" kg");

		HBox datosPeso = new HBox(pesoLabel, peso, unidadP);
		datosPeso.setSpacing(5);
		HBox datosAltura = new HBox(alturaLabel, altura, unidadA);
		datosAltura.setSpacing(5);

		imcLabel = new Label();

		txtLabel = new Label();
		txtLabel.setText("IMC = ");

		resLabel = new Label();

		HBox datosIMC = new HBox(txtLabel, imcLabel);

		VBox root = new VBox(5, datosPeso, datosAltura, datosIMC, resLabel);
		root.setFillWidth(false);
		root.setAlignment(Pos.CENTER);

		primaryStage.setTitle("IMC");
		primaryStage.setScene(new Scene(root, 320, 200));
		primaryStage.show();

		// BINDINGS
		peso.textProperty().bindBidirectional(p, new NumberStringConverter());
		altura.textProperty().bindBidirectional(a, new NumberStringConverter());
		imcLabel.textProperty().bind(result.asString("%.2f"));

		// LÓGICA
		calcularIMC();
	}

	private void calcularIMC() {
		DoubleExpression denominador = a.divide(100);
		DoubleExpression res = p.divide(denominador.multiply(denominador));

		result.bind(res);

		result.addListener((o, ov, nv) -> {

			double aux = nv.doubleValue();

			if (aux < 18.5) {
				resLabel.setText("Infrapeso");
			} else if (aux >= 18.5 && aux < 25) {
				resLabel.setText("Normal");
			} else if (aux >= 25 && aux < 30) {
				resLabel.setText("Sobrepeso");
			} else if (aux >= 30) {
				resLabel.setText("Obeso");
			}

		});
	}

	public static void main(String[] args) {
		launch(args);
	}

}
