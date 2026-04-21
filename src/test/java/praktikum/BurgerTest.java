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
    public void setBuns_shouldSetBun_andAffectPrice() {
        Burger burger = new Burger();

        Bun bun = mock(Bun.class);
        when(bun.getPrice()).thenReturn(100f);

        burger.setBuns(bun);

        assertEquals(200f, burger.getPrice(), 0.001);
    }

    // ---------- addIngredient ----------

    @Test
    public void addIngredient_shouldIncreaseIngredientsListSize() {
        Burger burger = new Burger();

        Ingredient ingredient = mock(Ingredient.class);

        burger.addIngredient(ingredient);

        assertEquals(1, burger.ingredients.size());
    }

    @Test
    public void addIngredient_shouldAffectPrice() {
        Burger burger = new Burger();

        Bun bun = mock(Bun.class);
        when(bun.getPrice()).thenReturn(100f);
        burger.setBuns(bun);

        Ingredient ingredient = mock(Ingredient.class);
        when(ingredient.getPrice()).thenReturn(50f);

        burger.addIngredient(ingredient);

        assertEquals(250f, burger.getPrice(), 0.001);
    }

    // ---------- removeIngredient ----------

    @Test
    public void removeIngredient_shouldRemoveIngredientByIndex() {
        Burger burger = new Burger();

        Ingredient i1 = mock(Ingredient.class);
        Ingredient i2 = mock(Ingredient.class);

        burger.addIngredient(i1);
        burger.addIngredient(i2);

        burger.removeIngredient(0);

        assertEquals(1, burger.ingredients.size());
        assertEquals(i2, burger.ingredients.get(0));
    }

    @Test
    public void removeIngredient_shouldDecreasePrice() {
        Burger burger = new Burger();

        Bun bun = mock(Bun.class);
        when(bun.getPrice()).thenReturn(100f);
        burger.setBuns(bun);

        Ingredient i1 = mock(Ingredient.class);
        when(i1.getPrice()).thenReturn(50f);

        Ingredient i2 = mock(Ingredient.class);
        when(i2.getPrice()).thenReturn(30f);

        burger.addIngredient(i1);
        burger.addIngredient(i2);

        burger.removeIngredient(0);

        assertEquals(230f, burger.getPrice(), 0.001);
    }

    // ---------- moveIngredient (параметризация) ----------

    @RunWith(Parameterized.class)
    public static class MoveIngredientParameterizedTest {

        private final int from;
        private final int to;
        private final int expectedFirstIndex;

        public MoveIngredientParameterizedTest(int from, int to, int expectedFirstIndex) {
            this.from = from;
            this.to = to;
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
        public void moveIngredient_shouldChangeOrderCorrectly() {
            Burger burger = new Burger();

            Ingredient i1 = mock(Ingredient.class);
            Ingredient i2 = mock(Ingredient.class);

            burger.addIngredient(i1);
            burger.addIngredient(i2);

            burger.moveIngredient(from, to);

            Ingredient expectedFirst = expectedFirstIndex == 0 ? i1 : i2;

            assertEquals(expectedFirst, burger.ingredients.get(0));
        }
    }

    @Test
    public void moveIngredient_sameIndex_shouldNotChangeOrder() {
        Burger burger = new Burger();

        Ingredient i1 = mock(Ingredient.class);
        Ingredient i2 = mock(Ingredient.class);

        burger.addIngredient(i1);
        burger.addIngredient(i2);

        burger.moveIngredient(0, 0);

        assertEquals(i1, burger.ingredients.get(0));
        assertEquals(i2, burger.ingredients.get(1));
    }

    // ---------- getPrice ----------

    @Test
    public void getPrice_shouldReturnCorrectPrice_withMultipleIngredients() {
        Burger burger = new Burger();

        Bun bun = mock(Bun.class);
        when(bun.getPrice()).thenReturn(100f);
        burger.setBuns(bun);

        Ingredient i1 = mock(Ingredient.class);
        when(i1.getPrice()).thenReturn(50f);

        Ingredient i2 = mock(Ingredient.class);
        when(i2.getPrice()).thenReturn(30f);

        burger.addIngredient(i1);
        burger.addIngredient(i2);

        assertEquals(280f, burger.getPrice(), 0.001);
    }

    // ---------- getReceipt ----------

    @Test
    public void getReceipt_shouldContainCorrectData() {
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

        assertTrue(receipt.contains("(==== black bun ====)"));
        assertTrue(receipt.contains("= filling cheese ="));
        assertTrue(receipt.contains("Price:"));
    }
}