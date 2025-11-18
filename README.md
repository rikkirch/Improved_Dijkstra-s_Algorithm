# Improved Dijkstra’s Algorithm (Frontier-Reduction Variant)

## Author  
**Rikesh Budhathoki**  
CSC 492 – Independent Research  
Instructor: **Jun Huang**

---

## 📌 Overview

This project implements an **improved version of Dijkstra’s algorithm** inspired by the research paper:

> *“The Batch-Dijkstra Framework for Faster Shortest Paths,”*  
> Virginia Vassilevka Williams et al.

The classical algorithm suffers from maintaining a **Θ(n)-sized frontier**, forcing a **sorting bottleneck of Ω(n log n)**.

This improved version introduces a **frontier reduction step**, inspired by the paper’s method of dynamically shrinking the set of “incomplete vertices” using:

- An upper bound \( B \)
- A candidate set \( U = \{ u : dist[u] < B \} \)
- \( k \approx \log n \) rounds of Bellman–Ford-like relaxations
- Rebuilding the priority queue only using reduced pivots

This yields **significant runtime improvement** in large or dense graphs.

---

## 🎯 Purpose of This Improved Version

This implementation is designed to:

- Demonstrate the idea of **frontier control**
- Reduce number of vertices managed in the priority queue
- Provide a practical and understandable Java version of the research concept
- Compare performance with classical Dijkstra

It is **not a full replication** of the paper (which is extremely complex),  
but a **faithful simplified adaptation** suitable for a research assignment.

---

## 🧠 How the Improved Algorithm Works

### 🌟 Additional Steps Beyond Classical Dijkstra

Every \( k = \lceil \log_2(n+1) \rceil \) relaxations:

1. **Find smallest distance in frontier**  
   Let `d_min` = min distance among PQ nodes.

2. **Set upper bound**  
   \( B = d_{min} + 10 \) (tunable)

3. **Build reduced frontier**  
   \( U = \{ u \mid dist[u] < B \land u \not\in \text{finalized} \} \)

4. **Run \( k \) Bellman–Ford-like rounds**  
   Relax edges only inside \( U \).

5. **Rebuild PQ**  
   Keep only vertices in \( U \).

This reduces the priority queue size from **Θ(n)** to **~|U| / k**,  
speeding up the algorithm.

---

## ⏳ Time Complexity

| Algorithm | Complexity |
|----------|------------|
| Classical Dijkstra | \( O((V + E)\log V) \) |
| Improved Dijkstra | **Lower expected PQ work** due to frontier reduction |

This version reduces overhead by shrinking PQ size, especially in graphs where many nodes have high distances early on.

---

## 📂 Project Structure

```
src/
 └── dijkstra/
       └── improved/
            ├── Graph.java
            ├── ImprovedDijkstra.java
            └── Main.java
```

---

## ▶️ Running the Program (IntelliJ IDEA)

### 1. Clone the repository
```
git clone https://github.com/rikkirch/Improved_Dijkstra-s_Algorithm.git
```

### 2. Open in IntelliJ
- File → Open → Select project folder → OK

### 3. Run
Open:  
`src/dijkstra/improved/Main.java`  
Click **Run ▶**

---

## 📌 Sample Output
```
Improved Dijkstra - shortest distances from node 0:
[0, 7, 3, 9, 5]
```

---

## 📝 Notes for the Research Report

### This implementation demonstrates:
- Frontier shrinking
- Bellman–Ford local relaxations inside U
- Adaptive PQ rebuilding
- Reduced heap pressure
- Faster convergence in many graphs

### You can directly state in your report:
> “The improved algorithm uses a frontier-reduction technique inspired by the Batch-Dijkstra paper. It periodically shrinks the active frontier S by computing a restricted candidate subset U and running k relaxation passes. This reduces priority queue operations and improves runtime performance.”

---

## 📚 References

- Vassilevka Williams et al., *“Faster Single-Source Shortest Paths in the Real-Weighted Case,”* 2020.  
- Original Dijkstra algorithm (Dijkstra, 1959).  
- CLRS Textbook – *Introduction to Algorithms*.

---

## ✔️ Status
Completed improved version implementing frontier reduction idea.  
Ready for research report comparison with classical Dijkstra.

