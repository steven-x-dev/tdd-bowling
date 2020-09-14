class Frame {


    protected static final int TOTAL_PINS = 10;
    protected static final int ADDITIONAL_SCORING_THROWS_FOR_STRIKE = 2;
    protected static final int ADDITIONAL_SCORING_THROWS_FOR_SPARE = 1;
    protected static final int ADDITIONAL_SCORING_THROWS_FOR_FAILED = 0;

    protected static final String THROW_MORE_THAN_TWO_TIMES_MSG =
            "you can't continue a frame which has already been completed";

    protected static final String STRIKING_NEGATIVE_PINS_MSG =
            "you can't strike down a negative number of pins";

    protected static final String STRIKING_TOO_MANY_PINS_MSG =
            "you can't strike down more pins than what's in this frame";


    protected int remainingBalls;
    protected int[] scores;
    protected int currThrow;
    protected boolean throwComplete;
    protected int remainingScoringThrows;


    Frame() {
        remainingBalls = TOTAL_PINS;
        scores = new int[] { -1, -1 };
        currThrow = 0;
        throwComplete = false;
        remainingScoringThrows = -1;
    }


    int getRemainingBalls() {
        return remainingBalls;
    }

    int[] getScores() {
        return scores;
    }

    int getCurrThrow() {
        return currThrow;
    }

    boolean isThrowComplete() {
        return throwComplete;
    }

    int getRemainingScoringThrows() {
        return remainingScoringThrows;
    }

    void throwBall(int pinsDown) {

        checkInput(pinsDown);

        strike(pinsDown);

        throwComplete = checkFrameComplete();

        if (throwComplete) {
            if (currThrow == 1) {
                remainingScoringThrows = ADDITIONAL_SCORING_THROWS_FOR_STRIKE;
            } else if (remainingBalls == 0) {
                remainingScoringThrows = ADDITIONAL_SCORING_THROWS_FOR_SPARE;
            } else {
                remainingScoringThrows = ADDITIONAL_SCORING_THROWS_FOR_FAILED;
            }
        }
    }


    protected void checkInput(int pinsDown) {
        if (throwComplete)
            throw new IllegalStateException(THROW_MORE_THAN_TWO_TIMES_MSG);

        if (pinsDown < 0)
            throw new IllegalArgumentException(STRIKING_NEGATIVE_PINS_MSG);

        if (pinsDown > remainingBalls)
            throw new IllegalArgumentException(STRIKING_TOO_MANY_PINS_MSG);
    }


    protected void strike(int pinsDown) {
        remainingBalls -= pinsDown;
        scores[currThrow] = pinsDown;
        currThrow++;
    }


    protected boolean checkFrameComplete() {
        return remainingBalls == 0 || currThrow == 2;
    }
}
