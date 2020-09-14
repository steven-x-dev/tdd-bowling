class LastFrame extends Frame {


    LastFrame() {
        scores = new int[] { -1, -1, -1 };
    }


    void throwBall(int pinsDown) {

        checkInput(pinsDown);

        strike(pinsDown);

        throwComplete = checkFrameComplete();

        if (remainingBalls == 0 && currThrow < 3) {
            remainingBalls = TOTAL_PINS;
        }
    }


    @Override
    protected boolean checkFrameComplete() {
        return (currThrow == 3 ||
                (currThrow == 2 && remainingBalls > 0));
    }

}
