package kellarc.springplatecalculator.model.entities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record Plate(double weight) {
    static final int BAR = 45;
    static final Plate FORTY_FIVE = new Plate(45);
    static final Plate TWENTY_FIVE = new Plate(25);
    static final Plate TEN = new Plate(10);
    static final Plate FIVE = new Plate(5);
    static final Plate TWO_AND_HALF = new Plate(2.5);
    public static List<Plate> PLATES = Arrays.asList(FORTY_FIVE, TWENTY_FIVE, TEN, FIVE, TWO_AND_HALF);

    public static List<Integer> getNumberOfPlates(int weight) {
        if ((weight < 45) || (weight % 5 != 0)) {
            throw new IllegalArgumentException("Weight must be greater than or equal to 45 lbs and divisible by 5.");
        }

        final int remainingWeight = weight - BAR;
        final int[] weightHolder = {remainingWeight};
        return PLATES.stream()
                .map(plate -> { // For each element in PLATES
                    int quantity = (int) (weightHolder[0] / (2 * plate.weight())); // Calculate how many plates go on each side
                    weightHolder[0] -= (int) (quantity * (2 * plate.weight())); // Subtract however many plates times their weight from the overall weight
                    return quantity; // Add how many plates go on each side to return list
                })
                .collect(Collectors.toList());
    }

    public static String convertToResponse(List<Integer> numberOfPlates) {
        try {
            DecimalFormat decimalFormat = new DecimalFormat("0.0");
            return IntStream.range(0, numberOfPlates.size())
                    .mapToObj(i -> {
                        Integer quantity = numberOfPlates.get(i);
                        Plate plate = Plate.PLATES.get(i);
                        String weightText;
                        if (!plate.equals(Plate.TWO_AND_HALF)) {
                            weightText = String.valueOf((int) plate.weight());
                        } else {
                            weightText = decimalFormat.format(plate.weight());
                        }
                        return weightText + " lb per side: " + quantity;
                    })
                    .collect(Collectors.joining("<br>"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
