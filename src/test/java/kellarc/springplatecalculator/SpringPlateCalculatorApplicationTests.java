package kellarc.springplatecalculator;

import kellarc.springplatecalculator.model.entities.Plate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
class SpringPlateCalculatorApplicationTests {
    @Test
    void testAlgorithmOnValidWeights() {
        IntStream.iterate(45, weight -> weight <= 1000000, weight -> weight + 5)
                .forEach(weight -> {
                    List<Integer> plateQuantities = Plate.getNumberOfPlates(weight);
                    double sum = 45.00 + IntStream.range(0, plateQuantities.size())
                            .mapToDouble(i -> plateQuantities.get(i) * 2 * Plate.PLATES.get(i).weight())
                            .sum();
                    Assertions.assertEquals(weight, sum);
                });
    }
}
