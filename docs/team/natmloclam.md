# Tan Yan Hao Malcolm - Project Portfolio Page

## Overview

**Finbro** is a command-line personal finance tracker designed to help users manage their expenses efficiently. It
allows users to record expenses, monitor spending habits, set financial limits, and perform currency conversions.


**Code Contributed**: [RepoSense link](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=natmloclam&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2026-02-20T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=)

---
## Core Features Implemented
### Set limit feature 

What it does: Allows user to set a monthly spending limit, after an additional confirmation step 

Justification: Enables users to better watch their spendings as they receive reminders as they are about to 
approach/exceed their monthly limit 

Highlights: Monthly limit is stored on disk, and is saved across Finbro sessions

### Visualization feature
What it does: Creates a bar graph that shows the user's spendings across different months 

Justification: Allows user to easily compare how much they are spending over time

### Logging
What it does: All logs are saved into `logs/finbro.log`

Justification: 
- Logging constantly keeps track of what the app does, making it easier for developers/users to identify the source of 
an error
- Storing the logs into a log file ensures the user's experience remains seamless

Highlights: Log messages follow the format: `[date, time] [log level] [Java class] [log details]`

---
## Enhancements Implemented
### Detailed Help Command 

What it does: Entering `help <command>` gives a more detailed help message on how to use the corresponding command 

Justification: Keeps the help messages short and easy to understand by providing only necessary information

### More robust storage loading

What it does: 
- Previously, a corrupted line in the storage file would throw an error, and no data would be read 
- Improved it such that if any line is corrupted, app would skip the corrupted line and continue loading the others
- If no/invalid limit is found in the storage file, initializes the limit to 0 

Justification: 
- If user accidentally makes changes to the storage file, they would not lose all the data they had previously input 

Highlights:
- No warning is thrown to the user if a corrupted line is found, but the corrupted line is logged in `logs/finbro.log`
- Had to consider many scenarios: 
  - Invalid limit data: initialize the default limit value 
  - First line is not limit: Treat it as an expense 
  - Expense data is invalid: Ignore line

---
## Documentation
### Contributions to UG 
- Added documentation for the following features: 
  - Setting limit 
  - Visualization

### Contributions to DG 
- Added details on architecture design 
  - Architecture Diagram 
  - Sequence diagram on architecture interaction 
- Added implementation details for setting limit using sequence diagram
- Added implementation details for visualization using sequence diagram
- Added user stories 

---
## Community
### Contributions to Team-Based Tasks
- Helped maintain the overall OOP structure of the codebase while new features were being added
  - Reformatted command classes and made code more SLAP, [PR #45](https://github.com/AY2526S2-CS2113-T10-4/tp/pull/45)
- Updated issue tracker on new features and bugs
- Added JUnit tests for the following classes: 
  - `Limit`, `SetLimitCommand`, `VisualCommand`, `ExpenseList`
- Helped to identify bugs, inconsistencies
  - Wrong help message for `edit limit`, [Issue #111](https://github.com/AY2526S2-CS2113-T10-4/tp/issues/111)
  - No checking when loading from storage, [Issue #87](https://github.com/AY2526S2-CS2113-T10-4/tp/issues/87)
  - Removing limit from storage causes all expenses to be deleted, [Issue #46](https://github.com/AY2526S2-CS2113-T10-4/tp/issues/46)
- Set up logging configuration 

### Review/mentoring Contributions
- PRs reviewed: [#30](https://github.com/AY2526S2-CS2113-T10-4/tp/pull/30), 
[#39](https://github.com/AY2526S2-CS2113-T10-4/tp/pull/30)
- Gave suggestion regarding budget warning service: to keep track of total expenses using a class attribute, instead 
of summing up all the expenses each time
