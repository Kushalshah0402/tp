# Wang Zaixi - Project Portfolio Page

## Overview

**Finbro** is a command-line personal finance tracker designed to help users manage their expenses efficiently. It
allows users to record expenses, monitor spending habits, set financial limits, and perform currency conversions.

My contributions focused on enhancing usability and flexibility through the implementation of the **Edit Limit**,
**Currency Conversion**, and **Natural Date Parsing** features. These improvements make the application more intuitive
and user-friendly by allowing flexible financial adjustments and more natural user inputs.

---

## Summary of Contributions

### Code Contributed

**RepoSense Link:**  
[View detailed code contributions](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=WangZX2001&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2026-02-20T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=)

---

### Core Features Implemented

#### 1. Edit Limit Feature

* Implemented the `edit limit` command to allow users to modify their monthly spending limit.
* Designed an interactive workflow where users can:
    * Increase the limit
    * Decrease the limit
    * Replace the limit
* Added input validation:
    * Prevents negative values
    * Ensures numeric input only
* Implemented a confirmation step before applying changes to prevent accidental updates.
* Integrated logging (`java.util.logging`) for debugging and traceability.

**Impact:**

* Improves flexibility in budget management.
* Reduces user errors through validation and confirmation prompts.

---

#### 2. Currency Conversion Feature

* Implemented the `currency` command to convert expense values between currencies.
* Designed a guided interaction flow:
    * Input source currency
    * Input target currency
    * Select an expense entry
* Used a **local currency rate table** without relying on external APIs.
* Implemented conversion logic using SGD as the base currency.

**Impact:**

* Enables users to better understand expenses in different currencies.
* Works offline without reliance on external services.
* Improves portability and reliability of the application.

---
### Enhancement Implemented

#### 3. Natural Date Parsing Feature

* Designed and implemented a **Natural Date Parsing utility** to allow users to input dates in a flexible and
  human-readable format.
* Eliminates the need for strict date formatting by supporting common natural language expressions.

**Supported formats include:**

* Relative days:
    * `today`
    * `tomorrow`
    * `yesterday`
* Relative durations:
    * `2 days ago`
    * `3 days later`
    * `2 weeks ago`
    * `1 week later`
* Weekly references:
    * `last week`
    * `next week`
* Day-of-week references:
    * `last monday`
    * `next friday`
* Standard format fallback:
    * `yyyy-MM-dd`

**Implementation details:**

* Built using Java’s `LocalDate` API for accurate date handling.
* Utilised pattern matching (regex) to interpret relative time expressions.
* Leveraged `TemporalAdjusters` to compute previous/next weekdays.
* Converts all inputs into a standardized internal date format for consistency.
* Includes robust exception handling to provide clear feedback on invalid inputs.

**Impact:**

* Significantly improves user experience by allowing intuitive, human-readable date inputs.
* Reduces friction for users who may not remember strict date formats.
* Enhances overall usability of the application across multiple features.

---

### Contributions to User Guide

Added documentation for:

* **Edit Limit Command**
    * Detailed workflow and examples
    * Explanation of increase, decrease, and replace operations
    * Error handling and validation

* **Currency Command**
    * Step-by-step usage instructions
    * Input prompts and expected outputs
    * Limitations of offline exchange rates

* **Natural Date Input**
    * Explained supported formats such as `today`, `2 days ago`, and `last monday`
    * Provided examples of valid inputs
    * Clarified fallback to standard `yyyy-MM-dd` format

---

### Contributions to Developer Guide

* Documented the design and logic flow for the edit limit feature
* Explained validation and error handling strategies
* Added design considerations for:
    * Command structure
    * Separation of concerns between UI and logic
* Documented currency conversion design using base currency mapping
* Documented the Natural Date Parsing utility:
    * Use of `LocalDate` and Java time API
    * Regex-based parsing for relative expressions
    * Handling of weekday-based calculations using `TemporalAdjusters`

---

### Contributions to Team-Based Tasks

* Assisted in integrating features into the existing parser architecture
* Improved overall code consistency and validation handling
* Participated in testing and debugging across multiple modules
* Contributed to refining user interaction flows for better usability

---