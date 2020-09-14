import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BowlingGameTest {

    @Test
    void should_construct_empty_frame() {

        Frame expected = new Frame();

        assertEquals(expected.getRemainingBalls(), 10);
        assertEquals(expected.getCurrThrow(), 0);
        assertFalse(expected.isThrowComplete());
        assertEquals(expected.getRemainingScoringThrows(), 2);
        assertArrayEquals(expected.getScores(), new int[] { -1, -1, -1 });
    }


    @Test
    void should_construct_strike_frame() {

        Frame expected = new Frame();
        expected.throwBall(10);

        assertEquals(expected.getRemainingBalls(), 0);
        assertEquals(expected.getCurrThrow(), 1);
        assertTrue(expected.isThrowComplete());
        assertEquals(expected.getRemainingScoringThrows(), 2);
        assertArrayEquals(expected.getScores(), new int[]{ 10, -1, -1 });
    }


    @Test
    void should_construct_spare_frame() {

        Frame expected = new Frame();
        expected.throwBall(8);
        expected.throwBall(2);

        assertEquals(expected.getRemainingBalls(), 0);
        assertEquals(expected.getCurrThrow(), 2);
        assertTrue(expected.isThrowComplete());
        assertEquals(expected.getRemainingScoringThrows(), 1);
        assertArrayEquals(expected.getScores(), new int[]{ 8, 2, -1 });
    }
}
