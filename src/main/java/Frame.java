class Frame extends AbstractFrame {

    private static final int MAX_THROWS = 2;

    private static final int ADDITIONAL_SCORING_THROWS_FOR_STRIKE = 2;
    private static final int ADDITIONAL_SCORING_THROWS_FOR_SPARE = 1;
    private static final int ADDITIONAL_SCORING_THROWS_FOR_FAILED = 0;


    private int remainingScoringThrows;


    Frame() {
        super();
        scores = new int[] { -1, -1 };
        remainingScoringThrows = -1;
    }


    @Override
    protected void throwBall(int pinsDown) {

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


    @Override
    protected boolean checkFrameComplete() {
        return remainingBalls == 0 || currThrow == MAX_THROWS;
    }


    int getRemainingScoringThrows() {
        return remainingScoringThrows;
    }
}
