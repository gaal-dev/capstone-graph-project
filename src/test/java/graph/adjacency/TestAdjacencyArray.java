package graph.adjacency;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.*;

public class TestAdjacencyArray {
    private AdjacencyArray array;

    @Before
    public void setUp() {
        array = new AdjacencyArray();
        array.addVertex(3);
        array.addVertex(1);
        array.addVertex(2);
        array.addEdge(1, 3);
        array.addEdge(1, 2);
        array.addEdge(2, 3);
    }

    @Test
    public void testGetVertices() {
        assertThat(array.getVertices(), contains(1, 2, 3));
    }

    @Test
    public void testAddVertex() {
        array.addVertex(4);
        assertThat(array.getVertices(), contains(1, 2, 3, 4));
    }

    @Test
    public void testRemoveVertex() {
        array.removeVertex(2);
        array.removeVertex(3);
        assertThat(array.getVertices(), contains(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveVertex_thrownIllegalArgumentException() {
        array.removeVertex(4);
    }

    @Test
    public void testRemoveEdge() {
        array.removeEdge(1, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveEdge_thrownIllegalArgumentException() {
        array.removeEdge(1, 4);
    }

    @Test
    public void testIsVertex_positive() {
        assertTrue(array.isVertex(1));
    }

    @Test
    public void testIsVertex_negative() {
        assertFalse(array.isVertex(4));
    }

    @Test
    public void testIsEdge_positive() {
        assertTrue(array.isEdge(1, 2));
    }

    @Test
    public void testIsEdge_negative() {
        assertFalse(array.isEdge(1, 4));
    }

    @Test
    public void testGetOutgoingVertices() {
        assertThat(array.getOutgoingVertices(1), contains(2, 3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOutgoingVertices_thrownIllegalArgumentException() {
        array.getOutgoingVertices(4);
    }

    @Test
    public void testGetIncomingVertices() {
        assertThat(array.getIncomingVertices(3), contains(1, 2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetIncomingVertices_thrownIllegalArgumentException() {
        array.getIncomingVertices(4);
    }

    @Test
    public void testGetVerticesAsArray() {
        assertThat(array.getVerticesAsArray(), equalTo(new int[]{1, 2, 3}));
    }

    @Test
    public void testGetOutgoingVerticesAsArray() {
        assertThat(array.getOutgoingVerticesAsArray(1), equalTo(new int[]{2, 3}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOutgoingVerticesAsArray_thrownIllegalArgumentException() {
        array.getOutgoingVerticesAsArray(4);
    }

    @Test
    public void testGetIncomingVerticesAsArray() {
        assertThat(array.getIncomingVerticesAsArray(3), equalTo(new int[]{1, 2}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetIncomingVerticesAsArray_thrownIllegalArgumentException() {
        array.getIncomingVerticesAsArray(4);
    }

    @Test
    public void testVertexCount() {
        assertEquals(array.getVertexCount(), 3);
    }

    @Test
    public void testEdgeCount() {
        assertEquals(array.getEdgeCount(), 3);
    }

    @Test
    public void testgetOutgoingVerticesCount() {
        assertEquals(array.getOutgoingVerticesCount(1), 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOutgoingVerticesCount_thrownIllegalArgumentException() {
        array.getOutgoingVerticesCount(4);
    }

    @Test
    public void testgetIncomingVerticesCount() {
        assertEquals(array.getIncomingVerticesCount(3), 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetIncomingVerticesCount_thrownIllegalArgumentException() {
        array.getIncomingVerticesCount(4);
    }

    @Test
    public void testGetVerticesToArray() {
        int[] arr = new int[3];
        array.getVerticesToArray(arr);
        assertThat(arr, equalTo(new int[]{1, 2, 3}));
    }

    @Test
    public void testGetOutgoingVerticesToArray() {
        int[] arr = new int[2];
        array.getOutgoingVerticesToArray(1, arr);
        assertThat(arr, equalTo(new int[]{2, 3}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOutgoingVerticesToArray_thrownIllegalArgumentException() {
        int[] arr = new int[0];
        array.getIncomingVerticesToArray(4, arr);
    }

    @Test
    public void testGetIncomingVerticesToArray() {
        int[] arr = new int[2];
        array.getIncomingVerticesToArray(3, arr);
        assertThat(arr, equalTo(new int[]{1, 2}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetIncomingVerticesToArray_thrownIllegalArgumentException() {
        int[] arr = new int[0];
        array.getIncomingVerticesToArray(4, arr);
    }
}
