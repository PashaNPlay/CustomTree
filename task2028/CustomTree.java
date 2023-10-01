package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* 
Построй дерево(1)
*/

public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {
    Entry<String> root;
    List<Entry<String>> entryList = new ArrayList<>();

    public CustomTree() {
        root = new Entry<>("entryName");
        entryList.add(root);
    }

    public String getParent(String s) {
        for (Entry<String> entry : entryList) {
            if (entry != null && entry.elementName.equals(s)) {
                return entry.parent.elementName;
            }
        }
        return "null";
    }

    @Override
    public int size() {
        return entryList.size() - 1;
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }

        for (Entry<String> entry : entryList) {
            if (!entry.isAvailableToAddChildren()) {
                continue;
            }

            if(entry.availableToAddLeftChildren & entry.availableToAddRightChildren) {
                entry.leftChild = new Entry<>(s);
                entry.leftChild.parent = entry;
                entry.availableToAddLeftChildren = false;
                entryList.add(entry.leftChild);
                return true;
            }

            if (entry.availableToAddRightChildren) {
                entry.rightChild = new Entry<>(s);
                entry.rightChild.parent = entry;
                entry.availableToAddRightChildren = false;
                entryList.add(entry.rightChild);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(Object s) {
        if (! (s instanceof String)) {
            throw new UnsupportedOperationException();
        }
        String stringToRemove = (String) s;

        Entry<String> entryToRemove = null;
        for (Entry<String> entry : entryList) {
            if (entry != null && entry.leftChild.elementName.equals(stringToRemove)) {
                entryToRemove = entry.leftChild;
                entry.availableToAddLeftChildren = true;
                break;
            }
            if (entry != null && entry.rightChild.elementName.equals(stringToRemove)) {
                entryToRemove = entry.rightChild;
                entry.availableToAddRightChildren = true;
                break;
            }
        }

        if (entryToRemove == null) {
            return false;
        }

        if (!entryToRemove.availableToAddLeftChildren) {
            remove(entryToRemove.leftChild.elementName);
        }
        if (!entryToRemove.availableToAddRightChildren) {
            remove(entryToRemove.rightChild.elementName);
        }

        return entryList.remove(entryToRemove);
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }


    static class Entry<T> implements Serializable {
        String elementName;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        Entry<T> parent, leftChild, rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            availableToAddLeftChildren = true;
            availableToAddRightChildren = true;
        }

        public boolean isAvailableToAddChildren() {
            return availableToAddLeftChildren || availableToAddRightChildren;
        }


    }
}
