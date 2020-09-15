class LastFrame extends AbstractFrame {

    private static final int MAX_THROWS = 3;

    LastFrame() {
        super();
        scores = new int[] { -1, -1, -1 };
    }


    @Override
    protected void throwBall(int pinsDown) {

        checkInput(pinsDown);

        strike(pinsDown);

        checkFrameComplete();

        if (remainingPins == 0 && currThrow < MAX_THROWS) {
            remainingPins = TOTAL_PINS;
        }
    }


    @Override
    protected void checkFrameComplete() {
        throwComplete = (currThrow == MAX_THROWS ||
                (currThrow == (MAX_THROWS - 1) && remainingPins > 0));
    }

}
