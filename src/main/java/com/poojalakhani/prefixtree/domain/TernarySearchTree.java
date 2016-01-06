package com.poojalakhani.prefixtree.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * A ternary search tree representation that holds an object of a type <code>T</code> along with the endOfString nodes
 *
 * @param <T> type of data to be held
 */
public class TernarySearchTree<T> {
    private TernarySearchTreeNode<T> root;

    public void add(String str, T t) {
        root = add(str, 0, root, t);
    }

    private TernarySearchTreeNode add(String str, int currentIndex, TernarySearchTreeNode node, T t) {
        Character currentChar = str.charAt(currentIndex);
        if (node == null) {
            node = new TernarySearchTreeNode(currentChar, false);
        }

        if (currentChar < node.getCharacter()) {
            node.setLowChild(add(str, currentIndex, node.getLowChild(), t));
        } else if (currentChar > node.getCharacter()) {
            node.setHighChild(add(str, currentIndex, node.getHighChild(), t));
        } else {
            if (isEndOfString(str, currentIndex)) {
                node.markEndOfString(t);
            } else {
                int nextIndex = currentIndex + 1;
                node.setEqChild(add(str, nextIndex, node.getEqChild(), t));
            }
        }
        return node;
    }

    private boolean isEndOfString(String str, int index) {
        int nextIndex = index + 1;
        return nextIndex == str.length();
    }

    public List<T> suggest(String prefix) {
        List<T> suggestions = new ArrayList<T>();
        TernarySearchTreeNode prefixNode = findNodeForString(root, prefix);
        if (prefixNode != null) {
            addToSuggestionsIfAWord(suggestions, prefixNode);
            suggestions.addAll(getAllWordsFromNode(prefix, prefixNode.getEqChild()));
        }
        return suggestions;
    }


    private TernarySearchTreeNode findNodeForString(TernarySearchTreeNode node, String prefix) {
        if (node == null || prefix.isEmpty())
            return node;
        Character firstCharacter = prefix.charAt(0);
        if (firstCharacter < node.getCharacter()) {
            return findNodeForString(node.getLowChild(), prefix);
        } else if (firstCharacter > node.getCharacter()) {
            return findNodeForString(node.getHighChild(), prefix);
        } else {
            String remainingString = prefix.substring(1);
            if (remainingString.isEmpty()) {
                return node;
            }
            return findNodeForString(node.getEqChild(), remainingString);
        }
    }

    private List<T> getAllWordsFromNode(String prefix, TernarySearchTreeNode<T> node) {
        List<T> words = new ArrayList<T>();
        if (node != null) {
            addToSuggestionsIfAWord(words, node);

            words.addAll(getAllWordsFromNode(prefix, node.getLowChild()));
            words.addAll(getAllWordsFromNode(prefix, node.getHighChild()));
            words.addAll(getAllWordsFromNode(prefix + node.getCharacter(), node.getEqChild()));
        }
        return words;
    }

    private void addToSuggestionsIfAWord(List<T> list, TernarySearchTreeNode<T> node) {
        if (node.isEndOfString()) {
            list.add(node.getData());
        }
    }

}


/**
 * A node of <code>TernarySearchTree</code> that holds the data of specific type along with a character, highChild, lowChild and equalChild
 *
 * @param <T> Type of data to be held
 */
class TernarySearchTreeNode<T> {
    private Character character;
    private TernarySearchTreeNode lowChild;
    private TernarySearchTreeNode highChild;
    private TernarySearchTreeNode eqChild;
    private boolean endOfString;
    private T data;

    public TernarySearchTreeNode(Character character, boolean endOfString) {
        this.character = character;
        this.endOfString = endOfString;
    }

    public Character getCharacter() {
        return character;
    }

    public TernarySearchTreeNode getLowChild() {
        return lowChild;
    }

    public void setLowChild(TernarySearchTreeNode lowChild) {
        this.lowChild = lowChild;
    }

    public TernarySearchTreeNode getHighChild() {
        return highChild;
    }

    public void setHighChild(TernarySearchTreeNode highChild) {
        this.highChild = highChild;
    }

    public TernarySearchTreeNode getEqChild() {
        return eqChild;
    }

    public void setEqChild(TernarySearchTreeNode eqChild) {
        this.eqChild = eqChild;
    }

    public boolean isEndOfString() {
        return endOfString;
    }

    public void markEndOfString(T t) {
        this.endOfString = true;
        this.data = t;
    }

    public T getData() {
        return data;
    }
}
