package graph.adjacency;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


public class TestAdjacencyMatrix {
    private AdjacencyMatrix matrix;

    @Before
    public void setUp() {
        matrix = new AdjacencyMatrix();
        matrix.addVertex(3);
        matrix.addVertex(1);
        matrix.addVertex(2);
        matrix.addEdge(1, 2);
        matrix.addEdge(1, 3);
        matrix.addEdge(2, 3);
    }

    @Test
    public void testGetVertices() {
        assertThat(matrix.getVertices(), contains(1, 2, 3));
    }

    @Test
    public void testAddVertex() {
        matrix.addVertex(4);
        assertThat(matrix.getVertices(), contains(1, 2, 3, 4));
    }

    @Test
    public void testRemoveVertex() {
        matrix.removeVertex(2);
        matrix.removeVertex(3);
        assertThat(matrix.getVertices(), contains(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveVertex_thrownIllegalArgumentException() {
        matrix.removeVertex(4);
    }

    @Test
    public void testRemoveEdge() {
        matrix.removeEdge(1, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveEdge_thrownIllegalArgumentException() {
        matrix.removeEdge(1, 4);
    }

    @Test
    public void testIsVertex_positive() {
        assertTrue(matrix.isVertex(1));
    }

    @Test
    public void testIsVertex_negative() {
        assertFalse(matrix.isVertex(4));
    }

    @Test
    public void testIsEdge_positive() {
        assertTrue(matrix.isEdge(1, 2));
    }

    @Test
    public void testIsEdge_negative() {
        assertFalse(matrix.isEdge(1, 4));;
    }

    @Test
    public void testGetOutgoingVertices() {
        assertThat(matrix.getOutgoingVertices(1), contains(2, 3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOutgoingVertices_thrownIllegalArgumentException() {
        matrix.getOutgoingVertices(4);
    }

    @Test
    public void testGetIncomingVertices() {
        assertThat(matrix.getIncomingVertices(3), contains(1, 2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetIncomingVertices_thrownIllegalArgumentException() {
        matrix.getIncomingVertices(4);
    }

    @Test
    public void testGetVerticesAsArray() {
        assertThat(matrix.getVerticesAsArray(), equalTo(new int[]{1, 2, 3}));
    }

    @Test
    public void testGetOutgoingVerticesAsArray() {
        assertThat(matrix.getOutgoingVerticesAsArray(1), equalTo(new int[]{2, 3}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOutgoingVerticesAsArray_thrownIllegalArgumentException() {
        matrix.getOutgoingVerticesAsArray(4);
    }

    @Test
    public void testGetIncomingVerticesAsArray() {
        assertThat(matrix.getIncomingVerticesAsArray(3), equalTo(new int[]{1, 2}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetIncomingVerticesAsArray_thrownIllegalArgumentException() {
        matrix.getIncomingVerticesAsArray(4);
    }

    @Test
    public void testVertexCount() {
        assertEquals(matrix.getVertexCount(), 3);
    }

    @Test
    public void testEdgeCount() {
        assertEquals(matrix.getEdgeCount(), 3);
    }

    @Test
    public void testgetOutgoingVerticesCount() {
        assertEquals(matrix.getOutgoingVerticesCount(1), 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOutgoingVerticesCount_thrownIllegalArgumentException() {
        matrix.getOutgoingVerticesCount(4);
    }

    @Test
    public void testgetIncomingVerticesCount() {
        assertEquals(matrix.getIncomingVerticesCount(3), 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetIncomingVerticesCount_thrownIllegalArgumentException() {
        matrix.getIncomingVerticesCount(4);
    }

    @Test
    public void testGetVerticesToArray() {
        int[] arr = new int[3];
        matrix.getVerticesToArray(arr);
        assertThat(arr, equalTo(new int[]{1, 2, 3}));
    }

    @Test
    public void testGetOutgoingVerticesToArray() {
        int[] arr = new int[2];
        matrix.getOutgoingVerticesToArray(1, arr);
        assertThat(arr, equalTo(new int[]{2, 3}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetOutgoingVerticesToArray_thrownIllegalArgumentException() {
        int[] arr = new int[0];
        matrix.getIncomingVerticesToArray(4, arr);
    }

    @Test
    public void testGetIncomingVerticesToArray() {
        int[] arr = new int[2];
        matrix.getIncomingVerticesToArray(3, arr);
        assertThat(arr, equalTo(new int[]{1, 2}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetIncomingVerticesToArray_thrownIllegalArgumentException() {
        int[] arr = new int[0];
        matrix.getIncomingVerticesToArray(4, arr);
    }
}