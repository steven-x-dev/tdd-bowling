import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class FrameTest {


    private static final int TOTAL_PINS = 10;

    private static final int ADDITIONAL_SCORING_THROWS_FOR_STRIKE = 2;
    private static final int ADDITIONAL_SCORING_THROWS_FOR_SPARE = 1;
    private static final int ADDITIONAL_SCORING_THROWS_FOR_FAILED = 0;

    private static final String THROW_MORE_THAN_ALLOWED_TIMES_MSG =
            "you can't continue a frame which has already been completed";

    private static final String STRIKING_NEGATIVE_PINS_MSG =
            "you can't strike down a negative number of pins";

    private static final String STRIKING_TOO_MANY_PINS_MSG =
            "you can't strike down more pins than what's in this frame";

    private static final String NOT_ENOUGH_SCORING_THROWS_MSG =
            "you can't add more scores to this frame";


    @Test
    void should_construct_empty_frame() {

        Frame expected = new Frame();

        assertEquals(expected.getRemainingBalls(), TOTAL_PINS);
        assertEquals(expected.getCurrThrow(), 0);
        assertFalse(expected.isThrowComplete());
        assertEquals(expected.getRemainingScoringThrows(), -1);
        assertArrayEquals(expected.getScores(), new int[] { -1, -1 });
        assertEquals(expected.getTotalScore(), 0);
    }


    @Test
    void should_construct_strike_frame() {

        Frame expected = new Frame();
        expected.throwBall(TOTAL_PINS);

        assertEquals(expected.getRemainingBalls(), 0);
        assertEquals(expected.getCurrThrow(), 1);
        assertTrue(expected.isThrowComplete());
        assertEquals(expected.getRemainingScoringThrows(), ADDITIONAL_SCORING_THROWS_FOR_STRIKE);
        assertArrayEquals(expected.getScores(), new int[] { TOTAL_PINS, -1 });
        assertEquals(expected.getTotalScore(), TOTAL_PINS);
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
        assertEquals(expected.getTotalScore(), TOTAL_PINS);
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
        assertEquals(expected.getTotalScore(), firstScore + secondScore);
    }


    @Test
    void should_throw_illegal_argument_exception_when_striking_negative_pins() {

        Frame expected = new Frame();

        assertThrows(IllegalArgumentException.class,
                () -> expected.throwBall(-1),
                STRIKING_NEGATIVE_PINS_MSG);
    }


    @Test
    void should_throw_illegal_argument_exception_when_striking_too_many_pins() {

        Frame expected1 = new Frame();
        expected1.throwBall(5);

        assertThrows(IllegalArgumentException.class,
                () -> expected1.throwBall(6),
                STRIKING_TOO_MANY_PINS_MSG);

        Frame expected2 = new Frame();

        assertThrows(IllegalArgumentException.class,
                () -> expected2.throwBall(11),
                STRIKING_TOO_MANY_PINS_MSG);
    }


    @Test
    void should_throw_illegal_state_exception_when_throw_after_strike() {

        Frame expected = new Frame();

        expected.throwBall(TOTAL_PINS);

        assertThrows(IllegalStateException.class,
                () -> expected.throwBall(2),
                THROW_MORE_THAN_ALLOWED_TIMES_MSG);
    }


    @Test
    void should_throw_illegal_state_exception_when_throw_three_times() {

        Frame expected = new Frame();

        expected.throwBall(5);
        expected.throwBall(2);

        assertThrows(IllegalStateException.class,
                () -> expected.throwBall(2),
                THROW_MORE_THAN_ALLOWED_TIMES_MSG);
    }


    @Test
    void should_construct_empty_frame_given_last_frame() {

        LastFrame expected = new LastFrame();

        assertEquals(expected.getRemainingBalls(), TOTAL_PINS);
        assertEquals(expected.getCurrThrow(), 0);
        assertFalse(expected.isThrowComplete());
        assertArrayEquals(expected.getScores(), new int[] { -1, -1, -1 });
        assertEquals(expected.getTotalScore(), 0);
    }


    @Test
    void should_construct_three_strikes_given_last_frame() {

        LastFrame expected = new LastFrame();

        expected.throwBall(TOTAL_PINS);

        assertEquals(expected.getRemainingBalls(), TOTAL_PINS);
        assertEquals(expected.getCurrThrow(), 1);
        assertFalse(expected.isThrowComplete());
        assertArrayEquals(expected.getScores(), new int[] { TOTAL_PINS, -1, -1 });
        assertEquals(expected.getTotalScore(), TOTAL_PINS);

        expected.throwBall(TOTAL_PINS);

        assertEquals(expected.getRemainingBalls(), TOTAL_PINS);
        assertEquals(expected.getCurrThrow(), 2);
        assertFalse(expected.isThrowComplete());
        assertArrayEquals(expected.getScores(), new int[] { TOTAL_PINS, TOTAL_PINS, -1 });
        assertEquals(expected.getTotalScore(), TOTAL_PINS * 2);

        expected.throwBall(TOTAL_PINS);

        assertEquals(expected.getRemainingBalls(), 0);
        assertEquals(expected.getCurrThrow(), 3);
        assertTrue(expected.isThrowComplete());
        assertArrayEquals(expected.getScores(), new int[] { TOTAL_PINS, TOTAL_PINS, TOTAL_PINS });
        assertEquals(expected.getTotalScore(), TOTAL_PINS * 3);
    }


    @Test
    void should_construct_spare_and_strike_given_last_frame() {

        LastFrame expected = new LastFrame();

        int firstScore = 6;
        int secondScore = TOTAL_PINS - firstScore;
        int thirdScore = TOTAL_PINS;

        expected.throwBall(firstScore);

        assertEquals(expected.getRemainingBalls(), secondScore);
        assertEquals(expected.getCurrThrow(), 1);
        assertFalse(expected.isThrowComplete());
        assertArrayEquals(expected.getScores(), new int[]{ firstScore, -1, -1 });
        assertEquals(expected.getTotalScore(), firstScore);

        expected.throwBall(secondScore);

        assertEquals(expected.getRemainingBalls(), TOTAL_PINS);
        assertEquals(expected.getCurrThrow(), 2);
        assertFalse(expected.isThrowComplete());
        assertArrayEquals(expected.getScores(), new int[]{ firstScore, secondScore, -1 });
        assertEquals(expected.getTotalScore(), TOTAL_PINS);

        expected.throwBall(thirdScore);

        assertEquals(expected.getRemainingBalls(), 0);
        assertEquals(expected.getCurrThrow(), 3);
        assertTrue(expected.isThrowComplete());
        assertArrayEquals(expected.getScores(), new int[] { firstScore, secondScore, thirdScore });
        assertEquals(expected.getTotalScore(), TOTAL_PINS * 2);
    }


    @Test
    void should_construct_spare_and_fail_given_last_frame() {

        LastFrame expected = new LastFrame();

        int firstScore = 6;
        int secondScore = TOTAL_PINS - firstScore;
        int thirdScore = 8;

        expected.throwBall(firstScore);

        assertEquals(expected.getRemainingBalls(), secondScore);
        assertEquals(expected.getCurrThrow(), 1);
        assertFalse(expected.isThrowComplete());
        assertArrayEquals(expected.getScores(), new int[]{ firstScore, -1, -1 });
        assertEquals(expected.getTotalScore(), firstScore);

        expected.throwBall(secondScore);

        assertEquals(expected.getRemainingBalls(), TOTAL_PINS);
        assertEquals(expected.getCurrThrow(), 2);
        assertFalse(expected.isThrowComplete());
        assertArrayEquals(expected.getScores(), new int[]{ firstScore, secondScore, -1 });
        assertEquals(expected.getTotalScore(), TOTAL_PINS);

        expected.throwBall(thirdScore);

        assertEquals(expected.getRemainingBalls(), TOTAL_PINS - thirdScore);
        assertEquals(expected.getCurrThrow(), 3);
        assertTrue(expected.isThrowComplete());
        assertArrayEquals(expected.getScores(), new int[] { firstScore, secondScore, thirdScore });
        assertEquals(expected.getTotalScore(), TOTAL_PINS + thirdScore);
    }


    @Test
    void should_construct_failed_frame_given_last_frame() {

        LastFrame expected = new LastFrame();

        int firstScore = 6;
        int secondScore = 1;

        expected.throwBall(firstScore);

        assertEquals(expected.getRemainingBalls(), TOTAL_PINS - firstScore);
        assertEquals(expected.getCurrThrow(), 1);
        assertFalse(expected.isThrowComplete());
        assertArrayEquals(expected.getScores(), new int[]{ firstScore, -1, -1 });
        assertEquals(expected.getTotalScore(), firstScore);

        expected.throwBall(secondScore);

        assertEquals(expected.getRemainingBalls(), TOTAL_PINS - firstScore - secondScore);
        assertEquals(expected.getCurrThrow(), 2);
        assertTrue(expected.isThrowComplete());
        assertArrayEquals(expected.getScores(), new int[]{ firstScore, secondScore, -1 });
        assertEquals(expected.getTotalScore(), firstScore + secondScore);
    }


    @Test
    void should_throw_illegal_argument_exception_when_striking_negative_pins_given_last_frame() {

        LastFrame expected = new LastFrame();

        assertThrows(IllegalArgumentException.class,
                () -> expected.throwBall(-1),
                STRIKING_NEGATIVE_PINS_MSG);
    }


    @Test
    void should_throw_illegal_argument_exception_when_striking_too_many_pins_given_last_frame() {

        LastFrame expected1 = new LastFrame();
        expected1.throwBall(5);

        assertThrows(IllegalArgumentException.class,
                () -> expected1.throwBall(6),
                STRIKING_TOO_MANY_PINS_MSG);

        LastFrame expected2 = new LastFrame();

        assertThrows(IllegalArgumentException.class,
                () -> expected2.throwBall(11),
                STRIKING_TOO_MANY_PINS_MSG);
    }


    @Test
    void should_throw_illegal_state_exception_when_throw_after_frame_completed_given_last_frame() {

        LastFrame expected1 = new LastFrame();

        expected1.throwBall(TOTAL_PINS);
        expected1.throwBall(TOTAL_PINS);
        expected1.throwBall(TOTAL_PINS);

        assertThrows(IllegalStateException.class,
                () -> expected1.throwBall(2),
                THROW_MORE_THAN_ALLOWED_TIMES_MSG);


        LastFrame expected2 = new LastFrame();

        expected2.throwBall(TOTAL_PINS);
        expected2.throwBall(TOTAL_PINS);
        expected2.throwBall(2);

        assertThrows(IllegalStateException.class,
                () -> expected2.throwBall(2),
                THROW_MORE_THAN_ALLOWED_TIMES_MSG);


        LastFrame expected3 = new LastFrame();

        int firstScore3 = 5;

        expected3.throwBall(firstScore3);
        expected3.throwBall(TOTAL_PINS - firstScore3);
        expected3.throwBall(TOTAL_PINS);

        assertThrows(IllegalStateException.class,
                () -> expected3.throwBall(2),
                THROW_MORE_THAN_ALLOWED_TIMES_MSG);


        LastFrame expected4 = new LastFrame();

        int firstScore4 = 5;

        expected4.throwBall(firstScore4);
        expected4.throwBall(TOTAL_PINS - firstScore4);
        expected4.throwBall(2);

        assertThrows(IllegalStateException.class,
                () -> expected4.throwBall(2),
                THROW_MORE_THAN_ALLOWED_TIMES_MSG);


        LastFrame expected5 = new LastFrame();

        int firstScore5 = 5;
        int secondScore5 = 2;

        expected5.throwBall(firstScore5);
        expected5.throwBall(secondScore5);

        assertThrows(IllegalStateException.class,
                () -> expected5.throwBall(2),
                THROW_MORE_THAN_ALLOWED_TIMES_MSG);
    }


    @Test
    void should_add_additional_score_two_times_and_throw_exception_for_third_time_given_strike_frame() {

        Frame expected = new Frame();
        expected.throwBall(TOTAL_PINS);

        int firstAdded = 8;
        int secondAdded = 10;

        expected.addAdditionalScore(firstAdded);
        assertEquals(expected.getTotalScore(), TOTAL_PINS + firstAdded);

        expected.addAdditionalScore(secondAdded);
        assertEquals(expected.getTotalScore(), TOTAL_PINS + firstAdded + secondAdded);

        assertThrows(IllegalStateException.class,
                () -> expected.addAdditionalScore(2),
                NOT_ENOUGH_SCORING_THROWS_MSG);
    }


    @Test
    void should_add_additional_score_once_and_throw_exception_for_second_time_given_spare_frame() {

        Frame expected = new Frame();
        int firstScore = 4;
        expected.throwBall(firstScore);
        expected.throwBall(TOTAL_PINS - firstScore);

        int added = 8;

        expected.addAdditionalScore(added);
        assertEquals(expected.getTotalScore(), TOTAL_PINS + added);

        assertThrows(IllegalStateException.class,
                () -> expected.addAdditionalScore(2),
                NOT_ENOUGH_SCORING_THROWS_MSG);
    }


    @Test
    void should_throw_exception_when_adding_score_given_failed_frame() {

        Frame expected = new Frame();
        int firstScore = 4;
        int secondScore = 3;
        expected.throwBall(firstScore);
        expected.throwBall(secondScore);

        assertThrows(IllegalStateException.class,
                () -> expected.addAdditionalScore(2),
                NOT_ENOUGH_SCORING_THROWS_MSG);
    }

}
