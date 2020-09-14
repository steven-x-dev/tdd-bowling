import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrameTest {


    private static final int TOTAL_PINS = 10;
    private static final int ADDITIONAL_SCORING_THROWS_FOR_STRIKE = 2;
    private static final int ADDITIONAL_SCORING_THROWS_FOR_SPARE = 1;
    private static final int ADDITIONAL_SCORING_THROWS_FOR_FAILED = 0;

    private static final String THROW_MORE_THAN_TWO_TIMES_MSG =
            "you can't continue a frame which has already been completed";

    private static final String STRIKING_NEGATIVE_PINS_MSG =
            "you can't strike down a negative number of pins";

    private static final String STRIKING_TOO_MANY_PINS_MSG =
            "you can't strike down more pins than what's in this frame";


    @Test
    void should_construct_empty_frame() {

        Frame expected = new Frame();

        assertEquals(expected.getRemainingBalls(), TOTAL_PINS);
        assertEquals(expected.getCurrThrow(), 0);
        assertFalse(expected.isThrowComplete());
        assertEquals(expected.getRemainingScoringThrows(), -1);
        assertArrayEquals(expected.getScores(), new int[] { -1, -1 });
    }


    @Test
    void should_construct_strike_frame() {

        Frame expected = new Frame();
        expected.throwBall(TOTAL_PINS);

        assertEquals(expected.getRemainingBalls(), 0);
        assertEquals(expected.getCurrThrow(), 1);
        assertTrue(expected.isThrowComplete());
        assertEquals(expected.getRemainingScoringThrows(), ADDITIONAL_SCORING_THROWS_FOR_STRIKE);
        assertArrayEquals(expected.getScores(), new int[]{ TOTAL_PINS, -1 });
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
        assertEquals(expected.getRemainingScoringThrows(), ADDITIONAL_SCORING_THROWS_FOR_SPARE);
        assertArrayEquals(expected.getScores(), new int[] { firstScore, TOTAL_PINS - firstScore });
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
        assertEquals(expected.getRemainingScoringThrows(), ADDITIONAL_SCORING_THROWS_FOR_FAILED);
        assertArrayEquals(expected.getScores(), new int[] { firstScore, secondScore });
    }


    @Test
    void should_throw_illegal_argument_exception_when_striking_negative_pins() {

        Frame expected = new Frame();

        assertThrows(IllegalStateException.class,
                () -> expected.throwBall(-1),
                STRIKING_NEGATIVE_PINS_MSG);
    }


    @Test
    void should_throw_illegal_argument_exception_when_striking_too_many_pins() {

        Frame expected1 = new Frame();
        expected1.throwBall(5);

        assertThrows(IllegalStateException.class,
                () -> expected1.throwBall(6),
                STRIKING_TOO_MANY_PINS_MSG);

        Frame expected2 = new Frame();

        assertThrows(IllegalStateException.class,
                () -> expected2.throwBall(11),
                STRIKING_TOO_MANY_PINS_MSG);
    }


    @Test
    void should_throw_illegal_state_exception_when_throw_three_times() {

        Frame expected = new Frame();

        expected.throwBall(5);
        expected.throwBall(2);

        assertThrows(IllegalStateException.class,
                () -> expected.throwBall(2),
                THROW_MORE_THAN_TWO_TIMES_MSG);
    }

}
