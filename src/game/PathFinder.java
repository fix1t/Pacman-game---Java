package src.game;

import src.tool.common.CommonField;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * A class that finds the shortest path between two fields in a maze.
 * The algorithm used is the A* algorithm.
 * The heuristic used is the Manhattan distance.
 * The algorithm is based on the pseudocode found on Wikipedia:
 * https://en.wikipedia.org/wiki/A*_search_algorithm#Pseudocode
 */
class PathFinder {
  // Helper class to represent a node in the A* algorithm
  static class Node {
    CommonField field;
    Node parent;
    int gCost;
    int hCost;

    Node(CommonField field, Node parent, int gCost, int hCost) {
      this.field = field;
      this.parent = parent;
      this.gCost = gCost;
      this.hCost = hCost;
    }

    int fCost() {
      return gCost + hCost;
    }
  }

  private static CommonField.Direction getDirectionTo(CommonField startField, CommonField neighborField) {
    int dx = neighborField.getCoordinate().getX() - startField.getCoordinate().getX();
    int dy = neighborField.getCoordinate().getY() - startField.getCoordinate().getY();

    if (dx == 1 && dy == 0) {
      return CommonField.Direction.DOWN;
    } else if (dx == -1 && dy == 0) {
      return CommonField.Direction.UP;
    } else if (dx == 0 && dy == 1) {
      return CommonField.Direction.RIGHT;
    } else if (dx == 0 && dy == -1) {
      return CommonField.Direction.LEFT;
    } else {
      return CommonField.Direction.STOP;
    }
  }

  /**
   * Calculates the Manhattan distance between two fields.
   *
   * @param a the first field.
   * @param b the second field.
   * @return the Manhattan distance between the two fields.
  */
  private static int manhattanDistance(CommonField a, CommonField b) {
    return Math.abs(a.getCoordinate().getX() - b.getCoordinate().getX()) + Math.abs(a.getCoordinate().getY() - b.getCoordinate().getY());
  }

  /**
   * Finds the shortest path direction from the start field to the destination field using the A* algorithm.
   *
   * @param startField      the start field.
   * @param destinationField the destination field.
   * @return the direction of the shortest path from the start to the destination.
   */
  public CommonField.Direction findShortestPathDirection(CommonField startField, CommonField destinationField) {
    // Initialize data structures for the A* algorithm
    PriorityQueue<Node> openList = new PriorityQueue<>((a, b) -> {
      int comparison = Integer.compare(a.fCost(), b.fCost());
      if (comparison != 0) {
        return comparison;
      } else {
        return Integer.compare(a.hCost, b.hCost);
      }
    });
    Set<CommonField> closedList = new HashSet<>();

    // Add the start node to the open list
    openList.add(new Node(startField, null, 0, manhattanDistance(startField, destinationField)));

    // Main A* loop
    while (!openList.isEmpty()) {
      Node currentNode = openList.poll();
      CommonField currentField = currentNode.field;

      // Check if the destination is reached
      if (currentField.equals(destinationField)) {
        // Backtrack to find the first move direction
        Node previousNode = currentNode.parent;
        while (previousNode != null && !previousNode.field.equals(startField)) {
          currentNode = previousNode;
          previousNode = currentNode.parent;
        }

        // Return the direction of the first move
        return getDirectionTo(startField, currentNode.field);
      }

      // Mark the current node as visited
      closedList.add(currentField);

      // Loop through the neighbors
      for (CommonField.Direction direction : CommonField.Direction.values()) {
        CommonField neighborField = currentField.nextField(direction);

        // Ignore wall fields, out of bounds fields, and fields in the closed list
        if (neighborField == null || !neighborField.canMove() || closedList.contains(neighborField)) {
          continue;
        }

        int tentativeGCost = currentNode.gCost + 1;
        Node neighborNode = new Node(neighborField, currentNode, tentativeGCost, manhattanDistance(neighborField, destinationField));

        // Check if the node is already in the open list with a lower fcost
        boolean shouldSkipNode = false;
        for (Node nodeInOpenList : openList) {
          if (nodeInOpenList.field.equals(neighborField) && nodeInOpenList.fCost() <= neighborNode.fCost()) {
            shouldSkipNode = true;
            break;
          }
        }
        if (shouldSkipNode) {
          continue;
        }

        // Add the neighbor node to the open list
        openList.add(neighborNode);
      }
    }

    // If the destination is not reachable, return STOP
    return CommonField.Direction.STOP;
  }
}
