# Improved Dijkstra's Algorithm (Priority Queue Version)

**Author:** Rikesh Budhathoki  
**Course:** CSC 492  
**Instructor:** Jun Huang  

This project implements the **optimized Dijkstra’s Algorithm** using a **Min Priority Queue (Binary Heap)** via Java’s `PriorityQueue`.

## 1. Overview

This improved version replaces the linear scan with a priority queue, producing far better performance on larger & sparse graphs.

## 2. Time & Space Complexity

| Version | Data Structure | Complexity |
|--------|----------------|------------|
| Classical | Linear scan | **O(V²)** |
| Improved | PriorityQueue | **O((V+E) log V)** |

## 3. Project Structure

```
src/
  dijkstra/
    improved/
      Main.java
      Graph.java
      ImprovedDijkstra.java
```

## 4. How to Clone

```bash
git clone https://github.com/rikkirch/Improved_Dijkstra-s_Algorithm.git
cd Improved_Dijkstra-s_Algorithm
```

## 5. Run in IntelliJ

Open → run `Main.java`.

Expected output:

```
Improved Dijkstra - shortest distances:
[0, 7, 3, 9, 5]
```

## 6. Modify Graph

Edit edges inside `Main.java`.
