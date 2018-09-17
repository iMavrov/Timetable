package utilities;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Mavrov
 * @param <T>
 */
public class IntervalTree<T extends IntervalTree.Interval> {
    
    public static class Interval {
        
        public Interval() {
            start = 0;
            end = 0;
        }
        
        public Interval(int inputStart, int inputEnd) {
            start = inputStart;
            end = inputEnd;
        }
        
        public int getStart() {
            return start;
        }
        
        public int getEnd() {
            return end;
        }
        
        public void setStart(int newStart) {
            start = newStart;
        }
        
        public void setEnd(int newEnd) {
            end = newEnd;
        }
        
        protected int start;
        protected int end;
    }
    
    public IntervalTree(List<T> intervals) {
        root = null;
        
        if (intervals == null || intervals.isEmpty()) {
            return;
        }
        
        //int earliestIntervalStart = Integer.MAX_VALUE;
        //int latestIntervalEnd = Integer.MIN_VALUE;
        
        int intervalCount = intervals.size();
        List<IntervalEvent> events = new ArrayList<>(2 * intervalCount);
        for (int intervalIndex = 0; intervalIndex < intervalCount; ++intervalIndex) {
            int intervalStart = intervals.get(intervalIndex).getStart();
            events.add(new IntervalEvent(true, intervalStart, intervalIndex));
            //earliestIntervalStart = Math.min(earliestIntervalStart, intervalStart);
            
            int intervalEnd = intervals.get(intervalIndex).getEnd();
            events.add(new IntervalEvent(false, intervalEnd, intervalIndex));
            //latestIntervalEnd = Math.max(latestIntervalEnd, intervalEnd);
        }
        
        //Collections.sort(events);
        //int pseudoPivot = (earliestIntervalStart + latestIntervalEnd) / 2;

        root = new Node();
        createNode(root, intervals, events, 0, events.size() - 1);
    }
    
    private class IntervalEvent implements Comparable<IntervalEvent> {
        
        @Override
        public int compareTo(IntervalEvent t) {
            int positionDifference = t.position - position;
            return positionDifference;
        }
        
        private IntervalEvent(boolean inputIsStart, int inputPosition, int inputIntervalIndex) {
            isStart = inputIsStart;
            position = inputPosition;
            intervalIndex = inputIntervalIndex;
        }
        
        private final boolean isStart;
        private final int position;
        private final int intervalIndex;
    }

    private class Node {
        
        private Node() {
            pivot = 0;
            start = 0;
            end = 0;
            intervals = new ArrayList<>();
            left = null;
            right = null;
        }
        
        private int pivot;
        private int start;
        private int end;
        
        private List<T> intervals;

        private Node left;
        private Node right;
    }
    
    private void createNode(Node node, List<T> intervals, List<IntervalEvent> events, int startIndex, int endIndex) {
        node.pivot = Statistics.getMedian(events).position;
        
        // The events list always has an even number of elements so it has two centers
        int centerIndexLeft = (startIndex + endIndex) / 2;
        int centerIndexRight = centerIndexLeft + 1;
        node.pivot = (events.get(centerIndexLeft).position + events.get(centerIndexRight).position) / 2;

        if (startIndex + 1 == endIndex) {
            node.intervals.add(intervals.get(events.get(centerIndexLeft).intervalIndex));
        }
        
        int leftEndIndex = centerIndexLeft;
        while (startIndex <= leftEndIndex) {
            T leftSlot = intervals.get(leftEndIndex);
            if (leftSlot.getEnd() <= node.pivot) {
                break;
            }
            --leftEndIndex;
        }

        int rightStartIndex = centerIndexRight;
        while (rightStartIndex <= endIndex) {
            T rightSlot = intervals.get(rightStartIndex);
            if (node.pivot <= rightSlot.getStart()) {
                break;
            }
            ++rightStartIndex;
        }

        for (int slotIndex = leftEndIndex + 1; slotIndex < rightStartIndex; ++slotIndex) {
            node.intervals.add(intervals.get(slotIndex));
        }

        if (startIndex <= leftEndIndex) {
            node.left = new Node();
            createNode(node.left, intervals, events, startIndex, leftEndIndex);
        }

        if (rightStartIndex <= endIndex) {
            node.right = new Node();
            createNode(node.right, intervals, events, rightStartIndex, endIndex);
        }
    }
    
    private Node root;
}
