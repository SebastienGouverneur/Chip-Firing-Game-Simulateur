package core;

public class RetrieveChipOp implements IChipOperation {

    @Override
    public int compute(int nbToApply, int nodeCurrentChips) {
        return (nodeCurrentChips >= nbToApply) ? (nodeCurrentChips - nbToApply) : (0);
    }
}
