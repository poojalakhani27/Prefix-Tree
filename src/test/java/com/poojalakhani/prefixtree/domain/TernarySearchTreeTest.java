package com.poojalakhani.prefixtree.domain;


import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class TernarySearchTreeTest {
    private TernarySearchTree<String> ternarySearchTree;

    @Before
    public void setUp() throws Exception {
        ternarySearchTree = new TernarySearchTree<String>();
    }

    @Test
    public void shouldAddAndSearchACompleteString() throws Exception {
        String addressForPooja = "Address for Pooja";
        ternarySearchTree.add("Pooja", addressForPooja);
        ternarySearchTree.add("Poojit", "Address for Poojit");

        List<String> result = ternarySearchTree.suggest("Pooja");

        assertTrue(result.size() == 1);
        assertTrue(result.contains(addressForPooja));
    }

    @Test
    public void shouldAddAndSearchByPrefix() throws Exception {
        String addressForPooja = "Address for Pooja";
        String addressForPoojit = "Address for Poojit";

        ternarySearchTree.add("Pooja", addressForPooja);
        ternarySearchTree.add("Poojit", addressForPoojit);

        List<String> result = ternarySearchTree.suggest("Poo");

        assertTrue(result.size() == 2);
        assertTrue(result.contains(addressForPooja));
        assertTrue(result.contains(addressForPoojit));
    }

    @Test
    public void shouldReturnEmptyResultIfNoneMatches() throws Exception {
        String addressForPooja = "Address for Pooja";
        String addressForPoojit = "Address for Poojit";

        ternarySearchTree.add("Pooja", addressForPooja);
        ternarySearchTree.add("Poojit", addressForPoojit);

        List<String> result = ternarySearchTree.suggest("INVALID_PREFIX");

        assertTrue(result.size() == 0);
    }

    @Test
    public void shouldReturnEmptyResultIfEmptyTree() throws Exception {
        List<String> result = ternarySearchTree.suggest("somePrefix");

        assertTrue(result.size() == 0);
    }
}
