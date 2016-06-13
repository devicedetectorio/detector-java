package io.devicedetector.visitor;

abstract public class PriorityVisitor implements Visitor, Comparable<PriorityVisitor> {
    private int priority = 1;

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(PriorityVisitor v) {
        int compare = v.getPriority() - this.getPriority();

        if (compare == 0) {
            compare = 1;
        }

        return compare;
    }
}
