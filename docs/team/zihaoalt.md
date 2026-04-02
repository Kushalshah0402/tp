# Xu Zihao - Project Portfolio Page

## Overview

**Finbro** is a command-line personal finance tracker designed to help users manage their expenses efficiently. It
allows users to record expenses, monitor spending habits, set financial limits, and perform currency conversions.

---
## Summary of Contributions

### Code Contributed

**RepoSense Link:**  
[RepoSense link](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=zihaoalt&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2026-02-20T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=)

---

### Enhancements Implemented

#### 1. Delete Command

* Implemented the `delete` command for removing expenses from a specific category by index.
* Extended the feature beyond a simple one-line delete by supporting both:
    * **direct mode**: `delete <category> <number>`
    * **walkthrough mode**: `delete`
* Designed the walkthrough mode to reduce user mistakes through a guided flow:
    * prompt for category selection
    * allow `-l` to list all available categories
    * prompt for the expense index within the chosen category
    * allow `-l` to list all expenses under that category
    * require a final confirmation before deletion
* Added validation for blank input, non-numeric index input, invalid categories, and out-of-range indices.

**Impact:**

* Makes deletion safer and more user-friendly, especially when users do not remember the exact expense index.
* Reduces accidental deletion by combining guided input with a confirmation step.

---

#### 2. View by Category Feature

* Implemented support for `view <category>` so that users can inspect only the expenses under one category instead of always viewing the full list.
* Helped distinguish the behavior of `view all` and category-specific viewing more clearly.
* Improved handling of category-based lookup as part of the viewing workflow.

**Impact:**

* Makes expense tracking more practical because users often want to inspect one spending category at a time.
* Improves clarity of the output by reducing noise from unrelated expenses.

---

#### 3. General Help Command

* Implemented the general `help` command to display the list of valid commands supported by the application.
* Used the existing command structure so that help output remains consistent with the rest of the system.
* Contributed the user-facing guidance for this command in the documentation.

**Impact:**

* Improves user friendliness for new users.
* Reduces friction when users forget command names or formats.

---

### Contributions to User Guide

Added and refined documentation for:

* **Help Command**
    * documented the purpose of the `help` command
    * added usage format and examples

* **Delete Command**
    * documented both direct mode and walkthrough mode
    * explained the guided deletion flow, including the use of `-l`
    * added examples and confirmation behavior

---

### Contributions to Developer Guide

* Added documentation for the **Delete Command** implementation.
* Added and updated the **DeleteCommand sequence diagram** to explain the interaction between the user, UI, parser, command, and expense list during deletion.
* Helped document the design considerations behind the delete workflow, especially the guided interaction and validation steps.

---

### Contributions to Team-Based Tasks

* Helped maintain the overall OOP structure of the codebase while new features were being added.
* Contributed testing across modules, including unit tests for relevant command behavior.
* Helped identify bugs, inconsistencies, and mismatches between the planned feature behavior and the actual implementation.
* Reviewed team code and raised issues related to correctness, consistency, and feature integration.
