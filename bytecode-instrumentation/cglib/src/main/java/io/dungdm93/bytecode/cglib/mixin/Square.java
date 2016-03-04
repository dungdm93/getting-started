package io.dungdm93.bytecode.cglib.mixin;

public class Square implements Shapeable {
    @Override
    public int sides() {
        return 4;
    }
}
