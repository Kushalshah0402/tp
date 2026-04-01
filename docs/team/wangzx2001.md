# Wang Zaixi - Project Portfolio Page

## Overview

**Finbro** is a command-line personal finance tracker designed to help users manage their expenses efficiently. It
allows users to record expenses, monitor spending habits, set financial limits, and perform currency conversions.

My primary contributions focused on enhancing financial management capabilities by implementing the **Edit Limit** and *
*Currency Conversion** features. These features improve usability by allowing users to dynamically adjust their budget
and view expenses across different currencies.

---

## Summary of Contributions

### Code Contributed

**RepoSense Link:
** [View detailed code contributions](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=WangZX2001&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2026-02-20T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=)

---

### Enhancements Implemented

#### 1. Edit Limit Feature

* Implemented the `edit limit` command to allow users to modify their monthly spending limit.
* Designed an interactive workflow where users can:
    - Increase the limit
    - Decrease the limit
    - Replace the limit
* Added input validation:
    - Prevents negative values
    - Ensures numeric input only
* Implemented confirmation step before applying changes to prevent accidental updates.
* Integrated logging (`java.util.logging`) for debugging and traceability.

**Impact:**

* Improves flexibility in budget management.
* Reduces user errors through validation and confirmation prompts.

---

#### 2. Currency Conversion Feature

* Implemented the `currency` command to convert expense values between currencies.
* Designed a guided interaction flow:
    - Input source currency
    - Input target currency
    - Select an expense entry
* Used a **local currency rate table** (no external API dependency).
* Implemented conversion logic using SGD as the base currency.

**Impact:**

* Enables users to better understand expenses in different currencies.
* Works offline without reliance on external services.

---

### Contributions to User Guide

Added documentation for:

* **Edit Limit Command**
    - Detailed behaviour and workflow
    - Example interactions with confirmation step
    - Error handling explanations

* **Currency Command**
    - Step-by-step usage
    - Input prompts and expected outputs
    - Limitations of local exchange rates

---

### Contributions to Developer Guide

* Documented logic flow for `EditLimitCommand`
* Explained validation and error handling mechanisms
* Added design considerations for:
    - Command structure
    - Separation of UI and logic
* Documented currency conversion approach using base currency mapping

---

### Contributions to Team-Based Tasks

* Assisted in integrating new commands into the existing parser structure
* Helped improve overall code consistency and error handling
* Participated in testing and debugging features across modules

---

## Reflection

This project strengthened my understanding of:

* Object-Oriented Programming (OOP) design in Java
* Command pattern implementation
* Input validation and error handling
* Designing user-friendly CLI interactions

I also improved my ability to:

* Write clear technical documentation
* Work collaboratively using Git and pull requests
* Debug and structure medium-scale software systems