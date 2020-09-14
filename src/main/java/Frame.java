class Frame {


    private static final int TOTAL_PINS = 10;
    private static final int ADDITIONAL_SCORING_THROWS = 2;


    private int remainingBalls;
    private int[] scores;
    private int currThrow;
    private boolean throwComplete;
    private int remainingScoringThrows;


    Frame() {
        remainingBalls = TOTAL_PINS;
        scores = new int[] { -1, -1, -1 };
        currThrow = 0;
        throwComplete = false;
        remainingScoringThrows = ADDITIONAL_SCORING_THROWS;
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

        if (remainingBalls == 0 || currThrow == 2)
            throwComplete = true;

        if (remainingBalls > 0) {
            if (currThrow == 1) {
                remainingScoringThrows = 1;
            } else {
                remainingScoringThrows = 0;
            }
        }
    }
}
