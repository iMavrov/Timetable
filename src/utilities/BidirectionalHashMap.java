package utilities;

import java.io.Serializable;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

/**
 *
 * @author Mavrov
 */
public class BidirectionalHashMap<ForwardKeyType extends Object, BackwardKeyType extends Object> implements Map<ForwardKeyType, BackwardKeyType>, Cloneable, Serializable {
    
    public BidirectionalHashMap() {
        forwardMap = new HashMap<>();
        backwardMap = new HashMap<>();
    }
    
    @Override
    public int hashCode() {
        int result = 13;
        result += 17 * forwardMap.hashCode();
        result += 29 * backwardMap.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        
        if (getClass() != o.getClass()) {
            return false;
        }
        
        final BidirectionalHashMap<ForwardKeyType, BackwardKeyType> other = (BidirectionalHashMap<ForwardKeyType, BackwardKeyType>) o;
        if (!forwardMap.equals(other.forwardMap)) {
            return false;
        }
        if (!backwardMap.equals(other.backwardMap)) {
            return false;
        }
        
        return true;
    }

    @Override
    public int size() {
        return forwardMap.size();
    }

    @Override
    public boolean isEmpty() {
        return forwardMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object o) {
        boolean containsForwardKey = forwardMap.containsKey(o);
        return containsForwardKey;
    }
    
    public boolean containsBackwardKey(BackwardKeyType o) {
        boolean containsBackwardKey = backwardMap.containsKey(o);
        return containsBackwardKey;
    }

    @Override
    public boolean containsValue(Object o) {
        boolean containsForwardValue = false;
        
        try {
            containsForwardValue = backwardMap.containsKey((BackwardKeyType)o);
        } catch (ClassCastException e) {
            // TODO: Log bad downcast problem here.
        }
        
        return containsForwardValue;
    }
    
    public boolean containsBackwardValue(ForwardKeyType o) {
        boolean containsBackwardValue = forwardMap.containsKey(o);
        return containsBackwardValue;
    }

    @Override
    public BackwardKeyType get(Object o) {
        return forwardMap.get(o);
    }

    public ForwardKeyType getBackward(BackwardKeyType o) {
        return backwardMap.get(o);
    }
    
    @Override
    public BackwardKeyType put(ForwardKeyType k, BackwardKeyType v) {
        backwardMap.put(v, k);
        return forwardMap.put(k, v);
    }
    
    public ForwardKeyType putBackward(BackwardKeyType k, ForwardKeyType v) {
        forwardMap.put(v, k);
        return backwardMap.put(k, v);
    }

    @Override
    public BackwardKeyType remove(Object o) {
        BackwardKeyType backwardKey = forwardMap.remove(o);
        ForwardKeyType forwardKey = backwardMap.remove(backwardKey);
        return backwardKey;
    }
    
    public ForwardKeyType removeBackward(BackwardKeyType o) {
        ForwardKeyType forwardKey = backwardMap.remove(o);
        BackwardKeyType backwardKey = forwardMap.remove(forwardKey);
        return forwardKey;
    }

    @Override
    public void putAll(Map<? extends ForwardKeyType, ? extends BackwardKeyType> map) {
        for (Entry<? extends ForwardKeyType, ? extends BackwardKeyType> newEntry : map.entrySet()) {
            put(newEntry.getKey(), newEntry.getValue());
        }
    }
    
    public void putAllBackward(Map<? extends BackwardKeyType, ? extends ForwardKeyType> map) {
        for (Entry<? extends BackwardKeyType, ? extends ForwardKeyType> newEntry : map.entrySet()) {
            putBackward(newEntry.getKey(), newEntry.getValue());
        }
    }

    @Override
    public void clear() {
        forwardMap.clear();
        backwardMap.clear();
    }

    @Override
    public Set<ForwardKeyType> keySet() {
        return forwardMap.keySet();
    }
    
    public Set<BackwardKeyType> keySetBackward() {
        return backwardMap.keySet();
    }

    @Override
    public Collection<BackwardKeyType> values() {
        return backwardMap.keySet();
    }
    
    public Collection<ForwardKeyType> valuesBackward() {
        return forwardMap.keySet();
    }
    
    @Override
    public Set<Entry<ForwardKeyType, BackwardKeyType>> entrySet() {
        return forwardMap.entrySet();
    }
    
    public Set<Entry<BackwardKeyType, ForwardKeyType>> entrySetBackward() {
        return backwardMap.entrySet();
    }
    
    private Map<ForwardKeyType, BackwardKeyType> forwardMap;
    private Map<BackwardKeyType, ForwardKeyType> backwardMap;
}
