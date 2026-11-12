# RouteWise — Smart Bus Ticket Booking & Route Optimization System

> **Tagline:** Smart Journeys. Simpler Bookings.  
> **Backend:** Java 21 / Spring Boot 3.3.5 / Spring MVC / Spring Data JPA / MySQL 8 / BCrypt  
> **Frontend:** Responsive HTML5 / CSS3 / Vanilla JavaScript (Inter & Plus Jakarta Sans typography)  
> **Server URL:** `http://localhost:8085`  
> **Database:** `routewise_db` on MySQL 8 (`localhost:3306`)

---

## 1. Executive Summary & Design System

RouteWise is a modern, commercial-grade intercity bus ticket booking and highway route optimization platform. The system combines an elegant, calm, muted green and warm off-white user interface with real-time seat locking, dynamic transit corridor calculations, automated waitlist management, and fleet capacity optimization.

### Bespoke Visual Design Palette

| Element | Hex Code | Purpose |
| :--- | :--- | :--- |
| **Main Background** | `#F7F8F4` | Warm, calm off-white page backdrop |
| **Secondary Background** | `#F0F3EC` | Soft contrasting sections and strips |
| **Card Background** | `#FFFFFF` | Crisp white cards with soft shadows |
| **Primary Brand Green** | `#39865F` | Primary actions, brand icons, active states |
| **Primary Green Light** | `#E7EFE5` | Subtle chip backgrounds and active tabs |
| **Secondary Sage Green** | `#8FA98B` | Subtle badges and network accents |
| **Primary Text** | `#242A25` | Deep dark forest green-gray typography |
| **Secondary Text** | `#777F77` | Balanced neutral readable secondary labels |
| **Borders** | `#E4E8E0` | Subtle clean card and input borders |
| **Muted Peach Accent** | `#E8B9A5` | Booked seat state and VIP waitlist tags |

---

## 2. Platform Architecture & Modules

### A. Public Travelers Portal
1. **Homepage (`/`)**:
   - Modern hero section with route search widget (Origin, Swap button, Destination, Journey Date, Passenger count).
   - High-frequency popular express routes with live starting fares (Delhi ⇄ Chandigarh ₹650, Chandigarh ⇄ Shimla ₹480, Delhi ⇄ Jaipur ₹550, etc.).
   - RouteWise core advantages (Punctual Schedules, Real-Time Seat Choice, Verified Luxury Fleet, Instant PNR).
2. **Bus Search & Filtering (`/search`)**:
   - Live query modify bar.
   - Filter sidebar: Coach Type (Volvo Multi-Axle, AC Sleeper, Executive, Electric), Departure Timeslots (Morning, Afternoon, Evening/Night), Amenities (WiFi, USB Charging, Blankets).
   - Dynamic sorting bar (Earliest Departure, Lowest Fare, Fastest Travel, Available Seats).
   - Real-time seat availability counters.
3. **Interactive Bus Cabin Seat Selection (`/seat-selection`)**:
   - Realistic 2x2 luxury cabin layout with driver compartment, front door, and center aisle.
   - Visual seat states:
     - **Available:** White with subtle border (`#E4E8E0`).
     - **Selected:** Muted green (`#39865F`) with white text.
     - **Booked:** Soft muted peach (`#E8B9A5`).
     - **Reserved:** Neutral gray (`#CED4CE`).
   - Live summary panel updating selected seats, base fare, 5% GST & service charge, and total payable amount.
   - Passenger details input (Name, Email, Mobile, Age, Gender).
   - One-click checkout with simulated payment and instant PNR generation.
4. **Digital Boarding Pass & Confirmation (`/booking-confirmation`)**:
   - Print-ready e-ticket with barcode mockup, unique 6-character PNR (e.g. `RW-A8F2K9`), journey times, and seat numbers.
5. **PNR Status & Self-Service Cancellation (`/pnr-status`)**:
   - Retrieve any ticket instantly without logging in.
   - One-click cancellation with immediate seat release and refund computation.
6. **Smart Route Explorer (`/routes`)**:
   - Interactive SVG map displaying the North India 10-city transit network.
   - Dynamic path planner: calculates shortest distance, fastest travel time, or fewest intermediate hops using graph algorithms.
   - Visual path highlighting and intermediate highway stop breakdowns.

---

### B. Passenger Dashboard (`/passenger/dashboard`)
- Passenger profile and contact management.
- Complete booking history with PNR, coach details, journey times, and total fares.
- Direct links to view printable boarding passes or cancel active reservations.

---

### C. Administrator Operations Portal (`/admin/dashboard`)
- **Operations Dashboard:** Live KPI cards (Total Revenue ₹, Confirmed vs Cancelled Bookings, Fleet Count, Active Schedules, Seat Occupancy %).
- **Fleet Management (`/admin/buses`):** Add, update, inspect, and toggle active/maintenance status for Volvo, Scania, Mercedes-Benz, and BharatBenz coaches.
- **Route Corridors (`/admin/routes`):** Create highway routes and manage intermediate stops with halt times.
- **Daily Schedules (`/admin/schedules`):** Schedule journey runs, assign buses, set departure/arrival times, and auto-generate 30–40 seats per bus.
- **Bookings Audit (`/admin/bookings`):** Search all bookings across the platform by PNR or passenger name.
- **Priority Waitlist Manager (`/admin/waiting-list`):** Automated priority queue (Priority 3 = VIP/Emergency, Priority 2 = Senior/Medical, Priority 1 = Standard) with single-click seat promotion.
- **Fleet Capacity & Revenue Optimizer (`/admin/optimizer`):** Resource allocation simulator (0/1 and Unbounded Knapsack) for corporate bulk reservations and cargo hold utilization, plus live sorting algorithm benchmarks.

---

## 3. Technology Stack

- **Backend Framework:** Spring Boot 3.3.5
- **Language:** Java 21 (LTS)
- **Persistence:** Spring Data JPA / Hibernate ORM with HikariCP
- **Database:** MySQL 8.0 (`routewise_db`)
- **Security:** Spring Security Crypto (`BCryptPasswordEncoder`), Session-based authentication
- **Manual Data Structures & Algorithms:** Custom implementations in `com.routewise.dsa`
  - Sorting: `QuickSort`, `MergeSort`, `HeapSort`, `RadixSort`, `BucketSort`
  - Linear Data Structures: `CustomLinkedList`, `CustomDoublyLinkedList`, `CustomStack`, `CustomQueue`
  - Priority Queue: `CustomPriorityQueue` (Binary Max-Heap)
  - Hash Table: `CustomHashTable` (Separate Chaining for PNR O(1) resolution)
  - Graph Engine: `TransportGraph` (Dijkstra, BFS, DFS, Prim, Kruskal with DSU, Bellman-Ford)
  - Dynamic Programming: `KnapsackOptimizer` (0/1 and Unbounded Knapsack)
  - Search Trees: `BinarySearchTree`, `AvlTree`

---

## 4. Demo Credentials

| Role | Email | Password | Access Level |
| :--- | :--- | :--- | :--- |
| **Administrator** | `admin@routewise.com` | `admin123` | Full access to Admin Operations, Fleet, Routes, Schedules, Bookings, Waitlist, and Optimizer |
| **Passenger** | `passenger@routewise.com` | `pass123` | Search buses, select seats, book journeys, view boarding passes, manage profile |

*(Demo accounts can also be logged into with a single click using the "One-Click Demo Accounts" buttons on `/login`.)*

---

## 5. Running the Application

### Prerequisites
1. Java 21+ (`java -version`)
2. Maven 3.8+ (`mvn -version`)
3. MySQL 8 running on `localhost:3306` with database `routewise_db`

### Quick Start
```bash
# 1. Navigate to project root
cd /Users/arman_ansari/.gemini/antigravity/scratch/routewise

# 2. Build the project
mvn clean package -DskipTests

# 3. Start the Spring Boot server
java -jar target/routewise-1.0.0.jar
```

Open your browser at:  
👉 **`http://localhost:8085`**
