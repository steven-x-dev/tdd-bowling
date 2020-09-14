class Frame extends AbstractFrame {

    private static final int MAX_THROWS = 2;

    private static final int ADDITIONAL_SCORING_THROWS_FOR_STRIKE = 2;
    private static final int ADDITIONAL_SCORING_THROWS_FOR_SPARE = 1;
    private static final int ADDITIONAL_SCORING_THROWS_FOR_FAILED = 0;

    private static final String ADDING_ILLEGAL_SCORE_MSG =
            "you can't add a negative score or a score higher than " + TOTAL_PINS;

    private static final String FRAME_NOT_COMPLETED_MSG =
            "you can't add a score from a later throw when this frame hasn't been completed";

    private static final String NOT_ENOUGH_SCORING_THROWS_MSG =
            "you can't add more scores to this frame";


    private int remainingScoringThrows;


    Frame() {
        super();
        scores = new int[] { -1, -1 };
        remainingScoringThrows = -1;
    }


    void addAdditionalScore(int score) {

        checkScoreInput(score);

        totalScore += score;
        remainingScoringThrows--;
    }


    private void checkScoreInput(int score) {

        if (score < 0 || score > TOTAL_PINS)
            throw new IllegalArgumentException(ADDING_ILLEGAL_SCORE_MSG);

        if (remainingScoringThrows == -1)
            throw new IllegalStateException(FRAME_NOT_COMPLETED_MSG);

        if (remainingScoringThrows < 1)
            throw new IllegalStateException(NOT_ENOUGH_SCORING_THROWS_MSG);
    }


    @Override
    protected void throwBall(int pinsDown) {

        checkInput(pinsDown);

        strike(pinsDown);

        checkFrameComplete();

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


    @Override
    protected void checkFrameComplete() {
        throwComplete = remainingBalls == 0 || currThrow == MAX_THROWS;
    }


    int getRemainingScoringThrows() {
        return remainingScoringThrows;
    }
}
