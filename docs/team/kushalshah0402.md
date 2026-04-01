---
layout: page
title: Kushal's Project Portfolio Page
---

### Project: FinBro

Finbro is a command-line personal finance management application designed to help users track expenses, monitor spending habits, and manage a monthly budget efficiently.

It is built using Java and follows a modular, command-based architecture, where user inputs are parsed into executable commands.

Given below are my contributions to the project.

* **New Feature**: Added the `add` command
  * What it does: Allows the user to record a new expense either via direct mode (single command with all parameters) or walkthrough mode (interactive prompts).
  * Justification: The dual-mode design improves usability by supporting both experienced users who prefer fast entry and new users who benefit from guided input.
  * Highlights:
    * Invalid dates, non-positive and non-number amounts are all met with applicable error messages and prompts the user with the correct format
    * Implementing the walkthrough mode required careful handling of sequential input validation with repeated prompts on invalid input. The confirmation step before saving reduces accidental entries.

* **New Feature**: Added the `view all` command
  * What it does: Retrieves all recorded expenses from the system and displays them in a structured, readable format — showing the amount, category, and date for each entry, along with a computed total expenditure at the end.
  * Justification: Users need a way to get a full overview of their spending history at a glance. Without this, there is no way to review past expenses in a single command.
  * Highlights: 
    * Handles the edge case where no expenses have been recorded yet, displaying an appropriate message instead of an empty or broken output
    * Total expenditure is computed dynamically by iterating through the expense list, so it is always accurate and up to date
    * Display logic is fully handled by `Ui` keeping `ViewCommand` focused solely on retrieving the data, which follows the separation of concerns principle


* **New Feature**: Added the `Storage` component
  * What it does: Handles persistent loading and saving of expenses and budget limits to a local `.txt` file. On startup, expenses and the budget limit are loaded automatically. After every command, the updated data is saved.
  * Justification: Persistence is a core requirement — without it, all expense records would be lost every time the app closes. The storage layer ensures data survives across sessions without requiring a database.
  * Highlights: 
    * Implemented corruption handling — invalid or malformed lines are logged and skipped rather than crashing the app
    * The budget limit is stored as the first line in a special `LIMIT | <value>` format, and is read separately before expenses are processed
    * If the data file does not exist on first launch, the app gracefully creates a new one instead of throwing an error
    * Amount validation is enforced during loading to prevent negative or zero values from being loaded into the system

* **Code contributed**: [RepoSense link](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=kushalshah0402&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2026-02-20T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=)

* **Enhancements implemented**:
  * Enhanced budget limit feedback after adding an expense (Pull request #32)
    * What it does: After every expense is added, the system checks the user's total spending against their set budget limit and warns them if they are close to or have exceeded it.
    * Justification: Previously, users had no immediate feedback on their budget status after adding an expense. This enhancement makes the app proactive rather than passive, helping users stay aware of their spending in real time.
    * Highlights:
      * Two distinct warning messages are shown depending on whether the user is approaching or has already exceeded the limit
      * The exact limit amount is displayed in the warning so users know precisely what threshold they are being measured against
      * Logic is cleanly separated — `Ui` handles the display while the budget checking logic lives outside of `Ui`, following separation of concerns

* **Documentation**:
  * User Guide:
    * Added documentation for the `view` command (Pull request [#93](https://github.com/AY2526S2-CS2113-T10-4/tp/pull/93))
    * Added documentation for the `add` command (Pull request [#64](https://github.com/AY2526S2-CS2113-T10-4/tp/pull/64))
  * Developer Guide:
    * Added implementation details for the `view` command including the UML sequence diagram (Pull request [#94](https://github.com/AY2526S2-CS2113-T10-4/tp/pull/94))
    * Added implementation details for the `add` command including the UML sequence diagram (Pull request [#92](https://github.com/AY2526S2-CS2113-T10-4/tp/pull/92))

* **Contributions to team-based tasks**:
  * Set up the GitHub team organisation and repository — forked the repo 
  * enabled and configured the issue tracker with custom labels, created milestones, added team members, set up the team PR 
  * configured Gradle
  * Managed releases — created the JAR file for the team demo and published the v1.0 release on GitHub
  * Maintained developer documentation not specific to a feature — wrote the Storage component section in the Developer Guide, covering the load/save operations, file format, and design considerations

* **Community**: