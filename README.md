# Idle Investment Game

An offline-capable JavaFX idle game where you earn money by purchasing and upgrading virtual investments. XP and levels are gained as you progress, and the game tracks total stats across sessions — even when the app is closed.

## Features

-  Passive income through investments
-  Upgradable investments that increase payouts
-  XP system with level-ups
-  Stat tracking: total playtime, earnings, upgrades, and more
-  Save/load system with persistent data
-  Offline earnings based on real-time inactivity

---

##  How to Run

1. Requirements:
   - Java 17 or newer (JavaFX included or configured)
   - IDE (IntelliJ, Eclipse, or VS Code) or command line with proper JavaFX setup

2. Launch:
   - Run the `IdleGameUI.java` class (main application entry point)
   - Save data will be automatically loaded (or created if none exists)

---

##  File Overview

| File / Class         | Description |
|----------------------|-------------|
| `IdleGameUI.java`    | Main JavaFX UI logic and rendering |
| `Player.java`        | Stores balance, XP, owned investments, and overall logic |
| `XPManager.java`     | Handles XP accumulation and leveling logic |
| `StatTracker.java`   | Tracks total session time, earnings, upgrades, etc. |
| `GameEngine.java`    | Core loop that generates passive income every second |
| `SaveManager.java`   | Saves/loads player state (to a text file) |
| `OfflineManager.java`| Calculates earnings while the game was closed |
| `Investment.java`    | Abstract base class for income generators |
| `LemonadeStand.java` | Example concrete implementation of an investment |
| `InvestmentType.java`| Enum of all available investment types |
| `InvestmentFactory.java` | Creates instances based on the enum |

---

##  Stats Tracked

- **Total Play Time**
- **Total Earnings**
- **Total Upgrades**
- **Investments Purchased**
- **XP and Levels**

---

##  How Saving Works

Every time the game is closed:

- A save file (`save.txt`) is created in the `saves/` directory.
- This includes:
  - Balance
  - Investments and their levels
  - XP and level data
  - Playtime and offline timestamps
  - All tracked stats

---

##  How Offline Earnings Work

1. When the app closes, it saves the current timestamp.
2. On the next launch, the game:
   - Calculates how many seconds have passed since last run
   - Estimates how much money each investment would have earned
   - Adds this to your balance
   - Updates your earnings stat

---

##  XP and Leveling

- Earn XP by buying and upgrading investments
- Level up when XP threshold is reached
- XP needed per level is calculated as:  
  `xpToNextLevel = 50 * level^2`
- XP bar dynamically shows progress

---

##  Planned Improvements

- Dynamic investment unlocking system
- Animated progress indicators
- Better UI/UX and game balancing
- Sound effects and mobile layout support
- Leaderboard and persistent account system

---

##  Author Notes

This project is a personal learning sandbox — an evolving experiment in:

- Object-Oriented Programming (OOP)
- JavaFX UI building
- Game logic structuring
- Persistent save systems
- Real-world stat tracking
- Clean project organization
