package core;

public class SubstractChipOp implements IChipOperation {

    @Override
    public int compute(int nbToApply, int nodeCurrentChips) {
        return (nodeCurrentChips >= nbToApply) ? (nodeCurrentChips - nbToApply) : (0);
    }
}
