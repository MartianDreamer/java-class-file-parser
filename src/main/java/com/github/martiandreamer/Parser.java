package com.github.martiandreamer;

public abstract class Parser<T> {
    protected final byte[] content;
    protected final int from;
    protected int current;

    protected Parser(byte[] content, int from) {
        this.content = content;
        this.from = from;
        this.current = from;
    }

    public abstract T parse();

    public byte[] getContent() {
        return content;
    }

    public int getFrom() {
        return from;
    }

    public int getCurrent() {
        return current;
    }
}
