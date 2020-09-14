class Frame {


    private static final int TOTAL_PINS = 10;
    private static final int ADDITIONAL_SCORING_THROWS_FOR_STRIKE = 2;
    private static final int ADDITIONAL_SCORING_THROWS_FOR_SPARE = 1;
    private static final int ADDITIONAL_SCORING_THROWS_FOR_FAILED = 0;


    private int remainingBalls;
    private int[] scores;
    private int currThrow;
    private boolean throwComplete;
    private int remainingScoringThrows;


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

        if (throwComplete)
            throw new IllegalStateException("you can't continue a frame which has already been completed");

        if (pinsDown > remainingBalls)
            throw new IllegalArgumentException("you can't strike down more pins than what's in this frame");

        remainingBalls -= pinsDown;
        scores[currThrow] = pinsDown;
        currThrow++;

        if (remainingBalls == 0 || currThrow == 2) {
            throwComplete = true;
        }

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
}
