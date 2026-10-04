import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TemperatureConverter extends Application {

    public static void main(String[] args) {
        if (args.length > 0) {
            double fahrenheit = Double.parseDouble(args[0]);
            TemperatureConverter converter = new TemperatureConverter();
            System.out.println(fahrenheit + " F = " + converter.fahrenheitToCelsius(fahrenheit) + " C");
            return;
        }
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Label title = new Label("Temperature Converter");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #172554;");

        Label prompt = new Label("Enter temperature in Fahrenheit");
        prompt.setStyle("-fx-font-size: 14px; -fx-text-fill: #475569;");

        TextField fahrenheitInput = new TextField("32");
        fahrenheitInput.setPromptText("e.g. 72");
        fahrenheitInput.setMaxWidth(260);
        fahrenheitInput.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");

        Button convertButton = new Button("Convert to Celsius");
        convertButton.setDefaultButton(true);
        convertButton.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 18px; -fx-background-radius: 8px;");

        Label result = new Label(" ");
        result.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        Label status = new Label(" ");
        status.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748b;");

        TemperatureConverter converter = new TemperatureConverter();
        convertButton.setOnAction(event -> {
            try {
                double fahrenheit = Double.parseDouble(fahrenheitInput.getText().trim());
                double celsius = converter.fahrenheitToCelsius(fahrenheit);
                result.setText(String.format("%.1f°F = %.1f°C", fahrenheit, celsius));
                boolean extreme = converter.isExtremeTemperature(celsius);
                status.setText(extreme ? "Extreme temperature" : "Temperature is within the normal range");
                status.setStyle(extreme
                        ? "-fx-font-size: 13px; -fx-text-fill: #b91c1c;"
                        : "-fx-font-size: 13px; -fx-text-fill: #15803d;");
            } catch (NumberFormatException exception) {
                result.setText("Please enter a valid number.");
                status.setText(" ");
            }
        });

        VBox content = new VBox(14, title, prompt, fahrenheitInput, convertButton, result, status);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(32));
        content.setStyle("-fx-background-color: linear-gradient(to bottom right, #eff6ff, #f8fafc);");

        stage.setTitle("Temperature Converter");
        stage.setScene(new Scene(content, 440, 360));
        stage.setMinWidth(380);
        stage.setMinHeight(320);
        stage.show();
    }

    public double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    public double celsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }

    public boolean isExtremeTemperature(double celsius) {
        return celsius < -40 || celsius > 50;
    }
}
