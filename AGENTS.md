# 1. Project Context
- **Domain:** Connect 4 game application (7x6 board grid, Red/Yellow tokens, alternating starting player).
- **Tech Stack:** Kotlin, Jetpack Compose.
- **Scope Restriction:** 100% Local PvP gameplay. The implementation of AI, bots, or PvE opponent logic is strictly FORBIDDEN.

# 2. Architecture & Constraints
- **Architectural Pattern:** Strict MVVM (Model-View-ViewModel).
- **Directory Structure:** The source code MUST enforce segregation into exactly three core directories: `/view`, `/viewmodel`, and `/model`.
- **Design Principles:** Code MUST comply with SOLID and GRASP principles. High cohesion and low coupling are required.
- **UI Standards:** MUST follow modern Android/Compose design practices. UI components (`@Composable`) must be stateless where possible, hoisting state and events upwards.
- **Security Constraint:** Hardcoding sensitive configuration data, tokens, or secrets in the repository is strictly FORBIDDEN.

# 3. Data Flow
- **Single Source of Truth (SSOT):** The `ViewModel` is the exclusive owner of the session state (board state, session win/loss counters, and current turn).
- **Persistence:** Data is strictly in-memory. Implementation of local databases (Room, SQLite) or persistent storage (DataStore, SharedPreferences) is FORBIDDEN.
- **State Mutation:** The `/view` layer is strictly passive. It MUST NOT mutate data. All UI interactions (dropping a token, restarting the match, resetting counters) MUST trigger explicit functions/intents within the `ViewModel`.
- **State Exposure:** The `ViewModel` MUST expose the UI state to the `/view` strictly through immutable, observable streams (e.g., `StateFlow`).

# 4. Git & Commits
- **Commit Convention:** All commit messages MUST strictly adhere to the Conventional Commits standard (e.g., `feat:`, `fix:`, `chore:`, `refactor:`).
- **Branching Model:** MUST follow Gitflow methodology (`feature/*`, `bugfix/*`, `release/*`, `hotfix/*`).
- **Branch Protection:** Direct commits or pushes to the `main` branch are strictly FORBIDDEN.
- **Integration Workflow:** All code changes MUST be integrated via Pull Requests (PRs). Automated merging into `main` is FORBIDDEN; human code review and validation are strictly required.