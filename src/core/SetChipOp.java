package core;

public class SetChipOp implements IChipOperation {

    @Override
    public int compute(int nbToApply, int nodeCurrentChips) {
        return (nbToApply < 0) ? 0 : nbToApply;
    }
    
}
