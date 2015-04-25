package core;

public class AddChipOp implements IChipOperation {

    @Override
    public int compute(int nbToApply, int nodeCurrentChips) {
        return nbToApply + nodeCurrentChips;
    }
}
