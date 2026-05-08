package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BurgerTest {

    // ---------- setBuns ----------

    @Test
    public void setBunsShouldAffectPrice() {
        Burger burger = new Burger();

        Bun bun = mock(Bun.class);
        when(bun.getPrice()).thenReturn(100f);

        burger.setBuns(bun);

        float expectedPrice = 200f;
        assertEquals(expectedPrice, burger.getPrice(), 0.001);
    }

    // ---------- addIngredient ----------

    @Test
    public void addIngredientShouldIncreaseListSize() {
        Burger burger = new Burger();

        Ingredient ingredient = mock(Ingredient.class);

        burger.addIngredient(ingredient);

        int expectedSize = 1;
        assertEquals(expectedSize, burger.ingredients.size());
    }

    @Test
    public void addIngredientShouldAffectPrice() {
        Burger burger = new Burger();

        Bun bun = mock(Bun.class);
        when(bun.getPrice()).thenReturn(100f);
        burger.setBuns(bun);

        Ingredient ingredient = mock(Ingredient.class);
        when(ingredient.getPrice()).thenReturn(50f);

        burger.addIngredient(ingredient);

        float expectedPrice = 250f;
        assertEquals(expectedPrice, burger.getPrice(), 0.001);
    }

    // ---------- removeIngredient ----------

    @Test
    public void removeIngredientShouldDecreaseListSize() {
        Burger burger = new Burger();

        Ingredient firstIngredient = mock(Ingredient.class);
        Ingredient secondIngredient = mock(Ingredient.class);

        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);

        int indexToRemove = 0;
        burger.removeIngredient(indexToRemove);

        int expectedSize = 1;
        assertEquals(expectedSize, burger.ingredients.size());
    }

    @Test
    public void removeIngredientShouldShiftElementsLeft() {
        Burger burger = new Burger();

        Ingredient firstIngredient = mock(Ingredient.class);
        Ingredient secondIngredient = mock(Ingredient.class);

        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);

        int indexToRemove = 0;
        burger.removeIngredient(indexToRemove);

        assertEquals(secondIngredient, burger.ingredients.get(0));
    }

    @Test
    public void removeIngredientShouldDecreasePrice() {
        Burger burger = new Burger();

        Bun bun = mock(Bun.class);
        when(bun.getPrice()).thenReturn(100f);
        burger.setBuns(bun);

        Ingredient firstIngredient = mock(Ingredient.class);
        when(firstIngredient.getPrice()).thenReturn(50f);

        Ingredient secondIngredient = mock(Ingredient.class);
        when(secondIngredient.getPrice()).thenReturn(30f);

        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);

        int indexToRemove = 0;
        burger.removeIngredient(indexToRemove);

        float expectedPrice = 230f;
        assertEquals(expectedPrice, burger.getPrice(), 0.001);
    }

    // ---------- moveIngredient (параметризация) ----------

    @RunWith(Parameterized.class)
    public static class MoveIngredientParameterizedTest {

        private final int fromIndex;
        private final int toIndex;
        private final int expectedFirstIndex;

        public MoveIngredientParameterizedTest(int fromIndex, int toIndex, int expectedFirstIndex) {
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
            this.expectedFirstIndex = expectedFirstIndex;
        }

        @Parameterized.Parameters
        public static Iterable<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {0, 1, 1},
                    {1, 0, 0}
            });
        }

        @Test
        public void moveIngredientShouldChangeOrderCorrectly() {
            Burger burger = new Burger();

            Ingredient firstIngredient = mock(Ingredient.class);
            Ingredient secondIngredient = mock(Ingredient.class);

            burger.addIngredient(firstIngredient);
            burger.addIngredient(secondIngredient);

            burger.moveIngredient(fromIndex, toIndex);

            Ingredient expectedFirst =
                    expectedFirstIndex == 0 ? firstIngredient : secondIngredient;

            assertEquals(expectedFirst, burger.ingredients.get(0));
        }
    }

    @Test
    public void moveIngredientSameIndexShouldKeepFirstElement() {
        Burger burger = new Burger();

        Ingredient firstIngredient = mock(Ingredient.class);
        Ingredient secondIngredient = mock(Ingredient.class);

        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);

        int index = 0;
        burger.moveIngredient(index, index);

        assertEquals(firstIngredient, burger.ingredients.get(0));
    }

    @Test
    public void moveIngredientSameIndexShouldKeepSecondElement() {
        Burger burger = new Burger();

        Ingredient firstIngredient = mock(Ingredient.class);
        Ingredient secondIngredient = mock(Ingredient.class);

        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);

        int index = 0;
        burger.moveIngredient(index, index);

        assertEquals(secondIngredient, burger.ingredients.get(1));
    }

    // ---------- getPrice ----------

    @Test
    public void getPriceShouldReturnCorrectPrice() {
        Burger burger = new Burger();

        Bun bun = mock(Bun.class);
        when(bun.getPrice()).thenReturn(100f);
        burger.setBuns(bun);

        Ingredient firstIngredient = mock(Ingredient.class);
        when(firstIngredient.getPrice()).thenReturn(50f);

        Ingredient secondIngredient = mock(Ingredient.class);
        when(secondIngredient.getPrice()).thenReturn(30f);

        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);

        float expectedPrice = 280f;
        assertEquals(expectedPrice, burger.getPrice(), 0.001);
    }

    // ---------- getReceipt ----------

    @Test
    public void getReceiptShouldContainBun() {
        Burger burger = new Burger();

        Bun bun = mock(Bun.class);
        when(bun.getName()).thenReturn("black bun");
        when(bun.getPrice()).thenReturn(100f);
        burger.setBuns(bun);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("(==== black bun ====)"));
    }

    @Test
    public void getReceiptShouldContainIngredient() {
        Burger burger = new Burger();

        Bun bun = mock(Bun.class);
        when(bun.getName()).thenReturn("black bun");
        when(bun.getPrice()).thenReturn(100f);
        burger.setBuns(bun);

        Ingredient ingredient = mock(Ingredient.class);
        when(ingredient.getName()).thenReturn("cheese");
        when(ingredient.getPrice()).thenReturn(50f);
        when(ingredient.getType()).thenReturn(IngredientType.FILLING);

        burger.addIngredient(ingredient);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("= filling cheese ="));
    }

    @Test
    public void getReceiptShouldContainPrice() {
        Burger burger = new Burger();

        Bun bun = mock(Bun.class);
        when(bun.getName()).thenReturn("black bun");
        when(bun.getPrice()).thenReturn(100f);
        burger.setBuns(bun);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("Price:"));
    }
}