package graph.adjacency;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.*;


public class TestAdjacencyList {
    private AdjacencyList list;

    @Before
    public void setUp() {
        list = new AdjacencyList();
        list.addVertex(3);
        list.addVertex(1);
        list.addVertex(2);
        list.addEdge(1, 2);
        list.addEdge(1, 3);
        list.addEdge(2, 3);
    }

    @Test
    public void testGetVertices() {
        assertThat(list.getVertices(), contains(1, 2, 3));
    }

    @Test
    public void testAddVertex() {
        list.addVertex(4);
        assertThat(list.getVertices(), contains(1, 2, 3, 4));
    }

    @Test
    public void testRemoveVertex() {
        list.removeVertex(2);
        list.removeVertex(3);
        assertThat(list.getVertices(), contains(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveVertex_thrownIllegalArgumentException() {
        list.removeVertex(4);
    }

    @Test
    public void testRemoveEdge() {
        list.removeEdge(1, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveEdge_thrownIllegalArgumentException() {
        list.removeEdge(1, 4);
    }

    @Test
    public void testIsVertex_positive() {
        assertTrue(list.isVertex(1));
    }

    @Test
    public void testIsVertex_negative() {
        assertFalse(list.isVertex(4));
    }

    @Test
    public void testIsEdge_positive() {
        assertTrue(list.isEdge(1, 2));
    }

    @Test
    public void testIsEdge_negative() {
        assertFalse(list.isEdge(1, 4));
    }

    @Test
    public void testGetOutgoingVertices() {
        assertThat(list.getOutgoingVertices(1), contains(2, 3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOutgoingVertices_thrownIllegalArgumentException() {
        list.getOutgoingVertices(4);
    }

    @Test
    public void testGetIncomingVertices() {
        assertThat(list.getIncomingVertices(3), contains(1, 2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetIncomingVertices_thrownIllegalArgumentException() {
        list.getIncomingVertices(4);
    }

    @Test
    public void testGetVerticesAsArray() {
        assertThat(list.getVerticesAsArray(), equalTo(new int[]{1, 2, 3}));
    }

    @Test
    public void testGetOutgoingVerticesAsArray() {
        assertThat(list.getOutgoingVerticesAsArray(1), equalTo(new int[]{2, 3}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOutgoingVerticesAsArray_thrownIllegalArgumentException() {
        list.getOutgoingVerticesAsArray(4);
    }

    @Test
    public void testGetIncomingVerticesAsArray() {
        assertThat(list.getIncomingVerticesAsArray(3), equalTo(new int[]{1, 2}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetIncomingVerticesAsArray_thrownIllegalArgumentException() {
        list.getIncomingVerticesAsArray(4);
    }

    @Test
    public void testVertexCount() {
        assertEquals(list.getVertexCount(), 3);
    }

    @Test
    public void testEdgeCount() {
        assertEquals(list.getEdgeCount(), 3);
    }

    @Test
    public void testgetOutgoingVerticesCount() {
        assertEquals(list.getOutgoingVerticesCount(1), 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOutgoingVerticesCount_thrownIllegalArgumentException() {
        list.getOutgoingVerticesCount(4);
    }

    @Test
    public void testgetIncomingVerticesCount() {
        assertEquals(list.getIncomingVerticesCount(3), 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetIncomingVerticesCount_thrownIllegalArgumentException() {
        list.getIncomingVerticesCount(4);
    }

    @Test
    public void testGetVerticesToArray() {
        int[] arr = new int[3];
        list.getVerticesToArray(arr);
        assertThat(arr, equalTo(new int[]{1, 2, 3}));
    }

    @Test
    public void testGetOutgoingVerticesToArray() {
        int[] arr = new int[2];
        list.getOutgoingVerticesToArray(1, arr);
        assertThat(arr, equalTo(new int[]{2, 3}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOutgoingVerticesToArray_thrownIllegalArgumentException() {
        int[] arr = new int[0];
        list.getIncomingVerticesToArray(4, arr);
    }

    @Test
    public void testGetIncomingVerticesToArray() {
        int[] arr = new int[2];
        list.getIncomingVerticesToArray(3, arr);
        assertThat(arr, equalTo(new int[]{1, 2}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetIncomingVerticesToArray_thrownIllegalArgumentException() {
        int[] arr = new int[0];
        list.getIncomingVerticesToArray(4, arr);
    }
}
