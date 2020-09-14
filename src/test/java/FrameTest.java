import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrameTest {


    private static final int TOTAL_PINS = 10;
    private static final int ADDITIONAL_SCORING_THROWS = 2;


    @Test
    void should_construct_empty_frame() {

        Frame expected = new Frame();

        assertEquals(expected.getRemainingBalls(), TOTAL_PINS);
        assertEquals(expected.getCurrThrow(), 0);
        assertFalse(expected.isThrowComplete());
        assertEquals(expected.getRemainingScoringThrows(), ADDITIONAL_SCORING_THROWS);
        assertArrayEquals(expected.getScores(), new int[] { -1, -1, -1 });
    }


    @Test
    void should_construct_strike_frame() {

        Frame expected = new Frame();
        expected.throwBall(TOTAL_PINS);

        assertEquals(expected.getRemainingBalls(), 0);
        assertEquals(expected.getCurrThrow(), 1);
        assertTrue(expected.isThrowComplete());
        assertEquals(expected.getRemainingScoringThrows(), ADDITIONAL_SCORING_THROWS);
        assertArrayEquals(expected.getScores(), new int[]{ TOTAL_PINS, -1, -1 });
    }


    @Test
    void should_construct_spare_frame() {

        Frame expected = new Frame();

        int firstScore = 8;

        expected.throwBall(firstScore);
        expected.throwBall(TOTAL_PINS - firstScore);

        assertEquals(expected.getRemainingBalls(), 0);
        assertEquals(expected.getCurrThrow(), 2);
        assertTrue(expected.isThrowComplete());
        assertEquals(expected.getRemainingScoringThrows(), ADDITIONAL_SCORING_THROWS - 1);
        assertArrayEquals(expected.getScores(), new int[] { firstScore, TOTAL_PINS - firstScore, -1 });
    }


    @Test
    void should_construct_failed_frame() {

        Frame expected = new Frame();

        int firstScore = 6;
        int secondScore = 3;

        expected.throwBall(firstScore);
        expected.throwBall(secondScore);

        assertEquals(expected.getRemainingBalls(), TOTAL_PINS - firstScore - secondScore);
        assertEquals(expected.getCurrThrow(), 2);
        assertTrue(expected.isThrowComplete());
        assertEquals(expected.getRemainingScoringThrows(), 0);
        assertArrayEquals(expected.getScores(), new int[] { firstScore, secondScore, -1 });
    }
}
